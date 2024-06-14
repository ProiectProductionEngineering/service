package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import ro.unibuc.hello.data.GameEntity;
import ro.unibuc.hello.dto.Gamedto;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ro.unibuc.hello.service.GameService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Tag("IntegrationTest")

public class GameControllerIntegrationTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.2")
            .withExposedPorts(27017)
            .withEnv("MONGO_INITDB_ROOT_USERNAME","root") // user
            .withEnv("MONGO_INITDB_ROOT_PASSWORD", "example") // password
            .withEnv("MONGO_INITDB_DATABASE", "testdb") // dbname
            .withCommand("--auth");

    @BeforeAll
    public static void setUp() {
        mongoDBContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        mongoDBContainer.stop();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        final String MONGO_URL = "mongodb://root:example@localhost:";
        final String PORT = String.valueOf(mongoDBContainer.getMappedPort(27017));

        registry.add("mongodb.connection.url", () -> MONGO_URL + PORT);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameService gameService;

    @BeforeEach
    public void cleanUpAndAddTestData() {
        gameService.deleteAllGames();

        GameEntity game1 = new GameEntity("1", "Game1", "Publisher1", "1 ianuarie 2000", "5/10");
        GameEntity game2 = new GameEntity("2", "Game2", "Publisher2", "2 ianuarie 2000", "7/10");

        gameService.saveGame(game1);
        gameService.saveGame(game2);

    }

    @Test
    public void testGetAllGames() throws Exception {

        mockMvc.perform(get("/Games"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].content").value("Game1 Publisher1 1 ianuarie 2000 5/10"))
        .andExpect(jsonPath("$[1].content").value("Game2 Publisher2 2 ianuarie 2000 7/10"));
    }

    @Test
    public void testDeleteGame() throws Exception {

        mockMvc.perform(delete("/DeleteGame/1"))
            .andExpect(status().isOk());

        mockMvc.perform(get("/Games"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].content").value("Game2 Publisher2 2 ianuarie 2000 7/10"));
    }

    
}
