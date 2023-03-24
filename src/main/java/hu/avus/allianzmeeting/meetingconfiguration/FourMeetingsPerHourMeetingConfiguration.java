package hu.avus.allianzmeeting.meetingconfiguration;

/**
 * Similar implementation to the DefaultMeetingConfiguration, except the minimum meeting
 * duration can be 15 minutes
 */
public class FourMeetingsPerHourMeetingConfiguration extends DefaultMeetingConfiguration {

    @Override
    public int getMinimumMeetingUnitInMinutes() {
        return 15;
    }

    @Override
    public int getMaximumReservableUnits() {
        return 12;
    }
}
