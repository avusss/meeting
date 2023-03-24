package hu.avus.allianzmeeting.model.constraint;

import hu.avus.allianzmeeting.model.validator.DurationLengthValidator;
import hu.avus.allianzmeeting.model.validator.ValidationMessages;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DurationLengthValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DurationLengthConstraint {
    String message() default ValidationMessages.DURATION_MIN_MAX_MSG;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
