package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GamedtoTest {
    
    Gamedto myGame = new Gamedto(1, "Game 1 Publisher1 1 ianuarie 2000 5/10");

    @Test
    void test_content(){
        Assertions.assertSame("Game 1 Publisher1 1 ianuarie 2000 5/10", myGame.getContent());
    }

    @Test
    void test_id() {
        Assertions.assertEquals(1, myGame.getId());

    }
}
