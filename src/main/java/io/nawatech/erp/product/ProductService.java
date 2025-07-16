package io.nawatech.erp.product;

import io.nawatech.erp.audit.entitychange.TrackEntityChange;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @TrackEntityChange
    @Transactional
    public Product saveUpdate(Product productInput) {
        Product product;

        if (productInput.getId() == null || productInput.getId() == 0L) {

            product = productRepository.save(productInput);
        } else {
            product = entityManager.find(Product.class, productInput.getId());
            if (product == null) throw new RuntimeException("Product not found");

            product.setName(productInput.getName());
            product.setPrice(productInput.getPrice());
            product.setEnabled(productInput.isEnabled());
            product.setDeleted(productInput.isDeleted());
        }

/*        if (auditProperties.isBusinessEventsEnabled()) {
            AuditLog log = new AuditLog();
            log.setPrincipal(getCurrentUsername());
            log.setEventType(isNew ? AuditEventType.PRODUCT_CREATE : AuditEventType.PRODUCT_UPDATE);
            log.setRequestTime(LocalDateTime.now());
            log.setRequestUri("/product/" + (isNew ? "create" : "edit/" + product.getId()));
            auditLogService.saveAuditLog(log);
        }*/

/*
        if(auditProperties.isFieldDiffsEnabled()) {
            // ✅ Force Hibernate to flush updates to DB *now*:
            entityManager.flush();
            // ✅ Now @PreUpdate must have run, so AuditContext is filled.
            List<AuditLogDetailInfo> logs = AuditLogContext.getAndClear();
            if (!logs.isEmpty()) {
                auditLogDetailInfoRepository.saveAll(logs);
            }
        }
*/

        return product;
    }

    public Product getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

/*        if (auditProperties.isBusinessEventsEnabled()) {
            AuditLog log = new AuditLog();
            log.setPrincipal(getCurrentUsername());
            log.setEventType(AuditEventType.PRODUCT_VIEW);
            log.setRequestTime(LocalDateTime.now());
            log.setRequestUri("/product/" + id);
            auditLogService.saveAuditLog(log);
        }*/

        return product;
    }

    public Iterable<Product> getAllProducts() {
        // Optional: add audit log for listing
/*        if (auditProperties.isBusinessEventsEnabled()) {
            AuditLog log = new AuditLog();
            log.setPrincipal(getCurrentUsername());
            log.setEventType(AuditEventType.PRODUCT_LIST);
            log.setRequestTime(LocalDateTime.now());
            log.setRequestUri("/product");
            auditLogService.saveAuditLog(log);
        }*/
        return productRepository.findByDeletedFalse();
    }

    @TrackEntityChange
    @Transactional
    public void deleteProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setDeleted(true);

/*        if (auditProperties.isBusinessEventsEnabled()) {
            // ✅ Hibernate sees it as UPDATE — your @PreUpdate fires.
            entityManager.flush();

            var logs = AuditLogContext.getAndClear();
            if (!logs.isEmpty()) {
                auditLogDetailInfoRepository.saveAll(logs);
            }
        }

        if (auditProperties.isBusinessEventsEnabled()) {
            AuditLog log = new AuditLog();
            log.setPrincipal(getCurrentUsername());
            log.setEventType(AuditEventType.PRODUCT_DELETE);
            log.setRequestTime(LocalDateTime.now());
            log.setRequestUri("/product/delete/" + id);
            auditLogService.saveAuditLog(log);
        }*/
    }

}
