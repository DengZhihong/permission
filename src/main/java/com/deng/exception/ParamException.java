package com.deng.exception;

public class ParamException extends RuntimeException {
    public ParamException() {
        super();
    }

    public ParamException(String s) {
        super(s);
    }

    public ParamException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ParamException(Throwable throwable) {
        super(throwable);
    }

    protected ParamException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
