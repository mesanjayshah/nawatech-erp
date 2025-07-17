package io.nawatech.erp.master.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "permission_template")
public class PermissionTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public PermissionTemplate(String name) {
        this.name = name;
    }
}