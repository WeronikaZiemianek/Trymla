package checkers.gui;
import checkers.client.ClientConnection;
import checkers.client.GUIClient;
import checkers.client.LoginAndColor;
import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.Move;
import checkers.core.boards.Board;
import checkers.core.clientServerInterfaces.Client;
import checkers.core.clientServerInterfaces.RemotePlayer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Controller {
    private RemotePlayer player;
    private ClientConnection connection;
    private ArrayList<LoginAndColor> players;
    private static Logger logger = LoggerFactory.getLogger(Controller.class);
    private int n = 0;
    private boolean isLocationChosen;
    private boolean isMyTurn;
    private boolean didGameStart;
    private Coordinates location;
    private Coordinates destination;

    //mainPage -------------------------------
    @FXML
    private StackPane mainPage;
    //loginPage ------------------------------
    @FXML
    private StackPane loginPage;
    @FXML
    private TextField nameTextArea;
    @FXML
    private Label loginOutputLabel;
    //createPage ------------------------------
    @FXML
    private StackPane createPage;
    @FXML
    private Label numberOfPlayerLabel;
    @FXML
    private Label createInfoLabel;
    //lobbyPage -------------------------------
    @FXML
    private StackPane lobbyPage;
    @FXML
    private Label player1OnLobby;
    @FXML
    private Label player2OnLobby;
    @FXML
    private Label player3OnLobby;
    @FXML
    private Label player4OnLobby;
    @FXML
    private Label player5OnLobby;
    @FXML
    private Label player6OnLobby;
    @FXML
    private GridPane playerColorsOnLobby;
    //gamePage --------------------------------
    @FXML
    private StackPane gamePage;
    @FXML
    private Label player1OnGame;
    @FXML
    private Label player2OnGame;
    @FXML
    private Label player3OnGame;
    @FXML
    private Label player4OnGame;
    @FXML
    private Label player5OnGame;
    @FXML
    private Label player6OnGame;
    @FXML
    private GridPane board;
    @FXML
    private GridPane playerColorsOnGame;
    //winnerPage ------------------------------
    @FXML
    private StackPane winnerPage;
    @FXML
    private Label winnerNameLabel;
    @FXML
    private Label winnerLabel;


    @FXML
    void initialize() {
        this.mainPage.setStyle("-fx-background-image: url('mainWP.jpg');");
        this.loginPage.setStyle("-fx-background-image: url('loginWP.jpg');");
        this.createPage.setStyle("-fx-background-image: url('createWP.jpg');");
        this.lobbyPage.setStyle("-fx-background-image: url('lobbyWP.jpg');");
        this.gamePage.setStyle("-fx-background-image: url('gameWP.jpg');");
        this.winnerPage.setStyle("-fx-background-image: url('winnerWP.jpg');");
    }

    public Controller() {
    }

    public void startOnClick(ActionEvent event) {
        didGameStart = false;
        isLocationChosen = false;
        try {
            connection = new ClientConnection();
        } catch(RemoteException e) {
            e.printStackTrace();
        }
        players = new ArrayList<>();
        this.mainPage.setVisible(false);
        this.mainPage.setDisable(true);
        this.loginPage.setVisible(true);
        this.loginPage.setDisable(false);
    }

    public void loginOnClick(ActionEvent event) {
        String login = nameTextArea.getText();
        logger.debug(login);
        if(!createNewPlayer(login)) {
            loginOutputLabel.setText("Wrong login, please reenter your login");
        } else {
            loginOutputLabel.setText("logging in...");
            try {
                joinGame();
            } catch(RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean createNewPlayer(String login) {
        if(login.equals("") || login.matches("bot[0-9]+") || login.matches("CLIENT.*")) {
            return false;
        }
        if(connection.addPlayer(login)) {
            player = connection.getPlayer();
            try {
                Client client = new GUIClient(this);
                connection.addClientPlayer(client);
                player.getClientPlayer();
            } catch(RemoteException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    private void joinGame() throws RemoteException {
        if(!player.joinGame()) {
            this.loginPage.setVisible(false);
            this.loginPage.setDisable(true);
            this.createPage.setVisible(true);
            this.createPage.setDisable(false);
        } else {
            this.loginPage.setVisible(false);
            this.loginPage.setDisable(true);
            this.lobbyPage.setVisible(true);
            this.lobbyPage.setDisable(false);
        }
    }

    public void twoPlayerOnClick(ActionEvent event) {
        n = 2;
        numberOfPlayerLabel.setText("2");
    }

    public void threePlayerOnClick(ActionEvent event) {
        n = 3;
        numberOfPlayerLabel.setText("3");
    }

    public void fourPlayerOnClick(ActionEvent event) {
        n = 4;
        numberOfPlayerLabel.setText("4");
    }

    public void sixPlayerOnClick(ActionEvent event) {
        n = 6;
        numberOfPlayerLabel.setText("6");
    }

    public void createGameOnClick(ActionEvent event) {
        if(n != 0) {
            try {
                if(!player.createGame(n)) {
                    createInfoLabel.setText("You've been added to already existing game");
                }
                this.createPage.setVisible(false);
                this.createPage.setDisable(true);
                this.lobbyPage.setVisible(true);
                this.lobbyPage.setDisable(false);
            } catch(RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private static void run(Runnable treatment) {
        if(treatment == null) throw new IllegalArgumentException("The treatment to perform can not be null");

        if(Platform.isFxApplicationThread()) treatment.run();
        else Platform.runLater(treatment);
    }

    private Color chooseColor(Checker color) {
        Color fxcolor;
        switch(color) {
            case RED: fxcolor = Color.RED; break;
            case BLUE: fxcolor = Color.BLUE; break;
            case GREEN: fxcolor = Color.GREEN; break;
            case YELLOW: fxcolor = Color.YELLOW; break;
            case WHITE: fxcolor = Color.WHITE; break;
            case BLACK: fxcolor = Color.BLACK; break;
            default: fxcolor = Color.TRANSPARENT; break;
        }
        return fxcolor;
    }

    private void setPlayer(String login, Checker color, int index) {
        Color fxcolor = chooseColor(color);
        for(Node node: playerColorsOnLobby.getChildren()) {
            if(GridPane.getRowIndex(node) == index-1) {
                ((Circle)node).setFill(fxcolor);
                ((Circle)node).setStroke(Color.BLACK);
            }
        }
        for(Node node: playerColorsOnGame.getChildren()) {
            if(GridPane.getRowIndex(node) == index-1) {
                ((Circle)node).setFill(fxcolor);
                ((Circle)node).setStroke(Color.BLACK);
            }
        }
        try {
            if(player.getLogin().equals(login)) {
                login = login.concat(" (you)");
            }
        } catch(RemoteException e) {
            e.printStackTrace();
        }
        switch(index) {
            case 1: player1OnLobby.setText(login); player1OnGame.setText(login); break;
            case 2: player2OnLobby.setText(login); player2OnGame.setText(login); break;
            case 3: player3OnLobby.setText(login); player3OnGame.setText(login); break;
            case 4: player4OnLobby.setText(login); player4OnGame.setText(login); break;
            case 5: player5OnLobby.setText(login); player5OnGame.setText(login); break;
            case 6: player6OnLobby.setText(login); player6OnGame.setText(login); break;
        }
    }

    public void newPlayer(String login, Checker color) {
        run(() -> {
            players.add(new LoginAndColor(login, color));
            setPlayer(login, color, players.size());
        });
    }

    public void renamePlayer(String login, int index) {
        run(() -> {
            players.get(index).setLogin(login);
            setPlayer(login, players.get(index).getColor(), index+1);
        });
    }

    public void addBotOnClick(ActionEvent event) {
        try {
            player.addBot();
        } catch(RemoteException e) {
            e.printStackTrace();
        }

    }

    private void initBoard() {
        try {
            Board playerBoard = player.getBoard();
            int columns = playerBoard.getNumOfCol();
            int rows = playerBoard.getNumOfRows();
            for(int r=0; r < rows; r++) {
                for(int c=0; c < columns; c++) {
                    ObservableList<Node> boardChildren = board.getChildren();
                    for(Node node : boardChildren) {
                        if(GridPane.getRowIndex(node) == r && GridPane.getColumnIndex(node) == c) {
                            ((Circle)node).setFill(chooseColor(playerBoard.getFieldOccupiedBy(new Coordinates(c,r))));
                        }
                    }
                }
            }
        } catch(RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized void update(boolean isMyTurn, Move lastMove) {
        run(() -> {
            logger.info("updating players");
            if(!didGameStart) {
                this.lobbyPage.setVisible(false);
                this.lobbyPage.setDisable(true);
                this.gamePage.setVisible(true);
                initBoard();
                didGameStart = true;
            } else {
                if(!(lastMove == null)) {
                    logger.info("lastMoveIsNotNull");
                    for(Node node : board.getChildren()) {
                        if(GridPane.getRowIndex(node) == lastMove.getLocation().Y() &&
                                GridPane.getColumnIndex(node) == lastMove.getLocation().X()) {
                            ((Circle)node).setFill(Color.TRANSPARENT);
                        }
                        if(GridPane.getRowIndex(node) == lastMove.getDestination().Y() &&
                                GridPane.getColumnIndex(node) == lastMove.getDestination().X()) {
                            ((Circle)node).setFill(chooseColor(lastMove.getColor()));
                        }
                    }
                }
            }
            this.isMyTurn = isMyTurn;
            if(isMyTurn) {
                this.gamePage.setDisable(false);
            }
        });
    }

    public void fieldOnClick(MouseEvent event) {
        logger.debug("You have clicked");
        int y = GridPane.getRowIndex((Node) event.getTarget());
        int x = GridPane.getColumnIndex((Node) event.getTarget());
        if(isLocationChosen) {
            destination = new Coordinates(x,y);
            logger.debug("destination is chosen");
            isLocationChosen = false;
            try {
                int move = player.makeMove(location, destination);
                if(move == 2) {
                    player.endMove(new Move(location, destination, player.getColor()));
                    this.gamePage.setDisable(true);
                }
                if(move == 4) {
                    player.endJump(new Move(location, destination, player.getColor()));
                }
            } catch(RemoteException e) {
                e.printStackTrace();
            }
        } else {
            location = new Coordinates(x,y);
            isLocationChosen = true;
            logger.debug("location is chosen");
        }
    }

    public void endMoveOnClick(ActionEvent event) {
        try {
            player.endMove(null);
        } catch(RemoteException e) {
            e.printStackTrace();
        }
        this.gamePage.setDisable(true);
    }

    public void exitOnClick(ActionEvent event) {
        logger.info("exit");
        Platform.exit();
    }

    private void initPlayersList() {
        player1OnLobby.setText(""); player1OnGame.setText("");
        player2OnLobby.setText(""); player2OnGame.setText("");
        player3OnLobby.setText(""); player3OnGame.setText("");
        player4OnLobby.setText(""); player4OnGame.setText("");
        player5OnLobby.setText(""); player5OnGame.setText("");
        player6OnLobby.setText(""); player6OnGame.setText("");
        for(Node node: playerColorsOnGame.getChildren()) {
            ((Circle)node).setFill(Color.TRANSPARENT);
            ((Circle)node).setStroke(Color.TRANSPARENT);
        }
        for(Node node: playerColorsOnLobby.getChildren()) {
            ((Circle)node).setFill(Color.TRANSPARENT);
            ((Circle)node).setStroke(Color.TRANSPARENT);
        }
        players = new ArrayList<>();
        didGameStart = false;
    }

    public void playAgainOnClick(ActionEvent event) {
        try {
            this.winnerPage.setVisible(false);
            this.winnerPage.setDisable(true);
            initPlayersList();
            joinGame();
        } catch(RemoteException e) {
            e.printStackTrace();
        }
    }

    public void endOfGame(String winnerLogin) {
        run(() -> {
            this.gamePage.setVisible(false);
            this.gamePage.setDisable(true);
            this.winnerPage.setVisible(true);
            this.winnerPage.setDisable(false);
            if(winnerLogin == null) {
                winnerLabel.setText("It's a tie");
            } else {
                winnerNameLabel.setText(winnerLogin);
            }
        });
    }

    public void stop() {
        if(player != null) {
            try {
                connection.deleteClientPlayerFromRegistry();
                player.disconnect();
            } catch(RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
