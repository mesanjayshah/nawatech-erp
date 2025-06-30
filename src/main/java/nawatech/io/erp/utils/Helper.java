package nawatech.io.erp.utils;

import nawatech.io.erp.utils.constants.MessageType;
import nawatech.io.erp.utils.dateutil.DateUtils;
import nawatech.io.erp.utils.dateutil.EngToNepaliDateConverter;
import nawatech.io.erp.utils.payload.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class Helper {


    //set to false in case of production
//    public static final boolean SKIP_PERMISSION_CHECK = BuildConfig.IS_LOCALHOST || BuildConfig.BUILD_TYPE.equalsIgnoreCase(BuildConfigConstant.BUILD_DEMO) || BuildConfig.BUILD_TYPE.equalsIgnoreCase(BuildConfigConstant.BUILD_QA);
    public static final boolean SKIP_PERMISSION_CHECK = true;
    public static final boolean SKIP_TEAM_ID_REDIRECT = SKIP_PERMISSION_CHECK;
    public static final String randomPasswordForEncryption = "pAb7tULhbydN%f6y";
    public static final String MASTER_PASSWORD = "iam10000%right";

    public static final String SCHOOL_REDIRECT_ATTRIBUTE = "SCHOOL_REDIRECT_URL";
    public static final String ADMIN_REDIRECT_ATTRIBUTE = "ADMIN_REDIRECT_URL";


    public static void setTitle(String title, Model model) {
        model.addAttribute("title", title);
    }

    public static final List<Integer> TEAM_ID_WITH_CUSTOM_THEME = Arrays.asList(613, 417, 680, 704, 539);
    public static final List<Integer> TEAM_IDS_TO_SKIP_SUPER_USER_CASE = Arrays.asList(705, 697, 732, 417, 312);

    public static final int RESTRICTED_ID = 1;

    public static int getLoggedInAdminId(HttpSession session) {
        return Integer.parseInt(session.getAttribute("admin_id").toString());
    }

    public static String getLoggedInAdminName(HttpSession session) {
        return session.getAttribute("admin_name").toString();
    }

    public static String getLoggedInAdminUsername(HttpSession session) {
        return session.getAttribute("admin_username").toString();
    }

    public static int getLoggedInTenantId(HttpSession session) {
        return Integer.parseInt(session.getAttribute("tenant_id").toString());
    }


    public static String getTenantLoginRedirectUrl(RedirectAttributes redirectAttributes) {
        Helper.setSessionExpiredFlashMessage(redirectAttributes);
        return "redirect:/login";
    }

    public static void setSessionExpiredFlashMessage(RedirectAttributes redirectAttributes) {
        Helper.setFlashMessage(MessageType.ERROR, Strings.SESSION_EXPIRED, redirectAttributes);
    }

    public static void setInvalidRequestFlashMessage(RedirectAttributes redirectAttributes) {
        Helper.setFlashMessage(MessageType.INFO, Strings.INVALID_REQUEST, redirectAttributes);
    }

    public static void setLinkBrokenOrNoEnoughPermissionFlashMessage(RedirectAttributes redirectAttributes) {
        Helper.setFlashMessage(MessageType.ERROR, Strings.LINK_BROKEN_OR_NO_ENOUGH_PERMISSION, redirectAttributes);
    }

    public static void setFlashMessage(String type, String message, RedirectAttributes redirectAttributes) {
        if (message != null && !message.trim().isEmpty())
            redirectAttributes.addFlashAttribute("message", getFlashMessage(type, message));
    }

    public static String getFlashMessage(String type, String message) {
        String className = "";

        switch (type) {
            case MessageType.SUCCESS:
                className = "alert-success";
                break;

            case MessageType.ERROR:
                className = "alert-danger";
                break;

            case MessageType.INFO:
                className = "alert-info";
                break;

            case MessageType.WARNING:
                className = "alert-warning";
                break;

            default:
                break;
        }

        return "<div class='d-print-none alert " + className + "'>" + message + "</div>";
    }


    public static Date getCurrentDateTime() {
        return new Date();
    }

    public static void setSuccessOrFailureFlashMessage(boolean success, boolean isUpdateCase, RedirectAttributes redirectAttributes) {
        Helper.setFlashMessage(success ? MessageType.SUCCESS : MessageType.ERROR, getSuccessOrFailureMessage(success, isUpdateCase), redirectAttributes);
    }

    public static String getSuccessOrFailureMessage(boolean success, boolean isUpdateCase) {
        return success ? (isUpdateCase ? Strings.UPDATED_SUCCESSFULLY : Strings.SAVED_SUCCESSFULLY) : (isUpdateCase ? Strings.FAILED_TO_UPDATE : Strings.FAILED_TO_SAVE);
    }

    public static final Long MAX_IMAGE_SIZE = 700000L, MAX_IMAGE_SIZE_3MB = 3145728L;//in byte where 700000L is equivalent to 700 KB

    public static String getCurrentTimeOnly() {
        DateFormat df = new SimpleDateFormat(Strings.TIME_PATTERN);
        Date date = new Date();
        return df.format(date);
    }

    public static String getCurrentNepaliDateOnly() {
        Date d = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String currentDate = "2074-01-01";
        try {
            currentDate = EngToNepaliDateConverter.engToNep(year, month, day);// yyyy,mm,dd
        } catch (Exception e) {
            e.printStackTrace();
        }

        return currentDate;
    }

    public static String getCurrentDateOnly(String operationDateSetting) {
        return operationDateSetting.equalsIgnoreCase("np") ? Helper.getCurrentNepaliDateOnly() : Helper.getCurrentEnglishDateOnly();
    }

    public static String getCurrentEnglishDateOnly() {
        DateFormat df = new SimpleDateFormat(Strings.DATE_PATTERN);
        Date date = new Date();
        return df.format(date);
    }

    public static boolean isNullOrEmpty(String val) {
        return (val == null || val.trim().isEmpty());
    }

    public static boolean isNullOrEmpty(List<?> val) {
        return (val == null || val.isEmpty());
    }

    public static int getCurrentNepaliYear() {
        Date d = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int nepaliYear = 2074;
        try {
            nepaliYear = Integer.parseInt(EngToNepaliDateConverter.engToNep(year, month, day).substring(0, 4));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nepaliYear;
    }

    public static int getCurrentEnglishYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static String getCurrentDate() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return df.format(date);
    }

    public static String getCurrentNepaliDateTime() {
        return getCurrentDateOnly() + " " + getCurrentTimeOnly();
    }

    public static String getCurrentDateOnly() {
        String final_date = "";

        Date d = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        try {
            final_date = EngToNepaliDateConverter.engToNep(year, month, day);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return final_date;
    }

    public static boolean isValueNullOrEmpty(String value) {
        return (value != null && !value.trim().isEmpty());
    }

    public static String getOrDefault(String value, String defaultValue) {
        return !Helper.isNullOrEmpty(value) ? value : defaultValue;
    }

    public static boolean isNotNullAndGreaterThanZero(Long id) {
        return id != null && id > 0;
    }

    public static boolean isListNull(List<Long> id) {
        return id != null && !id.isEmpty();
    }

    public static boolean isStringValueIsNotNull(List<String> values) {
        return values != null && !values.isEmpty();
    }

    public static boolean isIntegerListNull(List<Integer> id) {
        return id != null && !id.isEmpty();
    }

    public static boolean isNotNullAndGreaterThanZero(Integer id) {
        return id != null && id > 0;
    }

    public static String getTotalSavedMessage(int saved, int total) {
        return "Saved " + saved + " out of " + total + ".";
    }

    public static int getTenantId(HttpSession session) {
        return Integer.parseInt(session.getAttribute("tenant_id").toString());
    }

    public static ResponseEntity<Response> getResponseEntity(Object body, String message, boolean success) {
        return new ResponseEntity<>(new Response(body, message, success), HttpStatus.OK);
    }

    public static ResponseEntity<Response> getResponseEntity(Response response) {
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static Integer getOffsetByPageNumberAndLimit(Integer pageNumber, Integer limit) {
        if (pageNumber == null || limit == null) return null;
        return (pageNumber - 1) * limit;
    }

    public static String[] getAuthorJoins(String parentTableAlias) {
        return getAuthorJoins(parentTableAlias, true);
    }

    public static String[] getAuthorJoins(String parentTableAlias, boolean isSchool) {
        String tblAuthor = isSchool ? "tbl_website_user" : "tbl_admin";
        String selections = parentTableAlias + ".created_date as createdDate, cb.username as createdBy, " +
                parentTableAlias + ".modified_date as modifiedDate, mb.username as modifiedBy ";
        String joins = "JOIN " + tblAuthor + " cb on cb.id=" + parentTableAlias + ".created_by " +
                "LEFT JOIN " + tblAuthor + " mb on mb.id=" + parentTableAlias + ".modified_by ";
        return new String[]{selections, joins};
    }

    public static String getMessage(int totalSaved, int totalAvailable) {
        String message = null;
        if (totalAvailable > 0) message = "Total saved " + totalSaved + " out of " + totalAvailable + " successfully.";

        return message;
    }

    public static float roundOff(float val) {
        float res = 0;
        if (!Float.isNaN(val)) {
            DecimalFormat df = new DecimalFormat("#.##");
            res = Float.parseFloat(df.format(val));
        }
        return res;
    }


    public static String getAdminLoginRedirectUrl(RedirectAttributes redirectAttributes) {
        Helper.setSessionExpiredFlashMessage(redirectAttributes);
        return "redirect:/admin/login";
    }

    public static Integer checkIntegerNullAndSize(List<Integer> values, Integer index) {
        if (Helper.isNullOrEmpty(values)) return null;
        Integer val = null;
        if (values.size() > index) {
            if (values.get(index) != null) val = values.get(index);
        }
        return val;
    }

    public static Integer checkIntegerNullAndSizeAndReturnZero(List<Integer> values, Integer index) {
        if (Helper.isNullOrEmpty(values)) return null;
        Integer val = 0;
        if (values.size() > index) {
            if (values.get(index) != null) val = values.get(index);
        }
        return val;
    }

    public static Boolean checkBooleanNullAndSize(List<Boolean> values, Integer index) {
        if (Helper.isNullOrEmpty(values)) return false;
        Boolean val = false;
        if (values.size() > index) {
            if (values.get(index) != null) val = values.get(index);
        }
        return val;
    }

    public static Long checkLongNullAndSize(List<Long> values, Integer index) {
        if (Helper.isNullOrEmpty(values)) return null;
        Long val = null;
        if (values.size() > index) {
            if (values.get(index) != null) val = values.get(index);
        }
        return val;
    }

    public static String checkStringNullAndSize(List<String> values, Integer index) {
        if (Helper.isNullOrEmpty(values)) return null;
        String val = "";
        if (values.size() > index) {
            if (values.get(index) != null) val = values.get(index);
        }
        return val;
    }

    public static String getGeneratedMessage(int totalSaved, int totalAvailable) {
        String message = null;

        if (totalAvailable > 0)
            message = "Total generated " + totalSaved + " out of " + totalAvailable + " successfully.";

        return message;
    }

    /**
     * @param status
     * @param type
     * @return
     */
    public static String getDisplayableStatus(boolean status, String type, boolean isMDBootstrap) {
        String className = "", text = "";

        if (status) {
            className = (isMDBootstrap ? "badge" : "label") + "-success";
            if (type.equalsIgnoreCase(Strings.YES_NO)) text = "Yes";
            else if (type.equalsIgnoreCase(Strings.PENDING_RETURNED)) text = "RETURNED";
            else if (type.equalsIgnoreCase(Strings.OPTIONAL_COMPULSORY)) text = "COMPULSARY";
            else text = "Active";
        } else {
            className = (isMDBootstrap ? "badge" : "label") + "-danger";
            if (type.equalsIgnoreCase(Strings.YES_NO)) text = "No";
            else if (type.equalsIgnoreCase(Strings.PENDING_RETURNED)) text = "PENDING";
            else if (type.equalsIgnoreCase(Strings.OPTIONAL_COMPULSORY)) text = "OPTIONAL";
            else text = "In-Active";
        }

        return "<label class='text-uppercase " + (isMDBootstrap ? "badge " : "label ") + className + "'>" + text + "</label>";
    }

    public static String getDisplayableStatus(String status) {
        String text, className;
        if (status.equalsIgnoreCase("Pending")) {
            text = "PENDING";
            className = "badge badge-warning";
        } else if (status.equalsIgnoreCase("Approved")) {
            text = "APPROVED";
            className = "badge badge-success";
        } else if (status.equalsIgnoreCase("Checked")) {
            text = "CHECKED";
            className = "badge badge-success";
        } else if (status.equalsIgnoreCase("Cancelled")) {
            className = "badge badge-danger";
            text = "CANCELLED";
        } else if (status.equalsIgnoreCase("Terminated")) {
            className = "badge badge-warning";
            text = "TERMINATED";
        } else if (status.equalsIgnoreCase("Recommended")) {
            className = "badge badge-primary";
            text = "RECOMMENDED";
        } else if (status.equalsIgnoreCase("Forwarded")) {
            className = "badge badge-default";
            text = "FORWARDED";
        } else if (status.equalsIgnoreCase("Rejected")) {
            className = "badge badge-danger";
            text = "REJECTED";
        } else if (status.equalsIgnoreCase("Postponed")) {
            className = "badge badge-warning";
            text = "POSTPONDED";
        } else if (status.equalsIgnoreCase("Recommended")) {
            className = "badge badge-primary";
            text = "RECOMMENDED";
        } else if (status.equalsIgnoreCase("Rejected")) {
            className = "badge badge-danger";
            text = "REJECTED";
        } else if (status.equalsIgnoreCase("Forwarded")) {
            className = "badge badge-default";
            text = "FORWARDED";
        } else {
            text = "REJECTED";
            className = "badge badge-danger";
        }
        return "<label class= '" + className + "'>" + text + "</label>";
    }

    public static String getDisplayableStatusPendingApproved(boolean status) {
        String text, className;
        if (status) {
            className = "badge badge-success";
            text = "APPROVED";
        } else {
            text = "PENDING";
            className = "badge badge-warning";
        }
        return "<label class= '" + className + "'>" + text + "</label>";
    }

    public static String getRedirectUrl(HttpSession session, String redirectSessionAttribute, String defaultRedirectUrl) {

        String redirectUrl = defaultRedirectUrl;
        if (session.getAttribute(redirectSessionAttribute) != null) {
            redirectUrl = session.getAttribute(redirectSessionAttribute).toString();
            session.removeAttribute(redirectSessionAttribute);
        }
        return redirectUrl;
    }

    public static boolean contains(List<Long> list, Long value) {
        if (list == null || value == null) return false;
        return list.contains(value);
    }

    public static boolean contains(List<Long> list, BigInteger value) {
        if (list == null || value == null) return false;
        return list.contains(value.longValue());
    }

    public static boolean contains(List<Integer> list, Integer value) {
        if (list == null || value == null) return false;
        return list.contains(value);
    }

    public static boolean contains(List<String> list, String value) {
        if (list == null || value == null) return false;
        return list.contains(value);
    }

    public static boolean contains(String[] array, String value) {
        if (array == null || value == null) return false;
        return Arrays.asList(array).contains(value);
    }

    public static Map<String, Object> wrapToMap(Object data, Integer total) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("total", total);
        return map;
    }

    public static String getSchoolImageSource(String schoolLogo) {
        String fileSource = "dist/img/dn-rectangle.png";
        if (schoolLogo != null && !schoolLogo.isEmpty())
            fileSource = "files/tenant-logos/" + schoolLogo;
        return fileSource;
    }

    public static int getCurrentNepaliMonth() {
        return Integer.parseInt(getCurrentNepaliDateOnly().split("-")[1]);
    }

    public static int getCurrentEnglishMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static boolean checkAdminLogin(HttpSession session) {
/*        if (session.getAttribute("admin_username") != null
                && session.getAttribute("admin_logged_in").toString().equals("1")
                && !session.getAttribute("admin_username").toString().isEmpty()) {
                    session.setAttribute("CURRENT_DATE_TIME", Helper.getCurrentDate());
                    return true;
        }

        validateAndSetRedirectUrl(session, SessionHelper.ADMIN_REDIRECT_ATTRIBUTE);
        return false;*/
        return true;
    }

    public static boolean checkAdminPermission(String permissionKey, HttpSession session) {
        if (SKIP_PERMISSION_CHECK) return true;
        int adminId = SessionHelper.getLoggedInAdminId(session);
        return true;
//        return rolePermissionDao.hasPrivilege(adminId, permissionKey);
    }

    public static boolean checkTenantLogin(HttpSession session) {
        if (session.getAttribute("tenant_username") != null
                && session.getAttribute("tenant_logged_in").toString().equals("1")
                && !SessionHelper.getLoggedInTenantUsername(session).isEmpty()
                && (!(boolean) session.getAttribute("forcePasswordReset") || (boolean) session.getAttribute("skipForcePasswordReset"))) {
            session.setAttribute("skipForcePasswordReset", false);
            session.setAttribute("CURRENT_DATE_TIME", Helper.getCurrentDate());
            return true;
        }

        validateAndSetRedirectUrl(session, SessionHelper.TENANT_REDIRECT_ATTRIBUTE);
        return false;
    }

    public static void validateAndSetRedirectUrl(HttpSession session, String sessionAttribute) {
        //set redirect url to session if request is not XMLHttpRequest
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith == null || !xRequestedWith.equalsIgnoreCase("XMLHttpRequest")) {
            String redirectUrl = request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
            redirectUrl = redirectUrl.replaceAll(request.getContextPath() + "/", "");
            if (!Arrays.asList(ArraysUtil.EXCULDABLE_URL_ON_REDIRECT_SESSION_ATTRIBUTE).contains(redirectUrl)) {
                session.setAttribute(sessionAttribute, redirectUrl);
            }
        }
    }

    public static String getStringMonthNew(int month) {
        return DateUtils.getStringMonth(month, "np");
    }

    public static String getStringMonth(int month) {
        if (month <= 12)
            return ArraysUtil.NEPALI_MONTHS_ARRAY[month - 1];// array index starts from 0 so subtracting 1 from month input
        return "";
    }

    public static int getIntEnMonthNew(String month) {
        return Arrays.asList(ArraysUtil.ENGLISH_MONTHS).indexOf(month) + 1;// arraylist index starts from 0 but month starts from 1
    }

    public static String getStringEngMonthNew(int month) {
        return DateUtils.getStringMonth(month, "en");
        /*if (month <= 12)
            return com.edigitalnepal.sms.util.Arrays.ENGLISH_MONTHS[month - 1];// array index starts from 0 so subtracting 1 from month input
        return "";*/
    }

    public static String ordinalSuffixOf(int i) {
        int j = i % 10, k = i % 100;
        if (j == 1 && k != 11) return "st";
        if (j == 2 && k != 12) return "nd";
        if (j == 3 && k != 13) return "rd";
        return "th";
    }

    public static int parseToInteger(String val) {
        if (Helper.isNullOrEmpty(val) || !NumberUtils.isParsable(val.trim()))
            return 0;
        return Integer.parseInt(val.trim());
    }

    public static Long parseToLong(String val, Long defaultValue) {
        if (Helper.isNullOrEmpty(val) || !NumberUtils.isParsable(val.trim()))
            return defaultValue;

        return Long.parseLong(val.trim());
    }

    public static BigDecimal parseToBigDecimal(String val) {
        if (Helper.isNullOrEmpty(val) || !NumberUtils.isParsable(val.trim()))
            return BigDecimal.ZERO;
        return new BigDecimal(val.trim()).setScale(2, RoundingMode.HALF_UP);
    }

    public static Integer parseInt(String value, boolean returnZeroIfNull) {
        if (Helper.isNullOrEmpty(value)) return returnZeroIfNull ? 0 : null;
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            e.printStackTrace();
            return returnZeroIfNull ? 0 : null;
        }
    }

    public static String getClientIP(HttpServletRequest request) {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr())) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

/*    @Bean(name = "GeoIPCountry")
    public DatabaseReader databaseReader() throws IOException, GeoIp2Exception {
        final File resource = new File(this.getClass()
                .getClassLoader()
                .getResource("maxmind/GeoLite2-Country.mmdb")
                .getFile());
        return new DatabaseReader.Builder(resource).build();
    }*/

}
