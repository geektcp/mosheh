package com.geektcp.common.core.exception;

import com.geektcp.common.core.constant.Status;

import java.text.MessageFormat;

/**
 * @author tanghaiyang on 2018/5/16.
 */

public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private static final String EXCEPTION_SEPARATOR = "|";

    private int code = 600;
    private String desc;
    private Object[] args;

    public BaseException() {
    }

    public BaseException(Exception e) {
        this.setErrorCode(500, e.getMessage());
    }

    public BaseException(String desc) {
        super(desc);
    }

    public BaseException(Status exceptionStatus) {
        this(exceptionStatus.getCode(), exceptionStatus.toString() + EXCEPTION_SEPARATOR + exceptionStatus.getDesc());
    }

    public BaseException(Status exceptionStatus, Object... args) {
        super(exceptionStatus.getCode() + EXCEPTION_SEPARATOR + format(exceptionStatus.getDesc(), args));
        this.setErrorCode(code, exceptionStatus.getDesc(), args);
    }

    public BaseException(int code, String desc) {
        super(code + EXCEPTION_SEPARATOR + desc);
        this.setErrorCode(code, desc);
    }

    public BaseException(int code, String descPattern, Object... args) {
        super(code + EXCEPTION_SEPARATOR + format(descPattern, args));
        this.setErrorCode(code, descPattern, args);
    }

    public BaseException(int code, String desc, Throwable source) {
        super(code + EXCEPTION_SEPARATOR + desc, source);
        this.setErrorCode(code, desc);
    }

    public BaseException(int code, String descPattern, Throwable source, Object... args) {
        super(code + EXCEPTION_SEPARATOR + format(descPattern, args), source);
        this.setErrorCode(code, descPattern, args);
    }

    public BaseException(String descPattern, Throwable source, Object... args) {
        this(-1, descPattern, source, args);
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        if (args == null) {
            return desc;
        }
        return format(desc, args);
    }

    public static BaseException newException(String desc) {
        return new BaseException(desc);
    }

    public static BaseException newException(Exception e) {
        return new BaseException(e);
    }

    ///////////////////////
    // private functions
    ///////////////////////
    private void setErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private void setErrorCode(int code, String desc, Object... args) {
        this.code = code;
        this.desc = desc;
        this.args = args;
    }

    private static String format(String pattern, Object... arguments) {
        return MessageFormat.format(pattern, arguments);
    }
}
