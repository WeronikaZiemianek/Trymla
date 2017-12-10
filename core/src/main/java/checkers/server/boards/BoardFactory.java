package checkers.server.boards;

public interface BoardFactory {
    Board createNewBoard(int numOfSets) throws WrongNumberOfSetsException;
}
