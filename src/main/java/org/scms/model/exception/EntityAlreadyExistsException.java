package org.scms.model.exception;

public class EntityAlreadyExistsException extends Exception {

    private static final long serialVersionUID = -1814044875082872843L;

    public EntityAlreadyExistsException() {
    }

    public EntityAlreadyExistsException(String msg) {
        super(msg);
    }

    public EntityAlreadyExistsException (String message, Throwable cause) {
        super(message, cause);
    }

}