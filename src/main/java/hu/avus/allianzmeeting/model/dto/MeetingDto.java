package hu.avus.allianzmeeting.model.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class MeetingDto {

    private Long id;
    private EmployeeDto organizer;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

}
