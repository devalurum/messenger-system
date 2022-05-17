package org.urumov.messengersystem.domain.model;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feed_item")
public class ItemFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time")
    @CreationTimestamp
    private LocalDateTime time;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn
    private User sender;

    @ManyToOne
    @JoinColumn
    private Department department;

    @ManyToOne
    private NewsFeed newsFeed;
}
