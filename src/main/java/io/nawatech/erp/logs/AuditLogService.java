package io.nawatech.erp.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Async
    public void saveAuditLog(AuditLog log) {
        auditLogRepository.save(log);
    }
}

