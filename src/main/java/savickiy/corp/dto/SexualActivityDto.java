package savickiy.corp.dto;

import lombok.Data;
import savickiy.corp.entity.enums.ProtectionType;

import java.time.LocalDate;

@Data
public class SexualActivityDto {
    private LocalDate date;
    private Boolean protectedSex;
    private ProtectionType protectionType;
    private String notes;
}
