package project.shopping.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.shopping.domain.Member;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 회원 리포지터리
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager entityManager;

    public void save(Member member) {
        entityManager.persist(member);
    }

    public  Member findOne(Long id) {
        return entityManager.find(Member.class, id);
    }

    public List<Member> findAll() {
        return entityManager.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByUserId(String userId) {
        return entityManager.createQuery("select m from Member m where m.userId = :userId", Member.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public Optional<Member> findByOneUserId(String userId) {
        return findAll().stream().filter(m->m.getUserId().equals(userId)).findAny();
    }

    public void delete(Member member) {
        entityManager.remove(member);
    }

    public Long count() {
        return findAll().stream().count();
    }

    public int getEndPage(int viewMembers) {
        int count = count().intValue();
        return count%viewMembers==0?count/viewMembers:count/viewMembers+1;
    }

    public List<Member> getPageMember(int firstResult, int maxResult) {
        return entityManager.createQuery("select m from Member m order by m.id asc", Member.class)
                .setFirstResult(firstResult)
                .setMaxResults(maxResult)
                .getResultList();
    }
}
