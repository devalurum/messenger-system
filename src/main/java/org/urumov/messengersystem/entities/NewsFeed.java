package org.urumov.messengersystem.entities;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class NewsFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "newsFeed")
    private List<ItemFeed> feeds;
}
