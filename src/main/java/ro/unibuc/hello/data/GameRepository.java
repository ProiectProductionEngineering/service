package ro.unibuc.hello.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends MongoRepository<GameEntity, String> {

    List<GameEntity> findByGameName(String GameName);
    List<GameEntity> findByPublisher(String Publisher);
    List<GameEntity> findByReview(String Review);

}