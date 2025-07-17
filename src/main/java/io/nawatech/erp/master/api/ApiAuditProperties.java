package io.nawatech.erp.master.api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "audit")
public class ApiAuditProperties {
    private boolean apiCallEnabled = true;
}
