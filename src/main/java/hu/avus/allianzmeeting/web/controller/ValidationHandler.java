package hu.avus.allianzmeeting.web.controller;

import hu.avus.allianzmeeting.meetingconfiguration.MeetingConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ValidationHandler extends ResponseEntityExceptionHandler {

    private static final String[] PLACEHOLDERS = {"<MIN_DURATION>", "<MAX_DURATION>"};

    private final MeetingConfiguration configuration;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<Pair<String, String>> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {

            Class<?> errorClass = error.getClass();
            LOG.info("error class: {}", errorClass.getSimpleName());

            String fieldName = (error instanceof FieldError) ? ((FieldError) error).getField() : "_GLOBAL";
            String message = error.getDefaultMessage();

            for (String placeholder: PLACEHOLDERS) {
                if (message != null) {
                    message = message.replace(placeholder, placeholderResolver().apply(placeholder));
                }
            }

            errors.add(new ImmutablePair<>(fieldName, message));
        });
        return ResponseEntity.badRequest().body(errors);
    }

    private Function<String, String> placeholderResolver() {
        return s -> switch (s) {
            case "<MIN_DURATION>" -> String.valueOf(configuration.getMinimumMeetingUnitInMinutes());
            case "<MAX_DURATION>" -> String.valueOf(configuration.getMinimumMeetingUnitInMinutes() * configuration.getMaximumReservableUnits());
            default -> "";
        };
    }

}
