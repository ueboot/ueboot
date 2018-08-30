package com.ueboot.core.exception;

/**
 *
 * @author Neel
 * @date 2015/8/14
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
     * @param   message   the detail message.
     */
    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * Constructs a {@code NullPointerException} with the specified
     * detail message.
     *
     * @param   cause    he cause (which is saved for later retrieval by the
     *
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }


    /**
     * Constructs a {@code NullPointerException} with the specified
     * detail message.
     *
     * @param   message    the detail message.
     * @param   cause    he cause (which is saved for later retrieval by the
     *
     */
    public BusinessException(String message,Throwable cause) {
        super(message,cause);
    }

    /**
     * Constructs a {@code NullPointerException} with the specified
     * detail message.
     *
     * @param   code   the code.
     * @param   message   the detail message.
     * @param   cause  he cause (which is saved for later retrieval by the
     *
     */
    public BusinessException(String code, String message,Throwable cause) {
        super(message,cause);
        this.code = code;
    }

    /**
     * Constructs a {@code NullPointerException} with the specified
     * detail message.
     *
     * @param   message   the detail message.
     */
    public BusinessException(String message) {
        super(message);
    }

    public String getCode() {
        return this.code;
    }
}
