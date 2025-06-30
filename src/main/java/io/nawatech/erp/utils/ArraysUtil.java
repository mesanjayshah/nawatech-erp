package io.nawatech.erp.utils;

import java.util.List;

public class ArraysUtil {

    public static final String[] roles = {"Both", "Employee", "Student"};
    public static final String[] DEPRECIATION_APPRECIATION_TYPE = {"Depreciation", "Appreciation"};
    public static final String[] INVENTORY_VALUATION = {"Default", "FIFO"};
    public static final String[] SHIFTS = {"Morning", "Day", "Evening"};
    public static final String[] PAYROLL_TYPE = {"Monthly", "Yearly"};
    public static final String[] SOURCE_TYPE = {"Gift", "Purchase", "Donation", "Others"};
    public static final String[] BOOK_CONDITION = {"Good", "Lost", "Torn", "Not In use"};
    public static final String[] BOOK_RETURN_STATUS = {"Issued", "Returned"};
    public static final String[] RETURNED_STATUS = {"Good", "Damaged", "Excellent"};
    public static final String[] STOCK_REPORT_HEADING = {"Quantity", "Rate", "Total"};
    public static final String[] CHOOSE_FORM = {"General", "Public"};
    public static final String[] LOT_BY = {"Product-wise", "Product Group-wise", "Purchase Quantity-wise"};
    public static final String[] LOT_BY_NAME = {"Product + Lot + Quantity", "Lot + Quantity", "Product + Quantity"};
    public static final String[] ASSET_CODE_PRODUCT_TYPE = {"Assets Durable", "Durable", "Non-Durable"};
    public static final String[] INVENTORY_AGENDA_STATUS = {"Not Started", "Work in Progress", "Completed"};
    public static final String[] ADMISSION_STATUS = {"Pending", "Selected", "Rejected"};
    public static final String[] PURCHASE_STATUS = {"Pending", "Approved", "Rejected", "Checked", "Forwarded", "Cancelled"};
    public static final String[] REQUISITION_STATUS = {"Pending", "Approved", "Rejected", "Forwarded", "Recommended"};
    public static final String[] COLLEGE_ADMISSION_STREAM = {"Management", "Science", "Biology", "Computer Science"};
    public static final String[] PRIORITY = {"Low", "Medium", "High", "Urgent"};
    public static final String[] GENERATED_VIA = {"Employee", "Product", "Student", "Vendor And Supplier"};
    public static final String[] VOUCHER_TYPE = {"Type 1", "Type 2"};
    public static final String[] ONLINE_CLASS_TYPE = {"Classroom-Wise", "Subject-Wise"};
    public static final String[] SUPPORT_TICKET_STATUS = {"Pending", "On progress", "On hold", "Solved"};
    public static final String[] TYPE = {"Question", "Problem", "Incident", "Feature Request", "Other"};
    public static final String[] QUESTION_TYPE = {"Objective", "Subjective"};
    public static final String[] MOVEMENT_TYPE = {"Assign To", "Transfer To"};
    public static final String[] JOB_TYPES = {"Full Time", "Part Time", "Fixed Term", "Temporary", "Zero Hour"};
    public static final String[] EXCULDABLE_URL_ON_REDIRECT_SESSION_ATTRIBUTE = {"admin/login", "superuser/login"};
    public static final String[] EXPENDITURE_TYPES = {"Accomodation", "Accounting & Auditing", "Award Prizes & Incentive", "Computer, Electronics & Software", "Communication", "Entertainment", "Electricity & Water Bill", "Fooding", "Furniture & Equipments", "Miscellaneous", "Maintainance", "Photocopy & Printing", "Rent", "Rental & Lease Equipment", "Scholarship", "Stationary Goods", "Transportation", "Travel", "Training & Conference", "Others"};
    public static final String[] PURCHASE_TYPES = {"Fixed", "Current Assets", "Variable Assets", "Goods", "Others"};
    public static final String[] FAMILIAR_WITH_SMART_PHONES_OPTIONS = {"Yes", "No", "Android Phones", "IOS Phones", "Android & IOS Both", "Little Familiar", "Parents", "Students", "Both Parent & Student", "Don't Know"};
    public static final String[] FEE_TYPES = {"One Time", "Monthly", "Quarterly", "Yearly", "Semester", "Trimester"};
    public static final String[] FEE_TYPES_EXC_ONE_TIME = {"Monthly", "Quarterly", "Yearly", "Semester", "Trimester"};
    public static final String[] STAFF_PAYMENT_TYPES = {"Cash", "Cheque", "Deposit to Bank Account"};
    public static final String[] NEPALI_MONTHS_ARRAY = {"Baishak", "Jestha", "Ashad", "Shrawan", "Bhadra", "Ashoj", "Kartik", "Mangsir", "Poush", "Magh", "Falgun", "Chaitra"};
    public static final String[] NEPALI_MONTHS_INC_ALL_ARRAY = {"All", "Baishak", "Jestha", "Ashad", "Shrawan", "Bhadra", "Ashoj", "Kartik", "Mangsir", "Poush", "Magh", "Falgun", "Chaitra"};
    public static final String[] PAYMENT_METHODS = {"Cash", "Cheque", "Bank Transfer", "Others"};
    public static final String[] MODE_OF_PAYMENT = {"Cash", "Cheque"};
    public static final String[] OFFERED = {"Active", "In-active"};
    public static final String[] INVENTORY_APPROVAL_STATUS = {"Approved", "Checked", "Cancelled", "Forwarded", "Rejected"};
    public static final String[] REQUISITION_APPROVAL_STATUS = {"Approved", "Checked", "Forwarded", "Recommended", "Rejected"};
    public static final String[] CHEQUE_STATUS = {"Pending", "Cashed", " Bounced"};
    public static final String[] TXN_TYPE = {"Bank", "Cash Collection", "Capital", "Employee Payroll", "Expenditure", "Fees Collection", "Interest On Loan", "Loan", "Purchase", "Sales", "Withdraw"};
    public static final String[] RANK_FORMAT = {"XX", "XX/XX", "XX out of XX"};
    public static final String[] RANK_FORMAT_VALUE = {"", "/", " out of "};
    public static final String[] PURCHASE_EXPENDITURE_OPTION = {"Purchase", "Expenditure", "Both"};
    public static final String[] URGENCY = {"Standard", "Urgent", "High"};
    public static final String[] QUESTIONS_OPTION = {"A", "B", "C", "D"};
    public static final String[] SCRAP_STATUS = {"Damaged", "Not in use", "Stolen", "Out-Dated"};
    public static final String[] ROMAN_NUMERIALS = {"i", "ii", "iii", "iv", "v", "vi", "vii", "viii", "ix", "x"};
    public static final String[] ACADEMIC_FEE_TYPES_EXC_ALL = {"Admission Fee", "University / Exam Fee", "Sports Fee", "Book / Library Fee", "Uniform Fee", "Extra Curricular Activity Fee", "Registration Fee", "Annual Fee", "Computer Fee", "Security Deposit", "Lab Fee", "Medical Service", "Transportation Fee", "Hostel Fee", "Library Charges", "Accessories / Others"};
    public static final String[] ACADEMIC_FEE_TYPES_INC_ALL = {"All", "Admission Fee", "University / Exam Fee", "Sports Fee", "Book / Library Fee", "Uniform Fee", "Extra Curricular Activity Fee", "Registration Fee", "Annual Fee", "Computer Fee", "Security Deposit", "Lab Fee", "Medical Service", "Transportation Fee", "Hostel Fee", "Library Charges", "Accessories / Others"};
    public static final String[] ACADEMIC_FEE_TYPES_INC_ALL_AND_NONE = {"None", "All", "Admission Fee", "University / Exam Fee", "Sports Fee", "Book / Library Fee", "Uniform Fee", "Extra Curricular Activity Fee", "Registration Fee", "Annual Fee", "Computer Fee", "Security Deposit", "Lab Fee", "Medical Service", "Transportation Fee", "Hostel Fee", "Library Charges", "Accessories / Others"};
    public static final String[] ACADEMIC_FEE_TYPES_INC_TUTION_FEE_AND_ALL = {"All", "Tuition Fee", "Admission Fee", "University / Exam Fee", "Sports Fee", "Book / Library Fee", "Uniform Fee", "Extra Curricular Activity Fee", "Registration Fee", "Annual Fee", "Computer Fee", "Security Deposit", "Lab Fee", "Medical Service", "Transportation Fee", "Hostel Fee", "Library Charges", "Accessories / Others"};
    public static final String[] ACADEMIC_FEE_TYPES_INC_TUTION_FEE_OTHERS_AND_ALL = {"All", "Tuition Fee", "Admission Fee", "University / Exam Fee", "Sports Fee", "Book / Library Fee", "Uniform Fee", "Extra Curricular Activity Fee", "Registration Fee", "Annual Fee", "Computer Fee", "Security Deposit", "Lab Fee", "Medical Service", "Transportation Fee", "Hostel Fee", "Library Charges", "Others Fee", "Accessories / Others"};
    public static final String[] BLOOD_GROUPS = {"A+", "B+", "O+", "AB+", "A-", "B-", "O-", "AB-"};
    public static final String[] ETHNIC_GROUPS = {"Brahmin", "Bengali", "Dalit", "Janajati", "Kshatriya", "Madhesi", "Muslim", "Vaishya", "Shudra", "Others"};
    public static final String[] FUTURE_BENEFITS = {"CIT", "PF", "Gratuity"};
    public static final String[] CERTIFICATE_THEME_TYPE = {"Achievement", "Award", "Character", "Conduct", "Transfer", "Transcript"};
    public static final String[] MAILING_AGENT = {"Gmail", "Outlook"};
    public static final String[] PURPOSE = {"Admission", "Admin", "General"};

