package Version_Enseignant.All_Controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import Version_Enseignant.MainEnseignant;
import Version_Enseignant.DeplacementFenetre;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;

import javafx.scene.text.Font;
import javafx.util.Duration;

public class Controller_Page_Des_Options implements Initializable {

	@FXML
	private RadioButton radioButtonEntrainement;
	@FXML
	private RadioButton radioButtonEvaluation;
	@FXML
	private RadioButton radioButton2Lettres;
	@FXML
	private RadioButton radioButton3Lettres;
	@FXML
	private TextField CaraOccul;
	@FXML
	private TextField nbMinute;
	@FXML
	private CheckBox checkBoxMotIncomplet;
	@FXML
	private CheckBox checkBoxSolution;
	@FXML
	private CheckBox checkBoxMotsDecouverts;
	@FXML
	private CheckBox sensibiliteCasse;
	@FXML
	private Button enregistrer;

	// Variables qui contiennent les informations sur l'exercice
	public static String caraOcculString;
	public static String nbMin;
	public static boolean sensiCasse;
	public static boolean entrainement;
	public static boolean evaluation;
	public static boolean solution;
	public static boolean motDecouverts;
	public static boolean motIncomplet;
	public static boolean lettres_2;
	public static boolean lettres_3;

	// Toutes les variables des tooltip
	@FXML
	private ImageView toolTipOccul;
	@FXML
	private ImageView toolTipSensi;
	@FXML
	private ImageView toolTipEntr;
	@FXML
	private ImageView toolTipEval;
	@FXML
	private ImageView toolTipNbMin;
	@FXML
	private ImageView toolTipMotDecouvert;
	@FXML
	private ImageView toolTipSolution;
	@FXML
	private ImageView toolTipMotIncomplet;

	@FXML
	private CheckMenuItem dark;

	// M�thode d'initialisation de la page
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Pour la fonction ouvrir
		// On met le caract�re d'occultation
		if (caraOcculString != null) {
			CaraOccul.setText(caraOcculString);
		}

		// On met la sensibilit� � la casse si celle-ci est activ�e
		if (sensiCasse == true) {
			sensibiliteCasse.setSelected(true);
		}

		// On met le bon mode
		// Entrainement
		if (entrainement == true) {
			radioButtonEntrainement.setSelected(true);

			// On met disable ce qui concerne le mode Evaluation
			nbMinute.setDisable(true);

			// Si l'affichage de la solution est autoris�
			if (solution == true) {
				checkBoxSolution.setSelected(true);
			}

			// Si l'affichage du nombre de mots d�couverts en temps r�el est autoris�
			if (motDecouverts == true) {
				checkBoxMotsDecouverts.setSelected(true);
			}

			// Si l'option mot incomplet est autoris�
			if (motIncomplet == true) {
				checkBoxMotIncomplet.setSelected(true);

				// Si c'est pour deux lettres
				if (lettres_2 == true) {
					radioButton2Lettres.setSelected(true);
				}

				// Si c'est pour trois lettres
				if (lettres_3 == true) {
					radioButton3Lettres.setSelected(true);
				}

			}
		}

		// Evaluation
		if (evaluation == true) {
			radioButtonEvaluation.setSelected(true);

			// On met disable ce qui concerne le mode Entrainement
			checkBoxMotsDecouverts.setDisable(true);
			checkBoxMotIncomplet.setDisable(true);
			checkBoxSolution.setDisable(true);
			radioButton2Lettres.setDisable(true);
			radioButton3Lettres.setDisable(true);

			nbMinute.setText(nbMin);
			nbMinute.setDisable(false);
		}

		checkMode();

