package org.example;

import java.awt.*;

public class Main {
    public static void main (String [] args) {
        //new FaceDetector();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VideoFrame().setVisible(true);
            }
        });
    }
}