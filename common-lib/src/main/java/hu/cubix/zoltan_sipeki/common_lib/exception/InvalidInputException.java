package hu.cubix.zoltan_sipeki.common_lib.exception;

import java.util.Map;

public class InvalidInputException extends RuntimeException {

    private Map<String, String> errors;

    public InvalidInputException(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
