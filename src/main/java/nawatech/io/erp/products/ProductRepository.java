package nawatech.io.erp.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select u from Product u where LOWER(u.name) = LOWER(:name)")
    List<Product> findByNameIgnoreCase(@Param("name") String name);
}
