package hu.avus.allianzmeeting.service;

import hu.avus.allianzmeeting.model.dto.EmployeeDto;
import hu.avus.allianzmeeting.model.mapper.EmployeeMapper;
import hu.avus.allianzmeeting.model.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public List<EmployeeDto> getEmployees() {
        return employeeRepository.findAll(Sort.by("name").ascending())
                .stream()
                .map(employeeMapper::employeeToEmployeeDto)
                .collect(Collectors.toList());
    }

}
