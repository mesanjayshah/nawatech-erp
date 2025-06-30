package io.nawatech.erp.utils;

import io.nawatech.erp.config.BuildConfig;

import java.util.List;

public class Strings {

    public static final String SOFTWARE_VERSION = BuildConfig.SOFTWARE_VERSION;
    public static final String LIVE_BASE_URL = BuildConfig.PROTOCOL + "://" + BuildConfig.DOMAIN + "/" + BuildConfig.WEB_APP_INSTANCE;
    public static final String ELEARNING_AUTH_BASE_ROUTE = "redirect:" + BuildConfig.PROTOCOL + "://" + BuildConfig.DOMAIN + "/" + BuildConfig.E_PORTAL_INSTANCE + "/login/authorize/";
    public static final String SOMETHING_WENT_WRONG = "Sorry but something went wrong. Please try again later.";
    public static final String SESSION_EXPIRED = "Session expired. You must log in first.";
    public static final String INCORRECT_LOGIN_CREDENTIALS = "Invalid login credentials.";
    public static final String LOGGED_OUT_SUCCESSFULLY = "Logged out successfully.";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final String TIMEZONE = "GMT+0545";
    public static final String LINK_BROKEN_OR_NO_ENOUGH_PERMISSION = "Sorry but seems like the link has been broken or you don't have enough permission to perform this action.";
    public static final String BRAND_COLOR = "#0e76bd";
    public static final String THEME_COLOR = "#e9c46a";
    public static final String SECONDARY_COLOR = "#2a9d8f";
    public static final String TERNARY_COLOR = "#264653";
    public static final String EXPECTED_LAST_PWD_RESET_DATE = "2024-12-26";

    public static final String FILE_UPLOAD_BASE_PATH = BuildConfig.FILE_UPLOAD_BASE_PATH;

    public static final String NO_PERMISSION = "nopermission";
    public static final String INVALID_REQUEST = "Invalid request.";
    public static final String SAVED_SUCCESSFULLY = "Saved successfully.";
    public static final String FAILED_TO_UPDATE = "Sorry but failed to update.";
    public static final String UPDATED_SUCCESSFULLY = "Updated successfully.";
    public static final String REMOVED_SUCCESSFULLY = "Removed successfully.";
    public static final String FAILED_TO_REMOVE = "Sorry but failed to remove.";
    public static final String FAILED_TO_SAVE = "Sorry but failed to save.";

    public static final String DELETED_SUCCESSFULLY = "Deleted successfully.";
    public static final String WEBSITE_USER_NOT_FOUND = "No such website user exists in our database.";
    public static final String FAILED_TO_DELETE = "Sorry but failed to delete.";
    public static final String NOTHING_TO_SAVE = "Nothing to save.";
    public static final String NOTHING_TO_ASSIGN = "Nothing to assign.";
    public static final String NOTHING_TO_UPDATE = "Nothing to update.";
    public static final String NOTHING_TO_SHOW = "Nothing to show.";
    public static final String AUTHORIZATION = "Authorization";
    public static final String ACTIVE_INACTIVE = "active_inactive";
    public static final String OPTIONAL_COMPULSORY = "optional_compulsory";
    public static final String YES_NO = "yes_no";
    public static final String PENDING_RETURNED = "pending_returned";

    public static final String PASSWORD_UPDATED_SUCCESSFULLY = "Password updated successfully.";
    public static final String FAILED_TO_UPDATE_PASSWORD = "Sorry but failed to update password.";

    //for custom list pagination limit
    public static final List<Integer> LIMITS = java.util.Arrays.asList(10, 25, 50, 100);

//    public static final String FILE_UPLOAD_BASE_PATH = BuildConfigConstant.FILE_UPLOAD_BASE_PATH;

    public static String substringBeforeLast(String str, String separator) {
        if (isEmpty(str) || isEmpty(separator)) {
            return str;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

}
