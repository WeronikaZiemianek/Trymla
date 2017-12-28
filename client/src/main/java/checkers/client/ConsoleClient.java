package checkers.client;
import checkers.core.clientServerInterfaces.ClientPlayer;
import checkers.core.clientServerInterfaces.RemotePlayer;

import java.rmi.RemoteException;
import java.util.Scanner;

public class ConsoleClient {
    Scanner input;
    RemotePlayer player;
    ClientConnection connection;
    ClientPlayer clientPlayer;

    ConsoleClient() throws RemoteException {
        connection = new ClientConnection();
        input = new Scanner(System.in);
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
            player.createGame(n);
        }
    }


}
