package hu.avus.allianzmeeting;

import hu.avus.allianzmeeting.enums.WeekDay;
import hu.avus.allianzmeeting.meetingconfiguration.DefaultMeetingConfiguration;
import hu.avus.allianzmeeting.meetingconfiguration.MeetingConfiguration;
import hu.avus.allianzmeeting.model.dto.MeetingCommand;
import hu.avus.allianzmeeting.model.entity.Meeting;
import hu.avus.allianzmeeting.model.repository.EmployeeRepository;
import hu.avus.allianzmeeting.model.repository.MeetingRepository;
import hu.avus.allianzmeeting.model.validator.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.validation.*;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

@Slf4j
public class DefaultMeetingConfigurationValidationTest {

    private static final Long VALID_USER_ID = 1L;
    private static final Long INVALID_USER_ID = 100L;
    private static final String VALID_DAY = "MON";
    private static final String INVALID_DAY = "SAT";
    private static final Integer VALID_HOUR = 12;
    private static final Integer VALID_MINUTE = 0;
    private static final Integer VALID_DURATION = 60;

    private Validator validator;

    @BeforeEach
    public void setUp() {
        validator = mockValidator();
    }

    @Test
    public void zeroViolations() {
        MeetingCommand command = createValidMeetingCommand();
        Set<ConstraintViolation<MeetingCommand>> violations = validator.validate(command);
        Assertions.assertEquals(0, violations.size());
    }

    @Test
    public void invalidOrganizer() {
        MeetingCommand command = createValidMeetingCommand()
                .setOrganizerId(INVALID_USER_ID);
        Set<ConstraintViolation<MeetingCommand>> violations = validator.validate(command);
        Assertions.assertEquals(1, violations.size());
        Assertions.assertTrue(
                violations
                        .stream()
                        .anyMatch(violation -> ValidationMessages.SELECTED_EMPLOYEE_NOT_EXISTS_MSG.equals(violation.getMessage()))
        );
    }

    @Test
    public void invalidDayOfWeek() {
        MeetingCommand command = createValidMeetingCommand()
                .setDayOfWeek(INVALID_DAY);
        Set<ConstraintViolation<MeetingCommand>> violations = validator.validate(command);
        Assertions.assertEquals(1, violations.size());
        Assertions.assertTrue(
                violations
                        .stream()
                        .anyMatch(violation -> ValidationMessages.INVALID_WEEKDAY_MSG.equals(violation.getMessage()))
        );
    }

    @Test
    public void invalidDuration() {
        MeetingCommand command = createValidMeetingCommand();

        Predicate<ConstraintViolation<?>> minMaxViolationPredicate
                = violation ->ValidationMessages.DURATION_MIN_MAX_MSG.equals(violation.getMessage());
        Predicate<ConstraintViolation<?>> notMultipleViolationPredicate
                = violation ->ValidationMessages.DURATION_MULTIPLE_MSG.equals(violation.getMessage());

        int durationLengthViolations = 0;
        int durationNotMultipleViolations = 0;
        for (int i = 0; i < 30; i++) {
            command.setDurationInMinutes(i);
            Set<ConstraintViolation<MeetingCommand>> violations = validator.validate(command);
            durationLengthViolations += violations
                    .stream()
                    .filter(minMaxViolationPredicate)
                    .count();
            durationNotMultipleViolations += violations
                    .stream()
                    .filter(notMultipleViolationPredicate)
                    .count();

        }
        Assertions.assertEquals(durationLengthViolations, 30);
        Assertions.assertEquals(durationNotMultipleViolations, 29);

        for (int i = 30; i <= 180; i++) {
            command.setDurationInMinutes(i);
            Set<ConstraintViolation<MeetingCommand>> violations = validator.validate(command);
            durationLengthViolations += violations
                    .stream()
                    .filter(minMaxViolationPredicate)
                    .count();
            durationNotMultipleViolations += violations
                    .stream()
                    .filter(notMultipleViolationPredicate)
                    .count();
        }
        Assertions.assertEquals(durationLengthViolations, 30);
        Assertions.assertEquals(durationNotMultipleViolations, 29 + 145);

        for (int i = 181; i <= 200; i++) {
            command.setDurationInMinutes(i);
            Set<ConstraintViolation<MeetingCommand>> violations = validator.validate(command);
            durationLengthViolations += violations
                    .stream()
                    .filter(minMaxViolationPredicate)
                    .count();
            durationNotMultipleViolations += violations
                    .stream()
                    .filter(notMultipleViolationPredicate)
                    .count();
        }
        Assertions.assertEquals(durationLengthViolations, 30 + 20);
        Assertions.assertEquals(durationNotMultipleViolations, 29 + 145 + 20);
    }

    @Test
    public void outOfTimeBounds() {
        int[] durations = {30, 60, 90, 120, 150, 180};
        int[] minutes = {0, 30};
        MeetingCommand command = createValidMeetingCommand();
        int violationsCount = 0;
        Predicate<ConstraintViolation<?>> outOfBoundsViolationPredicate
                = violation ->ValidationMessages.MEETING_TIME_OUT_OF_BOUNDS_MSG.equals(violation.getMessage());

        for (int hour = 0; hour <= 23; hour++) {
            for (int minute: minutes) {
                for (int duration: durations) {
                    command
                            .setHour(hour)
                            .setMinute(minute)
                            .setDurationInMinutes(duration);
                    Set<ConstraintViolation<MeetingCommand>> violations = validator.validate(command);
                    violationsCount += violations
                            .stream()
                            .filter(outOfBoundsViolationPredicate)
                            .count();
                }
            }
        }

        // before 9:00 - 18x6 violations
        // 9:00 - 14:00 - 0 violations
        // 14:30 - 1 violations
        // 15:00 - 2 violations
        // 15:30 - 3 violations
        // 16:00 - 4 violations
        // 16:30 - 5 violations
        // after 17:00 - 14x6 violations
        Assertions.assertEquals(violationsCount, 207);
    }

