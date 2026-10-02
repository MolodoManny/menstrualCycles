package savickiy.corp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PredictionDto {
    private LocalDate predictionStartDate;
    private LocalDate predictionEndDate;
    private LocalDate predictionOvulationDate;
    private LocalDate predictionFertileStartDate;
    private LocalDate predictionFertileEndDate;
    private Integer averageCycleLength;
}
