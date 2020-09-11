import com.sun.prism.Graphics;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;


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
        boolean xTurn = false;
        int gridDim = 4;
        Color bgColor = Color.web("#d6d6d6");
        clearScreen(gc, bgColor);
        drawGrid(gc, Color.BLACK, gridDim);
        Point2D[] positions = generatePositions(prefSize, gridDim);

        gc.setStroke(Color.RED );
        for(int i=0;i< gridDim*gridDim;i++)
            gc.strokeOval(positions[i].getX()-50, positions[i].getY()-50,100,100);

        //loop

        StackPane layout = new StackPane();
        layout.getChildren().add(canvas);
        Scene scene = new Scene(layout, prefSize, prefSize);
        window.setTitle("Tic Tac Toe");
        window.setScene(scene);
        window.show();
    }

    public Point2D[] generatePositions(int size, int gridDim) {
        Point2D[] pos = new Point2D[gridDim*gridDim];
        int stepSize = size / gridDim;
        int reducedStepSize = stepSize / 2;

        for (int i = 0; i < gridDim; i++) {
            for (int j = 0; j < gridDim; j++) {
                pos[i+j*gridDim] = new Point2D(reducedStepSize + stepSize * i,reducedStepSize + stepSize * j);
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
