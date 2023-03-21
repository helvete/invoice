package cz.helvete.invoice.db;

import cz.helvete.invoice.db.entity.Invoice;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class InvoiceDAO {

    @Inject
    private EntityManager em;

    // TODO: fix ordering
    public Invoice findById(Integer id) {
        return em.createQuery(
                "SELECT i FROM Invoice i" +
                " LEFT JOIN i.items t" +
                " WHERE i.id = :id" +
                "  AND i.deletedAt IS NULL" +
                " ORDER BY t.ordering DESC",
                Invoice.class)
            .setParameter("id", id)
            .getResultList()
            .stream()
            .findFirst()
            .orElse(null);
    }

    public List<Invoice> getAll() {
        return em.createQuery(
                "SELECT i FROM Invoice i ORDER BY i.issuedAt ASC",
                Invoice.class)
            .getResultList();
    }

    public Invoice persist(Invoice invoice) {
        em.persist(invoice);
        em.flush();
        return invoice;
    }

    public Invoice merge(Invoice invoice) {
        invoice = em.merge(invoice);
        em.flush();
        return invoice;
    }
}
