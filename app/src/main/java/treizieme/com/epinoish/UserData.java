package treizieme.com.epinoish;

/**
 * Created by Quach on 18/01/16.
 */
public class UserData {

    private String login;
    private String password;
    private String token;
    private static UserData instance = null;

    private UserData() {

    }

    public static UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }
        return instance;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
