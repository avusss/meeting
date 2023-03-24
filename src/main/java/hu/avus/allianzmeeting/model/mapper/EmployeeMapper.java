package hu.avus.allianzmeeting.model.mapper;

import hu.avus.allianzmeeting.model.dto.EmployeeDto;
import hu.avus.allianzmeeting.model.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto employeeToEmployeeDto(Employee employee);

}
