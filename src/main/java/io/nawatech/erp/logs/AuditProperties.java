package io.nawatech.erp.logs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "audit")
public class AuditProperties {

    private boolean apiCallEnabled = true;
    private boolean businessEventsEnabled = true;
    private boolean fieldDiffsEnabled = true;
}
