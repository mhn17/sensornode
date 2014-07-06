package com.github.hammertonmarc.sensornode.publication;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by marc on 18.05.14.
 */
public class FilePublisher implements Publisher {

    protected String fileType;

    public FilePublisher(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public void publish(String id, byte[] data) {
        if (this.fileType.equals("JPEG")) {
            this.publishAsJpeg(id, data);
        }
    }

    protected void publishAsJpeg(String id, byte[] data) {
        try {
            FileOutputStream fos = new FileOutputStream(id + ".jpg");
            fos.write(data);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*File file = new File("image.jpg");

        //BufferedImage bi = ImageIO.
        try {
            ImageIO.write(bi, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void run() {

    }
}
