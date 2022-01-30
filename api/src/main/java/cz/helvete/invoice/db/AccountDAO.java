package cz.helvete.invoice.db;

import cz.helvete.invoice.db.entity.Account;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class AccountDAO {

    @Inject
    private EntityManager em;

    public Account findByEmail(String email) {
        return em.createQuery(
                "SELECT a FROM Account a" +
                " WHERE a.email = :email" +
                "  AND a.deletedAt IS NULL",
                Account.class)
            .setParameter("email", email)
            .getResultList()
            .stream()
            .findFirst()
            .orElse(null);
    }
}
