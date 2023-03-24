package hu.avus.allianzmeeting.meetingconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MeetingConfigurationBeans {

    private static final String CONFIG_NAME = "meeting.config-implementation";
    private static final String CONFIG_DEFAULT_VALUE = "default";
    private static final String CONFIG_ALL_WORK_VALUE = "all-work";
    private static final String CONFIG_FOUR_PER_HOUR_VALUE = "four-per-hour";
    private static final String CONFIG_EARLY_FRIDAY_VALUE = "early-friday";

    @Bean
    @ConditionalOnProperty(matchIfMissing = true, name = CONFIG_NAME, havingValue = CONFIG_DEFAULT_VALUE)
    public MeetingConfiguration defaultConfiguration() {
        return new DefaultMeetingConfiguration();
    }

    @Bean
    @ConditionalOnProperty(name = CONFIG_NAME, havingValue = CONFIG_ALL_WORK_VALUE)
    public MeetingConfiguration allWorkConfiguration() {
        return new AllWorkZeroFunMeetingConfiguration();
    }

    @Bean
    @ConditionalOnProperty(name = CONFIG_NAME, havingValue = CONFIG_FOUR_PER_HOUR_VALUE)
    public MeetingConfiguration fourPerHourConfiguration() {
        return new FourMeetingsPerHourMeetingConfiguration();
    }

    @Bean
    @ConditionalOnProperty(name = CONFIG_NAME, havingValue = CONFIG_EARLY_FRIDAY_VALUE)
    public MeetingConfiguration earlyFridayConfiguration() {
        return new FridayGoHomeEarlyMeetingConfiguration();
    }

}
