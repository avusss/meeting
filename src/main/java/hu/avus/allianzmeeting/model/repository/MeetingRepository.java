package hu.avus.allianzmeeting.model.repository;

import hu.avus.allianzmeeting.model.entity.Employee;
import hu.avus.allianzmeeting.model.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    List<Meeting> findAllByOrganizerOrderByDayOfWeekAscStartTimeAsc(Employee organizer);

    List<Meeting> findAllByDayOfWeekOrderByDayOfWeekAscStartTimeDesc(int dayOfWeek);
    List<Meeting> findAllByDayOfWeekOrderByDayOfWeekAscStartTimeAsc(int dayOfWeek);

}
