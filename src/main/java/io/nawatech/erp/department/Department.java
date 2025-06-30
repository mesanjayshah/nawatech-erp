package io.nawatech.erp.department;

import io.nawatech.erp.base.AuditorEntityAdmin;
import jakarta.persistence.Entity;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department extends AuditorEntityAdmin {

    @NonNull
    private String name;

    private String brand_logo;

}
