package savickiy.corp.entity.jpa;

import jakarta.persistence.*;
import lombok.*;
import savickiy.corp.entity.enums.NotificationType;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private NotificationType type;

    @Column(name = "schedule_at",nullable = false,updatable = false)
    private LocalDateTime scheduleAt;

    @Column(name = "sent",nullable = false)
    private Boolean sent = false;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "message")
    private String message;
}
