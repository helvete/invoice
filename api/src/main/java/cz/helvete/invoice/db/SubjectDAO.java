package cz.helvete.invoice.db;

import cz.helvete.invoice.db.entity.Subject;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class SubjectDAO {

    @Inject
    private EntityManager em;

    public Subject findById(Integer id) {
        return em.createQuery(
                "SELECT s FROM Subject s" +
                " WHERE s.id = :id" +
                "  AND s.deletedAt IS NULL",
                Subject.class)
            .setParameter("id", id)
            .getResultList()
            .stream()
            .findFirst()
            .orElse(null);
    }

    public List<Subject> getAll() {
        return em.createQuery(
                "SELECT s FROM Subject s",
                Subject.class)
            .getResultList();
    }

    public Subject persist(Subject subject) {
        em.persist(subject);
        em.flush();
        return subject;
    }

    public Subject merge(Subject subject) {
        subject = em.merge(subject);
        em.flush();
        return subject;
    }
}