		// Si tous les trucs n�cessaires sont coch�s on met le bouton enregistrer dispo
		if (!CaraOccul.getText().isEmpty() && (radioButtonEntrainement.isSelected()
				|| (radioButtonEvaluation.isSelected() && !nbMinute.getText().isEmpty()))) {
			enregistrer.setDisable(false);
		}

	}

	// Listener qui v�rifie qu'au moins un mode a �t� coch� avant de passer � la
	// suite
	private void checkMode() {

		// Pour le TextField du caract�re d'occultation
		CaraOccul.textProperty()
				.addListener((ObservableValue<? extends String> arg0, String oldValue, String newValue) -> {
					if (!CaraOccul.getText().isEmpty() && (radioButtonEntrainement.isSelected()
							|| (radioButtonEvaluation.isSelected() && !nbMinute.getText().isEmpty()))) {
						enregistrer.setDisable(false);
					} else {
						enregistrer.setDisable(true);
					}
				});

		// Pour le radioButton du mode entrainement
		radioButtonEntrainement.selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
			if (!CaraOccul.getText().isEmpty() && (radioButtonEntrainement.isSelected()
					|| (radioButtonEvaluation.isSelected() && !nbMinute.getText().isEmpty()))) {
				enregistrer.setDisable(false);
			} else {
				enregistrer.setDisable(true);
			}
		});

		// Pour le radioButton du mode �valuation
		radioButtonEvaluation.selectedProperty().addListener((obs, wasPreviouslySelected, isNowSelected) -> {
			if (!CaraOccul.getText().isEmpty() && (radioButtonEntrainement.isSelected()
					|| (radioButtonEvaluation.isSelected() && !nbMinute.getText().isEmpty()))) {
				enregistrer.setDisable(false);
			} else {
				enregistrer.setDisable(true);
			}
		});

		// Pour le nombre de min
		nbMinute.textProperty().addListener((obs, oldValue, newValue) -> {
			if (radioButtonEvaluation.isSelected()) {
				if (!CaraOccul.getText().isEmpty() && !nbMinute.getText().isEmpty()) {
					enregistrer.setDisable(false);
				} else {
					enregistrer.setDisable(true);
				}
			}
		});
	}

	// M�thode qui permet de se rendre au manuel utilisateur == tuto
	@FXML
	public void tuto() throws MalformedURLException, IOException, URISyntaxException {

		InputStream is = MainEnseignant.class.getResourceAsStream("Manuel_Utilisateur.pdf");

		File pdf = File.createTempFile("Manuel Utilisateur", ".pdf");
		pdf.deleteOnExit();
		try (OutputStream out = new FileOutputStream(pdf)) {
			byte[] buffer = new byte[4096];
			int bytesRead = 0;

			while (is.available() != 0) {
				bytesRead = is.read(buffer);
				out.write(buffer, 0, bytesRead);
			}

			is.close();

			Desktop.getDesktop().open(pdf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Bouton Quitter qui permet � l'enseignant de quitter l'application (disponible
	// sur toutes les pages)
	@FXML
	public void quitter(ActionEvent event) throws IOException {

		Stage primaryStage = new Stage();
		Parent root = FXMLLoader
				.load(getClass().getResource("/Version_Enseignant/FXML_Files/ConfirmationQuitter.fxml"));
		Scene scene = new Scene(root, 400, 200);
		// On bloque sur cette fen�tre
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		scene.setFill(Color.TRANSPARENT);

		// Bordure
		Rectangle rect = new Rectangle(400, 200);
		rect.setArcHeight(20.0);
		rect.setArcWidth(20.0);
		root.setClip(rect);

		DeplacementFenetre.deplacementFenetre((Pane) root, primaryStage);
		primaryStage.setScene(scene);
		darkModeActivation(scene);
		primaryStage.show();
	}

	// Bouton qui fait retourner l'enseignant � la page d'apercu (bouton retour)
	@FXML
	public synchronized void pageApercu(ActionEvent event) throws IOException {

		if (!CaraOccul.getText().isEmpty() && !"".equals(CaraOccul.getText())) {
			caraOcculString = CaraOccul.getText();
		}

		if (!nbMinute.getText().isEmpty() && !"".equals(nbMinute.getText())) {
			nbMin = nbMinute.getText();
		}

		Stage primaryStage = (Stage) nbMinute.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/Version_Enseignant/FXML_Files/PageApercu.fxml"));
		Scene scene = new Scene(root, MainEnseignant.width, MainEnseignant.height - 60);
		primaryStage.setScene(scene);
		darkModeActivation(scene);
	}

	// M�thode pour retourner au menu
	public void retourMenu() throws IOException {
		Stage stage = (Stage) CaraOccul.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/Version_Enseignant/FXML_Files/Menu.fxml"));
		Scene scene = new Scene(root, MainEnseignant.width, MainEnseignant.height - 60);
		stage.setScene(scene);
		darkModeActivation(scene);
		stage.show();
	}

	@FXML
	public synchronized void pageEnregistrementFinal(ActionEvent event) throws IOException {

		// Quand on passe � la page suivante, on m�morise les informations des options
		caraOcculString = CaraOccul.getText();
		nbMin = nbMinute.getText();

		retourMenu();

		Stage primaryStage = new Stage();
		Parent root = FXMLLoader
				.load(getClass().getResource("/Version_Enseignant/FXML_Files/ValidationEnregistrement.fxml"));
		Scene scene = new Scene(root, 320, 150);
		DeplacementFenetre.deplacementFenetre((Pane) root, primaryStage);
		// On bloque sur cette fen�tre
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setScene(scene);
		darkModeActivation(scene);
		primaryStage.show();
	}

	@FXML
	public void pageNouvelExo() throws IOException {
		// R�initialisation des variables
		Controller_Page_Accueil c = new Controller_Page_Accueil();
		c.delete();
		Stage primaryStage = (Stage) CaraOccul.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/Version_Enseignant/FXML_Files/NouvelExo.fxml"));
		Scene scene = new Scene(root, MainEnseignant.width, MainEnseignant.height - 60);
		primaryStage.setScene(scene);
		darkModeActivation(scene);
		primaryStage.show();
	}

	// Gestion de si je s�lectionne un mode, l'autre se d�coche
	@FXML
	public synchronized void selectionModeEvaluation(ActionEvent event) {
		// On fait appara�tre ce qui concerne le mode Evaluation
		nbMinute.setDisable(false);
		evaluation = true;

		// On enl�ve les s�lections du mode entrainement et on passe les variables �
		// false
		checkBoxMotIncomplet.setSelected(false);
		motIncomplet = false;
		checkBoxSolution.setSelected(false);
		solution = false;
		checkBoxMotsDecouverts.setSelected(false);
		motDecouverts = false;
		radioButton2Lettres.setSelected(false);
		lettres_2 = false;
		radioButton3Lettres.setSelected(false);
		lettres_3 = false;

		// On met disable ce qui concerne le mode Entrainement
		checkBoxMotsDecouverts.setDisable(true);
		checkBoxMotIncomplet.setDisable(true);
		checkBoxSolution.setDisable(true);
		radioButton2Lettres.setDisable(true);
		radioButton3Lettres.setDisable(true);

		// On regarde si l'autre bouton est s�lectionn�, si c'est le cas on le
		// d�selectionne
		if (radioButtonEntrainement.isSelected()) {
			radioButtonEntrainement.setSelected(false);
			entrainement = false;
		}

		// Dans le cas d'une d�selection du bouton, on retire ce qui concerne le mode
		// Evaluation
		if (!radioButtonEvaluation.isSelected()) {
			evaluation = false;

			// on vide le textField
			nbMinute.setText(null);
			nbMinute.setDisable(true);
		}

	}

	@FXML
	public synchronized void selectionModeEntrainement(ActionEvent event) {
		// On fait appara�tre ce qui concerne le mode Entrainement
		// On enleve disable ce qui concerne le mode Entrainement
		checkBoxMotsDecouverts.setDisable(false);
		checkBoxMotIncomplet.setDisable(false);
		checkBoxSolution.setDisable(false);
		radioButton2Lettres.setDisable(false);
		radioButton3Lettres.setDisable(false);

		// On cache ce qui concerne le mode Evaluation
		nbMinute.setDisable(true);
		entrainement = true;

		// On r�initialise le nombre de minutes
		nbMinute.setText("");

		// On regarde si l'autre bouton est s�lectionn�, si c'est le cas on le
		// d�selectionne
		if (radioButtonEvaluation.isSelected()) {
			radioButtonEvaluation.setSelected(false);
			evaluation = false;
		}

		// Dans le cas d'une d�selection du bouton, on retire ce qui concerne le mode
		// Entrainement
		if (!radioButtonEntrainement.isSelected()) {

			checkBoxMotsDecouverts.setDisable(true);
			checkBoxMotIncomplet.setDisable(true);
			checkBoxSolution.setDisable(true);
			radioButton2Lettres.setDisable(true);
			radioButton3Lettres.setDisable(true);

			// On enl�ve les s�lections du mode entrainement et on passe les variables �
			// false
			checkBoxMotIncomplet.setSelected(false);
			motIncomplet = false;
			checkBoxSolution.setSelected(false);
			solution = false;
			checkBoxMotsDecouverts.setSelected(false);
			motDecouverts = false;
			radioButton2Lettres.setSelected(false);
			lettres_2 = false;
			radioButton3Lettres.setSelected(false);
			lettres_3 = false;

			entrainement = false;
		}

	}

	// Gestion de si je s�lectionne une nombre de lettres minimum autoris�, l'autre
	// se d�coche
	@FXML
	public synchronized void selection2Lettres(ActionEvent event) {
		if (radioButton3Lettres.isSelected()) {
			radioButton3Lettres.setSelected(false);

			lettres_2 = true;
			lettres_3 = false;
		} else {
			lettres_2 = true;
		}
	}

	@FXML
	public synchronized void selection3Lettres(ActionEvent event) {
		if (radioButton2Lettres.isSelected()) {
			radioButton2Lettres.setSelected(false);

			lettres_3 = true;
			lettres_2 = false;
		} else {
			lettres_3 = true;
		}
	}

	// M�thode qui restreint � un caract�re, la saisie du caract�re d'occultation
	@FXML
	public void RestrictionOne(KeyEvent event) {
		if (CaraOccul.getText().length() > 1) {
			CaraOccul.deletePreviousChar();
		}
	}

	// M�thode qui restreint � la saisie de chiffres uniquement pour la saisie du
	// temps
	@FXML
	public void RestrictionChiffre(KeyEvent event) {
		if (nbMinute.getText().length() > 0 && !nbMinute.getText().matches("[0-9]*")) {
			nbMinute.deletePreviousChar();
		}
	}

	// M�thode qui enl�ve ou fait appara�tre le choix du nombre de lettre si mot
	// incomplet est coch�
	@FXML
	public synchronized void motIncompletFunction(ActionEvent event) {

		// Si on coche le radioButton
		if (checkBoxMotIncomplet.isSelected()) {

			radioButton2Lettres.setDisable(false);

			// On coche par d�faut les 2 lettres
			radioButton2Lettres.setSelected(true);
			lettres_2 = true;
			radioButton3Lettres.setDisable(false);

			// on passe � true
			motIncomplet = true;
		}
		// Si on le d�coche
		if (!checkBoxMotIncomplet.isSelected()) {
			// on le cache
			radioButton2Lettres.setDisable(true);
			radioButton3Lettres.setDisable(true);
			// on les d�selectionne et on repasse les variables � false
			radioButton2Lettres.setSelected(false);
			radioButton3Lettres.setSelected(false);

			lettres_2 = false;
			lettres_3 = false;

			// on passe � false
			motIncomplet = false;
		}

	}

	// M�thode qui passe � true ou false la variable sensiCasse
	@FXML
	public synchronized void sensiCasseFunction(ActionEvent event) {
		// Si la case est coch�e
		if (sensibiliteCasse.isSelected()) {
			sensiCasse = true;
		}
		// Dans le cas contraire
		else {
			sensiCasse = false;
		}
	}

	// M�thode qui passe � true ou false la variable solution
	@FXML
	public synchronized void affichageSolution(ActionEvent event) {
		// Si la case est coch�e
		if (checkBoxSolution.isSelected()) {
			solution = true;
		}
		// Dans le cas contraire
		else {
			solution = false;
		}
	}

	// M�thode qui passe � true ou false la variable motsD�couverts
	@FXML
	public synchronized void motDecouvertsFunction(ActionEvent event) {
		// Si la case est coch�e
		if (checkBoxMotsDecouverts.isSelected()) {
			motDecouverts = true;
		}
		// Dans le cas contraire
		else {
			motDecouverts = false;
		}
	}

	/////// Toutes les m�thodes concernant les toolTip///////////////
	// M�thode pour afficher une tooltip et agrandir l'image
	private Tooltip t;

	public void affichageToolTip(ImageView image, String description) {
		t = new Tooltip(description);
		t.setShowDelay(Duration.seconds(0));
		t.setFont(new Font("Arial", 20));
		Tooltip.install(image, t);
	}

	// M�thode pour r�trcir une image
	public void adaptationImage(ImageView image) {
		image.setFitWidth(image.getFitWidth() - 2);
		image.setFitHeight(image.getFitHeight() - 2);
		Tooltip.uninstall(image, t);
	}

	public void adaptationImage2(ImageView image) {
		image.setFitWidth(image.getFitWidth() + 2);
		image.setFitHeight(image.getFitHeight() + 2);
	}

	@FXML
	public void tipOcculEnter() {
		affichageToolTip(toolTipOccul, "Ce caract�re servira � crypter le script de votre document");
	}

	@FXML
	public void tipSizeOccul() {
		adaptationImage2(toolTipOccul);
	}

	@FXML
	public void tipOcculExit() {
		adaptationImage(toolTipOccul);
		Tooltip.uninstall(toolTipOccul, t);
	}

	@FXML
	public void tipSensiEnter() {
		affichageToolTip(toolTipSensi,
				"Activer la sensibilit� � la casse signifie prendre en compte la diff�rence entre minuscule et majuscule");
	}

	@FXML
	public void tipSizeSensi() {
		adaptationImage2(toolTipSensi);
	}

	@FXML
	public void tipSensiExit() {
		adaptationImage(toolTipSensi);
		Tooltip.uninstall(toolTipSensi, t);
	}

	@FXML
	public void tipEvalEnter() {
		affichageToolTip(toolTipEval, "Le mode Evaluation n'autorise aucune aide pour l'�tudiant");
	}

	@FXML
	public void tipSizeEval() {
		adaptationImage2(toolTipEval);
	}

	@FXML
	public void tipEvalExit() {

		adaptationImage(toolTipEval);
		Tooltip.uninstall(toolTipEval, t);
	}

	@FXML
	public void tipMinEnter() {
		affichageToolTip(toolTipNbMin, "Le nombre de minutes dont l'�l�ve disposera pour faire l'exercice");
	}

	@FXML
	public void tipSizeMin() {
		adaptationImage2(toolTipNbMin);
	}

	@FXML
	public void tipMinExit() {

		adaptationImage(toolTipNbMin);
		Tooltip.uninstall(toolTipNbMin, t);
	}

	@FXML
	public void tipEntrEnter() {
		affichageToolTip(toolTipEntr, "Le mode Entra�nement autorise ou non ceratiens options (list�es ci-dessous)");
	}

	@FXML
	public void tipSizeEntr() {
		adaptationImage2(toolTipEntr);
	}

	@FXML
	public void tipEntrExit() {

		adaptationImage(toolTipEntr);
		Tooltip.uninstall(toolTipEntr, t);

	}

	@FXML
	public void tipMotDecouvertEnter() {
		affichageToolTip(toolTipMotDecouvert,
				"Cette option permet � l'�tudiant de voir en temps r�el le nombre de mots qu'il a trouv�");
	}

	@FXML
	public void tipSizeMotDecouvert() {
		adaptationImage2(toolTipMotDecouvert);
	}

	@FXML
	public void tipMotDecouvertExit() {
		adaptationImage(toolTipMotDecouvert);
		Tooltip.uninstall(toolTipMotDecouvert, t);
	}

	@FXML
	public void tipSolutionEnter() {
		affichageToolTip(toolTipSolution,
				"Autoriser � ce que l'�tudiant puisse consulter la solution pendant l'exercice");
	}

	@FXML
	public void tipSizeSolution() {
		adaptationImage2(toolTipSolution);
	}

	@FXML
	public void tipSolutionExit() {
		adaptationImage(toolTipSolution);
		Tooltip.uninstall(toolTipSolution, t);

	}

	@FXML
	public void tipMotIncompletEnter() {
		affichageToolTip(toolTipMotIncomplet,
				"Autoriser le remplacement partiel des mots � partir d'un nombre minimum de lettres");
	}

	@FXML
	public void tipSizeMotIncomplet() {
		adaptationImage2(toolTipMotIncomplet);
	}

	@FXML
	public void tipMotIncompletExit() {
		adaptationImage(toolTipMotIncomplet);
		Tooltip.uninstall(toolTipMotIncomplet, t);

	}

	private static final String MENUANDBUTTONSTYLES = "/Version_Enseignant/FXML_Files/MenuAndButtonStyles.css";
	private static final String DARKMODETEST = "/Version_Enseignant/FXML_Files/darkModeTest.css";

	// M�thode pour passer ou non le darkMode
	@FXML
	public synchronized void darkMode() {
		if (dark.isSelected()) {
			CaraOccul.getScene().getStylesheets().removeAll(
					getClass().getResource(MENUANDBUTTONSTYLES).toExternalForm());
			CaraOccul.getScene().getStylesheets()
					.addAll(getClass().getResource(DARKMODETEST).toExternalForm());
			Controller_Page_Accueil.isDark = true;
		} else {
			CaraOccul.getScene().getStylesheets().removeAll(
					getClass().getResource(DARKMODETEST).toExternalForm());
			CaraOccul.getScene().getStylesheets().addAll(
					getClass().getResource(MENUANDBUTTONSTYLES).toExternalForm());
			Controller_Page_Accueil.isDark = false;
		}

	}

	// M�thode qui regarde si le darkMode est actif et l'applique en cons�quence �
	// la scene
	public void darkModeActivation(Scene scene) {
		if (Controller_Page_Accueil.isDark) {
			scene.getStylesheets().removeAll(
					getClass().getResource(MENUANDBUTTONSTYLES).toExternalForm());
			scene.getStylesheets()
					.addAll(getClass().getResource(DARKMODETEST).toExternalForm());
			dark.setSelected(true);
		} else {
			scene.getStylesheets().removeAll(
					getClass().getResource(DARKMODETEST).toExternalForm());
			scene.getStylesheets().addAll(
					getClass().getResource(MENUANDBUTTONSTYLES).toExternalForm());
			dark.setSelected(false);
		}
	}

}
