package io.nawatech.erp.mtenant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "master_tenant")
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
