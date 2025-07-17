package io.nawatech.erp.base;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.nawatech.erp.master.entity.Admin;
import io.nawatech.erp.utils.Strings;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(columnDefinition = "boolean default true")
    private boolean isEnabled;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    @CreatedDate
    @JsonFormat(pattern = Strings.DATE_TIME_PATTERN, timezone = Strings.TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @CreatedBy
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Admin createdBy;

    @LastModifiedDate
    @JsonFormat(pattern = Strings.DATE_TIME_PATTERN, timezone = Strings.TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(insertable = false)
    private Date modifiedAt;

    @LastModifiedBy
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(insertable = false)
    private Admin modifiedBy;

}
