package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class WeeklyTrainingSummaryJob  {

    private final WeeklyTrainingSummaryService reportService;

    private static final ZoneId ZONE = ZoneId.of("Europe/Warsaw");

    // co poniedziałek 06:00
//    @Scheduled(cron = "0 0 6 * * MON", zone = "Europe/Warsaw")
    @Scheduled(fixedRate = 10000)
    public void run() {
        final ZoneId ZONE = ZoneId.of("Europe/Warsaw");

        LocalDate today = LocalDate.now(ZONE);

        LocalDate thisMonday = today.with(
                TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)
        );

        LocalDate lastMonday = thisMonday.minusWeeks(1);

        LocalDateTime fromLdt = lastMonday.atStartOfDay();
        LocalDateTime toLdt   = thisMonday.atStartOfDay();

        Date from = Date.from(fromLdt.atZone(ZONE).toInstant());
        Date to   = Date.from(toLdt.atZone(ZONE).toInstant());

        reportService.printWeeklySummary(from, to);

    }
}
