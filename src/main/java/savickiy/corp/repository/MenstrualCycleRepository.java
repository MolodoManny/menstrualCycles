package savickiy.corp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import savickiy.corp.entity.jpa.MenstrualCycle;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenstrualCycleRepository extends JpaRepository<MenstrualCycle, Long> {


    List<MenstrualCycle> findByUserIdOrderByStartDateDesc(Long userId);


    @Query("""
                SELECT c FROM MenstrualCycle c
                WHERE c.user.id = :userId
                ORDER BY c.startDate DESC
                LIMIT :limit
            """)
    List<MenstrualCycle> findRecentCycles(@Param("userId") Long userId,
                                          @Param("limit") int limit);


    @Query("""
                SELECT c FROM MenstrualCycle c
                WHERE c.user.id = :userId
                  AND c.startDate <= :date
                  AND (c.endDate IS NULL OR c.endDate >= :date)
                ORDER BY c.startDate DESC
                LIMIT 1
            """)
    Optional<MenstrualCycle> findActiveCycle(@Param("userId") Long userId,
                                             @Param("date") LocalDate date);


    Optional<MenstrualCycle> findFirstByUserIdOrderByStartDateDesc(Long userId);


    @Query(value = """
                SELECT AVG(diff) FROM (
                    SELECT c.start_date - LAG(c.start_date) OVER (ORDER BY c.start_date) AS diff
                    FROM menstrual_cycles c
                    WHERE c.user_id = :userId
                ) sub
                WHERE diff IS NOT NULL
            """, nativeQuery = true)
    Double findAverageCycleLength(@Param("userId") Long userId);


    @Query("""
                SELECT AVG(FUNCTION('DATE_PART', 'day', c.endDate) - FUNCTION('DATE_PART', 'day', c.startDate))
                FROM MenstrualCycle c
                WHERE c.user.id = :userId AND c.endDate IS NOT NULL
            """)
    Double findAveragePeriodLength(@Param("userId") Long userId);

    List<MenstrualCycle> findByUserIdAndStartDateBetweenOrderByStartDateDesc(
            Long userId, LocalDate from, LocalDate to);
}
