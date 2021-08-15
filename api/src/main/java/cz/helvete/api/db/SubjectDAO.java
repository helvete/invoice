package cz.helvete.api.db;

import cz.helvete.api.db.entity.Subject;
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
                "SELECT s FROM Subject i",
                Subject.class)
            .getResultList();
    }
}
