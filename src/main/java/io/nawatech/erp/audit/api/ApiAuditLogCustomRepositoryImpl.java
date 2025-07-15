package io.nawatech.erp.audit.api;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ApiAuditLogCustomRepositoryImpl implements ApiAuditLogCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<ApiAuditLog> search(ApiAuditLogSearchRequest req) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ApiAuditLog> cq = cb.createQuery(ApiAuditLog.class);
        Root<ApiAuditLog> root = cq.from(ApiAuditLog.class);

        List<Predicate> predicates = new ArrayList<>();

        if (req.getPrincipal() != null)
            predicates.add(cb.equal(root.get("principal"), req.getPrincipal()));

        if (req.getEventType() != null)
            predicates.add(cb.equal(root.get("eventType"), req.getEventType()));

        if (req.getIpAddress() != null)
            predicates.add(cb.equal(root.get("ipAddress"), req.getIpAddress()));

        if (req.getFrom() != null && req.getTo() != null)
            predicates.add(cb.between(root.get("requestTime"), req.getFrom(), req.getTo()));

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(root.get("requestTime")));

        TypedQuery<ApiAuditLog> query = em.createQuery(cq);
        query.setFirstResult(req.getPage() * req.getSize());
        query.setMaxResults(req.getSize());

        List<ApiAuditLog> logs = query.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<ApiAuditLog> countRoot = countQuery.from(ApiAuditLog.class);
        countQuery.select(cb.count(countRoot)).where(predicates.toArray(new Predicate[0]));
        Long count = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(logs, PageRequest.of(req.getPage(), req.getSize()), count);
    }
}
