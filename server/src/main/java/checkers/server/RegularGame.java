package checkers.server;

import checkers.server.boards.RegularBoard;

import java.util.List;

public class RegularGame implements Game {
    private List<Player> players;
    private int numOfPlayers;
    private RegularRulesManager rulesManager;
    private RegularBoard board;
    private GamesManager gamesManager;
    private GameState state;
    private Turn turn;

    RegularGame(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
        state = GameState.OPEN;
    }

    public void makeMove(Coordinates destination, Coordinates currLocation, Player player){
        if(player == turn.getTurn()) {
            if(rulesManager.checkMove(destination, currLocation, player.getColor())) {
                turn.setCurrMov(destination);
                board.makeMove(destination, currLocation);
            }
        }
    }
    @Override
    public void endMove() {
        turn = new Turn(players.nextPlayer(turn.getTurn()));
        updatePlayers();

    }

    private void updatePlayers() {
        int numOfPlayers = players.size();
        for(int i = 0; i < numOfPlayers; i++) {
            players.get(i).update(turn.getTurn());
        }
    }

    @Override
    public Coordinates getCurrMov() {
        return turn.getCurrMov();
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
