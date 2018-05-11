package cz.closeit.homework.data;

import cz.closeit.homework.model.Flight;
import org.simpleflatmapper.csv.CellValueReader;
import org.simpleflatmapper.csv.CsvMapper;
import org.simpleflatmapper.csv.CsvMapperFactory;
import org.simpleflatmapper.csv.CsvParser;
import org.simpleflatmapper.util.ListCollector;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Methods for parsing CSV file
 */
public class DataParser {

    private static Logger logger = Logger.getLogger("DataParser");

    /**
     * Parses CSV file into list of flights
     *
     * @param dataFile with flight data
     * @return list of flights
     */
    public static List<Flight> parseDataFile(File dataFile) {
        logger.info("Parsing CSV file...");

        CsvMapper<Flight> mapper = configureMapper();

        List<Flight> flights = null;
        try {
            flights = CsvParser.mapWith(mapper)
                    .forEach(dataFile, new ListCollector<>())
                    .getList();
        } catch (IOException ioe) {
            logger.log(Level.SEVERE, "Parsing CSV failed", ioe);
        }

        logger.info("Parsing successful, parsed " + flights.size() + " flights!");

        return flights;
    }


    /**
     * Configures CSV mapper to only map certain columns, ignore others and handle special 'NA' value
     *
     * @return CSV mapper
     */
    private static CsvMapper<Flight> configureMapper() {
        return CsvMapperFactory
                .newInstance()
                .ignorePropertyNotFound()
                .addAlias("ArrDelay", "delay")
                .addAlias("Dest", "airportCode")
                .addCustomValueReader("ArrDelay", (CellValueReader<Integer>) (chars, offset, length, parsingContext) -> {
                    String val = String.valueOf(chars, offset, length);
                    return (val.equals("NA")) ? 0 : Integer.parseInt(val);
                })
                .newMapper(Flight.class);
    }

}
