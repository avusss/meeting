package hu.avus.allianzmeeting.model.constraint;

import hu.avus.allianzmeeting.model.validator.StartTimeWithinBoundsValidator;
import hu.avus.allianzmeeting.model.validator.ValidationMessages;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StartTimeWithinBoundsValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface StartTimeWithinBoundsConstraint {

    String message() default ValidationMessages.MEETING_TIME_OUT_OF_BOUNDS_MSG;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
