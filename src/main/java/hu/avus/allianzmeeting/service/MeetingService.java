package hu.avus.allianzmeeting.service;

import hu.avus.allianzmeeting.enums.WeekDay;
import hu.avus.allianzmeeting.meetingconfiguration.MeetingConfiguration;
import hu.avus.allianzmeeting.model.dto.EmployeeDto;
import hu.avus.allianzmeeting.model.dto.MeetingCommand;
import hu.avus.allianzmeeting.model.dto.MeetingDto;
import hu.avus.allianzmeeting.model.entity.Employee;
import hu.avus.allianzmeeting.model.entity.Meeting;
import hu.avus.allianzmeeting.model.mapper.EmployeeMapper;
import hu.avus.allianzmeeting.model.mapper.MeetingMapper;
import hu.avus.allianzmeeting.model.repository.EmployeeRepository;
import hu.avus.allianzmeeting.model.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final EmployeeRepository employeeRepository;
    private final MeetingMapper meetingMapper;
    private final EmployeeMapper employeeMapper;
    private final MeetingConfiguration configuration;

    public List<MeetingDto> getMeetings() {
        return meetingRepository.findAll(Sort.by(Sort.Direction.ASC, "dayOfWeek", "startTime"))
                .stream()
                .map(meetingMapper::meetingToMeetingDto)
                .collect(Collectors.toList());
    }


    public List<MeetingDto> getMeetingsForEmployee(long employeeId) {
        Employee organizer = employeeRepository.findById(employeeId).orElseThrow();
        return meetingRepository.findAllByOrganizerOrderByDayOfWeekAscStartTimeAsc(organizer)
                .stream()
                .map(meetingMapper::meetingToMeetingDto)
                .collect(Collectors.toList());
    }

    public MeetingDto createMeeting(MeetingCommand command) {
        LocalTime startTime = LocalTime.of(command.getHour(), command.getMinute());
        Meeting meeting = new Meeting()
                .setOrganizer(employeeRepository.findById(command.getOrganizerId()).orElseThrow())
                .setDayOfWeek(WeekDay.valueOf(command.getDayOfWeek()).getDayNum())
                .setStartTime(startTime)
                .setEndTime(startTime.plusMinutes(command.getDurationInMinutes()));
        meetingRepository.save(meeting);
        return meetingMapper.meetingToMeetingDto(meeting);
    }

    public EmployeeDto getEmployeeFor(WeekDay dayOfWeek, LocalTime time) {
        return meetingRepository.findAllByDayOfWeekOrderByDayOfWeekAscStartTimeDesc(dayOfWeek.getDayNum())
                .stream()
                .filter(meeting -> !meeting.getStartTime().isAfter(time) && !time.isAfter(meeting.getEndTime()))
                .findFirst()
                .map(Meeting::getOrganizer)
                .map(employeeMapper::employeeToEmployeeDto)
                .orElse(null);
    }

    public Map<WeekDay, List<Pair<LocalTime, LocalTime>>> getFreeHours() {
        Map<WeekDay, List<Pair<LocalTime, LocalTime>>> resultMap = new LinkedHashMap<>();

        Arrays.stream(WeekDay.values())
                .filter(weekDay -> configuration.getStartTimeOnDay(weekDay) != null)
                .forEach(weekDay -> resultMap.put(weekDay, getFreeHoursForDay(weekDay)));

        return resultMap;
    }

    private List<Pair<LocalTime, LocalTime>> getFreeHoursForDay(WeekDay day) {
        List<Pair<LocalTime, LocalTime>> returnList = new ArrayList<>();

        List<Meeting> meetingsForThatDay
                = meetingRepository.findAllByDayOfWeekOrderByDayOfWeekAscStartTimeAsc(day.getDayNum());

//        int minimumDuration = configuration.getMinimumMeetingUnitInMinutes();
//        LocalTime actualTime = configuration.getStartTimeOnDay(weekDay);
//
//        do {
//            Meeting collidingMeeting = meetingsForThatDay
//                    .stream()
//                    .anyMatch(meeting -> meeting.getStartTime().equals(actualTime))
//
//            actualTime.plusMinutes()
//        } while (actualTime.isBefore(configuration.getEndTimeOnDay(weekDay)));
//
//        resultMap.put(weekDay, daysList);

        return returnList;
    }
}
