package com.gmail.ek.chernyavskaya.repository.impl;

import com.gmail.ek.chernyavskaya.repository.ItemRepository;
import com.gmail.ek.chernyavskaya.repository.model.Item;
import com.gmail.ek.chernyavskaya.repository.model.Pagination;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ItemRepositoryImpl extends GenericDaoImpl<Long, Item> implements ItemRepository {
    @Override
    public List<Item> findAllWithPagination(Pagination pagination) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaQuery = criteriaBuilder.createQuery(Item.class);
        Root<Item> root = criteriaQuery.from(Item.class);
        criteriaQuery.select(root).orderBy(criteriaBuilder.asc(root.get("name")));
        TypedQuery<Item> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pagination.getStartElement());
        typedQuery.setMaxResults(pagination.getElementsPerPage());
        return typedQuery.getResultList();
    }
}