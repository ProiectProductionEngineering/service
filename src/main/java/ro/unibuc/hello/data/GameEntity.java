package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class GameEntity {
    @Id

    public String id;

    public String GameName;
    public String Publisher;
    public String release_date;
    public String Review;

    public GameEntity() {}

    public GameEntity(String GameName, String Publisher, String release_date, String Review) {
        this.GameName = GameName;
        this.Publisher = Publisher;
        this.release_date = release_date;
        this.Review = Review;
    }

    public GameEntity(String id, String GameName, String Publisher, String release_date, String Review) {
        this.GameName = GameName;
        this.Publisher = Publisher;
        this.release_date = release_date;
        this.Review = Review;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameName() {
        return GameName;
    }

    public void setGameName(String GameName) {
        this.GameName = GameName;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String Publisher) {
        this.Publisher = Publisher;
    }

    public String getReleaseDate(String release_date) {
        return release_date;
    }

    public void setReleaseDate(String release_date) {
        this.release_date = release_date;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String Review) {
        this.Review = Review;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
