package ln.facade;

import dl.UserDAO;
import ln.models.User;
import ln.singleton.SessionManager;

public class SubUserFacade implements ISubUser {
    private final UserDAO userDAO;

    public SubUserFacade() {
        this.userDAO = UserDAO.getInstance();
    }

    @Override
    public void endSession() {
        SessionManager.getInstance().endSession();
    }

    @Override
    public String authenticate(String email, String password) {
        User findUser = userDAO.findByEmail(email);

        if (findUser == null) {
            System.out.println("Email ou palavra-passe incorretos.");
            return null;
        }

        boolean passwordMatch = password.equals(findUser.getPassword());
        if (!passwordMatch) {
            System.out.println("Email ou palavra-passe incorretos.");
            return null;
        }

        SessionManager.getInstance().setLoggedUserEmail(findUser.getEmail());

        if (findUser.getUserType() == User.UserType.DIRECTOR) return "diretor";
        return "aluno";
    }

    @Override
    public boolean verifyEmail(String email) {
        return false;
    }

    @Override
    public boolean sendEmailNewPassword(String email) {
        return false;
    }

    @Override
    public boolean verifySendCode(String code) {
        return false;
    }

    @Override
    public boolean saveNewPassword(String newPassword) {
        return false;
    }

    @Override
    public boolean verifyRegister(String email) {
        return false;
    }
}
