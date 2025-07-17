package io.nawatech.erp.domain.product;

import io.nawatech.erp.domain.audit.entitychange.AuditableEntity;
import io.nawatech.erp.domain.audit.entitychange.EntityChangeEventListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@DynamicUpdate
@EntityListeners(EntityChangeEventListener.class)
public class Product extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private boolean enabled = true;
    private boolean deleted = false;
}
