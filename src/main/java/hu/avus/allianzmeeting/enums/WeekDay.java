package hu.avus.allianzmeeting.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum WeekDay {

    MON(1),
    TUE(2),
    WED(3),
    THU(4),
    FRI(5),
    SAT(6),
    SUN(7);

    private final int dayNum;

    public static WeekDay fromDayNumber(int dayNum) {
        return Arrays.stream(WeekDay.values())
                .filter(weekDay -> weekDay.dayNum == dayNum)
                .findFirst()
                .orElseThrow();
    }

}
