package pl.wsb.fitnesstracker.event;

import jakarta.persistence.*;

import java.util.Date;

// TODO: Define the Event entity with appropriate fields and annotations
@Entity
@Table(name="event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Date startTime;

    @Column
    private Date endTime;

    @Column
    private String city;

    @Column
    private String country;
}