    //array of table names which is not a part of feature in desktop or web

    public static final String[] IGNORABLE_TABLES_FROM_ADMIN = {"tbl_module_new", "tbl_book_category", "tbl_package_template_info", "tbl_sms_package", DatabaseTables.TABLE_MUNICIPALITY};

    //new nepali months arrays
    public static final String[] NEPALI_MONTH_NEW = {"Baishakh", "Jestha", "Ashadh", "Shrawan", "Bhadra", "Ashwin", "Kartik", "Mangsir", "Poush", "Magh", "Falgun", "Chaitra"};
    public static final String[] NEPALI_MONTHS_INC_ALL_NEW = {"All", "Baishakh", "Jestha", "Ashadh", "Shrawan", "Bhadra", "Ashwin", "Kartik", "Mangsir", "Poush", "Magh", "Falgun", "Chaitra"};

    //proposed
//   public static final String[] NEPALI_MONTH_NEW = {"Baishakh", "Jestha", "Aashadha", "Shrawan", "Bhadra", "Ashwin", "Kartik", "Mangsir", "Paush", "Magh", "Falgun", "Chaitra"};
//   public static final String[] NEPALI_MONTHS_INC_ALL_NEW = {"All", "Baishakh", "Jestha", "Aashadha", "Shrawan", "Bhadra", "Ashwin", "Kartik", "Mangsir", "Paush", "Magh", "Falgun", "Chaitra"};

