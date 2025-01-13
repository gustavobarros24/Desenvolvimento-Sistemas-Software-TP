package ln.facade;

import ln.strategy.ImportStrategy;
import ln.strategy.ImportStudentsStrategy;
import ln.strategy.ImportTurnosStrategy;
import ln.strategy.ImportUCsStrategy;

interface IDataImporter {
    void importData(String filePath, String fileType);
}

public class DataImporterFacade implements IDataImporter {
    private ImportStrategy strategy;

    public void setStrategy(ImportStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void importData(String filePath, String fileType) {
        if (fileType.equals("alunos")) setStrategy(new ImportStudentsStrategy());
        if (fileType.equals("ucs")) setStrategy(new ImportUCsStrategy());
        if (fileType.equals("turnos")) setStrategy(new ImportTurnosStrategy());

        strategy.importData(filePath);
        System.out.println("Dados importados de: " + filePath);
    }
}
