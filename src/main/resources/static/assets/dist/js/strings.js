const SOMETHING_WENT_WRONG = "Sorry but something went wrong. Please try again later.";
const NOT_ENOUGH_PERMISSION = "Sorry, You dont have required permission to perform this action.";
const CANCEL_CONFIRMATION = "Are you sure you want to cancel this item ?";
const DELETE_SUPER_USER_EXAM_SUBJECT = "Deleting this subject will hamper generated result. You must update marks ledgers related to this exam. Do you want to continue ?";
const PAYROLL_SAVE_CONFIRMATION = "Surely you don't want to continue without SAVING PAYROLL? This action cannot be undone. [Yes I do, leave me alone] [Oh right, thanks for reminding me]";
const CONFIRM_DELETE = "Confirm Delete";
const CONFIRM_CANCEl = "Confirm Cancel";
const CONFIRM_PUBLISHED = "Are you sure you want to publish the result?";
const CONFIRM_UNPUBLISHED = "Are you sure you want to unpublish the result?";
const SAVE_CONFIRMATION = "Are you sure you want to save?";
const DELETE_CONFIRMATION = "Are you sure you want to delete?";
const EDIT_CONFIRMATION = "Are you sure you want to update?";
const IMPORT_CONFIRMATION = "Are you sure you want to import all?";
const FORM_IS_NOT_VALID = "Form is not valid for submission.";
const APPROVE_CONFIRMATION = "Are you sure you want to approve?";
const REJECT_CONFIRMATION = "Are you sure you want to reject?";
const NO_DATA_AVAILABLE = "No data available for selected criteria.";
const CONFIRM_SAVE = "Confirm Save";
const CONFIRM_IMPORT = "Confirm Import";
const CONFIRM_EDIT = "Confirm Update";
const SAVING = "Saving";
const UPDATING = "Updating";
const PLEASE_WAIT = "Please wait";
const NO_DATA_AVAILABLE_ROW = `<tr class="text-center"><td colspan="100%">${NO_DATA_AVAILABLE}</td></tr>`;
const PRIVATE = "<i class='material-icons'>public_off</i> Private";
const PUBLIC = "<i class='material-icons'>public</i> Public";
const OTHERS_INCOME = "Others";
const OTHERS_INCOME_ID = 0, FINE_ID = -4;
const JUSTIFY_CONTENT_LEFT = "justify-content-left";
const JUSTIFY_CONTENT_END = "justify-content-end";
const JUSTIFY_CONTENT_CENTER = "justify-content-center";
const WORD_BREAK_ALL = "word-break-all";

const ROLE_EMPLOYEE = "Employee";
const ROLE_STUDENT = "Student";

const DELETE_TEXT = "Delete";
const EDIT_TEXT = "Edit";
const UPDATE_TEXT = "Update";
const VIEW_DETAILS_TEXT = "View Details";
const PRINT_TEXT = "Print";
const RESULT = "Result";
const FILTERING = "Filtering";
const RESETING = "Reseting";
const PROCEEDING = "Proceeding";
const DELETING = "Deleting";
const FETCHING = "Fetching";
const GENERATING = "Generating";
const IMPORTING = "Importing";

const DATE_FORMAT = "YYYY-MM-DD", TIME_FORMAT = "HH:mm:ss", TIME_FORMAT_SHORT = "HH:mm",
    DATE_TIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;

// icons
const ADD_ICON = "add";
const EDIT_ICON = "edit";
const SAVE_ICON = "save";
const UPDATE_ICON = "update";
const DELETE_ICON = "delete_sweep";
const PRINT_ICON = "print";
const DETAILS_ICON = "visibility";
const ASSIGN_ICON = "library_books";
const APPROVE_REJECT_ICON = "done_all";
const GENERATE_ICON = "bolt";
const REPLY = "reply_all";
const TRANFER_TO = "trending_flat";
const VIEW_DETAIL = DETAILS_ICON;
const ASSIGN = "library_books";
const SECURITY = "security";
const MONEY = "money";
const ARROW_UPWARD = "arrow_upward";
const VIEW_PAGE = DETAILS_ICON;
const PRINT = "print";
const CLOSE_ICON = "close";
const CLOSE = CLOSE_ICON;
const IN = "check_circle_outline";
const SETTINGS = "settings";
const DONE = "done";
const MARKS_CHECK_ICON = IN;
const SEARCH = "search";
const LOCK_OPEN = "lock_open";
const BLOCK = "block";
const REMOVE_ICON = "remove";
const DEFAULT_SELECT_OPTION_DISABLED = "<option value='' selected disabled>--- Select ---</option>";
const DEFAULT_SELECT_OPTION = "<option value='' selected >--- Select ---</option>";
const DEFAULT_SELECT_OPTION_UNSELECTED = "<option value='' disabled>--- Select ---</option>";
const DEFAULT_SELECT_OPTION_HEAD_OFFICE = "<option value=''> Head Office </option>";
const ALL_OPTION = "<option value='' selected>All</option>";
const DISABLED_ALL_OPTION = "<option value='' selected disabled>All</option>";
const VIEW_RESULT = "equalizer";

const DEFAULT_SELECT_OPTION_SELECTED_DISABLED = "<option value='' disabled selected>--- Select ---</option>";
const DEFAULT_SELECT_OPTION_SELECTED = "<option value='' selected>--- Select ---</option>";

const SAVE_BUTTON = "<i class='material-icons'>" + SAVE_ICON + "</i> Save";
const EDIT_BUTTON = "<i class='material-icons'>" + UPDATE_ICON + "</i> Update";
const TRANSFER = "subdirectory_arrow_right";
const CHECK_ALL_HTML = `<div class="form-check pl-0">
                        <input type="checkbox" class="form-check-input enable" id="checkAll">
                        <label class="form-check-label" for="checkAll"></label>
                    </div>`;

let iconsMap = new Map();
iconsMap.set('Others', 'add');
iconsMap.set('Notice', 'notifications');
iconsMap.set('Video', 'video_file');
iconsMap.set('News', 'newspaper');
iconsMap.set('Event', 'event_available');
iconsMap.set('Blog', 'dynamic_feed');