package pl.wsb.fitnesstracker.healthmetrics.api;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.wsb.fitnesstracker.user.api.User;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "health_metrics")
public class HealthMetrics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private Float weight;

    @Column
    private Float height;

    @Column(name = "heart_rate")
    private Integer heartRate;

    @Column
    private Date date;
}
