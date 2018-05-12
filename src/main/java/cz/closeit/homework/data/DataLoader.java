package cz.closeit.homework.data;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Methods for retrieving flight data
 */
public class DataLoader {

    private static Logger logger = Logger.getLogger("DataLoader");

    public static final String DATA_SOURCE = "http://stat-computing.org/dataexpo/2009/1989.csv.bz2";
    public static final String COMPRESSED_DATA_PATH = "./compressed-data.bz2";
    public static final String DATA_PATH = "./data.csv";


    /**
     * Downloads (if necessary) and uncompresses (if necessary) data file
     *
     * @return file with flight data in CSV format or null if retrieving fails
     **/
    public static File getDataFile() {
        logger.info("Retrieving data...");

        File dataFile;
        try {
            File downloadedFile = downloadDataFile();
            dataFile = uncompressDataFile(downloadedFile);
        } catch (CompressorException | IOException e) {
            logger.log(Level.SEVERE, "Retrieving data failed", e);
            return null;
        }

        logger.info("Data file successfully retrieved!");

        return dataFile;
    }

    /**
     * Downloads compressed data file
     *
     * @return data file in BZip2 format
     * @throws IOException when downloading fails
     */
    private static File downloadDataFile() throws IOException {
        logger.info("Downloading data from " + DATA_SOURCE + "...");

        File compressed = new File(COMPRESSED_DATA_PATH);
        if (compressed.exists()) {
            return compressed;
        }

        URL dataUrl = new URL(DATA_SOURCE);
        FileUtils.copyURLToFile(dataUrl, compressed, 1000, 1000);

        return compressed;
    }

    /**
     * Uncompresses data file
     *
     * @param compressed BZip2 file
     * @return uncompressed CSV file
     * @throws CompressorException when uncompressing fails
     */
    private static File uncompressDataFile(File compressed) throws CompressorException {
        logger.info("Uncompressing data...");

        File uncompressed = new File(DATA_PATH);
        if (uncompressed.exists()) {
            return uncompressed;
        }

        try (InputStream in = new BZip2CompressorInputStream(new BufferedInputStream(new FileInputStream(compressed)))) {
            FileUtils.copyInputStreamToFile(in, uncompressed);
        } catch (IOException ioe) {
            // rethrowing, so we know this exception occurred during uncompressing process
            throw new CompressorException("Error uncompressing file", ioe);
        }


        return uncompressed;
    }

}
