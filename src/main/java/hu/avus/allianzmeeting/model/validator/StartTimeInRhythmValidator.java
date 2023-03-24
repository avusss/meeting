package hu.avus.allianzmeeting.model.validator;

import hu.avus.allianzmeeting.enums.WeekDay;
import hu.avus.allianzmeeting.meetingconfiguration.MeetingConfiguration;
import hu.avus.allianzmeeting.model.constraint.StartTimeInRhythmConstraint;
import hu.avus.allianzmeeting.model.dto.MeetingCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class StartTimeInRhythmValidator implements ConstraintValidator<StartTimeInRhythmConstraint, MeetingCommand> {

    private final MeetingConfiguration configuration;

    @Override
    public boolean isValid(MeetingCommand meetingCommand, ConstraintValidatorContext constraintValidatorContext) {
        try {
            WeekDay weekDay = WeekDay.valueOf(meetingCommand.getDayOfWeek());
            LocalTime minStartTime = configuration.getStartTimeOnDay(weekDay);
            LocalTime actualStartTime = LocalTime.of(meetingCommand.getHour(), meetingCommand.getMinute());
            if (minStartTime != null) {
                long differenceInMinutes = actualStartTime.until(minStartTime, ChronoUnit.MINUTES);
                return differenceInMinutes % configuration.getMinimumMeetingUnitInMinutes() == 0;
            }
        } catch (IllegalArgumentException ignored) {
        }
        // If we get here for any reason, another validator will fire, no need to return false here
        return true;
    }

}
