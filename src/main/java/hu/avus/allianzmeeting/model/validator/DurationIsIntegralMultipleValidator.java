package hu.avus.allianzmeeting.model.validator;

import hu.avus.allianzmeeting.meetingconfiguration.MeetingConfiguration;
import hu.avus.allianzmeeting.model.constraint.DurationIsIntegralMultipleConstraint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
@Slf4j
public class DurationIsIntegralMultipleValidator implements ConstraintValidator<DurationIsIntegralMultipleConstraint, Integer> {

    private final MeetingConfiguration configuration;

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value % configuration.getMinimumMeetingUnitInMinutes() == 0;
    }
}
