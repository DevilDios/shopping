package project.shopping.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shopping.domain.Member;
import project.shopping.repository.MemberRepository;


/**
 * 로그인 서비스
 */
@RequiredArgsConstructor
@Service
public class LoginService {

    public final MemberRepository memberRepository;

    //로그인
    @Transactional(readOnly = true)
    public Member login(String userId, String password) {
       return memberRepository.findByOneUserId(userId).filter(m -> m.getPassword().equals(password)).orElse(null);
    }
}
