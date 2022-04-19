package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class LicenseFileReader {


    // Variables


    // Constructors


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public String readLicenseFileContent(String licenseFileLocation) throws FileNotFoundException {
        InputStream inputStream = getClass().getResourceAsStream(licenseFileLocation);
        if (inputStream != null) {
            return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining(System.lineSeparator()));
        } else {
            throw new FileNotFoundException();
        }
    }


}
