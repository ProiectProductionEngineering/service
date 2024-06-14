package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.data.GameEntity;
import ro.unibuc.hello.dto.Gamedto;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.GameService;

@Controller
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/CreateGame") //Create a Game
    public ResponseEntity<GameEntity> addGame(@RequestBody GameEntity game) {
        GameEntity savedGame = gameService.saveGame(game);
        return ResponseEntity.ok(savedGame);
    }

    @DeleteMapping("/DeleteGame/{id}") //Delete a Game
    public ResponseEntity<?> DeleteGame(@PathVariable String id) {
        gameService.deleteGame(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/UpdateGame/{id}") //Update a Game
    public ResponseEntity<GameEntity> updateGame(@PathVariable String id, @RequestBody GameEntity gameDetails) {
        GameEntity updatedGame = gameService.updateGame(id, gameDetails);
        return ResponseEntity.ok(updatedGame);

    }

    @GetMapping("/Game/{GameName}") // GET a Game by Name
    @ResponseBody
    public Gamedto game(@PathVariable("GameName") String GameName) throws EntityNotFoundException {
        return gameService.getGameByName(GameName);
    }

    @GetMapping("/Games") //GET All Games
    @ResponseBody
    public Gamedto game() throws EntityNotFoundException {
        return gameService.getAllGames();
    }

}
