package validator;

import dto.LoginWriterDto;

public class CreateWriterValidator implements Validator<LoginWriterDto> {
    private static final CreateWriterValidator INSTANCE = new CreateWriterValidator();

    private CreateWriterValidator() {
    }

    @Override
    public ValidationResult isValid(LoginWriterDto object) {
        ValidationResult validationResult = new ValidationResult();
        if (object.getFirstName().isEmpty() || object.getFirstName().contains(" ")) {
            validationResult.add(Error.of("Invalid.firstName", "Name is invalid"));
        }
        if (object.getLastName().isEmpty() || object.getLastName().contains(" ")) {
            validationResult.add(Error.of("Invalid.lastName", "Name is invalid"));
        }
        if (!object.getEmail().contains("@") || object.getEmail().contains(" ")) {
            validationResult.add(Error.of("Invalid.email", "Email is invalid"));
        }

        return validationResult;
    }

    public static CreateWriterValidator getInstance() {
        return INSTANCE;
    }

    public ValidationResult checkPassword(String firstPassword, String secondPassword) {
        ValidationResult validationResult = new ValidationResult();
        if (!firstPassword.equals(secondPassword)) {
            validationResult.add(Error.of("Invalid.password", "Passwords are not same"));
        }
        return validationResult;
    }
}
