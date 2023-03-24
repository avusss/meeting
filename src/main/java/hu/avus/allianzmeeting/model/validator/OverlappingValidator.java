package hu.avus.allianzmeeting.model.validator;

import hu.avus.allianzmeeting.enums.WeekDay;
import hu.avus.allianzmeeting.model.constraint.OverlappingConstraint;
import hu.avus.allianzmeeting.model.dto.MeetingCommand;
import hu.avus.allianzmeeting.model.entity.Meeting;
import hu.avus.allianzmeeting.model.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OverlappingValidator implements ConstraintValidator<OverlappingConstraint, MeetingCommand> {

    private final MeetingRepository meetingRepository;

    @Override
    public boolean isValid(MeetingCommand meetingCommand, ConstraintValidatorContext constraintValidatorContext) {
        try {

            WeekDay selectedWeekDay = WeekDay.valueOf(meetingCommand.getDayOfWeek());
            List<Meeting> allMeetingsForThatDay
                    = meetingRepository.findAllByDayOfWeekOrderByDayOfWeekAscStartTimeAsc(selectedWeekDay.getDayNum());
            LocalTime selectedStartTime = LocalTime.of(meetingCommand.getHour(), meetingCommand.getMinute());
            LocalTime selectedEndTime = selectedStartTime.plusMinutes(meetingCommand.getDurationInMinutes());

            return allMeetingsForThatDay
                    .stream()
                    .noneMatch(meeting -> overlap(meeting.getStartTime(), meeting.getEndTime(),
                            selectedStartTime, selectedEndTime));
        } catch (IllegalArgumentException ignored) {
        }
        // If we get here for any reason, another validator will fire, no need to return false here
        return true;
    }

    private boolean overlap(
            LocalTime oldStartTime, LocalTime oldEndTime,
            LocalTime newStartTime, LocalTime newEndTime) {

        boolean neededCondition1 = !newStartTime.isBefore(oldEndTime);
        boolean neededCondition2 = !oldStartTime.isBefore(newEndTime);

        boolean noOverlapping = neededCondition1 || neededCondition2;
        return !noOverlapping;
    }
}
