package project.shopping.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shopping.domain.Member;
import project.shopping.repository.MemberRepository;
import project.shopping.web.page.Page;

import java.util.ArrayList;
import java.util.List;


/**
 * 회원 서비스
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void join(Member member) throws IllegalStateException {
        List<Member> members = memberRepository.findByUserId(member.getUserId());
        if (!members.isEmpty()) {
            throw new IllegalStateException("이미 가입되어있는 회원입니다.");
        }
        memberRepository.save(member);
    }

    //회원 userID 조회
    @Transactional(readOnly = true)
    public Member findByOneUserId(String userId) {
        return memberRepository.findByOneUserId(userId).orElse(null);
    }

    //회원 전체 조회
    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    //회원 pk 조회
    @Transactional(readOnly = true)
    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }

    //회원 수정
    @Transactional
    public void updateMember(String userId, String password, String name, String address, String phone) throws IllegalStateException{
        Member member = memberRepository.findByOneUserId(userId).orElseThrow(() -> new IllegalStateException("member update fail"));
        member.memberModifyInit(password, name, address, phone);
    }

    //회원 삭제
    @Transactional
    public void delete(Long id) {
        Member member = memberRepository.findOne(id);
        if (member == null) {
            return;
        }
        memberRepository.delete(member);
    }

    //마지막 페이지
    @Transactional(readOnly = true)
    public int getLastPage(int listNum) {
        int count = memberRepository.count().intValue();
        return (count % listNum == 0) ?
                count/listNum : count/listNum+1;
    }

    //페이징 회원 리스트
    @Transactional(readOnly = true)
    public List<Member> getPageMember(int firstResult, int maxResult) {
        return memberRepository.getPageMember(firstResult, maxResult);
    }

}
