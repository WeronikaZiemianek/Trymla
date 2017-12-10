package checkers.server;

import java.util.ArrayList;

public class PlayersList {
    private ArrayList<Player> players;
    private ArrayList<Checker> colors;

    void addPlayer(Player player, Checker color) {
        players.add(player);
        colors.add(color);
    }

    Player nextPlayer(Player player) {
        int i = players.indexOf(player) + 1;
        if(i == players.size()) {
            return players.get(0);
        } else {
            return players.get(i);
        }
    }

    Checker getColor(Player player) {
        int i = players.indexOf(player);
        return colors.get(i);
    }

    Player getPlayer(Checker color) {
        int i = colors.indexOf(color);
        return players.get(i);
    }

    Player getPlayer(int i) {
        return players.get(i);
    }

    int numOfPlayers() {
        return players.size();
    }
}
