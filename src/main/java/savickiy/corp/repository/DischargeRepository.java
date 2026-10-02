package savickiy.corp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import savickiy.corp.entity.jpa.Discharge;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DischargeRepository extends JpaRepository<Discharge, Long> {

    List<Discharge> findByCycleIdOrderByDateAsc(Long cycleId);

    Optional<Discharge> findByCycleIdAndDate(Long cycleId, LocalDate date);

    List<Discharge> findByCycleUserIdAndDateBetween(Long userId,
                                                    LocalDate from,
                                                    LocalDate to);

    void deleteByCycleId(Long cycleId);
}
