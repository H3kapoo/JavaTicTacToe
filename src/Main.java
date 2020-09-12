import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


//HekaBranch

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    int gridDim = 3;
    int prefSize = 500;
    int controlAreaSize = 50;
    int movesLeft = gridDim * gridDim;
    int turnID = 2;  //O - Starts here  // O - 2   X - 3
    boolean gameFinished = false;
    Button resetBtn;
    Point2D[] positions = generatePositions(prefSize);
    int[] availablePos = generateAvailablePos(positions.length);

    @Override
    public void start(Stage window) throws Exception {

        Color bgColor = Color.web("#d6d6d6");

        resetBtn = new Button("Click to reset");
        Canvas canvas = new Canvas(prefSize, prefSize);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        clearScreen(gc, bgColor);
        drawGrid(gc, Color.BLACK);

        VBox layout = new VBox();
        HBox controlArea = new HBox();

        resetBtn.setTextAlignment(TextAlignment.CENTER);
        resetBtn.setPrefSize(prefSize, controlAreaSize);
        resetBtn.getStyleClass().add("controlBtn");

        controlArea.setPrefSize(prefSize, controlAreaSize);
        controlArea.getStylesheets().add("style.css");
        controlArea.getStyleClass().add("controlArea");


        canvas.setOnMouseClicked(e -> handleTurn(e, gc));
        resetBtn.setOnMouseClicked(e -> reset(gc, bgColor, Color.BLACK));

        controlArea.getChildren().add(resetBtn);
        layout.getChildren().addAll(canvas, controlArea);
        Scene scene = new Scene(layout, prefSize, prefSize + controlAreaSize);

        window.setTitle("Tic Tac Toe");
        window.setScene(scene);
        window.show();

    }

    public void handleTurn(MouseEvent e, GraphicsContext gc) {
        int closestIndex = getClosestIndex(positions, e.getSceneX(), e.getSceneY());

        if (availablePos[closestIndex] == 1 && !gameFinished) {
            if (turnID == 3) {
                drawX(gc, Color.RED, positions[closestIndex].getX(), positions[closestIndex].getY());
                availablePos[closestIndex] = 3;
            }
            if (turnID == 2) {
                drawZero(gc, Color.RED, positions[closestIndex].getX(), positions[closestIndex].getY());
                availablePos[closestIndex] = 2;
            }

            int winnerID = winChecker(availablePos);
            if (winnerID != 0) {
                gameFinished = true;
                String winner = winnerID == 2 ? "O" : "X";
                resetBtn.setText("Winner is " + winner + "\nClick to reset");
                return;
            }

            if (turnID == 3) turnID = 2;
            else if (turnID == 2) turnID = 3;
            movesLeft--;

            if (movesLeft == 0) {
                gameFinished = true;
                resetBtn.setText("Tie\nClick to reset");
            }
        }

    }

    public void reset(GraphicsContext gc, Color bgColor, Color gridColor) {
        clearScreen(gc, bgColor);
        drawGrid(gc, Color.BLACK);
        positions = generatePositions(prefSize);
        availablePos = generateAvailablePos(positions.length);
        gameFinished = false;
        resetBtn.setText("Click to reset");
        movesLeft = gridDim * gridDim;
        turnID = 2;
    }

    public int winChecker(int[] pos) {

        // O - 2
        // X - 3

        // - - -
        // - - -
        // - - -
        for (int i = 0; i < gridDim; i++) {
            boolean ok = true;
            for (int j = 0; j < gridDim; j++)
                if (pos[i * gridDim + j] != turnID)
                    ok = false;
            if (ok) return turnID;
        }

        // |  |  |
        // |  |  |
        // |  |  |
        for (int i = 0; i < gridDim; i++) {
            boolean ok = true;
            for (int j = 0; j < gridDim; j++)
                if (pos[j * gridDim + i] != turnID)
                    ok = false;
            if (ok) return turnID;
        }

        //  \ /
        //   X
        //  / \
        boolean ok = true;
        for (int i = 0; i < gridDim; i++)
            if (pos[i * gridDim + i] != turnID)
                ok = false;
        if (ok) return turnID;

        ok = true;
        for (int i = 0; i < gridDim; i++)
            if (pos[i * gridDim + (gridDim - 1 - i)] != turnID)
                ok = false;
        if (ok) return turnID;


        return 0;
    }

    public int[] generateAvailablePos(int length) {
        int[] av = new int[length];
        for (int i = 0; i < length; i++)
            av[i] = 1;
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

    public void drawX(GraphicsContext gc, Color color, double xPos, double yPos) {

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

    public void drawZero(GraphicsContext gc, Color color, double xPos, double yPos) {

        int size = (int) gc.getCanvas().getWidth();
        int zeroSize = (int) ((size / gridDim) * 0.8f); //80% of the space

        try {
            gc.beginPath();
            gc.setStroke(color);
            gc.strokeOval(xPos - zeroSize / 2f, yPos - zeroSize / 2f, zeroSize, zeroSize);
        } catch (Exception ignore) {
        }
    }

    public Point2D[] generatePositions(int size) {

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

    public void drawGrid(GraphicsContext gc, Color color) {

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
