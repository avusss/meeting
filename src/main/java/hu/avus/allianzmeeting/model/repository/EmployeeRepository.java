package hu.avus.allianzmeeting.model.repository;

import hu.avus.allianzmeeting.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
