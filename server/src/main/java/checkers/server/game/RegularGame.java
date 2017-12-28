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
    volatile private List<Player> players;
    volatile private List<Integer> playersInHome;
    private int numOfPlayers;
    volatile private int turnPlayer;
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

    @Override
    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    private void countHome(Coordinates currLocation, Coordinates destination, Player player) {
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
    public int makeMove(Coordinates currLocation, Coordinates destination, Player player) {
        logger.info("making move i game");
        if(state != GameState.RUNNING) {
            logger.warn("game not running");
            return -1;
        }
        if(player.equals(turn.getPlayer())) {
            logger.info("player == turnPlayer");
            int validationOfMove = rulesManager.checkMove(this, currLocation, destination, player.getColor());
            if(validationOfMove != -1) {
                turn.setCurrMov(destination);
                board.makeMove(currLocation, destination);
                countHome(currLocation, destination, player);
                logger.info(board.toString());
                return validationOfMove;
            }
        }
        return -1;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public RulesManager GetRulesManager() {
        return rulesManager;
    }

    @Override
    public void startGame() {
        turn = new Turn(players.get(turnPlayer));
        state = GameState.RUNNING;
        updatePlayers();
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
        logger.debug("Player ends move");
        turnPlayer++;
        if(numOfPlayers <= turnPlayer) {
            turnPlayer = 0;
        }
        turn = new Turn(players.get(turnPlayer));
        updatePlayers();
    }

    @Override
    public void addPlayer(Player player) {
        players.add(player);
        player.setGameAndColor(this, board.colorForPlayer(numOfPlayers), board);
        logger.info("new player with login: " + player.getPlayerName() + " and color: " + player.getColor() + " added");
        numOfPlayers++;
        for(Player p: players) {
            player.addNewPlayer(p.getPlayerName(), p.getColor());
            if(player != p) {
                p.addNewPlayer(player.getPlayerName(), player.getColor());
            }
        }
        if(numOfPlayers == board.getExNumOfPlayers()) {
            startGame();
        }
    }

    @Override
    public void updatePlayers() {
        logger.debug("updating players");
        for(Player p: players) {
            p.update(false);
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
