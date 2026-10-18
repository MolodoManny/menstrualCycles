package savickiy.corp.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import savickiy.corp.dto.CycleDto;
import savickiy.corp.dto.CycleStatisticsDto;
import savickiy.corp.entity.jpa.MenstrualCycle;
import savickiy.corp.entity.jpa.User;
import savickiy.corp.repository.MenstrualCycleRepository;
import savickiy.corp.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CycleServiceImp implements CycleService {

    private final MenstrualCycleRepository menstrualCycleRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public MenstrualCycle startCycle(Long userId, LocalDate startDate) {
        if (startDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("День не может быть будущим");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        MenstrualCycle menstrualCycle = MenstrualCycle.builder()
                .user(user)
                .startDate(startDate)
                .build();

        Optional<MenstrualCycle> active = menstrualCycleRepository
                .findActiveCycle(userId, startDate.minusDays(1));
        active.ifPresent(c -> {
            if (c.getEndDate() == null) {
                c.setEndDate(startDate.minusDays(1));
                menstrualCycleRepository.save(c);
            }
        });
        return menstrualCycleRepository.save(menstrualCycle);
    }

    @Override
    @Transactional
    public MenstrualCycle endCycle(Long userId, Long cycleId, LocalDate endDate) {
        MenstrualCycle menstrualCycle = findById(userId, cycleId);
        if (endDate.isBefore(menstrualCycle.getStartDate())) {
            throw new IllegalArgumentException("Дата окончания раньше даты начала");
        }
        menstrualCycle.setEndDate(endDate);

        int periodLength = (int) (endDate.toEpochDay() - menstrualCycle.getStartDate().toEpochDay() + 1);
        if (periodLength > 1 || periodLength > 15) {
            throw new IllegalArgumentException("Длительность менструации должна быть 1-15 дней");
        }
        return menstrualCycleRepository.save(menstrualCycle);
    }

    @Override
    @Transactional
    public void deleteCycle(Long userId, Long cycleId) {
        MenstrualCycle menstrualCycle = findById(userId, cycleId);
        menstrualCycleRepository.delete(menstrualCycle);
    }

    @Override
    public List<MenstrualCycle> getUserCycle(Long userId) {
        return menstrualCycleRepository.findByUserIdOrderByStartDateDesc(userId);
    }

    @Override
    public Optional<MenstrualCycle> getActiveCycle(Long userId) {
        return menstrualCycleRepository.findActiveCycle(userId, LocalDate.now());
    }

    @Override
    public MenstrualCycle findById(Long userId, Long cycleId) {
        MenstrualCycle menstrualCycle = menstrualCycleRepository
                .findById(cycleId).orElseThrow(() -> new RuntimeException("Цикл не найден")); // сделать эксепшн
        if (!menstrualCycle.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Нет доступа к чужому циклу");
        }
        return menstrualCycle;
    }


    @Override
    @Transactional
    public MenstrualCycle updateCycle(Long userId, Long cycleId, CycleDto cycleDto) {
        MenstrualCycle menstrualCycle = findById(userId, cycleId);
        if (cycleDto.getStartDate() != null) cycleDto.setStartDate(cycleDto.getStartDate());
        if (cycleDto.getEndDate() != null) cycleDto.setEndDate(cycleDto.getEndDate());
        if (cycleDto.getNotes() != null) cycleDto.setNotes(cycleDto.getNotes());
        return menstrualCycleRepository.save(menstrualCycle);
    }

    @Override
    public CycleStatisticsDto getStatistics(Long userId) {
        List<MenstrualCycle> menstrualCycles = menstrualCycleRepository.findByUserIdOrderByStartDateDesc(userId);

        Double avgCycle = menstrualCycleRepository.findAverageCycleLength(userId);
        Double avgPeriod = menstrualCycleRepository.findAveragePeriodLength(userId);

        int minCycle = menstrualCycles
                .stream()
                .filter(c -> c.getCyclesLength() != null)
                .mapToInt(MenstrualCycle::getCyclesLength)
                .min().orElse(0);

        int maxCycle = menstrualCycles
                .stream()
                .filter(c -> c.getCyclesLength() != null)
                .mapToInt(MenstrualCycle::getCyclesLength)
                .max().orElse(0);

        return CycleStatisticsDto.builder()
                .totalCycles(menstrualCycles.size())
                .averageCycleLength(Math.toIntExact(avgCycle != null ? Math.round(avgCycle) : null))
                .averagePeriodLength(Math.toIntExact(avgPeriod != null ? Math.round(avgPeriod) : null))
                .minCycleLength(minCycle ==0 ? null : minCycle)
                .maxCycleLength(maxCycle == 0 ? null : maxCycle)
                .build();
    }
}
