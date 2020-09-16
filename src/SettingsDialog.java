import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SettingsDialog {
    private static int thisWindowSize=440;
    public static int windowSize = 550;
    public static int gridDim = 3;
    public static int dimToWin = 3;
    public static int turnID = 2;
    public static boolean close = false;
    static ComboBox<String> winSize_cb;
    static ComboBox<Integer> gridDim_cb;
    static ComboBox<Integer> dimToWin_cb;
    static ComboBox<String> turnID_cb;
    static Button confirmBtn;


    public static void display() {

        Stage window = new Stage();
        Font font = new Font("Arial",14);
        float heightPercentage = thisWindowSize* 0.1f;
        float widthPercentage = thisWindowSize* 0.5f;
        int textPadding = 25;
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Tic Tac Toe Settings");

        Label winSize_lb = new Label("Window Size ");
        winSize_lb.setPrefHeight(textPadding);
        winSize_lb.setFont(font);
        winSize_cb = new ComboBox<>();
        winSize_cb.setPrefSize(widthPercentage,heightPercentage);
        winSize_cb.getItems().addAll("300x300", "400x400", "500x500","600x600","700x700","800x800","900x900","1000x1000");
        winSize_cb.setValue("500x500");
        winSize_cb.setOnAction(e -> windowSize = Integer.parseInt(winSize_cb.getValue().toString().split("x")[0]));

        Label gridDim_lb = new Label("Play area size ");
        gridDim_lb.setPrefHeight(textPadding);
        gridDim_lb.setFont(font);
        gridDim_cb = new ComboBox<>();
        gridDim_cb.setPrefSize(widthPercentage,heightPercentage);
        gridDim_cb.getItems().addAll(3, 4, 5, 6, 7, 8, 9, 10);
        gridDim_cb.setValue(3);
        gridDim_cb.setOnAction(e -> gridDim = gridDim_cb.getValue());

        Label dimToWin_lb = new Label("Win condition ");
        dimToWin_lb.setPrefHeight(textPadding);
        dimToWin_lb.setFont(font);
        dimToWin_cb = new ComboBox<>();
        dimToWin_cb.setPrefSize(widthPercentage,heightPercentage);
        dimToWin_cb.getItems().addAll(3, 4, 5, 6, 7, 8, 9, 10);
        dimToWin_cb.setValue(3);
        dimToWin_cb.setOnAction(e -> dimToWin = dimToWin_cb.getValue());

        Label turnID_lb = new Label("Game start ");
        turnID_lb.setFont(font);
        turnID_lb.setPrefHeight(textPadding);
        turnID_cb = new ComboBox<>();
        turnID_cb.setPrefSize(widthPercentage,heightPercentage);
        turnID_cb.getItems().addAll("X", "O");
        turnID_cb.setValue("O");
        turnID_cb.setOnAction(e -> {
            if (turnID_cb.getValue() == "O") turnID = 2;
            else turnID = 3;

        });

        confirmBtn = new Button("Play");
        confirmBtn.setFont(font);
        confirmBtn.setPrefSize(widthPercentage,heightPercentage);
        confirmBtn.setOnAction(e -> window.close());

        VBox layout = new VBox(winSize_lb,winSize_cb,gridDim_lb, gridDim_cb, dimToWin_lb,dimToWin_cb,turnID_lb, turnID_cb,confirmBtn);
        VBox.setMargin(confirmBtn,new Insets(textPadding,0,0,0));
        layout.getStylesheets().add("style.css");
        layout.getStyleClass().add("controlArea");
        winSize_cb.getStyleClass().add("combo-box");
        winSize_cb.getStyleClass().add("controlBtn");
        gridDim_cb.getStyleClass().add("combo-box");
        gridDim_cb.getStyleClass().add("controlBtn");
        dimToWin_cb.getStyleClass().add("combo-box");
        dimToWin_cb.getStyleClass().add("controlBtn");
        turnID_cb.getStyleClass().add("combo-box");
        turnID_cb.getStyleClass().add("controlBtn");
        confirmBtn.getStyleClass().add("controlBtn");

        layout.setAlignment(Pos.CENTER);
        Scene s = new Scene(layout, thisWindowSize, thisWindowSize);
        window.setScene(s);
        window.setOnCloseRequest(e -> close=true);
        window.setResizable(false);
        window.showAndWait();

    }
}
