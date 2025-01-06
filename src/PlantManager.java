import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class PlantManager {
    private List<Plant> plantList = new ArrayList<>();

    public void addPlant(Plant newPlantList) {
        plantList.add(newPlantList);
    }

    public Plant getIndexPlant(int index) {
        return plantList.get(index);
    }

    public void removePlants(int index) {
        plantList.remove(index);
    }

    public List<Plant> getPlants() {
        return new ArrayList<>(plantList);
    }

    public List<Plant> getPlantsToWater() {
        List<Plant> plantsToWater = new ArrayList<>();
        for (Plant plant : plantList) {
            LocalDate nextWatering = plant.getWatering().plusDays(plant.getFrequencyOfWatering());
            if (!LocalDate.now().isBefore(nextWatering)) {
                plantsToWater.add(plant);
            }
        }
        return plantsToWater;
    }

    public void sortName() {
        plantList.sort(null);
    }
    public void sortWatering(){
        plantList.sort(Comparator.comparing(Plant::getWatering));
    }
    public void loadFromFile(String filename, String delimiter) throws PlantException {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)))) {
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineNumber++;
                plantList.add(Plant.parse(line, lineNumber, delimiter));
            }
        } catch (FileNotFoundException e) {
            throw new PlantException("Soubor " + filename + " nebyl nalezen!\n" + e.getLocalizedMessage());
        }
    }

    public void saveToFile(String filename, String delimiter) throws PlantException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            for (Plant plants : plantList) {
                writer.println(plants.toFileString(delimiter));
            }
        } catch (IOException e) {
            throw new PlantException("Soubor " + filename + " nebyl nalezen!\n" + e.getLocalizedMessage());
        }
    }
}