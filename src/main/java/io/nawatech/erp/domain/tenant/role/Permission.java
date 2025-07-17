package io.nawatech.erp.domain.tenant.role;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Permission(String name) {
        this.name = name;
    }
}