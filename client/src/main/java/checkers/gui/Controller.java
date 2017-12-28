package checkers.gui;
import checkers.client.ClientConnection;
import checkers.client.GUIClientPlayer;
import checkers.client.LoginAndColor;
import checkers.core.Checker;
import checkers.core.clientServerInterfaces.ClientPlayer;
import checkers.core.clientServerInterfaces.RemotePlayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    //gamePage --------------------------------
    @FXML
    private StackPane gamePage;
    @FXML
    private StackPane winnerPage;



    public Controller() {
    }

    public void startOnClick(ActionEvent event) {
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

//    public void newPlayer(String login, Checker color) {
//        players.add(new LoginAndColor(login, color));
//        switch(players.size()) {
//            case 1: player1OnGame.setText(login); break;
//            case 2: player2OnGame.setText(login); break;
//            case 3: player3OnGame.setText(login); break;
//            case 4: player4OnGame.setText(login); break;
//            case 5: player5OnGame.setText(login); break;
//            case 6: player6OnGame.setText(login); break;
//        }
//    }

    public void addBotOnClick(ActionEvent event) {

    }

    public void endMoveOnClick(ActionEvent event) {

    }

    public void exitOnClick(ActionEvent event) {

    }

    public void playAgainOnClick(ActionEvent event) {

    }

    public void fieldOnClick(ActionEvent event) {

    }
}
