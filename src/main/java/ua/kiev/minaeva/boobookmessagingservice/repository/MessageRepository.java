package ua.kiev.minaeva.boobookmessagingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kiev.minaeva.boobookmessagingservice.entity.Message;

import java.util.LinkedList;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Optional<LinkedList<Message>> findByFromOrToOrderByDateTimeAsc(Long id1, Long id2);

    Optional<LinkedList<Message>> findByFromOrToOrderByDateTimeDesc(Long id1, Long id2);

}
