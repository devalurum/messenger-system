package org.urumov.messengersystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.urumov.messengersystem.domain.entity.ItemFeed;

import java.util.List;
import java.util.Optional;

public interface ItemFeedRepository extends JpaRepository<ItemFeed, Long> {

    Optional<ItemFeed> findByIdAndNewsFeed_Id(Long id, Long newsId);

    List<ItemFeed> findAllByNewsFeed_Id(Long id);

    List<ItemFeed> findAllByNewsFeed_IdAndDepartment_Id(Long id, Long departmentId);

}