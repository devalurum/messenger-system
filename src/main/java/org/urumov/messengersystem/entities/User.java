package org.urumov.messengersystem.entities;

import lombok.*;
import org.locationtech.jts.geom.Point;
import org.urumov.messengersystem.model.Gender;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column
    private String nickname;

    @Column(name = "first_name", length=50, nullable=false)
    private String firstName;

    @Column(name = "last_name", length=50, nullable=false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    //@Column(length = 7, columnDefinition = "varchar(7) default 'UNKNOWN'")
    private Gender gender;

    @Column(name = "date_of_birth")
    private LocalDate dob;

    @Column(name = "phone")
    private String phone;

    @Column(name = "image")
    private String image;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @ManyToOne
    @JoinColumn
    private Department department;

    @Column(name = "coordinates")
    private Point coordinates;

}
