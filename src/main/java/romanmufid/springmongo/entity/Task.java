package romanmufid.springmongo.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("tasks")
public class Task {

    @Id
    private ObjectId id;

    private String title;

    private String description;

    private String status;

    private Date createdAt;
}
