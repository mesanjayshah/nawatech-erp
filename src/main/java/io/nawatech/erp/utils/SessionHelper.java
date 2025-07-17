package io.nawatech.erp.utils;

import io.nawatech.erp.master.admin.Admin;
import io.nawatech.erp.utils.constants.UserTypes;
import io.nawatech.erp.utils.payload.Response;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SessionHelper {

    public static final String TENANT_REDIRECT_ATTRIBUTE = "TENANT_REDIRECT_URL";
    public static final String ADMIN_REDIRECT_ATTRIBUTE = "ADMIN_REDIRECT_URL";

/*    public static TenantAccount getLoggedInSchool(HttpSession session) {
        TenantAccount tenantAccount = new TenantAccount();
        tenantAccount.setId(Helper.getLoggedInSchoolId(session));
        tenantAccount.setName(SessionHelper.getLoggedInSchoolName(session));
        tenantAccount.setUsername(Helper.getLoggedInSchoolUsername(session));
        tenantAccount.setTeam(new Team(Helper.getTeamId(session)));
        return tenantAccount;
    }*/

    public static String getLoggedInTenantName(HttpSession session) {
        return session.getAttribute("tenant_name").toString();
    }

    public static String getLoggedInTenantUsername(HttpSession session) {
        return session.getAttribute("tenant_username").toString();
    }

    public static ResponseEntity<Response> getSessionExpiredResponse() {
        return Helper.getResponseEntity(null, Strings.SESSION_EXPIRED, false);
    }

    public static Integer getLoggedInAdminId(HttpSession session) {
        return Integer.parseInt(session.getAttribute("admin_id").toString());
    }

    public static String getOperationDateSetting(HttpSession session, String userType) {
        if (userType == null || userType.trim().isEmpty()) return SessionHelper.getOperationDateSetting(session);

        String operationDateSetting;
        switch (userType.trim().toUpperCase()) {

            case UserTypes.ADMIN:
                operationDateSetting = getAdminOperationDateSetting(session);
                break;

            case UserTypes.SUPER_USER:
                operationDateSetting = getSuperUserOperationDateSetting(session);
                break;

            default:
                operationDateSetting = getOperationDateSetting(session);
                break;
        }

        return operationDateSetting;
    }

    public static String getOperationDateSetting(HttpSession session) {
        return session.getAttribute("operation_date_setting").toString();
    }

    public static String getAdminOperationDateSetting(HttpSession session) {
        Object operationDateSetting = session.getAttribute("admin_operation_date_setting");
        return operationDateSetting != null ? operationDateSetting.toString() : "np";
    }

    public static String getSuperUserOperationDateSetting(HttpSession session) {
        return session.getAttribute("super_user_operation_date_setting").toString();
    }

    public static Admin getLoggedInAdmin(HttpSession session) {
        Admin admin = new Admin();
        admin.setId(Long.valueOf(session.getAttribute("admin_id").toString()));
        admin.setName(session.getAttribute("admin_name").toString());
        admin.setUsername(session.getAttribute("admin_email").toString());
        return admin;
    }

    public static ResponseEntity<Response> getLinkBrokenOrNoEnoughPermissionResponse() {
        return Helper.getResponseEntity(null, Strings.LINK_BROKEN_OR_NO_ENOUGH_PERMISSION, false);
    }

}
