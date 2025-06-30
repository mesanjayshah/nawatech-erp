package io.nawatech.erp.department;

import lombok.Data;

@Data
public class DepartmentDTO {

    private Long id;

    private String name;

    private String brand_logo;

    private boolean isEnabled = false;

}
