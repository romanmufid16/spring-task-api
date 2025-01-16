package romanmufid.springmongo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponse {

    private String id;

    private String userId;

    private String title;

    private String description;

    private String status;

    private Date createdAt;

}
