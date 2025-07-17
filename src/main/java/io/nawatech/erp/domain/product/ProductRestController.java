package io.nawatech.erp.domain.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductRestController {

    private final ProductService productService;

    @PreAuthorize("hasPermission(null, 'product:read')")
    @GetMapping("")
    public ResponseEntity<?> getAllProducts(Authentication authentication) {
        return ResponseEntity.ok(productService.getAllProducts());
    }
}
