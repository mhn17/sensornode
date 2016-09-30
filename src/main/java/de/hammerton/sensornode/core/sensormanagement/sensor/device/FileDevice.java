package de.hammerton.sensornode.core.sensormanagement.sensor.device;

import de.hammerton.sensornode.core.sensormanagement.sensor.NoDataAvailableException;
import de.hammerton.sensornode.core.sensormanagement.sensor.device.adapter.IStringAdapter;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

/**
 * @author Marc Hammerton
 */
public class FileDevice implements IBasicDevice {
    private File file;
    private IStringAdapter adapter;
    private String lineSeparator;
    private long lastModified = 0;

    public FileDevice(String path, IStringAdapter adapter) throws IOException {
        this.file = new File(path);
        if (!file.isFile() || !file.canRead()) {
            throw new IOException("Cannot access file: " + path);
        }

        // set lastModified to current timestamp to make sure no "old" data is read from the file
        this.lastModified = new Date().getTime();

        this.adapter = adapter;
        this.lineSeparator = System.getProperty("line.separator");
    }

    @Override
    public byte[] readData() throws NoDataAvailableException {
        // no new data available, don't read data
        if (this.lastModified >= this.file.lastModified()) {
            throw new NoDataAvailableException("FileDevice: the file has not been updated since the last read operation");
        }

        String rawData = "";

        try {
            rawData = this.readFileContent();
            this.lastModified = this.file.lastModified();
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
     * @throws IOException An IOException can be thrown when the file is trying to be read
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
