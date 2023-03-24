package hu.avus.allianzmeeting.model.validator;

import hu.avus.allianzmeeting.model.constraint.EmployeeExistsConstraint;
import hu.avus.allianzmeeting.model.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class EmployeeExistsValidator implements ConstraintValidator<EmployeeExistsConstraint, Long> {

    private final EmployeeRepository employeeRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return employeeRepository.existsById(value);
    }
}
