package com.uni.tech.exception;

public class TransferToSameAccountException extends RuntimeException {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TransferToSameAccountException(String message) {
        super(message);
    }

}