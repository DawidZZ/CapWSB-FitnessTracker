package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingRepository;

import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
class TrainingController {

    private final TrainingRepository trainingRepository;

    @GetMapping
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    @GetMapping("/by-user/{userId}")
    public List<Training> getTrainingsByUser(@PathVariable Long userId) {
        return trainingRepository.findAll().stream()
                .filter(t -> t.getUser() != null)
                .filter(t -> t.getUser().getId().equals(userId))
                .toList();
    }
}
