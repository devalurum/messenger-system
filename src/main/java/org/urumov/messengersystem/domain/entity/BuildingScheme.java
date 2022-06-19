package org.urumov.messengersystem.domain.entity;


import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class BuildingScheme {

    @Id
    private String name;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private String geoJson;
}
