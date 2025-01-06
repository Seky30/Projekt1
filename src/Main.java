import java.time.LocalDate;
import java.util.Comparator;

public class Main {
    private static final String FILENAME = "resources/kvetiny.txt";
    private static final String FILE_NAME_BACKUP = "resources/kvetiny-backup.txt";
    private static final String DELIMITER = "\t";

    public static void main(String[] args) {
        try {
            demoReadFile();
        } catch (PlantException ex) {
            System.err.println(ex.getMessage());
        }

    }

    private static void demoReadFile() throws PlantException {
        PlantManager plantManager = new PlantManager();
        plantManager.loadFromFile(FILENAME, DELIMITER);
        System.out.println("\nNačtení ze souboru: " + FILENAME);
        for (Plant plant:plantManager.getPlants()){
            System.out.println(plant.getWateringInfo());
        }

        plantManager.addPlant(new Plant("Narcis", "žlutý",
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 2, 14), 10));

        for (int i = 1; i <= 10; i++) {
            plantManager.addPlant(new Plant("Tulipán na prodej" + " " + i, "",
                    LocalDate.now(), LocalDate.now(), 14));
        }

        System.out.println("\nUložení do souboru" + FILE_NAME_BACKUP);
        System.out.println("\nSeřazení podle jména květiny: ");
        plantManager.sortName();
        for (Plant plant: plantManager.getPlants()){
            System.out.println(plant.getWateringInfo());
        }
        System.out.println("\nSeřazení podle poslední zálivky květiny: ");
        plantManager.sortName();
        for (Plant plant: plantManager.getPlants()){
            System.out.println(plant.getWateringInfo());
        }
        System.out.println("\nKvětiny,které je potřeba zalít: ");
        for (Plant plant: plantManager.getPlantsToWater()){
            System.out.println(plant.getWateringInfo());
        }
        plantManager.sortName();
        plantManager.sortWatering();
        plantManager.removePlants(2);
        plantManager.saveToFile(FILE_NAME_BACKUP, DELIMITER);
    }
}