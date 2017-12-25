package checkers.server.game;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.Player;
import checkers.core.boards.Board;
import checkers.server.rules.RegularRulesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class RegularGame implements Game {
    private List<Player> players;
    private List<Integer> playersInHome;
    private int numOfPlayers;
    private int turnPlayer;
    private RegularRulesManager rulesManager;
    private Board board;
    private GameState state;
    private Logger logger;

    private Turn turn;

    public RegularGame(Board board) throws RemoteException {
        players = new ArrayList<>();
        playersInHome = new ArrayList<>();
        this.numOfPlayers = 0;
        this.board = board;
        state = GameState.OPEN;
        logger = LoggerFactory.getLogger(RegularGame.class);
        turnPlayer = 0;
        rulesManager = new RegularRulesManager(board);
    }

    public void makeMove(Coordinates destination, Coordinates currLocation, Player player) throws RemoteException {
        if(player == turn.getPlayer()) {
            int validationOfMove = rulesManager.checkMove(this ,destination, currLocation, player.getColor());
            if(validationOfMove == 1 || validationOfMove == 2 ) {
                turn.setCurrMov(destination);
                board.makeMove(destination, currLocation);
                if((getFieldType(currLocation) != player.getColor() && getFieldType(destination) == player.getColor())) {
                    int index = players.indexOf(player);
                    int buf = playersInHome.get(index);
                    buf++;
                    playersInHome.set(index,buf);
                    if(playersInHome.get(index) == 10){
                        //Player win
                    }
                }
            }
        }
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
        numOfPlayers++;
        players.add(player);
    }

    private void updatePlayers() {
        int numOfPlayers = players.size();
        for(int i = 0; i < numOfPlayers; i++) {
            players.get(i).update(false);
        }
        players.get(turnPlayer).update(true);
    }

    @Override
    public Coordinates getCurrMov() {
        return turn.getCurrMov();
    }

    @Override
    public Turn getTurn() {
        return turn;
    }

    @Override
    public Checker getOccupiedByType (Coordinates location) {
        return board.getFieldOccupiedBy(location);
    }

    @Override
    public Checker getFieldType (Coordinates location) {
        return board.getFieldType(location);
    }
}
