package project.shopping.repository;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.shopping.domain.Member;

import java.util.List;


class MemberRepositoryTest {


    @Autowired MemberRepository memberRepository;

    @Test
    void findByUserId() {

        String userId = "member1";
//        Member member = memberRepository.findByOneUserId(userId);

//        Assertions.assertThat(member.getUserId()).isEqualTo(userId);


//        String userId = "member1";
//        String temp = null;
//        List<Member> memberList = entityManager.createQuery("select m from Member m", Member.class).getResultList();
//
//        for (Member member : memberList) {
//            System.out.println("member = " + member);
//            if (member.getUserId().equals(userId)) {
//                temp = member.getUserId();
//            }
//        }
//
//        memberRepository.findByUserId(userId)
//
//        Member member = memberList.stream().filter(m -> m.getUserId().equals(userId)).findAny().get();
//
//        Assertions.assertThat(temp).isEqualTo(member.getUserId());
    }

}