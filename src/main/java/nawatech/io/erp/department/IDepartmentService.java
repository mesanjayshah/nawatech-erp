package nawatech.io.erp.department;

import nawatech.io.erp.utils.payload.Response;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IDepartmentService {

    boolean saveOrUpdate(Department department);

    ResponseEntity<Response> createDepartment(Department department, HttpSession session);

    DepartmentResponse getDepartments(
            Boolean status, String keyword, int pageNo, int pageSize, String sortBy, String sortDir);

    Optional<Department> getDepartmentById(Long id);

    Optional<Department> updatePost(DepartmentDTO departmentDTO);

    Boolean deleteDepartment(Long id);

    Optional<Department> findById(Long id);

}
