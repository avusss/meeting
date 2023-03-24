package hu.avus.allianzmeeting.model.constraint;

import hu.avus.allianzmeeting.model.validator.DurationIsIntegralMultipleValidator;
import hu.avus.allianzmeeting.model.validator.ValidationMessages;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DurationIsIntegralMultipleValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DurationIsIntegralMultipleConstraint {

    String message() default ValidationMessages.DURATION_MULTIPLE_MSG;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
