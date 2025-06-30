package io.nawatech.erp.config;


import io.nawatech.erp.utils.Helper;
import io.nawatech.erp.utils.Strings;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class BuildConfig {

    public static final boolean IS_LOCALHOST = true;
    public static final boolean ENABLE_BUILD_CONFIG_CONSOLE = false;
    public static final boolean IS_LINUX_SERVER = false;
    public static final String DOMAIN = BuildConfigConstant.LOCALHOST;
    public static final String WEB_APP_INSTANCE = BuildConfigConstant.SMS;

    public static final String BUILD_TYPE = BuildConfigConstant.BUILD_EAP;
    public static final String BUILD_VERSION = "v0.0.1";

    //not required to update in any cases. it's final and always will be.
    public static final List<String> INSTANCES_FOR_SMS = Arrays.asList(
            BuildConfigConstant.SMS, BuildConfigConstant.SMS_BETA, BuildConfigConstant.MIS, BuildConfigConstant.EAP
    );

    private static final List<String> INSTANCES_FOR_QA = Arrays.asList(BuildConfigConstant.QA, BuildConfigConstant.QA_EAP);

    private static final List<String> CUSTOM_DIR_INSTANCES =
            Arrays.asList(
                    BuildConfigConstant.QA, BuildConfigConstant.QA_EAP,
                    BuildConfigConstant.DEMO,
                    BuildConfigConstant.CMS
            );

    public static final String FILE_UPLOAD_BASE_PATH = getRootDirectory(BuildConfig.CUSTOM_DIR_INSTANCES);
    public static final String E_PORTAL_INSTANCE = getEPortalInstance(BuildConfig.WEB_APP_INSTANCE);

    public static final String PROTOCOL =
            (BuildConfig.IS_LOCALHOST)
                    ? BuildConfigConstant.HTTP : BuildConfigConstant.HTTPS;

    public static final String SOFTWARE_VERSION = BUILD_VERSION + BuildConfig.BUILD_TYPE;

    public static void x() {
        if (BuildConfig.ENABLE_BUILD_CONFIG_CONSOLE) {
            log.info("# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # ");
            log.info("# IS LOCALHOST BUILD: " + (BuildConfig.IS_LOCALHOST ? "Yes" : "No"));
            log.info("# BASE URL: " + Strings.LIVE_BASE_URL);
            log.info("# VERSION: " + Strings.SOFTWARE_VERSION);
            log.info("# E-LEARNING AUTH BASE ROUTE: " + Strings.ELEARNING_AUTH_BASE_ROUTE);
            log.info("# FILE UPLOAD BASE PATH: " + Strings.FILE_UPLOAD_BASE_PATH);
            log.info("# SKIP PERMISSION CHECK: " + (Helper.SKIP_PERMISSION_CHECK ? "Yes" : "No"));
            log.info("# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # ");
        }
    }

    public static String getRootDirectory(List<String> customDirInstances) {
        if (IS_LOCALHOST)
            return (IS_LINUX_SERVER ? BuildConfigConstant.LOCAL_LINUX_FILE_UPLOAD_BASE_PATH : BuildConfigConstant.LOCAL_WIN_FILE_UPLOAD_BASE_PATH);

        boolean hasCustomDir = customDirInstances.contains(BuildConfig.WEB_APP_INSTANCE);
        String dir = null;

        if (hasCustomDir) {
            if (BuildConfig.INSTANCES_FOR_QA.contains(BuildConfig.WEB_APP_INSTANCE))
                dir = BuildConfigConstant.QA;
            else
                dir = BuildConfig.WEB_APP_INSTANCE;
        }

        return (hasCustomDir ? "/home/ubuntu/" + dir + "/file-uploads/" : BuildConfigConstant.LIVE_FILE_UPLOAD_BASE_PATH);
    }

    public static String getEPortalInstance(String webAppInstance) {
        return INSTANCES_FOR_SMS.contains(webAppInstance)
                ? BuildConfigConstant.E
                : BuildConfigConstant.E +
                (INSTANCES_FOR_QA.contains(BuildConfig.WEB_APP_INSTANCE) ? "qa" : BuildConfig.WEB_APP_INSTANCE);
    }

}
