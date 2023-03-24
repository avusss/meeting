package hu.avus.allianzmeeting.meetingconfiguration;

import hu.avus.allianzmeeting.enums.WeekDay;

import java.time.LocalTime;

/**
 * Similar implementation to the DefaultMeetingConfiguration, except that we
 * can go home earlier on Fridays
 */
public class FridayGoHomeEarlyMeetingConfiguration extends DefaultMeetingConfiguration {

    @Override
    public LocalTime getEndTimeOnDay(WeekDay weekDay) {
        return switch (weekDay) {
            case MON, TUE, WED, THU -> LocalTime.of(17, 0);
            case FRI -> LocalTime.of(14, 30);
            default -> null;
        };
    }
}
