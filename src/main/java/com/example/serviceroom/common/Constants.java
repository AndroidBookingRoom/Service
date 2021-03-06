package com.example.serviceroom.common;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by NhanNguyen on 4/22/2021
 *
 * @author: NhanNguyen
 * @date: 4/22/2021
 */
public class Constants {

    /**
     * Created by NhanNguyen on 1/18/2021
     * RESPONSE_TYPE
     *
     * @author: NhanNguyen
     * @date: 1/18/2021
     */
    private static HttpServletRequest req;

    public static class RESPONSE_TYPE {
        public static final String SUCCESS = "SUCCESS";
        public static final String ERROR = "ERROR";
        public static final String WARNING = "WARNING";
        public static final String CONFIRM = "CONFIRM";
        public static final String invalidPermission = "invalidPermission";

        public static final String doesNotExist = "doesNotExist";
        public static final String OK = "OK";
        public static final String NOK = "NOK";
        public static final String EXIST = "EXIST";

    }

    /**
     * Created by NhanNguyen on 1/18/2021
     * RESPONSE_CODE
     *
     * @author: NhanNguyen
     * @date: 1/18/2021
     */

    public static class RESPONSE_CODE {
        public static final String SUCCESS = "success";
        public static final String DELETE_SUCCESS = "deleteSuccess";
        public static final String UPDATE_STATUS_SUCCESS = "updateStatusSuccess";
        public static final String UPDATE_SUCCESS = "updateSuccess";
        public static final String ERROR = "error";
        public static final String WARNING = "warning";
        public static final String RECORD_DELETED = "record.deleted";
        public static final String EMAIL_ADDRESS_DELETED = "emailAddress.deleted";
        public static final String RECORD_INUSED = "record.inUsed";
        public static final String DOCUMENT_TYPE_EXISTED = "documentTypeExits";
        public static final String PARAMETER_USED = "parameterUsed";
        public static final String ROLE_EXIST = "permission.role.exist";
        public static final String MENU_HAVE_CHILD = "permission.menu.haveChild";
        public static final String ERROR_COMPOSITE = "error.composite";
        public static final String SUCCESS_COMPOSITE = "success.composite";
        public static final String ERROR_SEND = "error.send";
        public static final String SUCCESS_SEND = "success.send";
        public static final String SUCCESS_SAVE = "success.save";
        public static final String DELETE_ERROR = "error.delete";
        public static final String SAVE_DUPLICATE_CODE = "save.duplicateCode";
        public static final String EXISTS_NAME = "exists.name";
        public static final String EXISTS_OPTION = "exists.option";
    }

    public static class WARNING_TYPE {

        public static final Long EMAIL = 0l;
        public static final Long SMS = 1l;
        public static final Long EMAIL_SMS = 2l;
    }

    public static class MESSAGE {
        public static final String LOGIN_FAIL="Incorrect username or password";
        public static final String UPLOAD_ERROR="upload.fail";
        public static final String DELETE_ERROR="delete.fail";
        public static final String ISEMPTY = "ISEMPTY";

    }


    public enum ProcessType {
        //Qua trinh trong
        INTERIOR(1L, "Interior"),
        //Qu?? tr??nh ngo??i
        EXTERIOR(2L, "Exterior"),
        //Qua trinh nha'p
        TYPE_DRAFT(3L, "Draft"),
        //la qua trinh hien t???i
        STATUS_CURRENT(1L, "Current"),
        //ko phai qua trinh hien tai
        STATUS_NOT_CURRENT(null, "NotCurrent");
        private Long key;
        private String code;

        private ProcessType(Long key, String code) {
            this.key = key;
            this.code = code;
        }

        public Long getKey() {
            return key;
        }

        public String getCode() {
            return code;
        }
    }

    public interface COMMON {
        String FONT_FOLDER = CommonUtil.getConfig("fontFolder");
        String MARKET_COMPANY_ID = "MARKET_COMPANY_ID";
        String EXPORT_FOLDER = CommonUtil.getConfig("exportFolder");
        //Thu muc chua file tam de import
        String IMPORT_FOLDER = "/share/import/";
        String DATE_TIME_FORMAT = "yyyy-MM-dd";
    }

    public enum attributeType {
        DATE1(1L, "dd/MM/yyyy"),
        DATE2(2L, "MM/dd/yyyy"),
        DATE3(3L, "yyyy/MM/dd"),
        DOUBLE(4L, "Double"),
        LONG(5L, "Long"),
        STRING(6L, "String"),
        DATE(7L, "Date");

        private Long key;
        private String code;

        private attributeType(Long key, String code) {
            this.key = key;
            this.code = code;
        }

        public Long getKey() {
            return key;
        }

        public String getCode() {
            return code;
        }
    }

    public static final String KEY_USER = "USER";
    public static final String KEY_TOKEN = "TOKEN";
}
