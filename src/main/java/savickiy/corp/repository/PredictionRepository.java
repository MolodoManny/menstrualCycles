package savickiy.corp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import savickiy.corp.entity.jpa.Prediction;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Long> {

    List<Prediction> findByUserIdOrderByPredictedStartDateDesc(Long userId);

        Optional<Prediction> findFirstByUserIdOrderByCreatedAtDesc(Long userId);

    List<Prediction> findByUserIdAndPredictedStartDateBetweenOrderByPredictedStartDateAsc(
            Long userId, LocalDate from, LocalDate to);

    void deleteByUserId(Long userId);
}
