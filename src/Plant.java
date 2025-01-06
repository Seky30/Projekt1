import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Plant implements Comparable<Plant> {
    private String name;
    private String notes;
    private LocalDate planted;
    private LocalDate watering;
    private int frequencyOfWatering;

    public Plant(String name, String notes, LocalDate planted, LocalDate watering,
                 int frequencyOfWatering) throws PlantException {
        this.name = name;
        this.notes = notes;
        this.planted = planted;
        setWatering(watering);
        setFrequencyOfWatering(frequencyOfWatering);
    }

    public Plant(String name) throws PlantException {
        this(name, "", LocalDate.now(), LocalDate.now(), 7);
    }

    public Plant(String name, int frequencyOfWatering) throws PlantException {
        this(name, "", LocalDate.now(), LocalDate.now(), frequencyOfWatering);
    }

    public void doWateringNow(LocalDate frequencyOfWatering) {
        setWatering(LocalDate.now());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getPlanted() {
        return planted;
    }

    public void setPlanted(LocalDate planted) {
        this.planted = planted;
    }

    public LocalDate getWatering() {
        return watering;
    }

    public void setWatering(LocalDate watering) throws PlantException {
        if (watering.isBefore(planted)) {
            throw new PlantException("Datum zálivky nemůže být starší než datum zasazení");
        }
        this.watering = watering;
    }

    public int getFrequencyOfWatering() {
        return frequencyOfWatering;
    }

    public void setFrequencyOfWatering(int frequencyOfWatering) throws PlantException {
        if (frequencyOfWatering <= 0) {
            throw new PlantException("Frekvence zálivky nesmí být zaporná ani nesmí být nula! " +
                    "Zadáno bylo: " + frequencyOfWatering + "!");
        }
        this.frequencyOfWatering = frequencyOfWatering;
    }

    public String toFileString(String delimiter) {
        return name + delimiter +
                notes + delimiter +
                planted + delimiter +
                watering + delimiter +
                frequencyOfWatering + delimiter;
    }

    public static Plant parse(String line, int lineNumber, String delimiter) throws PlantException {
        int plantRequired = 5;
        String[] parts = line.split(delimiter);
        if (parts.length != plantRequired) {
            throw new PlantException("Nesprávný počet položek na řádku číslo: " + lineNumber + " Očekáváme "
                    + plantRequired + " položek:\n" + line);
        }
        String name = parts[0].trim();
        if (name.isEmpty()) {
            throw new PlantException("Název květiny musí být vyplněn!" + " Na řádku číslo " + lineNumber);
        }
        return parsePartialAttributes(lineNumber, parts, name);
    }

    private static Plant parsePartialAttributes(int lineNumber, String[] parts, String name)
            throws PlantException {
        String part = "";
        try {
            part = parts[1].trim();
            String notes = new String(part);
            part = parts[2].trim();
            LocalDate planted = LocalDate.parse(part);
            part = parts[3].trim();
            LocalDate watering = LocalDate.parse(part);
            part = parts[4].trim();
            int frequencyOfWatering = Integer.parseInt(part);
            return new Plant(name, notes, planted, watering, frequencyOfWatering);
        } catch (DateTimeParseException | IllegalArgumentException e) {
            throw new PlantException("Chybný formát udaje: " + part + " Na řádku číslo " + lineNumber);
        }
    }

    public String getWateringInfo() {
        LocalDate nextWatering = watering.plusDays(frequencyOfWatering);
        return String.format("Květina: %s, Poslední zálivka: %s, Doporučená další zálivka: %s",
                name, watering, nextWatering);
    }

    @Override
    public int compareTo(Plant plant) {
        return this.name.compareTo(plant.name);
    }
}