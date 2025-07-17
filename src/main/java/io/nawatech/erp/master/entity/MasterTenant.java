package io.nawatech.erp.master.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MasterTenant {

    @Id
    private String tenantId;

    private String dbUrl;

    private String dbUsername;

    private String dbPassword;

    public MasterTenant() {}

    public MasterTenant(String tenantId, String dbUrl, String dbUsername, String dbPassword) {
        this.tenantId = tenantId;
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

}
