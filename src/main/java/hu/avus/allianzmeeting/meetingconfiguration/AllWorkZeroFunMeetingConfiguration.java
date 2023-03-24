package hu.avus.allianzmeeting.meetingconfiguration;

import hu.avus.allianzmeeting.enums.WeekDay;

import java.time.LocalTime;

/**
 * Similar implementation to the DefaultMeetingConfiguration, except there is business
 * on every day (even weekends) until 23:30
 */
public class AllWorkZeroFunMeetingConfiguration implements MeetingConfiguration {
    @Override
    public LocalTime getStartTimeOnDay(WeekDay weekDay) {
        return LocalTime.of(0, 0);
    }

    @Override
    public LocalTime getEndTimeOnDay(WeekDay weekDay) {
        return LocalTime.of(23, 30);
    }

    @Override
    public int getMinimumMeetingUnitInMinutes() {
        return 30;
    }

    @Override
    public int getMaximumReservableUnits() {
        return 6;
    }
}
