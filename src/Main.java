import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;


//HekaBranch

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {

        int prefSize = 500;
        Canvas canvas = new Canvas(prefSize, prefSize);
        GraphicsContext gc = canvas.getGraphicsContext2D();


        boolean gameFinished = true;
        boolean[] xTurn = {false};
        int gridDim = 3;
        Color bgColor = Color.web("#d6d6d6");
        clearScreen(gc, bgColor);
        drawGrid(gc, Color.BLACK, gridDim);
        Point2D[] positions = generatePositions(prefSize, gridDim);
        boolean[] availablePos = generateAvailablePos(positions.length);

        canvas.setOnMouseClicked(e -> {
            int closestIndex = getClosestIndex(positions, e.getSceneX(), e.getSceneY());

            if (availablePos[closestIndex]) {
                if (xTurn[0])
                    drawZero(gc, gridDim, Color.RED, positions[closestIndex].getX(), positions[closestIndex].getY());
                else
                    drawX(gc, gridDim, Color.RED, positions[closestIndex].getX(), positions[closestIndex].getY());
            }

            availablePos[closestIndex] = false;
            xTurn[0] = !xTurn[0];

        });

        //loop

        StackPane layout = new StackPane();
        layout.getChildren().add(canvas);
        Scene scene = new Scene(layout, prefSize, prefSize);
        window.setTitle("Tic Tac Toe");
        window.setScene(scene);
        window.show();
    }

    public boolean[] generateAvailablePos(int length) {
        boolean[] av = new boolean[length];
        for (int i = 0; i < length; i++)
            av[i] = true;
        return av;
    }

    public int getClosestIndex(Point2D[] pos, double xPos, double yPos) {
        double closest = 99999;
        int closest_index = -1;

        for (int i = 0; i < pos.length; i++) {
            double x = pos[i].getX();
            double y = pos[i].getY();
            double d = Math.hypot(xPos - x, yPos - y);
            if (d < closest) {
                closest = d;
                closest_index = i;
            }
        }
        return closest_index;
    }

    public void drawX(GraphicsContext gc, int gridDim, Color color, double xPos, double yPos) {

        int size = (int) gc.getCanvas().getWidth();
        int XSize = (int) ((size / gridDim) * 0.4f); //80% of the space

        try {
            gc.beginPath();
            gc.setStroke(color);
            gc.moveTo(xPos - XSize, yPos - XSize);
            gc.lineTo(xPos + XSize, yPos + XSize);
            gc.moveTo(xPos - XSize, yPos + XSize);
            gc.lineTo(xPos + XSize, yPos - XSize);
            gc.stroke();
        } catch (Exception ignore) {
        }
    }

    public void drawZero(GraphicsContext gc, int gridDim, Color color, double xPos, double yPos) {

        int size = (int) gc.getCanvas().getWidth();
        int zeroSize = (int) ((size / gridDim) * 0.8f); //80% of the space

        try {
            gc.beginPath();
            gc.setStroke(color);
            gc.strokeOval(xPos - zeroSize / 2f, yPos - zeroSize / 2f, zeroSize, zeroSize);
        } catch (Exception ignore) {
        }
    }

    public Point2D[] generatePositions(int size, int gridDim) throws Exception {

        if (gridDim <= 0) throw new Exception("ERROR: gridDim must be greater than zero");

        Point2D[] pos = new Point2D[gridDim * gridDim];
        int stepSize = size / gridDim;
        int reducedStepSize = stepSize / 2;

        for (int i = 0; i < gridDim; i++) {
            for (int j = 0; j < gridDim; j++) {
                pos[i + j * gridDim] = new Point2D(reducedStepSize + stepSize * i, reducedStepSize + stepSize * j);
            }
        }
        return pos;
    }

    public void clearScreen(GraphicsContext gc, Color color) {
        int size = (int) gc.getCanvas().getWidth();
        gc.setFill(color);
        gc.fillRect(0, 0, size, size);
    }

    public void drawGrid(GraphicsContext gc, Color color, int gridDim) throws Exception {

        if (gridDim <= 0) throw new Exception("ERROR: gridDim must be greater than zero");

        int size = (int) gc.getCanvas().getWidth();
        int stepSize = size / gridDim;

        gc.beginPath();
        gc.setStroke(color);
        gc.setLineWidth(2);

        //vertical lines
        for (int i = 1; i < gridDim; i++) {
            gc.moveTo(stepSize * i, 0);
            gc.lineTo(stepSize * i, size);
            gc.stroke();
        }
        //horizontal lines
        for (int i = 1; i < gridDim; i++) {
            gc.moveTo(0, stepSize * i);
            gc.lineTo(size, stepSize * i);
            gc.stroke();
        }
    }
}
