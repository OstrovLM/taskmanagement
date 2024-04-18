package lev.ostrov.taskmanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;
//@Value
@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDTO {
    Long id;
    String title;
    String description;
    Date dueDate;
    boolean completed;
}
