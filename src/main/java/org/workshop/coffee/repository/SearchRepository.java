package org.workshop.coffee.repository;

import org.workshop.coffee.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.List;
import java.util.Locale;

@Repository
public class SearchRepository {

    @Autowired
    EntityManager em;

    @Autowired
    DataSource dataSource;

    public List<Product> searchProduct (String input) {
        String search = "SELECT p FROM Product p WHERE lower(p.name) LIKE lower(:input) OR lower(p.description) LIKE lower(:input)";
        return em.createQuery(search, Product.class)
                .setParameter("input", "%" + input + "%")
                .getResultList();
    }

    public List<Product> filterProducts(String type, Double minPrice, Double maxPrice) {
        StringBuilder queryBuilder = new StringBuilder("SELECT p FROM Product p WHERE 1 = 1");

        if (type != null && !type.isBlank()) {
            queryBuilder.append(" AND p.productType = :type");
        }
        if (minPrice != null) {
            queryBuilder.append(" AND p.price >= :minPrice");
        }
        if (maxPrice != null) {
            queryBuilder.append(" AND p.price <= :maxPrice");
        }

        var query = em.createQuery(queryBuilder.toString(), Product.class);

        if (type != null && !type.isBlank()) {
            query.setParameter("type", org.workshop.coffee.domain.ProductType.valueOf(type.toUpperCase()));
        }
        if (minPrice != null) {
            query.setParameter("minPrice", minPrice);
        }
        if (maxPrice != null) {
            query.setParameter("maxPrice", maxPrice);
        }

        return query.getResultList();
    }

}
