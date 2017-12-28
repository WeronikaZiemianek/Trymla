package checkers.client;
import checkers.core.Checker;
import checkers.core.Coordinates;
import checkers.core.clientServerInterfaces.ClientPlayer;
import checkers.core.clientServerInterfaces.RemotePlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleClient {
    Scanner input;
    RemotePlayer player;
    ClientConnection connection;
    ClientPlayer clientPlayer;
    ArrayList<LoginAndColor> players;
    static Logger logger = LoggerFactory.getLogger(ClientConnection.class);

    ConsoleClient() throws RemoteException {
        connection = new ClientConnection();
        input = new Scanner(System.in);
        players = new ArrayList<>();
        startGame();
        joinGame();
    }

    boolean createNewPlayer(String login) {
        if(login.matches("bot[0-9]+")) {
            return false;
        }
        if(connection.addPlayer(login)) {
            player = connection.getPlayer();
            try {
                clientPlayer = new RegularClientPlayer(this);
                connection.addClientPlayer(clientPlayer);
                player.getClientPlayer();
            } catch(RemoteException e) {
                e.printStackTrace();
            }
            System.out.print(clientPlayer);
            return true;
        } else {
            return false;
        }
    }

    private void startGame() throws RemoteException {
        System.out.println("Welcome to Trymla, please enter your login: ");
        String login = input.nextLine();
        while(!createNewPlayer(login)) {
            System.out.println("Wrong login, please reenter your login: ");
        }
    }

    private void joinGame() throws RemoteException {
        if(!player.joinGame()) {
            System.out.println("How many users would you like in your game?");
            String num = input.nextLine();
            int n = Integer.parseInt(num);
            while(!(n == 2 || n == 3 || n == 4 || n == 6)) {
                System.out.println("Wrong num of players");
                num = input.nextLine();
                n = Integer.parseInt(num);
            }
            if(!player.createGame(n)) {
                System.out.println("Somebody else already created game");
            }
        }
    }

    void newPlayer(String login, Checker color) {
        players.add(new LoginAndColor(login, color));
        System.out.println(login + " " + color);
    }

    void drawBoard(boolean isMyTurn) throws RemoteException {
        System.out.print(player.getBoard().toString());
        if(isMyTurn) {
            System.out.println("Its your turn make a move");
            makeMove();
        }
    }

    private void makeMove() throws RemoteException {
        String location[];
        String destination[];
        int lx;
        int ly;
        int dx;
        int dy;
        int move = 0;
        do {
            System.out.println("Enter location coordinates separated by ' ' ");
            location = input.nextLine().split(" ");
            if(location[0].equals("end") && location[1].equals("move")) {
                move = 2;
                break;
            }
            System.out.println("Enter destination coordinates separated by ' ' ");
            destination = input.nextLine().split(" ");
            if(destination[0].equals("end") && destination[1].equals("move")) {
                move = 2;
                break;
            }
            lx = Integer.parseInt(location[0]);
            ly = Integer.parseInt(location[1]);
            logger.info("location coordinates " + lx + "x" + ly);
            dx = Integer.parseInt(destination[0]);
            dy = Integer.parseInt(destination[1]);
            logger.info("location coordinates " + dx + "x" + dy);
            move = player.makeMove( new Coordinates(lx, ly), new Coordinates(dx, dy));
        } while(move == -1);
        if(move == 2) {
            player.endMove();
            logger.info("ending move");
        } else {
            player.endJump();
        }
    }

    void endOfGame(String login) {
        System.out.println("The winner is :" + login);
    }


}
