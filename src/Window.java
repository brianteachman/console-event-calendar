import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

/**
 * Created by BTEACHMAN on 9/25/2017.
 */
public class Window extends Application {

    public void start(Stage theStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));
        Scene scene = new Scene(grid, 600, 400);
        theStage.setTitle("New test window");

        Text scenetitle = new Text("Here lies dragons!");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        theStage.setScene(scene);
        theStage.show();
    }

    public static int calcParabola(int x) {
        return x * x;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
