package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.dto.Gamedto;
import ro.unibuc.hello.data.GameRepository;
import ro.unibuc.hello.data.GameEntity;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.concurrent.atomic.AtomicLong;

import java.util.List;

@Component
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    private final AtomicLong counter = new AtomicLong();

    public GameEntity saveGame(GameEntity game) {
        return gameRepository.save(game);
    }

    public void deleteGame(String id) {
        gameRepository.deleteById(id);
    }

    public GameEntity updateGame(String id, GameEntity gameDetails) {
        GameEntity game = gameRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Game not found with id " + id));

        game.GameName = (gameDetails.GameName);
        game.Publisher = (gameDetails.Publisher);
        game.Review = (gameDetails.Review);

        return gameRepository.save(game);
    }

    public Gamedto getGameByName(String name) throws EntityNotFoundException {
        List<GameEntity> entities = gameRepository.findByGameName(name);
        if (entities == null) {
            throw new  EntityNotFoundException(name);
        }
        if(entities.size() == 0) {
            return new Gamedto(counter.incrementAndGet(),"NO Games Found!");
        
        }

        String result = "";
        for(int i = 0; i< entities.size();i++) {
            GameEntity entity = entities.get(i);
            result = result + entity.GameName + " " + entity.Publisher + " " + entity.Review + "\n\n";
        }

        return new Gamedto(counter.incrementAndGet(), result);
    }

    public Gamedto getAllGames() throws EntityNotFoundException{

        List<GameEntity> entities = gameRepository.findAll();

        String result = "";
        for(int i = 0; i< entities.size();i++) {
            GameEntity entity = entities.get(i);
            result = result + entity.GameName + " " + entity.Publisher + " " + entity.Review + "\n";
        }

        return new Gamedto(counter.incrementAndGet(), result);

    }

    
}
