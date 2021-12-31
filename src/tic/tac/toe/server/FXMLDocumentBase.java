package tic.tac.toe.server;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public  class FXMLDocumentBase extends BorderPane {

    protected final GridPane gridPane;
    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final RowConstraints rowConstraints;
    protected final RowConstraints rowConstraints0;
    protected final Text text;
    protected final Button btnStart;
    protected final Button btnStop;
    protected final PieChart pieChart;

    public FXMLDocumentBase() {

        gridPane = new GridPane();
        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        rowConstraints0 = new RowConstraints();
        text = new Text();
        btnStart = new Button();
        btnStop = new Button();
        pieChart = new PieChart();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(500.0);
        setPrefWidth(600.0);

        BorderPane.setAlignment(gridPane, javafx.geometry.Pos.CENTER);
        gridPane.setPrefHeight(153.0);
        gridPane.setPrefWidth(600.0);

        columnConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints.setMaxWidth(300.0);
        columnConstraints.setMinWidth(10.0);
        columnConstraints.setPrefWidth(300.0);

        columnConstraints0.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints0.setMaxWidth(300.0);
        columnConstraints0.setMinWidth(10.0);
        columnConstraints0.setPrefWidth(300.0);

        rowConstraints.setMaxHeight(121.0);
        rowConstraints.setMinHeight(10.0);
        rowConstraints.setPrefHeight(107.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints0.setMaxHeight(28.0);
        rowConstraints0.setMinHeight(1.0);
        rowConstraints0.setPrefHeight(28.0);
        rowConstraints0.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        text.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text.setStrokeWidth(0.0);
        text.setText("Press the button to start and stop recieving and sending play request");
        text.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        text.setWrappingWidth(572.6708984375);
        text.setFont(new Font(24.0));
        GridPane.setMargin(text, new Insets(5.0, 0.0, 0.0, 0.0));

        GridPane.setRowIndex(btnStart, 1);
        btnStart.setMnemonicParsing(false);
        btnStart.setText("Start");
        GridPane.setMargin(btnStart, new Insets(-15.0, 0.0, 0.0, 200.0));
        btnStart.setFont(new Font("System Bold", 18.0));

        GridPane.setColumnIndex(btnStop, 1);
        GridPane.setRowIndex(btnStop, 1);
        btnStop.setMnemonicParsing(false);
        btnStop.setText("Stop");
        GridPane.setMargin(btnStop, new Insets(-15.0, 0.0, 0.0, 0.0));
        btnStop.setFont(new Font("System Bold", 18.0));
        setTop(gridPane);

        BorderPane.setAlignment(pieChart, javafx.geometry.Pos.CENTER);
        setCenter(pieChart);

        gridPane.getColumnConstraints().add(columnConstraints);
        gridPane.getColumnConstraints().add(columnConstraints0);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane.getRowConstraints().add(rowConstraints0);
        gridPane.getChildren().add(text);
        gridPane.getChildren().add(btnStart);
        gridPane.getChildren().add(btnStop);
        
           
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Online Player", 50),
                new PieChart.Data("offline player", 50)
                
               );
        pieChart.setData(pieChartData);
        pieChart.setTitle("Active Players:");

    }
}
