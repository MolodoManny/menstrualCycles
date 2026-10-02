package savickiy.corp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import savickiy.corp.entity.jpa.UserNotification;
import savickiy.corp.entity.enums.NotificationType;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {

    List<UserNotification> findByUserIdOrderByScheduledAtDesc(Long userId);


    List<UserNotification> findBySentFalseAndScheduledAtBefore(LocalDateTime now);

    List<UserNotification> findByUserIdAndSentFalseOrderByScheduledAtAsc(Long userId);

    boolean existsByUserIdAndTypeAndScheduledAtBetween(
            Long userId, NotificationType type,
            LocalDateTime from, LocalDateTime to);
}
