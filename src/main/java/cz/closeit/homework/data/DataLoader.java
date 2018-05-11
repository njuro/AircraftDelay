package cz.closeit.homework.data;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;

/**
 * Methods for retrieving flight data
 */
public class DataLoader {

    public static final String DATA_SOURCE = "http://stat-computing.org/dataexpo/2009/1999.csv.bz2";
    public static final String COMPRESSED_DATA_PATH = "compressed-data.bz2";
    public static final String DATA_PATH = "data.csv";


    /**
     * Download (if necessary) and uncompress (if necessary) data file
     *
     * @return file with flight data in CSV format or null if retrieving fails
     **/
    public static File getDataFile() {
        File dataFile;

        System.out.println("Retrieving data file...");
        try {
            File downloadedFile = downloadDataFile();
            dataFile = uncompressDataFile(downloadedFile);
        } catch (CompressorException | IOException e) {
            System.err.println("Error retrieving data file");
            e.printStackTrace();
            return null;
        }

        System.out.println("Data file successfully retrieved!");

        return dataFile;
    }

    /**
     * Downloads compressed data file
     *
     * @return data file in BZip2 format
     * @throws IOException when downloading fails
     */
    private static File downloadDataFile() throws IOException {
        System.out.println("Downloading data from " + DATA_SOURCE + "...");

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
     * @param dataFile BZip2 compressed file
     * @return uncompressed CSV file
     * @throws CompressorException when uncompressing fails
     */
    private static File uncompressDataFile(File dataFile) throws CompressorException {
        System.out.println("Decompressing data...");

        File uncompressed = new File(DATA_PATH);
        if (uncompressed.exists()) {
            return uncompressed;
        }

        try (InputStream in = new BZip2CompressorInputStream(new BufferedInputStream(new FileInputStream(dataFile)))) {
            FileUtils.copyInputStreamToFile(in, uncompressed);
        } catch (IOException ioe) {
            // rethrowing, so we know this exception occurred during uncompressing process
            throw new CompressorException("Error uncompressing file", ioe);
        }


        return uncompressed;
    }

}
