package io.nawatech.erp.master.admin;

import jakarta.transaction.Transactional;
import io.nawatech.erp.utils.BasicDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Admin findByUsername(String username);
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByProviderAndProviderId(String provider, String providerId);
    Optional<Admin> findByProviderId(String providerId);

    @Query("SELECT new io.nawatech.erp.utils.BasicDTO(u.id, u.name) FROM Admin u " +
            "WHERE u.isEnabled = true AND u.isAccountNonLocked = true ")
    List<BasicDTO> findAllAdmin();

    @Modifying
    @Query(value = "UPDATE Admin u set u.name =:name, u.email =:email where u.id =:id")
    void update(String name, String email, Long id);

    @Query("SELECT u FROM Admin u WHERE u.isAccountNonLocked = false AND u.lockTime < :currentTime")
    List<Admin> findExpiredLockedAdmin(@Param("currentTime") Date date);

    @Query("update Admin u set u.failedAttempt=?1 where u.username=?2 ")
    @Modifying
    @Transactional
    void updateFailedAttempt(int attempt, String username);

}
