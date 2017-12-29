package checkers.server.player;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.server.Player;
import checkers.core.clientServerInterfaces.ClientPlayer;
import checkers.core.clientServerInterfaces.RemotePlayer;
import checkers.core.boards.Board;
import checkers.server.game.Game;
import checkers.server.game.GamesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DefaultBot implements Player {
    private boolean isMyTurn;
    private Board board;
    private Checker color;
    private Game game;
    private String login;
    private Logger logger;

    public DefaultBot(String login) {
        this.login = login;
        logger = LoggerFactory.getLogger(DefaultPlayer.class);
    }

    @Override
    public Checker getColor() {
        return color;
    }

    @Override
    public void update (Boolean isMyTurn) {
        this.isMyTurn = isMyTurn;
        if(isMyTurn) {
            makeMove();
            game.endMove();
        }

    }

    @Override
    public void endGame(String login) {
        logger.info("game finished winner is: " + login);
    }

    @Override
    public void addNewPlayer(String login, Checker color) {}

    public int makeMove() {
        for(int x = 0; x<25; x++)
        {
            for(int y = 0; y<17; y++)
            {
                if(board.getFieldOccupiedBy(new Coordinates(x,y)) == color)
                {
                    switch (color){
                        case RED:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x+2,y-2),color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x+2, y-2), this);
                            }
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x-2,y-2),color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x-2, y-2), this);
                            }
                            break;
                        case BLUE:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x-4,y),color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x-4, y), this);
                            }
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x-2,y+2),color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x-2, y+2), this);
                            }
                            break;
                        case GREEN:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x+2,y+2),color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x+ 2, y+2), this);
                            }
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x-2,y+2),color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x-2, y+2), this);
                            }
                            break;
                        case WHITE:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x+4,y),color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x+4, y), this);
                            }
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x+2,y-2),color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x+2, y-2), this);
                            }
                            break;
                        case YELLOW:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x, y),new Coordinates(x+4,y),color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x+4,y), this);
                            }
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x+2,y+2),color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x+2,y+2), this);
                            }
                            break;
                        case BLACK:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x-4,y),color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x-4,y), this);
                            }
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x-2,y-2),color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x-2,y-2), this);
                            }
                            break;
                    }
                }
            }
        }
        for(int x = 0; x<25; x++)
        {
            for(int y = 0; y<17; y++)
            {
                if(board.getFieldOccupiedBy(new Coordinates(x,y)) == color)
                {
                    switch (color){
                        case RED:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x-1,y-1),color) != -1) {
                                return game.makeMove(new Coordinates(x,y),new Coordinates(x-1,y-1), this);
                            }
                            if( game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x+1,y-1),color) != -1) {
                                return game.makeMove(new Coordinates(x,y),new Coordinates(x+1,y-1), this);
                            }
                            break;
                        case BLUE:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x-2,y),color) != -1) {
                                return game.makeMove(new Coordinates(x,y),new Coordinates(x-2,y), this);
                            }
                            if( game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x-1,y+1),color) != -1) {
                                return game.makeMove(new Coordinates(x,y),new Coordinates(x-1,y+1), this);
                            }
                            break;
                        case GREEN:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x-1,y+1),color) != -1) {
                                return game.makeMove(new Coordinates(x,y),new Coordinates(x-1,y+1), this);
                            }
                            if( game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x+1,y+1),color) != -1) {
                                return game.makeMove(new Coordinates(x,y),new Coordinates(x+1,y+1), this);
                            }
                            break;
                        case WHITE:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x+2,y),color) != -1) {
                                return game.makeMove(new Coordinates(x,y),new Coordinates(x+2,y), this);
                            }
                            if( game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x+1,y-1),color) != -1) {
                                return game.makeMove(new Coordinates(x,y),new Coordinates(x+1,y-1), this);
                            }
                            break;
                        case YELLOW:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x+2,y),color) != -1) {
                                return game.makeMove(new Coordinates(x,y),new Coordinates(x+2,y), this);
                            }
                            if( game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x+1,y+1),color) != -1) {
                                return game.makeMove(new Coordinates(x,y),new Coordinates(x+1,y+1), this);
                            }
                            break;
                        case BLACK:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x-2,y),color) != -1) {
                                return game.makeMove(new Coordinates(x,y),new Coordinates(x-2,y), this);
                            }
                            if( game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x-1,y-1),color) != -1) {
                                return game.makeMove(new Coordinates(x,y),new Coordinates(x-1,y-1), this);
                            }
                            break;
                    }
                }
            }
        }
        return -1;
    }


    @Override
    public void setGameAndColor(Game game, Checker color, Board board) {
        this.game = game;
        this.board = board;
        this.color = color;
    }

    @Override
    public String getPlayerName() {
        return login;
    }

    @Override
    public void replaceWithBot(String login, int index) {}

}
