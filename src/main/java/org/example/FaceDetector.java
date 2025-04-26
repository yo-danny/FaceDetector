package org.example;

import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;

import javax.swing.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.bytedeco.opencv.global.opencv_imgproc.*;

public class FaceDetector {

    private static final String CASCADE_FILE = "src/main/resources/files/haarcascade_frontalface_alt.xml";
    private File videoFile;

    public FaceDetector(File videoFile){
        this.videoFile = videoFile;
        initDetection();
    }

    private void initDetection(){
        try {
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoFile);
            grabber.setFormat("mp4");

            if(grabber.getImageWidth() >= grabber.getImageHeight()) {
                grabber.setImageHeight(480);
                grabber.setImageWidth(640);
            } else {
                grabber.setImageHeight(640);
                grabber.setImageWidth(480);
            }

            grabber.start();
            Frame grabbedImage = grabber.grab();
            CanvasFrame canvasFrame = new CanvasFrame("Video with Detection");
            canvasFrame.setCanvasSize(grabbedImage.imageWidth, grabbedImage.imageHeight);
            grabber.setFrameRate(grabber.getFrameRate());

            canvasFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            CascadeClassifier cascade = new CascadeClassifier(CASCADE_FILE);
            Mat matImage = new Mat();
            OpenCVFrameConverter.ToMat toMatImage = new OpenCVFrameConverter.ToMat();

            while (canvasFrame.isVisible() && (grabbedImage = grabber.grab()) != null) {
                matImage = toMatImage.convert(grabbedImage);

                if (matImage == null) continue;

                Mat grayMat = new Mat();
                cvtColor(matImage, grayMat, COLOR_BGR2GRAY);

                RectVector faces = new RectVector();
                cascade.detectMultiScale(grayMat, faces);

                for (int i = 0; i < faces.size(); i++) {
                    Rect face = faces.get(i);
                    rectangle(matImage, face, Scalar.BLUE, 3, LINE_AA, 0);
                }

                Frame processedFrame = toMatImage.convert(matImage);
                canvasFrame.showImage(processedFrame);
            }
            grabber.stop();
            canvasFrame.dispose();

        } catch (FFmpegFrameGrabber.Exception e) {
            Logger.getLogger(FaceDetector.class.getName()).log(Level.SEVERE, null, e);
        }
    }


}
