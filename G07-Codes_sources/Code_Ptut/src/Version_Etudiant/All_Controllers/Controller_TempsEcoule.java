package Version_Etudiant.All_Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller_TempsEcoule {

	@FXML private Label recupScene;
	
	@FXML private Button recupScene1;

	@FXML
	public void retourAccueil() {
		recupScene.getScene().getWindow().hide();
	}
	
	@FXML
	public void retourAccueil1() {
		recupScene1.getScene().getWindow().hide();
	}

}
