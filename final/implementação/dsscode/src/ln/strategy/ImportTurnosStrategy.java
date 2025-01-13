package ln.strategy;

import dl.TurnoDAO;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImportTurnosStrategy implements ImportStrategy {
    private final TurnoDAO turnoDAO = TurnoDAO.getInstance();

    @Override
    public void importData(String filePath) {
        BufferedReader bf = null;
        String line = "";

        try {
            bf = new BufferedReader(new FileReader(filePath));

            bf.readLine(); // skip header
            while ((line = bf.readLine()) != null) {
                String[] lineData = line.split(";");

                String id = lineData[0];
                String sala = lineData[1];
                int lotacao = Integer.parseInt(lineData[2]);
                String tipo = lineData[3];
                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
                Date horas = formatter.parse(lineData[4]);
                int dia = Integer.parseInt(lineData[5]);
                int quantidadeDeInscritos = Integer.parseInt(lineData[6]);
                String codigoUC = lineData[7];

                turnoDAO.save(id, sala, lotacao, tipo, horas, dia, quantidadeDeInscritos, codigoUC);
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
