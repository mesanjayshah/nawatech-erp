package io.nawatech.erp.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.nawatech.erp.master.admin.Admin;
import io.nawatech.erp.utils.Strings;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, updatable = false)
    private Long id;

    @CreatedDate
    @JsonFormat(pattern = Strings.DATE_TIME_PATTERN, timezone = Strings.TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "created_at", updatable = false)
    private Date createdAt;

    @CreatedBy
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Admin createdBy;

    @LastModifiedDate
    @JsonFormat(pattern = Strings.DATE_TIME_PATTERN, timezone = Strings.TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(insertable = false, name = "modified_at")
    private Date modifiedAt;

    @LastModifiedBy
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(insertable = false)
    private Admin modifiedBy;

    @Column(columnDefinition = "boolean default true")
    private boolean isEnabled;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

/*    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "tenant_id", nullable = false, updatable = false)
    private Tenant tenant;*/

/*    @Column(name = "DeletedOn")
    private Date deletedOn;

    @Column(name = "DeletedBy", length = 50)
    private String deletedBy;

    @Column(name = "isDeleted", length = 50)
    private Boolean isDeleted = false;

    @PreUpdate
    @PrePersist
    public void beforeAnyUpdate() {
        if (isDeleted != null && isDeleted) {

            if (deletedBy == null) {
                //                deletedBy = SignedUserHelper.userId().toString();
                deletedBy = null;
            }

            if (getDeletedOn() == null) {
                deletedOn = new Date();
            }
        }
    }*/

}