package ln.facade;

public interface ISubUser {
    void endSession();
    String authenticate(String email, String password);
    boolean verifyEmail(String email);
    boolean sendEmailNewPassword(String email);
    boolean verifySendCode(String code);
    boolean saveNewPassword(String newPassword);
    boolean verifyRegister(String email);
}
