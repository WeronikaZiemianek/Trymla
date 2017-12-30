package checkers.server.player;

import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.Move;
import checkers.server.Player;
import checkers.core.boards.Board;
import checkers.server.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultBot implements Player {
    private boolean isMyTurn;
    private Board board;
    private Checker color;
    private Game game;
    private String login;
    private Logger logger;
    private boolean jumped = false;
    private int x;
    private int y;
    private int tempX;
    private int tempY;

    public DefaultBot(String login) {
        this.login = login;
        logger = LoggerFactory.getLogger(DefaultPlayer.class);
    }

    @Override
    public Checker getColor() {
        return color;
    }

    @Override
    public void update(Boolean isMyTurn, Move lastMove) {
        this.isMyTurn = isMyTurn;
        if (isMyTurn) {
            x = 0; y = 0;
            tempX = 0; tempY = 0;
            makeMove(0, 0);

            while (jumped) {
                jumped = false;
                makeJump(tempX,tempY);
            }
            game.endMove(new Move(new Coordinates(x, y), new Coordinates(tempX, tempY), color));
        }

    }

    @Override
    public void endGame(String login) {
        logger.info("game finished winner is: " + login);
    }

    @Override
    public void addNewPlayer(String login, Checker color) {
    }

    public int makeMove(int startX, int startY) {

        if (startX == 0 && startY == 0) {

            for (int x = 0; x < 25; x++) {
                for (int y = 0; y < 17; y++) {

                    this.x = x;
                    this.y = y;
                    tempX = x;
                    tempY = y;

                    if (board.getFieldOccupiedBy(new Coordinates(x, y)) == color) {
                        int jump = makeJump(x, y);
                        if(jump != -2) {
                            jumped = true;
                            return jump;
                        }
                    }
                }
            }
            for (int x = 0; x < 25; x++) {
                for (int y = 0; y < 17; y++) {

                    this.x = x;
                    this.y = y;
                    tempX = x;
                    tempY = y;

                    if (board.getFieldOccupiedBy(new Coordinates(x, y)) == color) {
                        return makeMoveByOne(x, y);
                    }
                }
            }
        }
        return -1;
    }

    private int makeJump(int x, int y) {
        switch (color){
            case RED:
                if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x+2,y-2),color) != -1) {
                    tempX+=2; tempY-=2;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x+2, y-2), this);
                }
                if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x-2,y-2),color) != -1) {
                    tempX-=2; tempY-=2;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x-2, y-2), this);
                }
                break;
            case BLUE:
                if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x-4,y),color) != -1) {
                    tempX-=4;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x-4,y), this);
                }
                if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x-2,y-2),color) != -1) {
                    tempX-=2; tempY-=2;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x-2,y-2), this);
                }
                break;

            case GREEN:
                if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x+2,y+2),color) != -1) {
                    tempX+=2; tempY+=2;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x+ 2, y+2), this);
                }
                if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x-2,y+2),color) != -1) {
                    tempX-=2; tempY+=2;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x-2, y+2), this);
                }
                break;
            case WHITE:
                if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x+4,y),color) != -1) {
                    tempX+=4;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x+4, y), this);
                }
                if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x+2,y-2),color) != -1) {
                    tempX+=2; tempY-=2;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x+2, y-2), this);
                }
                break;
            case YELLOW:
                if(game.GetRulesManager().checkMove(game, new Coordinates(x, y),new Coordinates(x+4,y),color) != -1) {
                    tempX+=4;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x+4,y), this);
                }
                if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x+2,y+2),color) != -1) {
                    tempX+=2; tempY+=2;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x+2,y+2), this);
                }
                break;
            case BLACK:
                if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x-4,y),color) != -1) {
                    tempX-=4;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x-4, y), this);
                }
                if(game.GetRulesManager().checkMove(game, new Coordinates(x,y),new Coordinates(x-2,y+2),color) != -1) {
                    tempX-=2; tempY+=2;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x-2, y+2), this);
                }
                break;

        }
     return -2;
    }
    private int makeMoveByOne(int x, int y){
        switch (color) {
            case RED:
                if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x - 1, y - 1), color) != -1) {
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x - 1, y - 1), this);
                }
                if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x + 1, y - 1), color) != -1) {
                    tempX+=1; tempY-=1;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x + 1, y - 1), this);
                }
                break;
            case BLUE:
                if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x - 2, y), color) != -1) {
                    tempX-=2;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x - 2, y), this);
                }
                if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x - 1, y - 1), color) != -1) {
                    tempX-=1; tempY-=1;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x - 1, y - 1), this);
                }
                break;

            case GREEN:
                if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x - 1, y + 1), color) != -1) {
                    tempX-=1; tempY+=1;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x - 1, y + 1), this);
                }
                if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x + 1, y + 1), color) != -1) {
                    tempX+=1; tempY+=1;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x + 1, y + 1), this);
                }
                break;
            case WHITE:
                if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x + 2, y), color) != -1) {
                    tempX+=2;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x + 2, y), this);
                }
                if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x + 1, y - 1), color) != -1) {
                    tempX+=1; tempY-=1;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x + 1, y - 1), this);
                }
                break;
            case YELLOW:
                if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x + 2, y), color) != -1) {
                    tempX+=2;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x + 2, y), this);
                }
                if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x + 1, y - 1), color) != -1) {
                    tempX+=1; tempY-=1;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x + 1, y - 1), this);
                }
                break;
            case BLACK:
                if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x - 2, y), color) != -1) {
                    tempX-=2;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x - 2, y), this);
                }
                if (game.GetRulesManager().checkMove(game, new Coordinates(x, y), new Coordinates(x - 1, y + 1), color) != -1) {
                    tempX-=1; tempY+=1;
                    return game.makeMove(new Coordinates(x, y), new Coordinates(x - 1, y + 1), this);
                }
                break;

        }
        return -1;
    }




    @Override
    public void setGameAndColor(Game game, Checker color) {
        this.game = game;
        this.board = game.getBoard();
        this.color = color;
    }

    @Override
    public String getPlayerName() {
        return login;
    }

    @Override
    public void replaceWithBot(String login, int index) {}

}
