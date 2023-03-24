package hu.avus.allianzmeeting.model.constraint;

import hu.avus.allianzmeeting.model.validator.StartTimeInRhythmValidator;
import hu.avus.allianzmeeting.model.validator.ValidationMessages;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StartTimeInRhythmValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface StartTimeInRhythmConstraint {

    String message() default ValidationMessages.INVALID_MEETING_START_TIME_MSG;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
