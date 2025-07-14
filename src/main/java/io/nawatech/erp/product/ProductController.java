package io.nawatech.erp.product;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasPermission(null, 'product:read')")
    @GetMapping("")
    public String getAllProducts(Model model, Authentication authentication) {
        model.addAttribute("products", productService.getAllProducts());
        return "product/index";
    }

    @PreAuthorize("hasPermission(null, 'product:read')")
    @GetMapping("/{id}")
    public void getProductById(@PathVariable Long id) {
        productService.getProductById(id);
    }

    @PreAuthorize("hasPermission(null, 'product:create')")
    @GetMapping("/create")
    public String loadCreateProductPage() {
        return "product/create";
    }

    @PreAuthorize("hasPermission(null, 'create')")
    @PostMapping("/create")
    public void saveProduct(@ModelAttribute Product product) {
        productService.saveUpdate(product);
    }

    @PreAuthorize("hasPermission(null, 'product:update')")
    @GetMapping("/edit/{id}")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "product/edit";
    }

    @PreAuthorize("hasPermission(null, 'update')")
    @PutMapping("/edit/")
    public void updateProduct(@ModelAttribute Product product) {
        productService.saveUpdate(product);
    }

    @PreAuthorize("hasPermission(null, 'product:delete')")
    @PostMapping("/delete/{id}")
    public String deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/product";
    }
}
