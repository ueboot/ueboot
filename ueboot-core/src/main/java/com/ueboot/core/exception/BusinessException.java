package com.ueboot.core.exception;

/**
 * Created by Neel on 2015/8/14.
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 5162710183389028892L;

    private String code;

    /**
     * Constructs a {@code NullPointerException} with no detail message.
     */
    public BusinessException() {
        super();
    }

    /**
     * Constructs a {@code NullPointerException} with the specified
     * detail message.
     *
     * @param   code   the code.
     * @param   s   the detail message.
     */
    public BusinessException(String code, String s) {
        super(s);
        this.code = code;
    }

    /**
     * Constructs a {@code NullPointerException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public BusinessException(String s) {
        super(s);
    }

    public String getCode() {
        return this.code;
    }
}
