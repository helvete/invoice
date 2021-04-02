package cz.helvete.api.db;

import cz.helvete.api.db.entity.Invoice;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class InvoiceDAO {

    @Inject
    private EntityManager em;

    public Invoice findById(Integer id) {
        return em
            .createQuery("SELECT i FROM Invoice i WHERE i.id = :id", Invoice.class)
            .setParameter("id", id)
            .getResultList()
            .stream()
            .findFirst()
            .orElse(null);
    }

    public List<Invoice> getAll() {
        return em.createQuery("SELECT i FROM Invoice i", Invoice.class).getResultList();
    }
}
