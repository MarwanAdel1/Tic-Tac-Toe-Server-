package ui;

import data.DatabaseManage;
import data.ServerRequestsHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FXMLDocumentBase extends BorderPane {

    protected final GridPane gridPane;
    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final RowConstraints rowConstraints;
    protected final RowConstraints rowConstraints0;
    protected final Text text;
    protected final Button btnStart;
    protected final Button btnStop;
    protected static PieChart pieChart;
    protected final FlowPane flowPane;
    protected final Label label;
    protected final Label IpLabel;
    private static ObservableList<PieChart.Data> pieChartData;

    public FXMLDocumentBase(Stage stage) {

        gridPane = new GridPane();
        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        rowConstraints0 = new RowConstraints();
        text = new Text();
        btnStart = new Button();
        btnStop = new Button();
        pieChart = new PieChart();
        flowPane = new FlowPane();
        label = new Label();
        IpLabel = new Label();

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
        text.setFont(new Font("System Bold", 24.0));
        GridPane.setMargin(text, new Insets(5.0, 0.0, 0.0, 0.0));

        GridPane.setRowIndex(btnStart, 1);
        btnStart.setMnemonicParsing(false);
        btnStart.setText("Start");
        GridPane.setMargin(btnStart, new Insets(-15.0, 0.0, 0.0, 200.0));
        btnStart.setFont(new Font("System Bold", 20.0));

        GridPane.setColumnIndex(btnStop, 1);
        GridPane.setRowIndex(btnStop, 1);
        btnStop.setMnemonicParsing(false);
        btnStop.setText("Stop");
        GridPane.setMargin(btnStop, new Insets(-15.0, 0.0, 0.0, 0.0));
        btnStop.setFont(new Font("System Bold", 20.0));
        setTop(gridPane);

        BorderPane.setAlignment(pieChart, javafx.geometry.Pos.CENTER);
        setCenter(pieChart);

        BorderPane.setAlignment(flowPane, javafx.geometry.Pos.CENTER);
        flowPane.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        flowPane.setHgap(10.0);
        flowPane.setPrefHeight(55.0);
        flowPane.setPrefWidth(600.0);

        label.setText("Not Started");
        label.setFont(new Font("System Bold Italic", 18.0));

        IpLabel.setText("Unknown");
        IpLabel.setFont(new Font("System Bold", 22.0));
        flowPane.setPadding(new Insets(0.0, 0.0, 0.0, 30.0));
        setBottom(flowPane);

        gridPane.getColumnConstraints().add(columnConstraints);
        gridPane.getColumnConstraints().add(columnConstraints0);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane.getRowConstraints().add(rowConstraints0);
        gridPane.getChildren().add(text);
        gridPane.getChildren().add(btnStart);
        gridPane.getChildren().add(btnStop);
        flowPane.getChildren().add(label);
        
        btnStart.setId("greenButton");
        btnStop.setId("greenButton");
        label.setId("greentext");
        IpLabel.setId("greentext");
        text.setId("greentext");

        DatabaseManage databaseManage = new DatabaseManage();

        pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Online Player", databaseManage.fetchOnlinePlayers()),
                new PieChart.Data("offline player", databaseManage.fetchOfflinePlayers()));
        pieChart.setData(pieChartData);

        btnStart.setOnAction((event) -> {
            ServerRequestsHandler serverRequestsHandler = ServerRequestsHandler.createInstance(stage);

            serverRequestsHandler.startServer();

            label.setText("Running");
        });

        btnStop.setOnAction((event) -> {
            ServerRequestsHandler serverRequestsHandler = ServerRequestsHandler.createInstance(stage);

            serverRequestsHandler.stopServer();

            label.setText("Stopped");
        });

    }

    public static void updateChart(int onlinePlayer, int offlinePlayer) {
        pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Online Player", onlinePlayer),
                new PieChart.Data("offline player", offlinePlayer));
        pieChart.setData(pieChartData);

    }
}
