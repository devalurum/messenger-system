package org.urumov.messengersystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.urumov.messengersystem.domain.model.Channel;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
}