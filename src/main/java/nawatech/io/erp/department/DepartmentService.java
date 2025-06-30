package nawatech.io.erp.department;

import nawatech.io.erp.exception.ResourceNotFoundException;
import nawatech.io.erp.utils.Helper;
import nawatech.io.erp.utils.Strings;
import nawatech.io.erp.utils.payload.Response;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService {

    private  final DepartmentRepository departmentRepository;

    public boolean saveOrUpdate(Department department) {
        try {
            departmentRepository.save(department);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ResponseEntity<Response> createDepartment(Department department, HttpSession session) {

        boolean isUpdateCase = department.getId() != null;

        Department department1 = new Department();
        department1.setName(department.getName());
        department1.setEnabled(department.isEnabled());

        boolean success = false;
        String message = "";

        try{
            success = saveOrUpdate(department1);
            message = Helper.getSuccessOrFailureMessage(success, isUpdateCase);
        } catch (Exception e){
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(department, message, success);
    }

    @Override
    public DepartmentResponse getDepartments(Boolean status, String keyword, int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Department> departments = departmentRepository.findAll(pageable);
        DepartmentResponse departmentResponse = getDepartmentResponse(departments);
        log.debug( "departmentResponse {}",departmentResponse);
        return departmentResponse;
    }

    private static DepartmentResponse getDepartmentResponse(Page<Department> departments) {
        List<Department> content = new ArrayList<>(departments.getContent());

        DepartmentResponse departmentResponse = new DepartmentResponse();
        departmentResponse.setContent(content);
        departmentResponse.setPageNo(departments.getNumber());
        departmentResponse.setPageSize(departments.getSize());
        departmentResponse.setTotalElements(departments.getTotalElements());
        departmentResponse.setTotalPages(departments.getTotalPages());
        departmentResponse.setLast(departments.isLast());
        return departmentResponse;
    }

    @Override
    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    @Override
    public Optional<Department> updatePost(DepartmentDTO departmentDTO) {

        Department department = departmentRepository.findById(departmentDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Department", "id", departmentDTO.getId()));
        department.setName(departmentDTO.getName());
        Department updateDepartment = departmentRepository.save(department);

        return Optional.of(updateDepartment);
    }

    @Override
    public Boolean deleteDepartment(Long id) {

        try {
            departmentRepository.deleteDepartmentById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    @Override
    public Optional<Department> findById(Long id) {
        log.debug("the created By id  is {}", id);
        return departmentRepository.findById(id);
    }

}
