package hu.avus.allianzmeeting.model.constraint;

import hu.avus.allianzmeeting.model.validator.DayOfWeekValidator;
import hu.avus.allianzmeeting.model.validator.ValidationMessages;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DayOfWeekValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DayOfWeekConstraint {

    String message() default ValidationMessages.INVALID_WEEKDAY_MSG;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
