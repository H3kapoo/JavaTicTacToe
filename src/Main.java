import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


//HekaBranch
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {

        int prefSize = 500;

        Canvas canvas = new Canvas(prefSize,prefSize);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Color c = new Color(100/255f,100/255f,100/255f,1);
        gc.setFill(c);
        gc.fillRect(0,0,prefSize,prefSize);
        drawGrid(gc);

        StackPane layout = new StackPane();
        layout.getChildren().add(canvas);

        Scene scene = new Scene(layout,prefSize,prefSize);
        window.setTitle("Tic Tac Toe");
        window.setScene(scene);
        window.show();
    }

    public void drawGrid(GraphicsContext gc){

        int size = (int)gc.getCanvas().getWidth();
        int stepSize = size/3;

        gc.beginPath();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.moveTo(stepSize,0);
        gc.lineTo(stepSize,size);
        gc.stroke();

        gc.beginPath();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.moveTo(stepSize*2,0);
        gc.lineTo(stepSize*2,size);
        gc.stroke();

        gc.beginPath();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.moveTo(0,stepSize);
        gc.lineTo(size,stepSize);
        gc.stroke();

        gc.beginPath();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.moveTo(0,stepSize*2);
        gc.lineTo(size,stepSize*2);
        gc.stroke();
    }
}
