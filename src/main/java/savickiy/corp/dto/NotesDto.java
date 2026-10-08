package savickiy.corp.dto;

import lombok.Data;
import savickiy.corp.entity.enums.Mood;

import java.time.LocalDate;

@Data
public class NotesDto {
    private LocalDate date;
    private String title;
    private String content;
    private Mood mood;
}
