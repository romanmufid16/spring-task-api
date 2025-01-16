package romanmufid.springmongo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskDto {

    @NotEmpty(message = "Title cannot be empty")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @NotEmpty(message = "Description cannot be empty")
    @Size(min = 5, max = 255, message = "Description must be between 5 and 255 characters")
    private String description;

    private String status;

}
