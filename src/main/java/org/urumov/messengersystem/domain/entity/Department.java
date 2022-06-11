package org.urumov.messengersystem.domain.entity;

import lombok.*;

import javax.persistence.*;
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
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String name;

    @OneToOne(mappedBy = "departmentAdmin")
    private User admin;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "department")
    private List<User> users;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "department")
    private List<ItemFeed> itemFeeds;

}
