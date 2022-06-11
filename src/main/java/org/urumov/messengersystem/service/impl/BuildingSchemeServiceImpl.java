package org.urumov.messengersystem.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.urumov.messengersystem.domain.dto.BuildingSchemeDto;
import org.urumov.messengersystem.domain.entity.BuildingScheme;
import org.urumov.messengersystem.domain.mapper.BuildingSchemeMapper;
import org.urumov.messengersystem.repository.BuildingSchemeRepository;
import org.urumov.messengersystem.service.BuildingSchemeService;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BuildingSchemeServiceImpl implements BuildingSchemeService {

    private final BuildingSchemeRepository buildingRepository;
    private final BuildingSchemeMapper buildingMapper;

    @Override
    @Transactional
    public BuildingSchemeDto getByName(@NonNull String name) {
        return buildingRepository.findById(name)
                .map(buildingMapper::toDTO)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public BuildingScheme save(@NonNull BuildingSchemeDto buildingSchemeDto) {
        BuildingScheme buildingScheme = buildingMapper.toModel(buildingSchemeDto);
        return buildingRepository.save(buildingScheme);
    }

    @Override
    @Transactional
    public BuildingScheme update(@NonNull BuildingSchemeDto buildingSchemeDto) {
        return buildingRepository.findById(buildingSchemeDto.getName())
                .map(scheme -> {
                    buildingMapper.updateModel(buildingSchemeDto, scheme);
                    buildingRepository.save(scheme);
                    return scheme;
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public boolean delete(@NonNull String name) {
        return buildingRepository.findById(name)
                .map(buildingScheme -> {
                    buildingRepository.delete(buildingScheme);
                    return true;
                })
                .orElseThrow(EntityNotFoundException::new);
    }
}
