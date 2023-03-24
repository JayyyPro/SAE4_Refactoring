package Version_Enseignant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainEnseignant extends Application {

	public static Parent root;

	//Param�tres de taille d'�cran
	public static double width;
	public static double height;

	@Override
	public synchronized void start(Stage primaryStage) throws Exception{
		root = FXMLLoader.load(getClass().getResource("/Version_Enseignant/FXML_Files/Menu.fxml"));
		primaryStage.setTitle("Reconstitution - Version Enseignante");
		primaryStage.getIcons().add(new Image("/Image/Logo_Reconstitution.png"));

		//On affiche le plein �cran
		primaryStage.setMaximized(true);
		primaryStage.setMinWidth(1200);
		primaryStage.setMinHeight(800);

		//On r�cup�re la largeur et la hauteur de l'�cran
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		width=screenBounds.getWidth();
		height=screenBounds.getHeight();
		Scene scene = new Scene(root, width, height);
		scene.getStylesheets().addAll(getClass().getResource("/Version_Enseignant/FXML_Files/MenuAndButtonStyles.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}

}
