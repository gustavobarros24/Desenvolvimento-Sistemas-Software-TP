package ln.strategy;

import dl.AlunoDAO;
import dl.UCDAO;
import dl.UserDAO;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ln.models.User;

public class ImportStudentsStrategy implements ImportStrategy {
    private final UserDAO userDAO = UserDAO.getInstance();
    private final AlunoDAO alunoDAO = AlunoDAO.getInstance();
    private final UCDAO ucDAO = UCDAO.getInstance();

    @Override
    public void importData(String filePath) {
        BufferedReader bf = null;
        String line = "";

        try {
            bf = new BufferedReader(new FileReader(filePath));

            bf.readLine(); // skip header
            while ((line = bf.readLine()) != null) {
                String[] lineData = line.split(";");

                String email = lineData[0];
                String name = lineData[1];
                String estatuto = lineData[2];
                String codigoUC = lineData[3];
                int matricula = Integer.parseInt(lineData[4]);

                // se o user ainda não existe, cria-o
                if (userDAO.findByEmail(email) == null) {
                    alunoDAO.save(email, estatuto, matricula, name);
                    userDAO.save(email, "password", User.UserType.STUDENT);
                }

                // adiciona a uc frequentada
                if (ucDAO.findByCode(codigoUC) != null) alunoDAO.inscreverEmUC(email, codigoUC);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert bf != null;
                bf.close();
            } catch (IOException underscore) {}
        }
    }
}
