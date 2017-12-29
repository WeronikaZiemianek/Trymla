package checkers.server.player;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.Move;
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
    private int tempx;
    private int tempy;
    private int x;
    private int y;



    public DefaultBot(String login) {
        this.login = login;
        logger = LoggerFactory.getLogger(DefaultPlayer.class);
    }

    @Override
    public Checker getColor() {
        return color;
    }

    @Override
    public void update (Boolean isMyTurn, Move lastMove) {
        this.isMyTurn = isMyTurn;
        if(isMyTurn) {
            makeMove();
            game.endMove(new Move(new Coordinates(x,y),new Coordinates(tempx,tempy),color));
        }

    }

    @Override
    public void endGame(String login) {
        logger.info("game finished winner is: " + login);
    }

    @Override
    public void addNewPlayer(String login, Checker color) {}

    public int makeMove() {
        boolean jumped = false;
        for(int x = 0; x<25; x++)
        {
            for(int y = 0; y<17; y++)
            {
                if(board.getFieldOccupiedBy(new Coordinates(x,y)) == color)
                {
                    tempx = x;
                    tempy = y;
                    this.x = x;
                    this.y = y;
                    switch (color){
                        case RED:
                            while(game.GetRulesManager().checkMove(game, new Coordinates(tempx,tempy),new Coordinates(tempx+2,tempy-2),color) != -1 ||
                            game.GetRulesManager().checkMove(game, new Coordinates(tempx,tempy),new Coordinates(tempx-2,tempy-2),color) != -1)
                        {
                            if (game.GetRulesManager().checkMove(game, new Coordinates(tempx, tempy), new Coordinates(tempx + 2, tempy - 2), color) != -1) {
                                tempx+=2; tempy-=2;
                                jumped = true;
                            }
                            if (game.GetRulesManager().checkMove(game, new Coordinates(tempx, tempy), new Coordinates(tempx - 2, tempy - 2), color) != -1) {
                                tempx-=2; tempy-=2;
                                jumped = true;
                            }
                        }
                        if(jumped) return game.makeMove(new Coordinates(x, y), new Coordinates(tempx, tempy), this);
                            break;
                        case BLUE:
                            while(game.GetRulesManager().checkMove(game, new Coordinates(tempx,tempy),new Coordinates(tempx-4,tempy),color) != -1 ||
                                    game.GetRulesManager().checkMove(game, new Coordinates(tempx,tempy),new Coordinates(tempx-2,tempy+2),color) != -1)
                            {
                                if (game.GetRulesManager().checkMove(game, new Coordinates(tempx, tempy), new Coordinates(tempx-4, tempy), color) != -1) {
                                    tempx-=4;
                                    jumped = true;
                                }
                                if (game.GetRulesManager().checkMove(game, new Coordinates(tempx, tempy), new Coordinates(tempx - 2, tempy + 2), color) != -1) {
                                    tempx-=2; tempy+=2;
                                    jumped = true;
                                }
                            }
                            if(jumped) return game.makeMove(new Coordinates(x, y), new Coordinates(tempx, tempy), this);
                        break;

                        case GREEN:
                            while(game.GetRulesManager().checkMove(game, new Coordinates(tempx,tempy),new Coordinates(tempx+2,tempy+2),color) != -1 ||
                                    game.GetRulesManager().checkMove(game, new Coordinates(tempx,tempy),new Coordinates(tempx-2,tempy+2),color) != -1)
                            {
                                if (game.GetRulesManager().checkMove(game, new Coordinates(tempx, tempy), new Coordinates(tempx + 2, tempy + 2), color) != -1) {
                                    tempx+=2; tempy+=2;
                                    jumped = true;
                                }
                                if (game.GetRulesManager().checkMove(game, new Coordinates(tempx, tempy), new Coordinates(tempx - 2, tempy + 2), color) != -1) {
                                    tempx-=2; tempy+=2;
                                    jumped = true;
                                }
                            }
                            if(jumped) return game.makeMove(new Coordinates(x, y), new Coordinates(tempx, tempy), this);

                            break;
                        case WHITE:
                            while(game.GetRulesManager().checkMove(game, new Coordinates(tempx,tempy),new Coordinates(tempx+4,tempy),color) != -1 ||
                                    game.GetRulesManager().checkMove(game, new Coordinates(tempx,tempy),new Coordinates(tempx+2,tempy-2),color) != -1)
                            {
                                if (game.GetRulesManager().checkMove(game, new Coordinates(tempx, tempy), new Coordinates(tempx+4, tempy), color) != -1) {
                                    tempx+=4;
                                    jumped = true;
                                }
                                if (game.GetRulesManager().checkMove(game, new Coordinates(tempx, tempy), new Coordinates(tempx + 2, tempy - 2), color) != -1) {
                                    tempx+=2; tempy-=2;
                                    jumped = true;
                                }
                            }
                            if(jumped) return game.makeMove(new Coordinates(x, y), new Coordinates(tempx, tempy), this);

                            break;
                        case YELLOW:
                            while(game.GetRulesManager().checkMove(game, new Coordinates(tempx,tempy),new Coordinates(tempx+4,tempy),color) != -1  ||
                                    game.GetRulesManager().checkMove(game, new Coordinates(tempx,tempy),new Coordinates(tempx+2,tempy+2),color) != -1)
                            {
                                if (game.GetRulesManager().checkMove(game, new Coordinates(tempx, tempy), new Coordinates(tempx+4, tempy), color) != -1) {
                                    tempx+=4;
                                    jumped = true;
                                }
                                if (game.GetRulesManager().checkMove(game, new Coordinates(tempx, tempy), new Coordinates(tempx + 2, tempy + 2), color) != -1) {
                                    tempx+=2; tempy+=2;
                                    jumped = true;
                                }
                            }
                            if(jumped) return game.makeMove(new Coordinates(x, y), new Coordinates(tempx, tempy), this);

                            break;
                        case BLACK:

                            while(game.GetRulesManager().checkMove(game, new Coordinates(tempx,tempy),new Coordinates(tempx-4,tempy),color) != -1  ||
                                    game.GetRulesManager().checkMove(game, new Coordinates(tempx,tempy),new Coordinates(tempx-2,tempy-2),color) != -1)
                            {
                                if (game.GetRulesManager().checkMove(game, new Coordinates(tempx, tempy), new Coordinates(tempx-4, tempy), color) != -1) {
                                    tempx-=4;
                                    jumped = true;
                                }
                                if (game.GetRulesManager().checkMove(game, new Coordinates(tempx, tempy), new Coordinates(tempx - 2, tempy - 2), color) != -1) {
                                    tempx-=2; tempy-=2;
                                    jumped = true;
                                }
                            }
                            if(jumped) return game.makeMove(new Coordinates(x, y), new Coordinates(tempx, tempy), this);

                            break;
                    }
                }
            }
        }
        return makeMoveByOne();
    }

    private int makeMoveByOne() {
        for (int x = 0; x < 25; x++) {
            for (int y = 0; y < 17; y++) {
                this.x = x;
                this.y = y;
                if (board.getFieldOccupiedBy(new Coordinates(x, y)) == color) {
                    switch (color) {
                        case RED:
                            if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x - 1, y - 1), color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x - 1, y - 1), this);
                            }
                            if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x + 1, y - 1), color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x + 1, y - 1), this);
                            }
                            break;
                        case BLUE:
                            if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x - 2, y), color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x - 2, y), this);
                            }
                            if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x - 1, y + 1), color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x - 1, y + 1), this);
                            }
                            break;
                        case GREEN:
                            if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x - 1, y + 1), color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x - 1, y + 1), this);
                            }
                            if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x + 1, y + 1), color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x + 1, y + 1), this);
                            }
                            break;
                        case WHITE:
                            if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x + 2, y), color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x + 2, y), this);
                            }
                            if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x + 1, y - 1), color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x + 1, y - 1), this);
                            }
                            break;
                        case YELLOW:
                            if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x + 2, y), color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x + 2, y), this);
                            }
                            if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x + 1, y + 1), color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x + 1, y + 1), this);
                            }
                            break;
                        case BLACK:
                            if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x - 2, y), color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x - 2, y), this);
                            }
                            if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x - 1, y - 1), color) != -1) {
                                return game.makeMove(new Coordinates(x, y), new Coordinates(x - 1, y - 1), this);
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
