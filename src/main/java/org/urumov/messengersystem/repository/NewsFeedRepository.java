package org.urumov.messengersystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.urumov.messengersystem.entities.NewsFeed;

public interface NewsFeedRepository extends JpaRepository<NewsFeed, Long> {

}