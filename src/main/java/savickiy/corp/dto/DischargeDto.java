package savickiy.corp.dto;

import lombok.Data;
import savickiy.corp.entity.enums.DischargeColor;
import savickiy.corp.entity.enums.DischargeIntensity;

import java.time.LocalDate;

@Data
public class DischargeDto {
    private LocalDate date;
    private DischargeIntensity intensity;
    private DischargeColor color;
    private String notes;
}
