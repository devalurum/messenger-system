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
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String name;

    @OneToOne
    private User admin;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private List<User> users;

}
