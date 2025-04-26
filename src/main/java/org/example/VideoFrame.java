package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class VideoFrame extends JFrame {

    private JLabel jLabel;

    public VideoFrame() {
        initComponents();
        this.setSize(500, 200);
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        jLabel = new JLabel("Choose the video to face detect:");
        JButton jButton = new JButton("Open Video");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel.setBounds(50,50,400,50);
        jLabel.setFont(new Font("Verdana",1,20));
        getContentPane().add(jLabel);

        jButton.setBounds(175, 100, 100, 30);
        getContentPane().add(jButton);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButtonActionPerformed(e);
            }
        });

        pack();
    }

    private void jButtonActionPerformed(ActionEvent e){
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION){
            File videoFile = fileChooser.getSelectedFile();
            playVideo(videoFile);
        }
    }

    private void playVideo(File videoFile){
        new Thread(() -> new FaceDetector(videoFile)).start();
    }
}