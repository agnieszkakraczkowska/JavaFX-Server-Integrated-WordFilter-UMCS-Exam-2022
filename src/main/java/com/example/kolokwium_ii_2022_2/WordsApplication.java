package com.example.kolokwium_ii_2022_2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class WordsApplication extends Application {
    private Controller controller;

    @Override
    public void start(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(WordsApplication.class.getResource("hello-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            stage.setTitle("Words Application!");
            stage.setScene(scene);
            stage.show();

            controller = fxmlLoader.getController();
            connectToTheServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connectToTheServer() {
        //connecting to the server should be executed in a new thread
        Thread networkThread = new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 5000);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    //variables used in lambdas should be final*
                    String finalLine = line;
                    Platform.runLater(() -> {
                        //*that's why this line needs to be passed through another method before it's passed to the controller method
                        addWordToList(finalLine);
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        networkThread.setDaemon(true);
        networkThread.start();
    }

    private void addWordToList(String line) {
        if (controller != null) {
            controller.addEntryToList(line);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
