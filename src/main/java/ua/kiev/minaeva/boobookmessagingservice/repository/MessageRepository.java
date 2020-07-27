package ua.kiev.minaeva.boobookmessagingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kiev.minaeva.boobookmessagingservice.entity.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Optional<List<Message>> findByFromOrTo(Long id1, Long id2);

}