    //new english months arrays
    public static final String[] ENGLISH_MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    public static final String[] ENGLISH_MONTHS_INC_ALL = {"All", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    //payroll fiscal Year months arrays
    public static final String[] FISCAL_YEAR_NEPALI_MONTHS = {"Shrawan", "Bhadra", "Ashwin", "Kartik", "Mangsir", "Poush", "Magh", "Falgun", "Chaitra", "Baishakh", "Jestha", "Ashadh"};
    public static final String[] FISCAL_YEAR_ENGLISH_MONTHS = {"July", "August", "September", "October", "November", "December", "January", "February", "March", "April", "May", "June"};

    //chapter types
    public static final String[] CHAPTER_TYPES = {"Chapter", "Topic", "Sub-Topic"};

    //online admission form related arrays
    public static final String[] GENDER = {"Male", "Female", "Others"};
    public static final String[] RELIGION = {"Buddhist", "Christian", "Hindu", "Islam", "Others"};
    public static final String[] LIVES_WITH = {"Father and Mother", "Father Only", "Mother Only", "Local Guardian"};
    public static final String[] MARITAL_STATUS = {"Married", "Separated", "Single Parent"};
    public static final String[] COLLEGE_ADMISSION_MARITAL_STATUS = {"Married", "Unmarried", "Others"};
    public static final String[] BLOOD_GROUP = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
    public static final String[] VACCINATED_AGAINST = {"DPT", "BCG", "Hepatitis B", "Measles"};
    public static final String[] OCCUPATION = {"Business", "Service"};
    public static final String[] DRIVER_CATEGORY = {"Bus Driver", "Van Driver", "Driver"};
    public static final String[] REFERRED_SOURCE = {"Family", "Friends", "Relatives", "Social Media", "School Website"};
    public static final String[] CHILDEN_ENJOY_ITEMS = {"Games and Sports", "Reading", "Writing", "Social Interaction", "Creative and Extensive Arts"};
    public static final String[] LOOKS_AFTER_CHILD_ABSENCE = {"Relatives", "Grand Parents"};
    public static final String[] PROVINCE = {"1", "2", "3", "4", "5", "6", "7"};
    public static final String[] PROVINCE_NAME = {"Province No. 1", "Province No. 2", "Bagmati Province", "Gandaki Province",
            "Province No. 5", "Karnali Province", "Sudurpashchim Province"};
    public static final String[] A_LEVEL_GROUP = {"Science", "Non-Science"};
    public static final String[] APPLY_FOR = {"School", "A-Level", "NEB(+2)"};
    public static final String[] EXITED_APPLICATION = {"Both", "Exited", "On Progress"};
    public static final String[] SCIENCE_GROUP = {"Physics Chemistry Biology (PCB)", "Physics Chemistry Computer (PCC)", "Physics Chemistry Economics (PCE)", "Physics Chemistry Further Math (PCF)"};
    public static final String[] NON_SCIENCE_GROUP = {"Economics Business Accounting (EBA)", "Economics Business Computer (EBC)"};
    public static final String[] NEB_PLUS_2 = {"Physics Chemistry Biology (PCB)", "Physics Chemistry Math (PCM)"};

    //online admission +2 admission form
    public static final String[] FATHEROCCUPATION = OCCUPATION;
    public static final String[] MOTHEROCCUPATION = {"Housewife", "Business", "Service"};
    public static final String[] CO_CURRICULAR_ACTIVITIES = {"Games and Sports", "Literary Activities", "Music", "Adventure sports", "Travelling", "Photography/ Media"};

    public static final String[] FEES_TYPE = {"One TIme", "Multiple Time"};
    public static final String[] BILL_PRINT_PAPER_SIZES = {"A4", "A5", "B4"};
    public static final String[] MARKSHEET_PRINT_PAPER_SIZES = {"A4", "A5"};

    //transportation static fee types
    public static final String[] TRANSPORTATION_STATIC_FEE_TYPES_NP = {"Admission Fee", "Route Fee", "Baishakh", "Jestha", "Ashadh", "Shrawan", "Bhadra", "Ashoj", "Kartik", "Mangsir", "Poush", "Magh", "Falgun", "Chaitra"};
    public static final String[] TRANSPORTATION_STATIC_FEE_TYPES_EN = {"Admission Fee", "Route Fee", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    //New Ethnic group
    public static final String[] ETHNIC_GROUP_NEW = {"Amat", "Athpahariya", "Badhaee", "Badi", "Bahing", "Bantaba", "Bantar/Sardar", "Barai", "Bengali", "Bhote", "Bhujel", "Bin", "Bote", "Brahmin", "Brahmu/Baramo", "Byansi/Sauka", "Chamar/Harijan/Ram", "Chamling", "Chepang", "Chhantyal", "Chidimar", "Damai/Dholi", "Danuwar", "Darai", "Dev", "Dhandi", "Dhankar/Dharikar", "Dhanuk", "Dhimal", "Dhobi", "Dhuniya", "Dolpo", "Dom", "Dura", "Dusadh/Pasawan/Pasi", "Gaderi/Bhediyar", "Gaine", "Gangai", "Ghale", "Gurung", "Hajam/Thakur", "Halkhor", "Hayu", "Hyolmo", "Janajati", "Jhangar/Uraon", "Jirel", "Kahar", "Kalar", "Kalwar", "Kamar", "Kami", "Kanu", "Kanu/Haluwai", "Kathabaniyan", "Kayastha", "Kewat", "Khaling", "Khatwe", "Khawas", "Kisan", "Koche", "Koiri/Kushwaha", "Kori", "Kshatriya", "Kulung", "Kumal", "Kumhar", "Kurmi", "Kusunda", "Lepcha", "Lhomi", "Lhopa", "Limbu", "Lodh", "Lohar", "Lohorung", "Madhesi", "Magar", "Majhi", "Mali", "Mallaha", "Marwadi", "Meche", "Mewahang", "Munda", "Musahar", "Musalman/Nepali Muslims", "Nachhring", "Natuwa", "Newar", "Nuniya", "Nurang", "Pahari", "Pathakatta/Kushwadia", "Punjabi", "Rai", "Rajbanshi", "Rajbhar", "Rajdhob", "Raji", "Rajput/Terai Kshetriya", "Raute", "Sampang", "Santhal", "Sanyasi/Dasnami", "Sarabaria", "Sarki", "Sherpa", "Shudra", "Sonar", "Sudhi", "Sunwar", "Tajpuriya", "Tamang", "Tatma/Tatwa", "Teli", "Thakali", "Thakuri", "Thami", "Tharu", "Thulung", "Topkegola", "Vaishya", "Walung", "Yadav", "Yakkha", "Yamphu"};

    // Mother tongue new
    public static final String[] MOTHER_TONGUE = {"Bhojpuri", "Bhujel", "Dateli", "Hindi", "Maithali", "Magar", "Nepali", "Newari", "Others", "Rai", "Sanskrit", "Tamang", "Tharu", "Urdu"};

    // Reason for leaving the tenant
    public static final String[] REASON = {"Financial Problem", "Family Problem", "Transportation Problem", "No Quality Education", "Management Problem", "Higher Fees", "Extra Facilities Problem", "No Tech Friendly", "Environment Problem", "Hostel Problem", "Lack Of Infrastructure", "Transferred to Another School", "Ragging & Teasing", "Descrimination"};

    //For Advanced Marital Status
    public static final String[] MARITAL_STATUS_ADVANCED = {"Single", "Married", "Divorced", "Separate"};

    // for personal identification
    public static final String[] PERSONAL_IDENTIFICATION = {"Citizenship", "Passport", "Driving license", "Others"};

    // for staff appointment type info
    public static final String[] APPOINTMENT_TYPE = {"Ad-hoc", "Contract", "Full Time", "Part Time", "Permanent", "Probationary", "Temporary"};

    // for staff status info
    public static final String[] CURRENT_EMPLOYEE_STATUS = {"Working", "Left"};
    public static final String[] TEAHCER_TYPE = {"Academic", "Non-Academic"};

    //Teacher Qualification
    public static final String[] TEACHER_QUALIFICATION = {"SLC/SEE", "Intermediate", "Bachelor", "Master", "Mphil", "Phd."};

    //Teacher Stream
    public static final String[] TEACHER_STREAM = {"Arts", "Commerce", "Education", "English", "Engineering", "Humanities", "I com", "Law", "MBBS", "MBA", "Mathematics", "Medical and Allied Science (MASS)", "Oversees", "Science", "Science & Technology", "Social Work"};

    //Teacher rank
    public static final String[] TEACHER_RANK = {"Distinction", "1st", "2nd", "3rd", "Failed"};
    public static final String[] SCHOOL_TYPES = {"Community (Aided)", "Community (Managed)", "Community (Teacher Aid)", "Community (Unaided)", "Institutional (Private)", "Institutional (Public)", "Institutional (Company)", "Public with religious", "Public", "Private", "Madrasa", "Gumba", "Aashram", "SOP/FSP", "Community ECD", "Other"};
    public static final String[] ECD_EXPERIENCE = {"All", "Yes", "No"};
    public static final String[] REGION = {"Rural", "Urban"};

    //for student disability info
    public static final String[] DISABILITY = {"N/A", "Blind", "Deaf", "Deaf and Blind", "Low Vision", "Mental", "Multiple Disability", "Physical", "Speech Impairment", "Others"};
    public static final String[] SHIFT_TYPE = {"General", "First Shift", "Second Shift", "Day Shift", "Morning Shift", "Evening Shift", "Night Shift"};
    public static final String[] AGE_GROUP = {"0-5", "6-14", "15-25", "26-40", "Above 40"};
    public static final String[] TEACHER_AGE_GROUP = {"18-29", "30-39", "40-49", "50-60", "Above 60"};
    public static final String[] DEDUCTION_TYPE = {"Late In", "Early Out"};
    public static final String[] SALARY_DEDUCTION_TYPE = {"Flat", "Half Day", "No. of Days", "Hourly"};
    public static final String[] INCREMENTAL_PERIOD = {"Monthly", "Yearly", "None"};
    public static final String[] LEAVE_SCHEME = {"Contract Scheme", "General Scheme", "Probation Scheme", "Trainee Scheme"};
    public static final String[] KAJ_TYPE = {"Official Visit", "Kaj"};
    public static final String[] PAYROLL = {"Salary", "Hourly", "Commission", "Volunteer"};
    public static final String[] APPLICABLE_GENDER = {"All", "Male", "Female", "Others"};
    public static final String[] APPROVAL_STATUS = {"Pending", "Approved", "Rejected"};
    public static final String[] TRAINING_APPROVAL_STATUS = {"Postponed", "Approved", "Cancelled"};
    public static final String[] INSURANCE_TYPE = {"Health Insurance", "Life Insurance", "Disability Insurance", "Vision Insurance", "Other"};
    public static final String[] HOLIDAY_TYPE = {"Academic", "National", "Religious", "Province", "Regional", "Public", "Optional", "Local ", "Season", "Observance", "Other"};
    public static final String[] DAY_LEAVE = {"Half Day", "Full Day"};
    public static final String[] WEEKEND = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    public static final String[] WORKING_DAYS = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    public static final String[] ALLOWANCE_ON = {"Amount", "Percentage"};
    public static final String[] DEDUCTION_REBATE = {"Deduction", "Rebate", "Bonus"};
    public static final String[] ALLOWANCE_PAID_ON = {"N/A", "Daily Basis", "Basic Salary", "Basic+Grade", "Gross Salary"};
    public static final String[] SALARY_PAY_ON = {"N/A", "Basic Salary", "Basic+Grade", "Gross Salary"};
    public static final String[] MISCELLANEOUS_PAY_ON = {"N/A", "Basic Salary", "Basic+Grade", "Gross Salary", "Period Basis"};
    public static final String[] PAY_MODE = {"Cash", "Cheque", "Online Transfer", "Bank Deposit", "Other"};
    public static final String[] TAX_TYPE = {"Normal", "SSF"};
    //    public static final String[] SALARY_TYPE = {"Daily", "Monthly", "Hourly", "Weekly"};
    public static final String[] SALARY_TYPE = {"Monthly"};
    public static final String[] SEPARATION_MODE = {"AWOL", "CONTRACT EXPIRY", "DEPORTED", "DEPORTED", "EXPIRED", "OTHERS", "RESIGNED", "RETIRED", "SICK", "TERMINATED", "TRANSFERRED", "WORKING"};
    public static final String[] DOCUMENT_TYPE = {"Citizenship", "Passport", "Driving-licence", "Educational Certificates", "Others"};
    public static final String[] EMPLOYEE_DOCUMENT_TYPE = {"Citizenship", "Driving-licence", "Educational Certificates", "Passport", "PAN", "Signature", "Others"};
    public static final String[] AMOUNT_OR_PERCENTAGE = {"Percentage", "Amount"};
    public static final String[] MARITAL_STATUS_PAYROLL = {"All", "Single", "Married", "Divorced", "Separate"};
    public static final String[] SURVEY_LEVEL_TYPE = {"Department-survey", "Designation-survey", "Employee-survey"};
    public static final String[] LEVEL_TYPE = {"Department", "Designation", "Employee"};
    public static final String[] SCHOOL_SUPPORT_TICKET_LEVEL_TYPE = {"Department", "Employee"};
    public static final String[] LEVEL_TYPE_WITH_SUB_DEPARTMENT = {"Department", "Designation", "Employee", "Sub-Department"};
    public static final String[] STUDENT_LEVEL_TYPE = {"Grade", "Classroom", "Student", "Group"};
    public static final String[] ACCOUNT_TYPE = {"Company", "Employee", "Other"};
    public static final String[] BANK_ACCOUNT_TYPE = {"Salary", "Current", "Saving"};
    public static final String[] DEALER_ROLES = {"marketing officer", "distributor", "dealer", "agent", "other"};
    public static final String[] PERIOD_TYPES = {"Assembly", "Class", "Practical", "Other"};
    public static final String[] SURVEY_QUESTION_TYPES = {"Question"};
    public static final String[] SHOW_ROWS_LIMIT = {"10", "25", "50", "100"};
    public static final String[] VALIDITY_TYPE = {"One Time", "Daily", "Weekly", "Monthly"};
    public static final String[] JOB_APPLICATION_STATUS = {"Applied", "Pending", "Interview", "Hired", "Rejected", "Expired"};
    public static final String[] CALL_RELATED = {"School", "Lead", "DAR", "Customer", "Client", "Others"};
    public static final String[] CALL_TYPE = {"Account Call", "Proposal", "Estimate", "General", "Cold Call", "Satisfaction", "Review Call", "Referral", "Follow-up Call"};
    public static final String[] CALL_DIRECTIONS = {"Inbound", "Outbound"};
    public static final String[] CALL_LOG_TYPE = {"Call", "SMS"};
    public static final String[] CALL_STATUS = {"Completed", "Busy", "No-answer", "Cancelled", "Not-reachable"};
    public static final String[] ADMIN_TASK_CATEGORY = {"To do", "Requirements", "Support", "Implementation", "Other"};
    public static final String[] ADMIN_TASK_PROGRESS = {"Not started", "In progress", "Completed"};
    public static final String[] ADMIN_SCHOOL_CONTACT_TYPE = {"Principal", "IT Officer", "Academics", "Inventory", "Account", "Billing", "HR", "General"};
    public static final String[] NETWORK_OPERATOR = {"NTC", "NCELL", "SMART", "UTL", "Other"};

    // For Teacher level
    public static final String[] TEACHER_LEVEL = {"Basic(1-8)", "ECD/PPC", "Higher Secondary", "Lower basis", "Secondary", "Secondary(9-12)", "Upper basis", "Without PPC/ECD"};

    // For Teaching Language
    public static final String[] TEACHING_LANGUAGE = {"Awadhi", "Bajjika", "Bhojpuri", "Bhujel", "Dateli", "English", "Hindi", "Magar", "Maithali", "Majhi", "Nepali", "Newari", "Others", "Rai", "Rajbansi", "Sanskrit", "Tamang", "Thami", "Tharu", "Urdu", "Bengali"};

    // For staff nationality
//    public static final String[] NATIONALITY = {"Afghan/Afghani", "Bangladeshi", "Chinese", "Indian", "Kazakh/Kazakhstani ", "Nepali", "Pakistani", "Sri Lankan"};
    public static final String[] NATIONALITY = {"Afghan/Afghani", "Bangladeshi", "Chinese", "Indian", "Nepali", "Pakistani", "Sri Lankan", "Mozambique"};

    //for teacher details
    public static final String[] TEACHER_DETAIL = {"Name", "Gender", "Caste", "Mother Tongue", "Nationality", "Phone No. ", "Date Of Birth", "Citizenship No", "Teacher Type", "Qualification", "Stream", "Teacher Level", "Rank"};

    //For student dropout details
    public static final String[] STUDENT_DROPOUT_DETAILS = {"Name", "Gender", "Caste", "Disability", "DOB", "Dropout Date", "Dropout Reasons", "Guardian Name", "Guardian Phone No"};
    public static final String[] STUDENT_ALUMINI_DETAILS = {"Name", "Gender", "Caste", "Disability", "DOB", "Left Date", "Reasons", "Guardian Name", "Guardian Phone No"};

    //For tenant type report
    public static final String[] SCHOOL_TYPE = {"Address", "Phone No.", "Principal Name", "Principal Phone No.", "Established Year"};
    public static final String[] CONSTRUCTION_TYPE = {"New Building", "Building Rehabilitation", "New Classroom", "Classroom Rehabilitation", "Toilet", "Girls Toilet", "Water", "Book Corner"};
    public static final String[] EVENTS_TYPE = {"Total opening days", "Teaching", "Exams", "Extra Curricular", "Public Holidays", "Festivals", "Other Activities"};
    public static final String[] YEAR = {"2076", "2075", "2074", "2073", "2072", "2071", "2070", "2069"};
    public static final String[] CURRICULUM_AND_BOOKS_DETAILS_TYPE = {"Text Books Number", "Teacher's Guide Number", "Child Material Number", "Book Corner Number", "Availability of Curriculum Number", "Local Curriculum Number", "Reference Materials Number"};
    public static final String[] CURRICULUM_DAETAILS = {"ECD", "Primary", "L.Sec", "Sec", "H.Sec"};
    public static final String[] YES_NO = {"Yes", "No", "Yes", "No", "Yes", "No", "Yes", "No", "Yes", "No"};
    public static final String[] CONSTRUCTION_DETAILS = {"DEO", "VDC/NP/DDC", "Other Agencies"};
    public static final String[] CONSTRUCTION_YES_NO = {"Yes", "No", "Yes", "No", "Yes", "No"};
    public static final String[] HOMEWORK_CHECK_STATUS = {"Completed", "Incomplete", "Late Submit", "Half Completed", "Not Done"};
    public static final String[] SCHOOL_DAYS = {"Planned", "Actual"};
    public static final String[] SCHOOL_DAYS_YES_NO = {"Yes", "No", "Yes", "No"};
    public static final String[] STUDENT_TYPE = {"Boarders", "Day Boarders", "Day Scholar"};
    public static final String[] DAILY_REPORT_STATUS = {"Interested", "Not Interested", "Quotation Sent", "Requested Quotation", "Requested Demo", "Highly Interested", "Motivated", "Impressed", "In Program", "Need Counselling", "Next Year"};
    public static final String[] ACCREDITATION_TYPE = {"Regional", "National", "Programmatic"};
    public static final String[] EVENT_NEWS_BLOG_OPTION = {"Event", "News", "Blog", "Notice"};
    public static final String[] JOB_LEVEL = {"Senior", "Mid-Level", "Junior"};
    public static final String[] JOB_TYPE = {"Full-Time", "Part-Time"};
    public static final String[] CONTACT_PERSON = {"Father", "Mother", "Local Guardian"};
    public static final String[] BALANCE_TYPE = {"Advance", "Dues"};
    public static final String[] EMPLOYEE_BALANCE_TYPE = {"Advance", "Payable"};
    public static final String[] MISCELLANEOUS_BALANCE_TYPE = {"Deduction", "Payable"};
    public static final String[] DIVISIONS = {"Distinction", "First Division", "Second Division", "Third Division", "Considered Pass", "Failed"};
    public static final String[] GRADES = {"A+", "A", "B+", "B", "C+", "C", "D+", "D", "E"};
    //For Sainik +2 form
    public static final String[] GRADES_V2 = {"A+", "A", "B+", "B", "C+", "C", "D+", "D"};

    public static final String[] HOSTEL_STATIC_FEE_TYPES_NP = {"Admission Fee", "Baishakh", "Jestha", "Ashadh", "Shrawan", "Bhadra", "Ashoj", "Kartik", "Mangsir", "Poush", "Magh", "Falgun", "Chaitra"};
    public static final String[] HOSTEL_STATIC_FEE_TYPES_EN = {"Admission Fee", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    //student leave types
    public static final String[] STUDENT_LEAVE_TYPE = {"Sick Leave", "Others", "Cultural & Ritual Ceremony"};

    //online admission themes
    public static final String[] ONLINE_ADMISSION_THEMES = {"General", "School", "College"};
    public static final String[] APPLIED_FOR = {"School", "College(+2)"};
    public static final String[] STREAMS = {"Management", "Humanities", "Science", "BBS"};

    //product type
    public static final String[] PRODUCT_TYPES = {"Assets Durable", "Consumable", "Construction", "Durable", "Expenditure",
            "Non-Consumable", "Non-Durable", "Fixed Asset", "Repair and Maintenance", "Service", "Others"};
    public static final String[] YES_NO_BOTH = {"Both", "Yes", "No"};

    //for teamId 618 (godawari)
    public static final String[] HOSTEL_TYPE = {"Day Borders", "Hostel"};
    public static final String[] PACKAGE_TYPE = {"Both", "E-Learning", "E-Library"};
    public static final String[] FIXED_ASSETS_TYPE = {"KA", "KHA", "GA", "GHA", "NGA"};

    //Sort students in assign studentattendance
    public static final String[] SORT_STUDENT_BY = {"Name", "Roll"};
    public static final String[] FINE_TYPE = {"One Time", "Daily", "Weekly", "Monthly", "Yearly"};

    public static final Float[] MARKSCARRIED = {0.5F, 1F, 1.5F, 2F, 2.5F, 3F, 3.5F, 4F, 4.5F,
            5F, 5.5F, 6F, 6.5F, 7F, 7.5F, 8F, 8.5F, 9F, 9.5F, 10F, 10.5F, 11F, 11.5F, 12F, 12.5F, 13F, 13.5F, 14F,
            14.5F, 15F, 15.5F, 16F, 16.5F, 17F, 17.5F, 18F, 18.5F, 19F, 19.5F, 20F};

    public static final long[] CONSTRUCTION_YEAR = {2075, 2076, 2077, 2078};

    public static final String[] ACADEMIC_BILL_PAYMENT_METHODS = {"Bank Deposit", "Bank Transfer", "Cash", "Cheque", "Connect IPS", "eSewa", "IME Pay", "Khalti", "Others"};
    public static final String[] ADMIN_EMAIL_PURPOSE = {"Accounts", "Career", "General", "Greetings", "Info", "Support", "Others"};

    //for teacher details
    public static final String[] TEACHER_DETAIL_NEW = {"Name", "Gender", "Caste", "Province", "District", "Mother Tongue", "Nationality", "Phone No. ", "Date of Birth", "Citizenship No",
            "Position", "Qualification", "Stream", "Teacher Level", "Rank"};

    //For Campus Publication
    public static final String[] PUBLICATION_TYPE = {"Professional", "Memorial", "Bulletin", "Others"};
    public static final String[] PUBLICATION_PERIOD = {"Weekly", "Monthly", "Half-Annual", "Annual", "Biannual", "Others"};
    public static final String[] FISCAL_YEAR = {"2074/2075", "2075/2076", "2076/2077", "2077/2078", "2078/2079", "2079/2080"};

    public static final String[] STUDENT_ACTIVE_TYPES = {"All", "Active", "Alumni", "Dropout"};

    //for custom list pagination limit
    public static final List<Integer> LIMITS = java.util.Arrays.asList(10, 25, 50, 100);

    //For Daily PLans
    public static final String[] PERIODS = {"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th"};
    public static final String[] DAILY_PLAN_TYPES = {"Basic", "Advance"};

    public static final String[] WITHDRAW_ISSUED_FOR = {"Cash", "Other"};

    public static final String[] CHANGELOG_TYPES = {"EAP", "Beta", "Release candidate", "Release", "Post-release fixes"};
    public static final String[] CHANGELOG_LABEL = {"New Feature", "Bug Fix", "Optimization", "Design Update", "Performance Tuning"};
    public static final String[] CHANGELOG_RELEASE_STATUS = {"Feature", "Bug", "Usability", "Cosmetics", "Performance", "Exception"};

    public static String[] getMonthsArray(String operationDateSetting, boolean includeAll) {
        String[] months;
        if (operationDateSetting.equalsIgnoreCase("np")) {
            months = includeAll ? NEPALI_MONTHS_INC_ALL_NEW : NEPALI_MONTH_NEW;
        } else {
            months = includeAll ? ENGLISH_MONTHS_INC_ALL : ENGLISH_MONTHS;
        }
        return months;
    }

    public static final String[] SIGNATURE_BLOCK_TYPE = {"Employee", "Seal", "Date of Issue"};

    //Library Role
    public static final String[] ROLE = {"Student", "Employee"};

    // evaluation ratings
    public static final String[] REVIEW_ON_RATING = {"All", "None", "First And Last", "Custom"};
    public static final String[] TWO_STAR_RATING = {"Excellent", "Very good"};
    public static final String[] THREE_STAR_RATING = {"Excellent", "Very good", "Good"};
    public static final String[] FOUR_STAR_RATING = {"Excellent", "Very good", "Good", "Satisfactory"};
    public static final String[] FIVE_STAR_RATING = {"Excellent", "Very good", "Good", "Satisfactory", "Unsatisfactory"};

    public static final String[] GROUP_FILTER_TYPE = {"Enroll", "Active", "Left"};

    //Sainik College Form
    public static final String[] CATEGORY_OF_APPLICANT = {"Nepali Army", "Open(खुला)", "Mahavidyalaya Staff"};
    public static final String[] CATEGORY_OF_APPLICANT_V2 = {"Nepali Army", "Open(खुला)", "Mahavidyalaya Staff", "Army applying for Civil Quota"};
    public static final String[] PRIVILEDGED_GROUP = {"Birgati", "Sahara Jarurat", "Kartabaya Palan/Sarkari Kamkaj Silsilama Mrityu", "None"};
    public static final String[] PRIVILEDGED_GROUP_V2 = {"In Service", "Birgati", "Sahara Jarurat", "Kartabaya Palan/Sarkari Kamkaj Silsilama Mrityu", "Retired"};
    public static final String[] SUB_CATEGORIES = {"Officer", "JCO", "Other Ranks", "Followers"};
    public static final String[] OFFICER = {"General", "Lt General", "Maj General", "Brig General", "Colonel", "Lt Colonel",
            "Major", "Captain", "Lieutenant", "2/Lieutenant"};
    public static final String[] JCO = {"Hon/Captain", "Hon/Lieutenant", "Subedar", "Jamadar"};
    public static final String[] OTHER_RANKS = {"Hudda", "Nayak", "L/Nayak", "Sipahi"};
    public static final String[] FOLLOWERS = {"Hudda", "Sipahi"};
    public static final String[] RELATED_COLLEGE = {"Sainik Awasiya Mahavidyalaya, Bhakatpur", "Sainik Awasiya Mahavidyalaya, Pokhara",
            "Sainik Awasiya Mahavidyalaya, Dharan", "Sainik Awasiya Mahavidyalaya, Surkhet", "Sainik Awasiya Mahavidyalaya, Teghari",
            "Sainik Awasiya Mahavidyalaya, Chitwan", "Sainik Awasiya Mahavidyalaya, Bardibas", "Bijeshwori Gyan Mandir Sainik Mahavidyalaya, Bijeshwori",
            "Ripumardini Sainik Vidyalaya, Bansbari", "Swettara Montessori, Chhauni"};

    public static final String[] VOUCHER_GENERATED_VIA = {"Advance Salary", "Expenditure", "Inventory Issued", "Payroll", "Payroll Payment",
            "Purchase", "Purchase Payment", "Sales", "Sales Payment"};

    //Donor Donation type
    public static final String[] BOOK_DONATION_TYPE = {"Donation", "Gift", "Others"};

    //School Task Recurrence type
    public static final String[] RECURRENCE_TYPE = {"Daily", "Weekly", "Monthly"};
    public static final Integer[] RECURRENCE_TIME = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    public static final Integer[] DAYS_IN_MONTH = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
    public static final String[] MONTHLY_RECURRENCE_TIME = {"First", "Second", "Third", "Fourth", "Last"};
    public static final String[] VOUCHER_INCOME_EXPENDITURE_TYPE = {"Advance", "Advance Cleared", "Expenditure", "Income", "Miscellaneous"};
    public static final String[] PAY_ON = {"Basic Salary", "Basic+Grade"};

    //App Privilege Types, Unique keys and icons
    public static final String[] APP_PRIVILEGE_STUDENT = {"My Profile", "Homework", "E-Learning", "Timetable",
            "Digital Card", "Notices", "Notification", "Attendance", "Communication", "Online Classes", "Online Exam",
            "Exam Result", "Exam Routine", "Library", "Credit Bills", "Payment", "Statements",
            "Complaint", "Academic Calender", "My Bus", "Leave", "Upcoming Exam"};

    public static final String[] APP_PRIVILEGE_STUDENT_UNIQUE_KEY = {"MY_PROFILE", "HOMEWORK", "E-LEARNING", "TIMETABLE",
            "DIGITAL_CARD", "NOTICES", "NOTIFICATION", "ATTENDANCE", "COMMUNICATION", "ONLINE_CLASSES", "ONLINE_EXAM",
            "EXAM_RESULT", "EXAM_ROUTINE", "LIBRARY", "CREDIT_BILLS", "PAYMENT", "STATEMENT",
            "COMPLAINT", "ACADEMIC_CALENDER", "MY_BUS", "LEAVE", "UPCOMING_EXAM"};

    public static final String[] APP_PRIVILEGE_STUDENT_ICONS = {"perm_identity", "edit", "all_inbox", "date_range",
            "explicit", "question_answer", "circle_notifications", "done_outline", "feedback", "stay_primary_portrait", "toll",
            "forum", "assignment", "book", "toll", "payment", "receipt",
            "gavel", "article", "directions_bus", "directions_run", "circle_notifications"};


    public static final String[] APP_PRIVILEGE_EMPLOYEE = {"Homework", "Timetable", "Online Class", "Profile", "Digital Card",
            "Notice", "Notification", "Attendance", "Communication", "Online Exam Marks", "Lesson Plan", "Library", "Payroll", "Academic Calender", "Leave", "Exam Result"};

    public static final String[] APP_PRIVILEGE_EMPLOYEE_UNIQUE_KEY = {"HOMEWORK", "TIMETABLE", "ONLINE_CLASS", "PROFILE", "DIGITAL_CARD",
            "NOTICE", "NOTIFICATION", "ATTENDANCE", "COMMUNICATION", "ONLINE_EXAM_MARKS", "LESSON_PLAN", "LIBRARY", "PAYROLL", "ACADEMIC_CALENDER", "LEAVE", "EXAM_RESULT"};

    public static final String[] APP_PRIVILEGE_EMPLOYEE_ICONS = {"edit", "date_range", "stay_primary_portrait", "perm_identity", "explicit",
            "question_answer", "circle_notifications", "done_outline", "feedback", "filter_9", "library_books", "book", "payment", "date_range", "directions_run", "explicit"};

    public static final String[] APP_PRIVILEGE_ADMIN = {"Communication", "Exam", "Library", "Homework", "Notice"};
    public static final String[] APP_PRIVILEGE_ADMIN_UNIQUE_KEY = {"COMMUNICATION", "EXAM", "LIBRARY", "HOMEWORK", "NOTICE"};
    public static final String[] APP_PRIVILEGE_ADMIN_ICONS = {"forum", "play_arrow", "book_online", "book", "all_inbox"};
    public static final String[] APP_PRIVILEGE_TEACHER = {"Communication", "Exam", "Library", "Homework", "Notice"};
    public static final String[] APP_PRIVILEGE_TEACHER_UNIQUE_KEY = {"COMMUNICATION", "EXAM", "E-LIBRARY", "HOMEWORK", "NOTICE"};
    public static final String[] APP_PRIVILEGE_TEACHER_ICONS = {"forum", "play_arrow", "book_online", "book", "all_inbox"};
    public static final String[] SOLD_TO = {"Others", "Student", "Employee",};
    public static final String[] PRODUCT_CONDITION = {"Damage", "Excellent", "Expired", "Good", "Lost", "Stolen"};

    public static final String[] STUDENT_ENROLLMENT_CATEGORY = {"Others", "EDJ", "Dalit", "Madhesi"};

    public static final String[] ISSUE_VISIBLE_TO = {"Issue Reader", "Public", "Private"};
    public static final String[] ISSUE_STATE = {"Pending", "Under Review", "Approved",};

    public static final String[] ADMIN_WEBINAR_TYPE = {"Live Training", "On-Demand Training", "Business Development Training"};
    public static final String[] ISSUE_RELATED_TO = {"Is duplicate of", "Has duplicate", "Blocks", "Blocked by", "Precedes", "Follows", "Copied to", "Copied from"};
    public static final String[] ISSUE_DOMAIN = {"edigitalnepal.com", "edigitalnepal.com.np", "Other"};
    public static final String[]  DOMAIN_INSTANCE = {"smsbeta", "sos", "tmp",  "demo", "qa", "millsberry", "cg", "premier", "e", "ebeta", "edemo", "kcc"};
    public static final String[] SUBSCRIPTION_TYPE = {"Monthly", "Trimonthly", "Half-yearly", "Yearly"};

    public static final String[] TRANSACTIONS_LOG_TYPES = {"All", "Academic Fee", "Bank Transactions", "Staff Payroll", "Expenditure", "Purchase", "Sales"};

    public static final String[] CASE_AND_COMPLAINT_TYPES = {"Case", "Complaint", "Suggestion", "Feedback", "Request", "Others"};
    public static final String[] CASE_AND_COMPLAINT_ICONS = {"gavel", "mode_edit", "format_list_numbered_rtl", "feedback", "flag", "more_horiz"};

    public static final String[] KCC_STREAM = {"Science", "Management", "Arts", "Education", "Diploma", "A-Level", "IT", "Others"};
    public static final String[] NOTEBOOK_TYPE = {"Text", "Checklist", "Photo", "File", "Contact", "Audio", "Sketch", "Link"};
    public static final String[] FEEDBACK_TYPE = {"Feedback", "Report an Issue" };

    public static final String[] SXC_ALEVEL_TYPE = {"A-Level", "AS-Level", "A2-Level"};


}
