package ln.singleton;

public class SessionManager {
    private static SessionManager instance = new SessionManager();
    private String loggedUserEmail;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public String getLoggedUserEmail() {
        return loggedUserEmail;
    }

    public void setLoggedUserEmail(String loggedUserEmail) {
        this.loggedUserEmail = loggedUserEmail;
    }

    public void endSession() {
        this.loggedUserEmail = null;
    }
}
