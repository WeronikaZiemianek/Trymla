package checkers.server;

import org.junit.Before;

import static org.mockito.Mockito.mock;

public class RegularRulesManagerTest {
    Game game;

    @Before
    void makeGame() {
        game = mock(RegularGame.class);
    }


}