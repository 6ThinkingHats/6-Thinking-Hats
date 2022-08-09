package com.ssafy.sixhats.exception;

public class UnAuthorizedException extends RuntimeException {
    private static final long serialVersionUID = -2238030302650813813L;

    public UnAuthorizedException() {
        super("No Permission");
    }

    public UnAuthorizedException(String message) {
        super(message);
    }
}

