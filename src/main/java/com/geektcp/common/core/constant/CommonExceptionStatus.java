package com.geektcp.common.core.constant;

/**
 *
 * 共用 错误码
 * @author tanghaiyang
 *  2021/7/15 14:30
 */
public enum CommonExceptionStatus implements Status {

    SYSTEM_BUSY(-1, "system is busy."),
    SYSTEM_TIMEOUT(-2, "system timeout "),
    PARAM_EX(-3, "para exception"),
    SQL_EX(-4, "sql exception"),
    NULL_POINT_EX(-5, "nop exception"),
    ILLEGAL_ARGUMENT_EX(-6, "illegal para"),
    MEDIA_TYPE_EX(-7, "type exception"),
    LOAD_RESOURCES_ERROR(-8, "load error"),
    BASE_VALID_PARAM(-9, "valid error"),
    OPERATION_EX(-10, "operate exception"),
    SERVICE_MAPPER_ERROR(-11, "mapper error"),
    CAPTCHA_ERROR(-12, "captcha error"),
    JSON_PARSE_ERROR(-13, "json error"),
    REQUEST_RETURN_NULL(-14, "request return null"),
    BAD_REQUEST(400, "bad request"),
    UNAUTHORIZED(401, "unauthorized"),
    NOT_FOUND(404, "not found"),
    METHOD_NOT_ALLOWED(405, "method not allowed"),
    TOO_MANY_REQUESTS(429, "too many requests"),
    INTERNAL_SERVER_ERROR(500, "internal server error"),
    BAD_GATEWAY(502, "bad gateway"),
    GATEWAY_TIMEOUT(504, "gateway timeout"),
    REQUIRED_FILE_PARAM_EX(1001, "required file param"),
    DATA_SAVE_ERROR(2000, "data save error"),
    DATA_UPDATE_ERROR(2001, "date update error"),
    TOO_MUCH_DATA_ERROR(2002, "too much data error"),
    JWT_BASIC_INVALID(4000, "basic invalid"),
    JWT_TOKEN_EXPIRED(4001, "token expired"),
    JWT_SIGNATURE(4002, "signature"),
    JWT_ILLEGAL_ARGUMENT(4003, "illegal argument"),
    JWT_GEN_TOKEN_FAIL(4004, "generate token failed"),
    JWT_PARSE_TOKEN_FAIL(4005, "pare token failed"),
    JWT_USER_INVALID(4006, "user invalid"),
    JWT_USER_ENABLED(4007, "user is forbid ！"),
    JWT_OFFLINE(4008, "login at other env！"),
    UPLOAD_FILE_TYPE_ERROR(4009, "file type error"),
    UPLOAD_FILE_ERROR(4010, "save file error"),

    ERROR_LOG_INSERT(5000, "log save error"),

    ;


    private int code;
    private String desc;

    CommonExceptionStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
