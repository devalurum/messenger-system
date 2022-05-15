package org.urumov.messengersystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.urumov.messengersystem.domain.model.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value="SELECT m FROM Message m WHERE (m.sender.id = :senderId AND m.receiver.id = :receiverId) OR " +
            "(m.sender.id = :receiverId AND m.receiver.id = :senderId) ORDER BY m.time")
    List<Message> findMessagesBetweenTwoUsers (@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

    Optional<Message> findByChannel_IdAndId(Long channelId, Long idMessage);
}