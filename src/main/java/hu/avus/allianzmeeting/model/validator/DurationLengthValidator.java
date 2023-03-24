package hu.avus.allianzmeeting.model.validator;

import hu.avus.allianzmeeting.meetingconfiguration.MeetingConfiguration;
import hu.avus.allianzmeeting.model.constraint.DurationLengthConstraint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class DurationLengthValidator implements ConstraintValidator<DurationLengthConstraint, Integer> {

    private final MeetingConfiguration configuration;

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        return value >= configuration.getMinimumMeetingUnitInMinutes()
                && value <= configuration.getMinimumMeetingUnitInMinutes() * configuration.getMaximumReservableUnits();
    }
}
