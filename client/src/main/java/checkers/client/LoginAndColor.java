package checkers.client;

import checkers.core.Checker;

public class LoginAndColor {
    String login;
    Checker color;

    public LoginAndColor(String login, Checker color) {
        this.color = color;
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Checker getColor() {
        return color;
    }
}
