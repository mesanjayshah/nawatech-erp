package io.nawatech.erp.product;

import io.nawatech.erp.logs.audit.AuditableEntity;
import io.nawatech.erp.logs.audit.AuditingEntityListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Product extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private boolean enabled = true;
    private boolean deleted = false;
}
