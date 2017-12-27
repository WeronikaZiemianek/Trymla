package checkers.server.game;

import checkers.core.Coordinates;
import checkers.server.Player;
import checkers.core.boards.Board;
import checkers.server.rules.RulesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

public class RegularGame implements Game {
    private List<Player> players;
    private List<Integer> playersInHome;
    private int numOfPlayers;
    private int turnPlayer;
    private RulesManager rulesManager;
    private Board board;
    private GameState state;
    private Logger logger;
    private Turn turn;

    public RegularGame(Board board, RulesManager rulesManager) {
        players = new ArrayList<>();
        playersInHome = new ArrayList<>();
        this.numOfPlayers = 0;
        this.board = board;
        state = GameState.OPEN;
        logger = LoggerFactory.getLogger(RegularGame.class);
        turnPlayer = 0;
        this.rulesManager = rulesManager;
    }

    private void countHome(Coordinates destination, Coordinates currLocation, Player player) {
        if((board.getFieldType(currLocation) != player.getColor() && board.getFieldType(destination) == player.getColor())) {
            int index = players.indexOf(player);
            int buf = playersInHome.get(index);
            buf++;
            playersInHome.set(index,buf);
            if(playersInHome.get(index) == 10) {
                endGame(players.get(index));
            }
        }
    }

    @Override
    public void makeMove(Coordinates destination, Coordinates currLocation, Player player) {
        if(state != GameState.RUNNING) {
            return;
        }
        if(player == turn.getPlayer()) {
            boolean validationOfMove = rulesManager.checkMove(this,destination, currLocation, player.getColor());
            if(validationOfMove) {
                turn.setCurrMov(destination);
                board.makeMove(destination, currLocation);
                countHome(destination, currLocation, player);
            }
        }
    }

    @Override
    public void startGame() {
        turn = new Turn(players.get(turnPlayer));
        updatePlayers();
        state = GameState.RUNNING;
    }

    private void endGame(Player player) {
        String login = player.getPlayerName();
        for(Player p : players) {
            p.endGame(login);
        }
        state = GameState.CLOSED;
        //TODO: inform manager
    }

    @Override
    public void endMove() {
        turnPlayer++;
        if(numOfPlayers <= turnPlayer)
            turnPlayer = 0;
        turn = new Turn(players.get(turnPlayer));
        updatePlayers();
    }

    @Override
    public void addPlayer(Player player) {
        players.add(player);
        player.setGameAndColor(this, board.colorForPlayer(numOfPlayers), board);
        logger.info("new player with login: " + player.getPlayerName() + " and color: " + player.getColor() + " added");
        numOfPlayers++;
        if(numOfPlayers == board.getExNumOfPlayers()) {
            startGame();
        }
    }

    private void updatePlayers() {
        for(Player player : players) {
            player.update(false);
        }
        players.get(turnPlayer).update(true);
    }

    @Override
    public Coordinates getCurrMov() {
        logger.info("value of currMov " + turn.getCurrMov());
        return turn.getCurrMov();
    }

    @Override
    public void setCurrMov(Coordinates player) {
         turn.setCurrMov(player);
    }

    @Override
    public void disconnectPlayer(Player player) {
        //TODO: disconnect player, erase from registry and factory memory, put a bot instead
    }

    @Override
    public GameState getState() {
        return state;
    }


}
