package hu.avus.allianzmeeting.model.dto;

import hu.avus.allianzmeeting.model.constraint.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@StartTimeWithinBoundsConstraint
@StartTimeInRhythmConstraint
@OverlappingConstraint
public class MeetingCommand {

    @EmployeeExistsConstraint
    private Long organizerId;

    @DayOfWeekConstraint
    @Schema(description = "The day of week, one of MON / TUE / WED / THU / FRI / SAT / SUN")
    private String dayOfWeek;

    private Integer hour;

    private Integer minute;

    @DurationLengthConstraint
    @DurationIsIntegralMultipleConstraint
    private Integer durationInMinutes;
}
