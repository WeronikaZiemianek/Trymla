package checkers.server;

import checkers.server.boards.Board;

import java.util.List;

public class RegularGame implements Game {
    private List<Player> players;
    private int numOfPlayers;
    private int turnPlayer;
    private RegularRulesManager rulesManager;
    private Board board;
    private GamesMenager gamesManager;
    private GameState state;
    private Turn turn;

    RegularGame(int numOfPlayers, Board board) {
        this.board = board;
        this.numOfPlayers = numOfPlayers;
        state = GameState.OPEN;
    }

    public void makeMove(Coordinates destination, Coordinates currLocation, Player player){
        if(player == turn.getPlayer()) {
            int validationOfMove = rulesManager.checkMove(destination, currLocation, player.getColor());
            if(validationOfMove == 1 || validationOfMove == 2 ) {
                turn.setCurrMov(destination);
                board.makeMove(destination, currLocation);
            }
        }
    }
    @Override
    public void endMove() {
        turnPlayer++;
        if(numOfPlayers < turnPlayer)
            turnPlayer = 0;
        turn = new Turn(players.get(turnPlayer));
        updatePlayers();
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
