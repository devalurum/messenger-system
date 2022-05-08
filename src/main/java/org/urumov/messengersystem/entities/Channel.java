package org.urumov.messengersystem.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60, nullable = false)
    private String name;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @OneToOne
    private User creator;

    @OneToMany
    private List<User> users;

    @OneToMany
    private List<Message> messages;

}
