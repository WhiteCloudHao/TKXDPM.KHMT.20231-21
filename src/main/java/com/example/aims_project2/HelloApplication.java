package com.example.aims_project2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import com.example.aims_project2.utils.Configs;
import com.example.aims_project2.views.screen.home.*;


public class HelloApplication extends Application {
//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/splash.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
//    }
    @Override
    public void start(Stage stage) throws IOException {
        // initialize the scene
        StackPane root = (StackPane) FXMLLoader.load(getClass().getResource(Configs.SPLASH_SCREEN_PATH));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Load splash screen with fade in effect
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), root);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);

        // Finish splash with fade out effect
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), root);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);

        // After fade in, start fade out
        fadeIn.play();
        fadeIn.setOnFinished((e) -> {
            fadeOut.play();
        });

        // After fade out, load actual content
        fadeOut.setOnFinished((e) -> {
            System.out.println("start Home screen");
            try {
                HomeScreenHandler homeHandler = new HomeScreenHandler(stage, Configs.HOME_PATH);
                homeHandler.setScreenTitle("Home Screen");
                homeHandler.setImage();
                homeHandler.show();
            } catch (IOException e1) {
                System.out.println("lot catch");
                e1.printStackTrace();
            }
        });
    }
    public static void main(String[] args) {
        launch();
    }
}