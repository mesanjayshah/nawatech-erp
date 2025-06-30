package nawatech.io.erp.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import nawatech.io.erp.admin.Admin;
import nawatech.io.erp.utils.Strings;
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
public class AuditorEntityAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(columnDefinition = "boolean default true")
    private boolean isEnabled;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    @CreatedDate
    @JsonFormat(pattern = Strings.DATE_TIME_PATTERN, timezone = Strings.TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "created_at", updatable = false)
    private Date createdAt;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private Admin createdBy;

    @LastModifiedDate
    @JsonFormat(pattern = Strings.DATE_TIME_PATTERN, timezone = Strings.TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(insertable = false, name = "modified_at")
    private Date modifiedAt;

    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "modified_by", insertable = false)
    private Admin modifiedBy;

/*  @Column(name = "DeletedOn")
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