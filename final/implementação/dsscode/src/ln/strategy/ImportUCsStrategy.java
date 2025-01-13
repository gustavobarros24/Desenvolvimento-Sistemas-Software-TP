package ln.strategy;

import dl.UCDAO;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ImportUCsStrategy implements ImportStrategy {
    private final UCDAO UcDAO = UCDAO.getInstance();

    @Override
    public void importData(String filePath) {
        BufferedReader bf = null;
        String line = "";

        try {
            bf = new BufferedReader(new FileReader(filePath));

            bf.readLine(); // skip header
            while ((line = bf.readLine()) != null) {
                String[] lineData = line.split(",");

                String code = lineData[0];
                int year = Integer.parseInt(lineData[1]);
                int semester = Integer.parseInt(lineData[2]);
                String name = lineData[3];

                UcDAO.save(code, year, semester, name, "criterio"); // FIXME: ler critério
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
