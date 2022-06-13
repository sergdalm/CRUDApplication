package exceptions;

import lombok.Getter;
import validator.Error;

import java.util.List;

public class ValidException extends RuntimeException {
    @Getter
    private final List<Error> errors;

    public ValidException(List<Error> errors) {
        this.errors = errors;
    }
}
