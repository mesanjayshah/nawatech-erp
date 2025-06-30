package nawatech.io.erp.department;

import nawatech.io.erp.utils.Helper;
import nawatech.io.erp.utils.Strings;
import nawatech.io.erp.utils.payload.Response;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static nawatech.io.erp.utils.constants.AppConstants.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping(value = "")
    public String loadIndex(HttpSession session, Model model, RedirectAttributes redirectAttributes) {

        Helper.setTitle("Department List", model);

        return "department/index";
    }

    @PostMapping(value = "find-all")
    public ResponseEntity<DepartmentResponse> findAllDepartment(
            @RequestParam(value = "status", required = false) Boolean status,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){

        boolean success = false;
        String message = null;
        DepartmentResponse departmentResponse = null;

        try {
            departmentResponse = departmentService.getDepartments(status, keyword, pageNo, pageSize, sortBy, sortDir);
            success = true;
        } catch (Exception e) {
            message = Strings.SOMETHING_WENT_WRONG;
            throw new RuntimeException(e);
        }

        log.debug( " controller departmentResponse {}  ", departmentResponse);

        return new ResponseEntity<>(departmentResponse, HttpStatus.OK);
    }

    @PostMapping("/find-by-id")
    public ResponseEntity<Response> findById(@RequestParam("id") Long id){

        System.out.println(id + " <<<< department id");
        System.out.println("the created By  is {}  " + departmentService.findById(id));

        log.debug("the created By  is {}", departmentService.findById(id));

        //validate account logged in status

        boolean success = false;
        String message = null;
        Department department = null;

        try {
            department =  departmentService.findById(id).get();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(department, message, success);
    }

    @PostMapping("/save-update")
    public ResponseEntity<Response> saveOrUpdate(@ModelAttribute Department department, HttpSession session){

        // session and permission check

        System.out.println(department.getName() + " <<<< name ");

        return departmentService.createDepartment(department, session);
    }

    @PostMapping("/update")
    public ResponseEntity<Optional<Department>> updatePost(@RequestBody DepartmentDTO departmentDTO){
        System.out.println(departmentDTO.getName() + " <<< updated name");
        return new ResponseEntity<>(departmentService.updatePost(departmentDTO), HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Boolean> deletePost(@PathVariable Long id){
        System.out.println(id + " <<< deleted ID");
        return new ResponseEntity<>(departmentService.deleteDepartment(id), HttpStatus.OK);
    }

}
