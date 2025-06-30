package io.nawatech.erp.utils;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.nawatech.erp.utils.constants.FileBelongTo;
import io.nawatech.erp.utils.payload.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FileUtil {

    public static final String ACCEPT_FILES_TYPES = "application/msword, application/vnd.openxmlformats-officedocument.wordprocessingml.document, application/pdf, application/vnd.ms-powerpoint, application/vnd.openxmlformats-officedocument.presentationml.presentation,othersDocument image/jpg, image/jpeg, image/png";
    public static final String FILE_UPLOAD_MESSAGE = "File size must be max 5MB (for images) and 2MB (for docx, ppt, pdf and gif) each. Max attachable: 5.";
    public static final String SINGLE_FILE_UPLOAD_MESSAGE = "File size must be max 5MB (for images) and 2MB (for docx, ppt, pdf and gif).";

    public static final List<String> VALID_ATTACHMENT_EXTENSIONS = Arrays.asList("pdf", "xls", "xlsx", "ppt", "pptx", "docx", "doc", "jpg", "jpeg", "png");
    public static final List<String> VALID_IMAGE_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png");
    public static final List<String> VALID_DOCUMENT_EXTENSION = Arrays.asList("jpg", "jpeg", "png", "pdf");
    public static final List<String> SPECIAL_DIR_FOR_COMPRESSION = Arrays.asList(FileBelongTo.EMPLOYEE, FileBelongTo.SUPPORT_TICKET);

    public static final Long ONE_MB = 1024000L;
    public static final Long TWO_MB = 2048000L;
    public static final Long FIVE_MB = 5120000L;
    public static final Long ONE_KB = 1024L;
    public static final Long MAX_FILE_SIZE_RELATED_TO_ADMISSION_APPLICATION = 1024000L;

    public static boolean isValidAttachment(MultipartFile file, List<String> validAttachmentExtensions, Long maxFileSize) {
        if (file == null || file.isEmpty()) return true;
        if (hasValidExtension(file, validAttachmentExtensions)) return file.getSize() <= maxFileSize;
        else return false;
    }

    public static boolean hasValidExtension(MultipartFile file, List<String> validFileExtensions) {
        if (file == null || file.isEmpty() || file.getSize() == 0) return true;
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        return validFileExtensions.contains(extension.toLowerCase());
    }

    public static String getDirectory(String belongTo) {
        return switch (belongTo) {
            case FileBelongTo.EMPLOYEE -> "employee-profile-images/";
            case FileBelongTo.SIGNATURE -> "signatures/";
            case FileBelongTo.TENANT -> "tenanttenant-profile-images/";
            case FileBelongTo.TENANT_ONLINE_RESOURCE -> "tenanttenant-online-resource/";
            case FileBelongTo.PARTNER -> "partner/";
            case FileBelongTo.IMAGE_GALLERY -> "image-gallery/";
            case FileBelongTo.IMAGES -> "images/";
            case FileBelongTo.AFFILIATION -> "affiliation/";
            case FileBelongTo.POST -> "post/";
            case FileBelongTo.ACCREDITATION -> "accreditation/";
            case FileBelongTo.CAREER -> "career/";
            case FileBelongTo.FACILITY -> "facility/";
            case FileBelongTo.TESTIMONIAL -> "testimonial/";
            case FileBelongTo.MESSAGE -> "message/";
            case FileBelongTo.HOMEWORK -> "attachments/";
            case FileBelongTo.REGISTRATIONFORM_ADMISTRATOR -> "registration-form/administrator";
            case FileBelongTo.SCHOOL_ADDITIONAL_INFO -> "agreement-documents/";
            case FileBelongTo.SUPPORT_TICKET -> "support-ticket/";
            case FileBelongTo.SCHOOL_SUPPORT_TICKET -> "tenant-support-ticket/";
            case FileBelongTo.CERTIFICATE_THEME -> "certificate-theme/";
            case FileBelongTo.MODULE_NEW -> "module-attachment/";
            case FileBelongTo.KNOWLEDGE_BASE -> "knowledge-base/";
            case FileBelongTo.NOTICE_ATTACHMENT -> "notice-attachment/";
            case FileBelongTo.SCHOOL_LOGO -> "tenant-logos/";
            case FileBelongTo.LOGIN_COVER -> "login-covers/";
            case FileBelongTo.SLIDER_IMAGE -> "slider-image/";
            case FileBelongTo.ONLINE_EXAM_ATTACHMENT -> "online-exam-attachments/";
            case FileBelongTo.GENERAL_ADMISSION_APPLICATIONS -> "general-admission-applications/";
            case FileBelongTo.COMMUNICATION_ATTACHMENT -> "communication-attachment/";
            case FileBelongTo.COMMUNICATION_COMMENT_ATTACHMENT -> "communication-comment-attachment/";
            case FileBelongTo.STAFF_DOCUMENT -> "staff-document/";
            case FileBelongTo.JOB_ATTACHMENT -> "job-attachment/";
            case FileBelongTo.JOB_APPLICATION_ATTACHMENT -> "job-application-attachment/";
            case FileBelongTo.EMPLOYEE_LEAVE_ATTACHMENT -> "employee-leave-attachment/";
            case FileBelongTo.ADMIN_PROFILE_IMAGE -> "admin-profile-image/";
            case FileBelongTo.E_PAYMENT_PFX -> "e-payment-pfx/";
            case FileBelongTo.ADMIN_TASK_ATTACHMENT -> "admin-task-attachment/";
            case FileBelongTo.TASK_ATTACHMENT -> "task-attachment/";
            case FileBelongTo.DAILY_ROUTINE -> "daily-routine/";
            case FileBelongTo.SETTINGS_ATTACHMENT -> "settings-attachment/";
            case FileBelongTo.BARCODE -> "barcode/";
            case FileBelongTo.QRCODE -> "qrcode/";
            case FileBelongTo.CHANGE_LOG -> "change-log/";
            case FileBelongTo.COLLEGE_ADMISSION_APPLICATIONS -> "college-admission-applications/";
            case FileBelongTo.EBOOK -> "ebook/";
            case FileBelongTo.BOOK -> "book/";
            case FileBelongTo.BLOG -> "blog/";
            case FileBelongTo.RECEIPT_ENTRY -> "sponsor-receipt/";
            case FileBelongTo.TEST -> "test/";
            case FileBelongTo.NAVIGATION -> "navigation/";
            case FileBelongTo.ISSUE_TRACKER -> "issue-tracker/";
            case FileBelongTo.SUBSCRIPTION_RENEWAL_ATTACHMENT -> "subscription-renewal-attachment/";
            case FileBelongTo.WEBINAR_VIDEOS -> "webinar-videos/";
            case FileBelongTo.CASE_AND_COMPLAINT -> "case-and-complaint/";
            case FileBelongTo.BILLING_SUPPORTING_DOCUMENT -> "supporting-documents/billing/";
            case FileBelongTo.VOUCHER_SUPPORTING_DOCUMENT -> "supporting-documents/voucher/";
            case FileBelongTo.OPENING_BALANCE_SUPPORTING_DOCUMENT -> "supporting-documents/opening-balance/";
            case FileBelongTo.NOTEBOOK -> "notebook/";
            case FileBelongTo.REMINDER -> "reminder/";
            case FileBelongTo.FEEDBACK -> "feedback/";
            case FileBelongTo.WRITE_TO_US_ATTACHMENT -> "write-to-us-attachment/";
            case FileBelongTo.ACTIVITY_LOG -> "activity-log";
            case FileBelongTo.CHAT_ATTACHMENT -> "chat-attachment/";
            default -> "";
        };
    }

    public static String getUploadDirectory(String fileOf) {
        return Strings.FILE_UPLOAD_BASE_PATH + getDirectory(fileOf);
    }

    public static String getImageUploadDirectory(String imageOf) {
        return FileUtil.getUploadDirectory(imageOf);
    }

    public static String uploadFile(MultipartFile file, String imageOf, String previousImageName) {
        if (file == null || file.isEmpty()) return previousImageName;

        String imageUploadDirectory = getImageUploadDirectory(imageOf);
        String fileName = System.currentTimeMillis() + "_" + UUID.randomUUID() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        String filePath = imageUploadDirectory + fileName;
        try {
            file.transferTo(new File(filePath));

            //delete previous file if available
            if (previousImageName != null) deleteImage(previousImageName, imageOf);

            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteImage(String imageName, String imageOf) {
        if (imageName == null || imageName.isEmpty()) return;
        String directory = getImageUploadDirectory(imageOf);
        try {
            String[] files = imageName.split(",");
            for (String file : files) {
                String filePath = directory + file;
                Files.deleteIfExists(new File(filePath).toPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getImageSource(String imageOf, String fileName, boolean useDefaultImageIfNotAvailable) {
        return getFileSource(imageOf, fileName, null, useDefaultImageIfNotAvailable);
    }

    /**
     * @param imageOf             file belongs to
     * @param fileName            full name of the file
     * @param gender              for employee, student and related peoples
     * @param includeDefaultImage flag to include default file if fileName is null
     * @return full path of a particular file
     */
    public static String getFileSource(String imageOf, String fileName, String gender, boolean includeDefaultImage) {
        String baseUrl = Strings.LIVE_BASE_URL;
        if (Helper.isNullOrEmpty(fileName)) {
            if (!includeDefaultImage)
                return null;

            String filePath;
            if (FileBelongTo.TENANT.equals(imageOf)) {
                filePath = "/resources/dist/img/dn-rectangle.png";
            } else if (!Helper.isNullOrEmpty(gender)) {
                if (gender.equalsIgnoreCase("male"))
                    filePath = "/resources/mdbootstrap/img/thumb-male.png";
                else filePath = "/resources/mdbootstrap/img/thumb-female.png";
            } else filePath = "/resources/dist/img/default-user.png";
            return baseUrl + filePath;
        }

        return baseUrl + "/files/" + getDirectory(imageOf) + fileName;
    }

    public static boolean isValidFileSizeForAdmissionApplicationRelatedFiles(MultipartFile file) {
        if (file == null || file.getSize() <= 0) return true;
        return file.getSize() <= MAX_FILE_SIZE_RELATED_TO_ADMISSION_APPLICATION;
    }

    public static String getFileName(MultipartFile file) {
        if (file == null || file.getSize() <= 0) return null;
        return System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
    }

    public static String resizeAndUploadImage(MultipartFile file, String fileUploadDirectory) {
        BufferedImage bf = null;
        if (Helper.isNullOrEmpty(fileUploadDirectory)) return null;

        try {
            bf = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bf != null) {
            if (file.getSize() > FileUtil.ONE_KB * 500) {
                float compressionRatio = SPECIAL_DIR_FOR_COMPRESSION.contains(fileUploadDirectory) ? 0.6f : 0.4f;
                bf = Scalr.resize(bf, Scalr.Method.BALANCED, (int) (bf.getWidth() * compressionRatio), (int) (bf.getHeight() * compressionRatio));
            }

            try {
                String extension = FilenameUtils.getExtension(file.getOriginalFilename());
                if (extension == null) extension = "jpg";
                String fileName = System.currentTimeMillis() + "-" + UUID.randomUUID() + "." + extension;
                String filePath = FileUtil.getImageUploadDirectory(fileUploadDirectory) + fileName;
                ImageIO.write(bf, extension, new File(filePath));
                return fileName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static Response validateAttachmentAndGetFileNames(List<MultipartFile> files, String fileOf, String previousFileName, boolean shouldCompressFile) {
        return validateAttachmentAndGetFileNames(files, fileOf, previousFileName, FileUtil.VALID_ATTACHMENT_EXTENSIONS, Arrays.asList(FileUtil.FIVE_MB, FileUtil.TWO_MB), shouldCompressFile);
    }

    //latest with dynamic extensions and valid file size
    public static Response validateAttachmentAndGetFileNames(List<MultipartFile> files,
                                                             String fileOf,
                                                             String previousFileName,
                                                             List<String> validExtensions,
                                                             List<Long> validFileSize,
                                                             boolean shouldCompressFile) {
        if (files == null || files.isEmpty() || Helper.isNullOrEmpty(fileOf))
            return new Response(previousFileName, null, true);

        if (validExtensions == null) validExtensions = FileUtil.VALID_IMAGE_EXTENSIONS;
        if (validFileSize == null) validFileSize = Arrays.asList(FileUtil.TWO_MB, FileUtil.TWO_MB);

        boolean hasFiles = false;

        for (MultipartFile file : files) {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            if (file.getSize() > 0 && !Helper.isNullOrEmpty(extension)) {
                hasFiles = true;
                extension = extension.trim().toLowerCase();
                if (validExtensions.contains(extension)) {
                    if (FileUtil.VALID_IMAGE_EXTENSIONS.contains(extension)) {
                        if (file.getSize() > validFileSize.get(0))
                            return new Response(null, "Some of the image attachment is more than " + (byteToMB(validFileSize.get(0))) + " MB.", false);
                    } else {
                        if (validFileSize.size() < 2 || file.getSize() > validFileSize.get(1))
                            return new Response(null, "Some of the attachment is more than " + (byteToMB(validFileSize.get(1))) + " MB.", false);
                    }
                }
            }
        }

        List<String> attachmentFileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String attachmentFileName = null;

            if (file.getSize() > 0 && !Helper.isNullOrEmpty(extension)) {
                extension = extension.toLowerCase();

                //image file validation
                if (FileUtil.VALID_IMAGE_EXTENSIONS.contains(extension) && shouldCompressFile)
                    attachmentFileName = FileUtil.resizeAndUploadImage(file, fileOf);
                else if (FileUtil.VALID_ATTACHMENT_EXTENSIONS.contains(extension)) //additional attachment validation like i.e. PDF, DOC, PPT
                    attachmentFileName = FileUtil.uploadFile(file, fileOf, null);
            }

            if (attachmentFileName != null)
                attachmentFileNames.add(attachmentFileName);
        }

        String attachmentNames = StringUtils.arrayToCommaDelimitedString(attachmentFileNames.toArray());
        String finalAttachmentNames = attachmentNames.isEmpty() ? previousFileName : attachmentNames;
        boolean success = !hasFiles || !Helper.isNullOrEmpty(finalAttachmentNames);

        if (success && !attachmentNames.isEmpty()) deleteImage(previousFileName, fileOf);

        return new Response(finalAttachmentNames, hasFiles && !success ? "Failed to upload attachment(s)." : "", success);
    }

    public static Float byteToMB(Long size) {
        return Helper.roundOff(size / (float) FileUtil.ONE_MB);
    }

    public static String getGuessedExtension(byte[] file, String type) {
        if (Helper.isNullOrEmpty(type)) type = "image";
        if (!type.equalsIgnoreCase("image"))
            return null;

        try {
            InputStream is = new ByteArrayInputStream(file);
            String mimeType = URLConnection.guessContentTypeFromStream(is);
            if (!Helper.isNullOrEmpty(mimeType))
                return mimeType.split("/")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param image    blob image to convert to file
     * @param belongTo module to which file belongs
     * @return name of the file uploaded by converting blob as a file
     */
    public static String convertBlobToFile(byte[] image, String belongTo) {
        String extension = FileUtil.getGuessedExtension(image, "image");
        if (Helper.isNullOrEmpty(extension))
            return null;

        String fileName = System.currentTimeMillis() + "_" + UUID.randomUUID() + "." + extension;
        Path path = Paths.get(Strings.FILE_UPLOAD_BASE_PATH + FileUtil.getDirectory(belongTo) + fileName);

        try {
            Files.write(path, image);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static FileConfigDTO getConfig(String fileBelongTo, HttpServletRequest request) {
        FileConfigDTO fileConfig = null;

        String jsonConfigFilePath = request.getSession().getServletContext().getRealPath("/resources/mdbootstrap/config/file-config.json");

        // Read from File to String
        JsonObject jsonObject = null;

        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(jsonConfigFilePath));
            jsonObject = jsonElement.getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (jsonObject != null) {
            String objectKey = null;

            switch (fileBelongTo) {

                case FileBelongTo.HOMEWORK:
                    objectKey = "homework";
                    break;

                case FileBelongTo.NOTICE_ATTACHMENT:
                    objectKey = "notice";
                    break;

                default:
                    objectKey = "config";
                    break;
            }

            JsonObject config = jsonObject.getAsJsonObject(objectKey);

            int maxAttachable = config.get("maxAttachable").getAsInt();
            int maxAttachmentSize = config.get("maxAttachmentSize").getAsInt();
            int maxImageSize = config.get("maxImageSize").getAsInt();

            fileConfig = new FileConfigDTO(
                    maxAttachable,
                    maxAttachmentSize * FileUtil.ONE_MB,
                    maxImageSize * FileUtil.ONE_MB);
        }

        return fileConfig;
    }

}

