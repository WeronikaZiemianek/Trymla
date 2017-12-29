package checkers.gui;
import checkers.client.ClientConnection;
import checkers.client.GUIClientPlayer;
import checkers.client.LoginAndColor;
import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.clientServerInterfaces.ClientPlayer;
import checkers.core.clientServerInterfaces.RemotePlayer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Controller {
    RemotePlayer player;
    ClientConnection connection;
    ClientPlayer clientPlayer;
    ArrayList<LoginAndColor> players;
    static Logger logger = LoggerFactory.getLogger(Controller.class);
    int n = 0;
    boolean isLocationChosen;
    Coordinates location;
    Coordinates destination;

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
    private StackPane winnerPage;


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

    boolean createNewPlayer(String login) {
        if(login.matches("bot[0-9]+")) {
            return false;
        }
        if(connection.addPlayer(login)) {
            player = connection.getPlayer();
            try {
                clientPlayer = new GUIClientPlayer(this);
                connection.addClientPlayer(clientPlayer);
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

    public static void run(Runnable treatment) {
        if(treatment == null) throw new IllegalArgumentException("The treatment to perform can not be null");

        if(Platform.isFxApplicationThread()) treatment.run();
        else Platform.runLater(treatment);
    }

    public void newPlayer(String login, Checker color) {
        run(() -> {
            players.add(new LoginAndColor(login, color));
            System.out.print(players.size());
            switch(players.size()) {
                case 1: player1OnLobby.setText(login); player1OnGame.setText(login); break;
                case 2: player2OnLobby.setText(login); player2OnGame.setText(login); break;
                case 3: player3OnLobby.setText(login); player3OnGame.setText(login); break;
                case 4: player4OnLobby.setText(login); player4OnGame.setText(login); break;
                case 5: player5OnLobby.setText(login); player5OnGame.setText(login); break;
                case 6: player6OnLobby.setText(login); player6OnGame.setText(login); break;
            }
            System.out.print(login);
        });
    }

    public void addBotOnClick(ActionEvent event) {
        try {
            player.addBot();
        } catch(RemoteException e) {
            e.printStackTrace();
        }

    }

    public void endMoveOnClick(ActionEvent event) {
        try {
            player.endMove();
        } catch(RemoteException e) {
            e.printStackTrace();
        }

    }

    public void exitOnClick(ActionEvent event) {
        try {
            player.disconnect();
        } catch(RemoteException e) {
            e.printStackTrace();
        }

    }

    public void playAgainOnClick(ActionEvent event) {
        try {
            joinGame();
        } catch(RemoteException e) {
            e.printStackTrace();
        }
    }

    public void fieldOnClick(MouseEvent event) {
        int x = GridPane.getRowIndex((Node) event.getTarget());
        int y = GridPane.getColumnIndex((Node) event.getTarget());
        if(isLocationChosen) {
            destination = new Coordinates(x,y);
            try {
                player.makeMove(location, destination);
            } catch(RemoteException e) {
                e.printStackTrace();
            }
        } else {
            location = new Coordinates(x,y);
        }
    }
}
