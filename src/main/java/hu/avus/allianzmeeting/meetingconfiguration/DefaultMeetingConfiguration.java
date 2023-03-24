package hu.avus.allianzmeeting.meetingconfiguration;

import hu.avus.allianzmeeting.enums.WeekDay;

import java.time.LocalTime;

public class DefaultMeetingConfiguration implements MeetingConfiguration {

    @Override
    public LocalTime getStartTimeOnDay(WeekDay weekDay) {
        return switch (weekDay) {
            case MON, TUE, WED, THU, FRI -> LocalTime.of(9, 0);
            default -> null;
        };
    }

    @Override
    public LocalTime getEndTimeOnDay(WeekDay weekDay) {
        return switch (weekDay) {
            case MON, TUE, WED, THU, FRI -> LocalTime.of(17, 0);
            default -> null;
        };
    }

    @Override
    public int getMinimumMeetingUnitInMinutes() {
        return 30;
    }

    @Override
    public int getMaximumReservableUnits() {
        return 6; // 6 units -> 6x30 = 180 minutes
    }
}
