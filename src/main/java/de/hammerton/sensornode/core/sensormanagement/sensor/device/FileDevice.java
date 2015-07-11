package de.hammerton.sensornode.core.sensormanagement.sensor.device;

import de.hammerton.sensornode.core.sensormanagement.sensor.device.adapter.IStringAdapter;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Marc Hammerton
 */
public class FileDevice implements IBasicDevice {
    private File file;
    private IStringAdapter adapter;
    private String lineSeparator;

    public FileDevice(String path, IStringAdapter adapter) throws IOException {
        this.file = new File(path);
        if (!file.isFile() || !file.canRead()) {
            throw new IOException("Cannot access file: " + path);
        }

        this.adapter = adapter;
        this.lineSeparator = System.getProperty("line.separator");
    }

    @Override
    public byte[] readData() {
        String rawData = "";

        try {
            rawData = this.readFileContent();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.adapter.extractData(rawData).getBytes();
    }

    @Override
    public void release() {
        // nothing to do here because the scanner is closed directly
        // after reading the file content
    }

    /**
     * Read the complete content from a file
     *
     * @return The raw content of the file
     * @throws IOException
     */
    private String readFileContent() throws IOException {
        StringBuilder fileContents = new StringBuilder((int)this.file.length());

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine()).append(this.lineSeparator);
            }

            return fileContents.toString();
        }
    }
}
