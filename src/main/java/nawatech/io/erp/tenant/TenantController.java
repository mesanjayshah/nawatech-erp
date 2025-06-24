package nawatech.io.erp.tenant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("tenant")
@Controller
@Slf4j
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

}
