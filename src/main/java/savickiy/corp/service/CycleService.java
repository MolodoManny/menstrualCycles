package savickiy.corp.service;

import savickiy.corp.dto.CycleDto;
import savickiy.corp.dto.CycleStatisticsDto;
import savickiy.corp.entity.jpa.MenstrualCycle;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CycleService {
    MenstrualCycle startCycle(Long userId, LocalDate startDate);

    MenstrualCycle endCycle(Long userId, Long cycleId,LocalDate endDate);

    void deleteCycle(Long userId, Long cycleId);

    List<MenstrualCycle> getUserCycle(Long userId);

    Optional<MenstrualCycle> getActiveCycle(Long userId);

    MenstrualCycle findById(Long userId, Long cycleId);

    MenstrualCycle updateCycle(Long userId, Long cycleId, CycleDto cycleDto);

    CycleStatisticsDto getStatistics(Long userId);

}
