package ro.unibuc.hello.data;

import java.util.List;
import org.springframework.data.annotation.Id;

public class GameEntity {
    @Id

    public String id;

    public String GameName;
    public String Publisher;
    public String release_date;
    public String Review;

    public GameEntity() {}

    @Override
    public String toString() {
        return super.toString();
    }
}
