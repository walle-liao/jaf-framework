package com.jaf.framework.core.exception;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2017-10-03
 * @since: 1.0
 */
public class UniquenessSelectException extends RuntimeException {
    private static final long serialVersionUID = 9000971442491289144L;

    public UniquenessSelectException() {
        super();
    }

    public UniquenessSelectException(String s) {
        super(s);
    }

    public UniquenessSelectException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniquenessSelectException(Throwable cause) {
        super(cause);
    }

}
