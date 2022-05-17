package org.urumov.messengersystem.domain.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.urumov.messengersystem.domain.dto.ItemFeedDto;
import org.urumov.messengersystem.domain.model.Department;
import org.urumov.messengersystem.domain.model.ItemFeed;
import org.urumov.messengersystem.domain.model.Role;
import org.urumov.messengersystem.repository.DepartmentRepository;

import java.util.Collections;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Mapper(componentModel = "spring")
public abstract class ItemFeedMapper {

    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "senderId", source = "sender.id")
    public abstract ItemFeedDto toDto(ItemFeed itemFeed);

    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "time", ignore = true)
    public abstract ItemFeed toModel(ItemFeedDto itemFeedDto);

}
