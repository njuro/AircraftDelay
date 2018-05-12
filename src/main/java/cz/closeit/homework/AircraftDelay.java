package cz.closeit.homework;

import cz.closeit.homework.data.DataLoader;
import cz.closeit.homework.data.DataParser;
import cz.closeit.homework.model.Flight;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.OptionalDouble;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AircraftDelay {

    private static Logger logger = Logger.getLogger("AircraftDelay");

    public static void main(String[] args) {
        double averageDelay = calculateAverageDelay();
        cleanUp();

        System.out.printf("\nThe average delay of arriving aircraft to LAX in 1989 was %.2f minutes\n", averageDelay);
    }

    /**
     * Calculates average arrival delay on LAX airport in 1989. Negative (early) arrivals are also taken
     * into account.
     *
     * @return average delay
     */
    private static double calculateAverageDelay() {
        File data = DataLoader.getDataFile();
        List<Flight> flights = DataParser.parseDataFile(data);

        OptionalDouble delay = flights
                .stream()
                .filter(flight -> !flight.isCancelled() && flight.getAirportCode().equals("LAX"))
                .mapToInt(Flight::getDelay)
                .average();

        if (delay.isPresent()) {
            return delay.getAsDouble();
        }

        throw new IllegalStateException("Calculation went wrong");
    }

    /**
     * Cleans up by deleting data files
     */
    private static void cleanUp() {
        logger.info("Cleaning up...");

        try {
            Files.deleteIfExists(Paths.get(DataLoader.COMPRESSED_DATA_PATH));
            Files.deleteIfExists(Paths.get(DataLoader.DATA_PATH));
        } catch (IOException ioe) {
            logger.log(Level.SEVERE, "Cleaning up failed", ioe);
        }
    }
}
