package savickiy.corp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import savickiy.corp.entity.jpa.SexualActivity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SexualActivityRepository extends JpaRepository<SexualActivity, Long> {

    List<SexualActivity> findByUserIdOrderByDateDesc(Long userId);

    List<SexualActivity> findByUserIdAndDateBetweenOrderByDateDesc(
            Long userId, LocalDate from, LocalDate to);


    List<SexualActivity> findByUserIdAndProtectedSexFalseOrderByDateDesc(Long userId);

    long countByUserIdAndProtectedSexAndDateBetween(Long userId,
                                                    boolean protectedSex,
                                                    LocalDate from,
                                                    LocalDate to);
}
