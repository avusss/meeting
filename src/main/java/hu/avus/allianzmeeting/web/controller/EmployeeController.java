package hu.avus.allianzmeeting.web.controller;

import hu.avus.allianzmeeting.model.dto.EmployeeDto;
import hu.avus.allianzmeeting.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("")
    public ResponseEntity<List<EmployeeDto>> actionList() {
        return ResponseEntity.ok(employeeService.getEmployees());
    }

}
