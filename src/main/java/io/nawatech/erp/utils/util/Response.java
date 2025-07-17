package io.nawatech.erp.utils.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Response {

    private String message;

    private boolean success;

    private boolean auto;//auto-generated

    private Boolean expired;//main session

    private Object body;

    @JsonFormat(pattern = Strings.DATE_TIME_PATTERN, timezone = Strings.TIMEZONE)
    private Date timestamp = new Date();

    public Response(Object body, String message, boolean success) {
        this.body = body;
        this.message = message;
        this.success = success;
    }

    public Response(Object body, String message, boolean success, Boolean expired) {
        this.body = body;
        this.message = message;
        this.success = success;
        this.expired = expired;
    }

}