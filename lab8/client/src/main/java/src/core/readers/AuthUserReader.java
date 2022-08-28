package src.core.readers;

public class AuthUserReader extends UserReader {

    @Override
    protected String getLogin() {
        String login;
        System.out.println("Authorization process started!");
        do {
            System.out.print("Please, enter login>> ");
            if ((login = super.console.readLine()) != null) login = login.trim();
        } while (login == null);
        return login;
    }

    @Override
    protected String getPassword() {
        char[] password;
        do {
            System.out.print("Please, enter password>>");
            password = super.console.readPassword();
        } while (password == null);
        return new String(password).trim();
    }
}
