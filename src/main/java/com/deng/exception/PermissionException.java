package com.deng.exception;

public class PermissionException extends RuntimeException{
    public PermissionException() {
        super();
    }

    public PermissionException(String s) {
        super(s);
    }

    public PermissionException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public PermissionException(Throwable throwable) {
        super(throwable);
    }

    protected PermissionException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
