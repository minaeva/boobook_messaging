package ua.kiev.minaeva.boobookmessagingservice.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_id")
    private Long from;

    @Column(name = "to_id")
    private Long to;

    private String message;

    @Column(name = "date_added")
    @CreationTimestamp
    private LocalDateTime dateTime;

}
