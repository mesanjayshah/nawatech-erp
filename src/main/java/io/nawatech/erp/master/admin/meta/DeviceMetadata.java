package io.nawatech.erp.master.admin.meta;

import io.nawatech.erp.master.admin.Admin;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class DeviceMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean enabled;

    @ManyToOne(targetEntity = Admin.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "admin_id")
    private Admin admin;

    private String deviceDetails;
    private String location;
    private Date lastLoggedIn;

    public DeviceMetadata(String location, Admin admin) {
        this.location = location;
        this.admin = admin;
        enabled = false;
    }

}
