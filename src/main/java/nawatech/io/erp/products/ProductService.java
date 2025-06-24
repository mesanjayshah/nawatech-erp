package nawatech.io.erp.products;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    boolean saveOrUpdate(Product product);
    List<Product> findByNameIgnoreCase(String name);
}
