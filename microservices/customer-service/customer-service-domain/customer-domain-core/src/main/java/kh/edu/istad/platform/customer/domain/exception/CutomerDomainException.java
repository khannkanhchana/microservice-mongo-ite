package kh.edu.istad.platform.customer.domain.exception;

import kh.edu.istad.common.domain.exception.DomainException;

public class CutomerDomainException extends DomainException {
    public CutomerDomainException(String message) {
        super(message);
    }

    public CutomerDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
