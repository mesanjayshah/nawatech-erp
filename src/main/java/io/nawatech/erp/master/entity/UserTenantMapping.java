package io.nawatech.erp.master.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_tenant_mapping")
public class UserTenantMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(name = "tenant_id")
    private String tenantId;

}