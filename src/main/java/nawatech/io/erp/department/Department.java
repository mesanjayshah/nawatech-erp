package nawatech.io.erp.department;

import nawatech.io.erp.base.AuditorEntityAdmin;
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
