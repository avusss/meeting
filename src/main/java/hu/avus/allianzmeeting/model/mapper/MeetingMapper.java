package hu.avus.allianzmeeting.model.mapper;

import hu.avus.allianzmeeting.enums.WeekDay;
import hu.avus.allianzmeeting.model.dto.MeetingDto;
import hu.avus.allianzmeeting.model.entity.Meeting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MeetingMapper {

    @Mapping(source = "dayOfWeek", target = "dayOfWeek", qualifiedByName = "dayOfWeek")
    MeetingDto meetingToMeetingDto(Meeting meeting);

    @Named("dayOfWeek")
    default String dayOfWeekIntToDayOfWeekString(Integer value) {
        return WeekDay.fromDayNumber(value).name();
    }
}
