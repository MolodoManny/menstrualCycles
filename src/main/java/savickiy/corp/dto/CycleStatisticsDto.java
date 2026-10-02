package savickiy.corp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CycleStatisticsDto {
    private int totalCycles;
    private Integer averageCycleLength;
    private Integer averagePeriodLength;
    private Integer minCycleLength;
    private Integer maxCycleLength;
}
