package nawatech.io.erp.utils.constants;

import nawatech.io.erp.config.BuildConfigConstant;

import java.util.List;

public class Constants {

    public static final String BOARD_EXAM_TYPE = "Board Examination";
    public static final String ONE_EXAM_CASE = "One Exam Case";
    public static final String TWO_EXAM_CASE = "Two Exams Case";
    public static final String THREE_EXAM_CASE = "Three Exams Case";
    public static final String FOUR_EXAM_CASE = "Four Exams Case";
    public static final String FIVE_EXAM_CASE = "Five Exams Case";
    public static final String OTHERS_INCOME = "Others";
    public static final String GENERAL = "General";
    public static final String ADMIN = "Admin";
    public static final String ADMISSION = "Admission";
    public static final String NOT_APPLICABLE = "N/A";

    public static final Integer LIMIT = 10;
    public static final Integer TRAINER_ADMIN_ID = 11;

    //Enabling custom list affect all the tables of all Module.
    public static final boolean ENABLE_CUSTOM_LIST = true;

    public static final boolean ENABLE_SCHOOL_PAYMENT_CUSTOM_LIST = true;

    //    public static final boolean ENABLE_STUDENT_V2 = BuildConfig.WEB_APP_INSTANCE.equalsIgnoreCase(BuildConfigConstant.SMS_BETA);//switch between old and new student portal's UI
    public static final boolean ENABLE_STUDENT_V2 = false;//switch between old and new student portal's UI

    public static final String DEFAULT_CLASSROOM_CURRENT_STATUS = "All";

    public static final String SEPARATOR = "_@_";

    public static final boolean ENABLE_ATTENDANCE_V2 = true;

    public static final boolean ENABLE_PADDING_IN_TRANSCRIPT = true;

    public static final String VERSION_V1 = "V1";
    public static final String VERSION_V2 = "V2";

    public static final String NP = "np";

    public static final Integer DEFAULT_GRADE_SECTION_ID = 0;
    public static final String DEFAULT_GRADE_SECION_NAME = "Uncategorized";

    //list of instance where gender of student should be represented as male/female/others instead of boy/girl/others
    public static final List<String> MALE_FEMALE_INSTANCES = java.util.Arrays.asList(
            BuildConfigConstant.CMS
    );

}

