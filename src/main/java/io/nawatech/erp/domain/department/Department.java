package io.nawatech.erp.domain.department;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.nawatech.erp.base.AuditorEntityAdmin;
import io.nawatech.erp.utils.Strings;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(columnDefinition = "boolean default true")
    private boolean isEnabled;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    @CreatedDate
    @JsonFormat(pattern = Strings.DATE_TIME_PATTERN, timezone = Strings.TIMEZONE)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @NonNull
    private String name;

    private String brand_logo;

}
