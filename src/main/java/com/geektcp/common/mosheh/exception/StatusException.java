package com.geektcp.common.mosheh.exception;


import com.geektcp.common.mosheh.constant.Status;

/**
 * @author geektcp on 2018/5/16.
 */
public class StatusException extends BaseException {

    private Status status;

    public StatusException(Status status) {
        this(status, new String[]{});
    }

    public StatusException(Status status, Object... args) {
        this(status, null, args);
    }

    public StatusException(Status status, Throwable cause) {
        super(status.getCode(), status.getDesc(), cause);
        this.status = status;
    }

    public StatusException(Status status, Throwable cause, Object... args) {
        super(status.getCode(), status.getDesc(), cause, args);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
