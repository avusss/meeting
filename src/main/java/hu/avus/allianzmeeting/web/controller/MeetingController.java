package hu.avus.allianzmeeting.web.controller;

import hu.avus.allianzmeeting.enums.WeekDay;
import hu.avus.allianzmeeting.model.dto.EmployeeDto;
import hu.avus.allianzmeeting.model.dto.MeetingCommand;
import hu.avus.allianzmeeting.model.dto.MeetingDto;
import hu.avus.allianzmeeting.service.MeetingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class MeetingController {

    private final MeetingService meetingService;

    @GetMapping("/meetings")
    public ResponseEntity<List<MeetingDto>> getMeetings() {
        return ResponseEntity.ok(meetingService.getMeetings());
    }

    @GetMapping("/meetings-for-employee/{employeeId}")
    public ResponseEntity<List<MeetingDto>> getMeetingsForEmploee(@PathVariable("employeeId") long employeeId) {
        return ResponseEntity.ok(meetingService.getMeetingsForEmployee(employeeId));
    }

    @PostMapping("/meetings")
    public ResponseEntity<MeetingDto> createMeeting(@RequestBody @Valid MeetingCommand command) {
        return ResponseEntity.ok(meetingService.createMeeting(command));
    }

    @GetMapping("/booked-by/{dayOfWeek}/{time}")
    public ResponseEntity<EmployeeDto> getBookedBy(
            @PathVariable("dayOfWeek") WeekDay dayOfWeek,
            @PathVariable("time") String timeString
            ) {
        return ResponseEntity.ok(meetingService.getEmployeeFor(dayOfWeek, LocalTime.parse(timeString)));
    }

    @GetMapping("/free-hours")
    public ResponseEntity<Map<WeekDay, List<Pair<LocalTime, LocalTime>>>> getFreeHours() {
        return ResponseEntity.ok(meetingService.getFreeHours());
    }
}
