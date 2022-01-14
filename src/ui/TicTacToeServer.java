/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Marwan Adel
 */
public class TicTacToeServer extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = new FXMLDocumentBase(stage);

        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add(getClass().getResource("/assets/styles/style.css").toExternalForm());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("TIC TAC TOE SERVER");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
