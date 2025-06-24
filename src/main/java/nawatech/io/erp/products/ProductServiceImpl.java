package nawatech.io.erp.products;

import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Transactional
    public boolean saveOrUpdate(Product product) {
        try {
            repository.save(product);
            return true;
        } catch (Exception e) {
            log.error("Failed to saveOrUpdate product", e);
            return false;
        }
    }

    @Override
    public Product createProduct(Product product) {
        log.info("Product created: {}", product.getName());
        return repository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Product updateProduct(Long id, Product updated) {
        Product product = repository.findById(id).orElseThrow();
        product.setName(updated.getName());
        product.setPrice(updated.getPrice());
        return repository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Product> findByNameIgnoreCase(String name) {
        return repository.findByNameIgnoreCase(name);
    }

}