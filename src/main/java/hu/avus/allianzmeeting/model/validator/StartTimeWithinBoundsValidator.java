package hu.avus.allianzmeeting.model.validator;

import hu.avus.allianzmeeting.enums.WeekDay;
import hu.avus.allianzmeeting.meetingconfiguration.MeetingConfiguration;
import hu.avus.allianzmeeting.model.constraint.StartTimeWithinBoundsConstraint;
import hu.avus.allianzmeeting.model.dto.MeetingCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class StartTimeWithinBoundsValidator implements ConstraintValidator<StartTimeWithinBoundsConstraint, MeetingCommand> {

    private final MeetingConfiguration configuration;

    @Override
    public boolean isValid(MeetingCommand meetingCommand, ConstraintValidatorContext constraintValidatorContext) {

        // This validator checks only the following things:
        // - The start time is within the bounds on the specified day
        // - The end time (start time + duration) is within the bounds on the specified day
        //   (meetings ending on the next day are not supported by the configuration)

        try {
            WeekDay selectedWeekDay = WeekDay.valueOf(meetingCommand.getDayOfWeek());
            LocalTime minStartTime = configuration.getStartTimeOnDay(selectedWeekDay);
            LocalTime maxEndTime = configuration.getEndTimeOnDay(selectedWeekDay);
            LocalTime startTime = LocalTime.of(meetingCommand.getHour(), meetingCommand.getMinute());
            LocalTime endTime = startTime.plusMinutes(meetingCommand.getDurationInMinutes());

            if (minStartTime != null) {
                return !startTime.isBefore(minStartTime) && !endTime.isAfter(maxEndTime) && !endTime.isBefore(startTime);
            }
        } catch (IllegalArgumentException ignored) {
        }
        // If we get here for any reason, another validator will fire, no need to return false here
        return true;
    }

}
