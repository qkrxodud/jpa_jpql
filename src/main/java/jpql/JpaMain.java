package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {


    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // 애플리케이션 에서 한개만 만들어 져야된다.

        EntityManager em = emf.createEntityManager(); //하나의 단위를 만들때마다 만들어 줘야된다.

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("TeamA");
            member1.setAge(10);
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("TeamA");
            member2.setTeam(teamA);
            member2.setAge(10);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("TeamB");
            member3.setTeam(teamB);
            member3.setAge(10);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select m.username FROM Team t join t.memberList m";
            //String query = "select m FROM Member m";
            // From 절에서 명시적 조인을 통해서 별칭을 얻으면 별칭을 통해 탐색 가능.

            List<Member> resultList = em.createQuery(query, Member.class)
                    .getResultList();
            System.out.println(resultList.size());
            for (Member member : resultList) {
                System.out.println("Member = " + member.getUsername() + ", "+ member.getTeam().getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }


}
