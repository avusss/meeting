package hu.avus.allianzmeeting.model.validator;

public class ValidationMessages {

    public static final String INVALID_WEEKDAY_MSG = "Invalid day selected";

    public static final String DURATION_MIN_MAX_MSG = "The meeting duration must be between <MIN_DURATION> and <MAX_DURATION> minutes";

    public static final String DURATION_MULTIPLE_MSG = "The duration must be an integral multiple of <MIN_DURATION> minutes";

    public static final String SELECTED_EMPLOYEE_NOT_EXISTS_MSG = "The specified employee does not exist";

    public static final String MEETING_TIME_OUT_OF_BOUNDS_MSG = "Meeting time out of bounds on the specified day";

    public static final String INVALID_MEETING_START_TIME_MSG = "Invalid start time for the meeting";

    public static final String OVERLAPPING_MSG = "This meeting overlaps with another one";

}
