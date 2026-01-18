package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.training.api.TrainingRepository;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.internal.UserRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WeeklyTrainingSummaryService {

    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;

    private record Acc(long count, double distanceSum, double speedSum) {}

    public void printWeeklySummary(Date from, Date to) {
        var trainings = trainingRepository
                .findAllByStartTimeGreaterThanEqualAndStartTimeLessThan(from, to);

        Map<Long, Acc> acc = new HashMap<>();

        for (var training : trainings) {
            User user = training.getUser();
            Long userId = user.getId();

            double dist = training.getDistance();
            double spd = training.getAverageSpeed();

            var cur = acc.getOrDefault(userId, new Acc(0, 0, 0));
            acc.put(userId, new Acc(cur.count() + 1, cur.distanceSum() + dist, cur.speedSum() + spd));
        }

        System.out.println("====================================================");
        System.out.printf("TYGODNIOWY RAPORT: %s -> %s%n", from, to);
        System.out.println("----------------------------------------------------");

        var users = userRepository.findAll();

        for (var u : users) {
            var a = acc.getOrDefault(u.getId(), new Acc(0, 0, 0));
            double avgSpeed = a.count() == 0 ? 0 : (a.speedSum() / a.count());

            System.out.printf(
                    "Użytkownik %d (%s %s): Treningi = %d, Dystans = %.2f, Średnia prędkość = %.2f%n",
                    u.getId(),
                    u.getFirstName(),
                    u.getLastName(),
                    a.count(),
                    a.distanceSum(),
                    avgSpeed
            );
        }

        System.out.println("====================================================");
    }
}
