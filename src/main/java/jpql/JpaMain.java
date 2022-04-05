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
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("TeamA");
            member.setType(MemberType.ADMIN);
            member.setAge(10);

            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m.username, 'HELLO', true" +
                            " From Member m " +
                            "where m.type = :userType";
            List<Object[]> resultList = em.createQuery(query)
                    .setParameter("userType", MemberType.ADMIN)
                    .getResultList();
            System.out.println(resultList.size());

            for (Object[] objects : resultList) {
                System.out.println(objects[0]);
                System.out.println(objects[1]);
                System.out.println(objects[2]);

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
