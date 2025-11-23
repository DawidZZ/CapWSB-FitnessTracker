package pl.wsb.fitnesstracker.training.api;

import jakarta.persistence.*;
import lombok.Getter;
import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.api.User;

import java.util.Date;

@Entity
@Table(name="trainings")
@Getter
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Column
    private Date startTime;

    @Column
    private Date endTime;

    @Column
    private ActivityType activityType;

    @Column
    private double distance;

    @Column
    private double averageSpeed;

    public Training(
            final User user,
            final Date startTime,
            final Date endTime,
            final ActivityType activityType,
            final double distance,
            final double averageSpeed) {
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
    }

    public Training() {

    }
}