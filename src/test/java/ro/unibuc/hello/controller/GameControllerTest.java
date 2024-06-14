package ro.unibuc.hello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.data.GameEntity;
import ro.unibuc.hello.dto.Gamedto;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.GameService;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class GameControllerTest {
    
    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();

    }

    @Test
    void test_CreateGame() throws Exception {
        // Arrange
        GameEntity newGame = new GameEntity("1", "Game1", "Publisher1", "1 ianuarie 2000", "5/10");
        when(gameService.saveGame(any(GameEntity.class))).thenReturn(newGame);

        // Act & Assert
        mockMvc.perform(post("/CreateGame")
                .content("{\"id\":\"1\", \"content\":\"Game1 Publisher1 1 ianuarie 2000 5/10\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.content").value("Game1 Publisher1 1 ianuarie 2000 5/10"));

    }

    @Test
    void test_updateGame() throws Exception {
        // Arrange
        String id = "1";
        GameEntity updatedGame = new GameEntity("1", "Game1", "Publisher1", "1 ianuarie 2000", "5/10");
        when(gameService.updateGame(eq(id), any(GameEntity.class))).thenReturn(updatedGame);

        // Act & Assert
        mockMvc.perform(put("/UpdateGame/{id}", id)
                .content("{\"id\":\"1\", \"content\":\"Game1 Publisher1 1 ianuarie 2000 5/10\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.content").value("Game1 Publisher1 1 ianuarie 2000 5/10"));
    }
    
    @Test
    void test_getAllGames() throws Exception {
        // Arrange
        List<GameEntity> games = Arrays.asList(new GameEntity("1", "Game1", "Publisher1", "1 ianuarie 2000", "5/10"),
                                            new GameEntity("2", "Game2", "Publisher2", "2 ianuarie 2000", "7/10"));
        when(gameService.getAllGames()).thenReturn(games);

        // Act & Assert
        mockMvc.perform(get("/Games"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.content").value("Game 1 Publisher1 1 ianuarie 2000 5/10"))
                .andExpect(jsonPath("$id").value("2"))
                .andExpect(jsonPath("$.content").value("Game 2 Publisher2 2 ianuarie 2000 7/10"));
    }

    @Test
    void test_DeleteGame() throws Exception {
        Gamedto Game = new Gamedto(1, "Game 2 Publisher2 2 ianuarie 2000 7/10");
        when(gameService.saveGame(any(Gamedto.class))).thenReturn(Game);

        mockMvc.perform(delete("/DeleteGame/{id}", id))
            .andExpect(status().isOk());

        verify(gameService, times(1)).deleteGame(id);

        mockMvc.perform(get("/Games"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());


    }

}
