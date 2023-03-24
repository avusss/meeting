package hu.avus.allianzmeeting.meetingconfiguration;

import hu.avus.allianzmeeting.enums.WeekDay;

import java.time.LocalTime;

public interface MeetingConfiguration {

    /**
     * Earliest time a meeting can start on a given weekday
     * @param weekDay
     * @return
     */
    LocalTime getStartTimeOnDay(WeekDay weekDay);

    /**
     * Lates time a meeting must be ended on a given day
     * @param weekDay
     * @return
     */
    LocalTime getEndTimeOnDay(WeekDay weekDay);

    /**
     * The minimum number of minutes of a meeting unit per reservation
     * @return
     */
    int getMinimumMeetingUnitInMinutes();

    /**
     * The maximum number of meeting units per reservation;
     * Effectively the maximum duration of a meeting
     * @return
     */
    int getMaximumReservableUnits();

}
