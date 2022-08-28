package src.util;

import java.io.Serializable;
import java.util.stream.Stream;

public class User implements Serializable {
    private static final long serialVersionUID = 2L;

    private final String login;
    private final String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() { return login; }

    public String getPassword() { return password; }

    @Override
    public int hashCode() {
        int result = 41;
        result = result * 13 + login.hashCode();
        result = result * 13 + password.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof User) {
            User other = (User) o;
            return Stream.of(login.equals(other.getLogin()),
                password.equals(other.getPassword())).allMatch(x -> x == true);
        }
        return false;
    }
}
