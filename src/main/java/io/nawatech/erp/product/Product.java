package io.nawatech.erp.product;

import io.nawatech.erp.logs.audit.AuditingEntityListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private boolean enabled = true;
    private boolean deleted = false;
}
