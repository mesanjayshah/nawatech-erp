package io.nawatech.erp.config;


public class BuildConfigConstant {

    //domain and sub-domain
    public static final String ITBLISS_DOT_COM = "itbliss.com";
    public static final String LOCALHOST = "192.168.1.68:8080";

    //protocol
    public static final String HTTP = "http";
    public static final String HTTPS = HTTP + "s";

    //web app instance
    public static final String SMS = "sms";
    public static final String MIS = "mis";
    public static final String SMS_BETA = SMS + "beta";
    public static final String TMP = "tmp";
    public static final String EAP = "eap";
    public static final String DEMO = "demo";
    public static final String QA_EAP = "qaeap";//for qa testing
    public static final String QA = "qa";//for qa testing
    public static final String CMS = "cms";//for college management system

    //e-portal instance
    public static final String E = "e";

    //build type
    public static final String BUILD_STABLE = "";
    public static final String BUILD_BETA = "-BETA";
    public static final String BUILD_EAP = "-EAP";
    public static final String BUILD_TMP = "-TMP";
    public static final String BUILD_DEMO = "-DEMO";
    public static final String BUILD_QA = "-QA";

    //file upload base path
    public static final String LIVE_FILE_UPLOAD_BASE_PATH = "/home/ubuntu/file-uploads/";
    public static final String LOCAL_WIN_FILE_UPLOAD_BASE_PATH = "D:/DATA/file-uploads/";
    public static final String LOCAL_LINUX_FILE_UPLOAD_BASE_PATH = "/home/mesanjay/official/file-uploads/";


}