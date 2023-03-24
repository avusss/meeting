package hu.avus.allianzmeeting.model.validator;

import hu.avus.allianzmeeting.enums.WeekDay;
import hu.avus.allianzmeeting.meetingconfiguration.MeetingConfiguration;
import hu.avus.allianzmeeting.model.constraint.DayOfWeekConstraint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class DayOfWeekValidator implements ConstraintValidator<DayOfWeekConstraint, String> {

    private final MeetingConfiguration configuration;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            WeekDay weekDay = WeekDay.valueOf(value);
            return configuration.getStartTimeOnDay(weekDay) != null;
        } catch (IllegalArgumentException ignored) {
        }
        return false;
    }

}
