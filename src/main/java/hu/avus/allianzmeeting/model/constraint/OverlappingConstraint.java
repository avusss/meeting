package hu.avus.allianzmeeting.model.constraint;

import hu.avus.allianzmeeting.model.validator.OverlappingValidator;
import hu.avus.allianzmeeting.model.validator.ValidationMessages;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OverlappingValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OverlappingConstraint {

    String message() default ValidationMessages.OVERLAPPING_MSG;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