    @Test
    public void notInRhythm() {
        MeetingCommand command = createValidMeetingCommand();
        int violationsCount = 0;
        Predicate<ConstraintViolation<?>> inRhythmViolationPredicate
                = violation ->ValidationMessages.INVALID_MEETING_START_TIME_MSG.equals(violation.getMessage());

        for (int hour = 9; hour < 17; hour++) {
            for (int minute = 0; minute < 60; minute++) {
                command.setHour(hour)
                        .setMinute(minute);
                Set<ConstraintViolation<MeetingCommand>> violations = validator.validate(command);
                violationsCount += violations
                        .stream()
                        .filter(inRhythmViolationPredicate)
                        .count();
            }
        }
        // We made 8x60=480 validations; of those 8x2 pass, so 464 do not
        Assertions.assertEquals(violationsCount, 464);
    }

    @Test
    public void overlaps() {
        MeetingCommand command = createValidMeetingCommand();
        int[] minutes = {0, 30};
        int violationsCount = 0;
        Predicate<ConstraintViolation<?>> overlapViolationPredicate
                = violation -> ValidationMessages.OVERLAPPING_MSG.equals(violation.getMessage());

        for (int hour = 9; hour < 17; hour++) {
            for (int minute: minutes) {
                command
                        .setHour(hour)
                        .setMinute(minute);
                Set<ConstraintViolation<MeetingCommand>> violations = validator.validate(command);
                violationsCount += violations
                        .stream()
                        .filter(overlapViolationPredicate)
                        .count();
            }

        }
        Assertions.assertEquals(violationsCount, 4);
    }

    private EmployeeRepository mockEmployeeRepository() {
        EmployeeRepository employeeRepositoryMock = Mockito.mock(EmployeeRepository.class);
        Mockito.when(employeeRepositoryMock.existsById(INVALID_USER_ID)).thenReturn(false);
        Mockito.when(employeeRepositoryMock.existsById(VALID_USER_ID)).thenReturn(true);
        return employeeRepositoryMock;
    }

    private MeetingRepository mockMeetingRepository() {
        int dayNum = WeekDay.valueOf(VALID_DAY).getDayNum();
        MeetingRepository employeeRepositoryMock = Mockito.mock(MeetingRepository.class);
        Mockito.when(employeeRepositoryMock.findAllByDayOfWeekOrderByDayOfWeekAscStartTimeAsc(dayNum))
                .thenReturn(List.of(
                        new Meeting()
                                .setId(1L)
                                .setDayOfWeek(dayNum)
                                .setOrganizer(null)
                                .setStartTime(LocalTime.of(13, 0))
                                .setEndTime(LocalTime.of(14, 30))
                ));
        return employeeRepositoryMock;
    }

    private Validator mockValidator() {
        MeetingConfiguration configuration = new DefaultMeetingConfiguration();
        EmployeeRepository employeeRepositoryMock = mockEmployeeRepository();
        MeetingRepository meetingRepository = mockMeetingRepository();

        ConstraintValidatorFactory cvfMock = Mockito.mock(ConstraintValidatorFactory.class);
        Mockito.when(cvfMock.getInstance(DurationIsIntegralMultipleValidator.class))
                .thenReturn(new DurationIsIntegralMultipleValidator(configuration));
        Mockito.when(cvfMock.getInstance(DurationLengthValidator.class))
                .thenReturn(new DurationLengthValidator(configuration));
        Mockito.when(cvfMock.getInstance(DayOfWeekValidator.class))
                .thenReturn(new DayOfWeekValidator(configuration));
        Mockito.when(cvfMock.getInstance(StartTimeWithinBoundsValidator.class))
                .thenReturn(new StartTimeWithinBoundsValidator(configuration));
        Mockito.when(cvfMock.getInstance(StartTimeInRhythmValidator.class))
                .thenReturn(new StartTimeInRhythmValidator(configuration));
        Mockito.when(cvfMock.getInstance(EmployeeExistsValidator.class))
                .thenReturn(new EmployeeExistsValidator(employeeRepositoryMock));
        Mockito.when(cvfMock.getInstance(OverlappingValidator.class))
                .thenReturn(new OverlappingValidator(meetingRepository));

        try (ValidatorFactory vf = Validation.buildDefaultValidatorFactory()) {
            return vf.usingContext()
                    .constraintValidatorFactory(cvfMock)
                    .getValidator();
        }
    }

    private MeetingCommand createValidMeetingCommand() {
        return new MeetingCommand()
                .setOrganizerId(VALID_USER_ID)
                .setDayOfWeek(VALID_DAY)
                .setHour(VALID_HOUR)
                .setMinute(VALID_MINUTE)
                .setDurationInMinutes(VALID_DURATION);
    }

}
