package checkers.server.game;

import checkers.core.Coordinates;
import checkers.core.Move;
import checkers.server.Player;
import checkers.core.boards.Board;
import checkers.server.player.DefaultBot;
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
    private Move lastMove;
    private int passesInRow;

    public RegularGame(Board board, RulesManager rulesManager) {
        passesInRow = 0;
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
    synchronized public int makeMove(Coordinates currLocation, Coordinates destination, Player player) {
        logger.info("making move i game");
        if(state != GameState.RUNNING) {
            logger.warn("game not running");
            return -1;
        }
        if(player.equals(turn.getPlayer())) {
            logger.info("player == turnPlayer");
            int validationOfMove = rulesManager.checkMove(this, currLocation, destination, player.getColor());
            if(validationOfMove != -1) {
                if(validationOfMove == 2) {
                    turn.setCanMove(false);
                }
                turn.setCurrMov(destination);
                lastMove = new Move(currLocation, destination, player.getColor());
                board.makeMove(currLocation, destination);
                passesInRow = 0;
                logger.info(board.toString());
                countHome(currLocation, destination, player);
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
    }

    private void endGame(Player player) {
        String login;
        state = GameState.CLOSED;
        if(player == null) {
            login = null;
        } else {
            login = player.getPlayerName();
        }
        for(Player p : players) {
            p.endGame(login);
        }
    }

    @Override
    public synchronized void endMove(Move lastMove) {
        passesInRow++;
        if(passesInRow == board.getExNumOfPlayers()+1) {
            endGame(null);
        }
        logger.debug("Player ends move");
        turnPlayer++;
        if(numOfPlayers <= turnPlayer) {
            turnPlayer = 0;
        }
        turn = new Turn(players.get(turnPlayer));
        updatePlayers(lastMove);
    }

    @Override
    public synchronized boolean addPlayer(Player player) {
        boolean res = false;
        if(numOfPlayers != board.getExNumOfPlayers()) {
            players.add(player);
            player.setGameAndColor(this, board.colorForPlayer(numOfPlayers), board);
            logger.info("new player with login: " + player.getPlayerName() + " and color: " + player.getColor() + " added");
            numOfPlayers++;
            res = true;
            for(Player p : players) {
                player.addNewPlayer(p.getPlayerName(), p.getColor());
                if(player != p) {
                    p.addNewPlayer(player.getPlayerName(), player.getColor());
                }
            }
        }
        if(numOfPlayers == board.getExNumOfPlayers()) {
            startGame();
        }
        return res;
    }

    @Override
    public synchronized void updatePlayers(Move lastMove) {
        logger.debug("updating players");
        for(Player p: players) {
            if(p != players.get(turnPlayer)) {
                p.update(false, lastMove);
            }
        }
        players.get(turnPlayer).update(true, lastMove);
    }

    @Override
    public boolean canMove() {
        return turn.getCanMove();
    }

    @Override
    public Coordinates getCurrMov() {
        logger.info("value of currMov " + turn.getCurrMov());
        return turn.getCurrMov();
    }

    @Override
    public void disconnectPlayer(Player player) {
        int index = players.indexOf(player);
        String login = "bot".concat(String.valueOf(index));
        players.add(index, new DefaultBot(login));
        logger.debug(players.toString());
        for(Player p : players) {
            p.replaceWithBot(login, index);
        }
        if(state == GameState.RUNNING) {
            updatePlayers(null);
        }
    }

    @Override
    public GameState getState() {
        return state;
    }

    @Override
    public Move getLastMove() {
        return lastMove;
    }
}
