package checkers.server.game;

import checkers.core.Coordinates;
import checkers.core.Move;
import checkers.server.player.Player;
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
    private int passesInRow;
    private String winner;
    private volatile int numOfBots;

    public RegularGame(Board board, RulesManager rulesManager, int turnPlayer) {
        numOfBots = 0;
        winner = null;
        passesInRow = 0;
        players = new ArrayList<>();
        playersInHome = new ArrayList<>();
        this.numOfPlayers = 0;
        this.board = board;
        state = GameState.OPEN;
        logger = LoggerFactory.getLogger(RegularGame.class);
        this.turnPlayer = turnPlayer;
        this.rulesManager = rulesManager;
    }

    @Override
    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    private boolean countHome(Coordinates currLocation, Coordinates destination, Player player) {
        if((board.getFieldType(currLocation) != player.getColor() && board.getFieldType(destination) == player.getColor())) {
            int index = players.indexOf(player);
            int buf = playersInHome.get(index);
            buf++;
            playersInHome.set(index,buf);
            if(playersInHome.get(index) == 10) {
                return endGameForPlayer(index);
            }
        }
        return false;
    }

    private synchronized boolean endGameForPlayer(int index) {
        Player winnerPlayer = players.get(index);
        if(winner == null) {
            winner = winnerPlayer.getPlayerName();
        }
        if(numOfPlayers == 2 || numOfPlayers == numOfBots + 1) {
            endGame(winner);
            return true;
        } else {
            winnerPlayer.endGame(winner);
            players.remove(index);
            playersInHome.remove(index);
            numOfPlayers--;
            if(turnPlayer == index) {
                endMove(null, null);
            }
        }
        logger.info("number of players is ".concat(String.valueOf(numOfPlayers*10 + players.size())));
        return false;
    }

    @Override
    synchronized public int makeMove(Coordinates currLocation, Coordinates destination, Player player) {
        logger.debug("making move in game");
        if(state != GameState.RUNNING) {
            logger.warn("game not running");
            return -1;
        }
        if(player.equals(turn.getPlayer())) {
            logger.debug("player == turnPlayer");
            int validationOfMove = rulesManager.checkMove(this, currLocation, destination, player.getColor());
            if(validationOfMove != -1) {
                if(validationOfMove == 2) {
                    turn.setCanMove(false);
                }
                turn.setCurrMov(destination);
                board.makeMove(currLocation, destination);
                passesInRow = 0;
                logger.info("\n" + board.toString());
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

    private void initInHome() {
        int numberOfPlayers = board.getExNumOfPlayers();
        for(int i = 0; i < numberOfPlayers; i ++) {
            playersInHome.add(0);
        }
    }

    @Override
    public void startGame() {
        turn = new Turn(players.get(turnPlayer));
        initInHome();
        state = GameState.RUNNING;
    }

    private void endGame(String player) {
        state = GameState.CLOSED;
        if(winner == null) {
            if(player != null) {
                winner = player;
            }
        }
        for(Player p : players) {
            p.endGame(winner);
        }
    }

    @Override
    public synchronized void endJump(Move lastMove, Player player) {
        if(lastMove != null) {
            countHome(lastMove.getLocation(), lastMove.getDestination(), player);
        }
        updatePlayers(lastMove);
    }

    @Override
    public synchronized void endMove(Move lastMove, Player player) {
        turnPlayer++;
        if(numOfPlayers <= turnPlayer) {
            turnPlayer = 0;
        }
        if(lastMove != null) {
            if(countHome(lastMove.getLocation(), lastMove.getDestination(), player)) {
                return;
            }
        }
        passesInRow++;
        if(passesInRow == board.getExNumOfPlayers()+1) {
            endGame(null);
        }
        logger.debug("Player ends move");
        turn = new Turn(players.get(turnPlayer));
        updatePlayers(lastMove);
    }

    @Override
    public synchronized boolean addPlayer(Player player) {
        boolean res = false;
        if(numOfPlayers != board.getExNumOfPlayers()) {
            players.add(player);
            player.setGameAndColor(this, board.colorForPlayer(numOfPlayers));
            logger.info("new player with login: " + player.getPlayerName() + " and color: " + player.getColor() + " added");
            numOfPlayers++;
            res = true;
            for(Player p : players) {
                player.addNewPlayer(p.getPlayerName(), p.getColor());
                if(player != p) {
                    p.addNewPlayer(player.getPlayerName(), player.getColor());
                }
            }
            logger.debug(String.valueOf(players.size()));
        }
        if(numOfPlayers == board.getExNumOfPlayers()) {
            startGame();
        }
        return res;
    }

    @Override
    public synchronized void updatePlayers(Move lastMove) {
        if(lastMove != null)  {
            logger.info("updating players\n last move from: " + lastMove.getLocation().X() + " " + lastMove.getLocation().Y() +
            "\n to: " + lastMove.getDestination().X() + " " + lastMove.getDestination().Y() +
            "\n with: " + lastMove.getColor());
        }
        for(Player p: players) {
            if(p != players.get(turnPlayer)) {
                p.update(false, lastMove);
            }
        }
        try {
            players.get(turnPlayer).update(true, lastMove);
        } catch(IndexOutOfBoundsException e) {
            turnPlayer = 0;
            players.get(turnPlayer).update(true, lastMove);
        }
    }

    @Override
    public boolean canMove() {
        return turn.getCanMove();
    }

    @Override
    public Coordinates getCurrMov() {
        return turn.getCurrMov();
    }

    @Override
    public synchronized void disconnectPlayer(Player player) {
        int index = players.indexOf(player);
        String login = "bot".concat(String.valueOf(index));
        Player bot = new DefaultBot(login);
        players.remove(index);
        players.add(index, bot);
        bot.setGameAndColor(this, board.colorForPlayer(index));
        increaseNumOfBots();
        if (numOfPlayers == numOfBots) {
            endGame(winner);
            return;
        }
        logger.debug(String.valueOf(players.size()));
        for(Player p : players) {
            p.replaceWithBot(login, index);
        }
        turn = new Turn(players.get(turnPlayer));
        if(state == GameState.RUNNING) {
            updatePlayers(null);
        }
    }

    @Override
    public GameState getState() {
        return state;
    }

    @Override
    public void increaseNumOfBots() {
        numOfBots++;
    }

}
