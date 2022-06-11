package org.urumov.messengersystem.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
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
    @CreationTimestamp
    private LocalDateTime createdTime;

    @OneToOne
    private User creator;

    @OneToMany
    private List<User> users;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Message> messages;

}
