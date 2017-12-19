package checkers.core.boards;

public interface BoardFactory {
    Board createNewBoard(int numOfSets) throws WrongNumberOfSetsException;
}
