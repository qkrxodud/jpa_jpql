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

            Member member1 = new Member();
            member1.setUsername("TeamA");
            member1.setAge(10);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("TeamA");
            member2.setAge(10);

            em.persist(member2);

            em.flush();
            em.clear();

            String query = "select function('group_concat', m.username) FROM Member m";
            List<String> resultList = em.createQuery(query, String.class)
                    .getResultList();
            System.out.println(resultList.size());
            for (String s : resultList) {
                System.out.println(s);
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
