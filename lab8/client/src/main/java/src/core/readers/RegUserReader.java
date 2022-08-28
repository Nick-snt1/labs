package src.core.readers;

public class RegUserReader extends UserReader {

    @Override
    protected String getLogin() {
        String login;
        System.out.println("Registration proccess started!");
        do {
            System.out.print("Please, enter login>>");
            if ((login = UserReader.console.readLine()) != null) login = login.trim();
        } while (!CheckInput.checkLogin(login));
        return login;
    }

    @Override
    protected String getPassword() {
        String password = null;
        do {
            char[] c;
            System.out.print("Please, enter password>>");
            if ((c = UserReader.console.readPassword()) != null) password = new String(c).trim();
        } while (!CheckInput.checkPassword(password));
        return password;
    }
}
