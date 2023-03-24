package hu.avus.allianzmeeting.model.constraint;

import hu.avus.allianzmeeting.model.validator.EmployeeExistsValidator;
import hu.avus.allianzmeeting.model.validator.ValidationMessages;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmployeeExistsValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmployeeExistsConstraint {

    String message() default ValidationMessages.SELECTED_EMPLOYEE_NOT_EXISTS_MSG;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
