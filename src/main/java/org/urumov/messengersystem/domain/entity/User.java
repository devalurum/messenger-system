package org.urumov.messengersystem.domain.entity;

import lombok.*;
import org.hibernate.annotations.*;
import org.locationtech.jts.geom.Point;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
@Table(name = "user_table")
public class User implements UserDetails {

    @CreatedDate
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @Column(nullable = false)
    @Builder.Default
    private boolean enabled = true;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    @Email
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(length = 7, columnDefinition = "varchar(7) default 'UNKNOWN'")
    private Gender gender = Gender.UNKNOWN;

    @Column(name = "date_of_birth")
    private LocalDate dob;

    @Column(name = "phone")
    private String phone;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Collection<Role> authorities = List.of(Role.USER);

    @OneToOne
    @JoinColumn
    private Department departmentAdmin;

    @ManyToOne
    @JoinColumn
    private Department department;

    @Column(name = "coordinates")
    private Point coordinates;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    private List<ItemFeed> itemFeeds;

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }
}
