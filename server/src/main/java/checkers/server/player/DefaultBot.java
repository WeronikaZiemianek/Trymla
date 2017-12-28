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
    private GamesManager gamesManager;
    private String login;
    private ClientPlayer clientPlayer;
    private Logger logger;

    public DefaultBot(GamesManager gamesManager, String login) {
        this.gamesManager = gamesManager;
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
        }

    }

    @Override
    public void endGame(String login) {
        logger.info("game finished winner is: " + login);
    }

    @Override
    public void addNewPlayer(String login, Checker color) {
        try {
            clientPlayer.newPlayerAdded(login, color);
        } catch(RemoteException e) {
            e.printStackTrace();
        }

    }

    public boolean makeMove() {
        for(int x = 0; x<25; x++)
        {
            for(int y = 0; y<17; y++)
            {
                if(board.getFieldOccupiedBy(new Coordinates(x,y)) == color)
                {
                    switch (color){
                        case RED:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x+2,y-2),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x + 2, y - 2), new Coordinates(x, y), this);
                            }
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x-2,y-2),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x - 2, y - 2), new Coordinates(x, y), this);
                            }
                        case BLUE:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x+4,y),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x + 4, y), new Coordinates(x, y), this);
                            }
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x+2,y+2),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x + 2, y + 2), new Coordinates(x, y), this);
                            }
                        case GREEN:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x+2,y+2),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x + 2, y + 2), new Coordinates(x, y), this);
                            }
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x-2,y+2),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x - 2, y + 2), new Coordinates(x, y), this);
                            }
                        case WHITE:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x-4,y),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x - 4, y), new Coordinates(x, y), this);
                            }
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x-2,y-2),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x - 2, y - 2), new Coordinates(x, y), this);
                            }
                        case YELLOW:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x-4,y),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x - 4, y), new Coordinates(x, y), this);
                            }
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x-2,y+2),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x - 2, y + 2), new Coordinates(x, y), this);
                            }
                        case BLACK:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x+4,y),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x + 4, y), new Coordinates(x, y), this);
                            }
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x+2,y-2),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x + 2, y - 2), new Coordinates(x, y), this);
                            }
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
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x-1,y-1),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x-1,y-1),new Coordinates(x,y), this);
                            }
                            if( game.GetRulesManager().checkMove(game, new Coordinates(x+1,y-1),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x+1,y-1),new Coordinates(x,y), this);
                            }
                        case BLUE:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x+2,y),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x+2,y),new Coordinates(x,y), this);
                            }
                            if( game.GetRulesManager().checkMove(game, new Coordinates(x+1,y+1),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x+1,y+1),new Coordinates(x,y), this);
                            }
                        case GREEN:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x-1,y+1),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x-1,y+1),new Coordinates(x,y), this);
                            }
                            if( game.GetRulesManager().checkMove(game, new Coordinates(x+1,y+1),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x+1,y+1),new Coordinates(x,y), this);
                            }
                        case WHITE:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x-2,y),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x-2,y),new Coordinates(x,y), this);
                            }
                            if( game.GetRulesManager().checkMove(game, new Coordinates(x-1,y-1),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x-1,y-1),new Coordinates(x,y), this);
                            }
                        case YELLOW:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x-2,y),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x-2,y),new Coordinates(x,y), this);
                            }
                            if( game.GetRulesManager().checkMove(game, new Coordinates(x-1,y+1),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x-1,y+1),new Coordinates(x,y), this);
                            }
                        case BLACK:
                            if(game.GetRulesManager().checkMove(game, new Coordinates(x+2,y),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x+2,y),new Coordinates(x,y), this);
                            }
                            if( game.GetRulesManager().checkMove(game, new Coordinates(x+1,y-1),new Coordinates(x,y),color) != -1) {
                                return game.makeMove(new Coordinates(x+1,y-1),new Coordinates(x,y), this);
                            }
                    }
                }
            }
        }
        return false;
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

}
