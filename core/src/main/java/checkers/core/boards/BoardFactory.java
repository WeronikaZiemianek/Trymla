package checkers.core.boards;

public interface BoardFactory {
    Board createNewBoard(int numbOfPlayers) throws WrongNumberOfPlayersException;
}
