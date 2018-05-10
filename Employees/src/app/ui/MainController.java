package app.ui;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;

import app.entities.Employees;
import app.entities.Result;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController implements Initializable{
	
	@FXML
	private Button btn;
	
	@FXML
	private TableView<Result> table;
	
	@FXML
	private TableColumn<Result, Integer> emplIdA;
	
	@FXML
	private TableColumn<Result, Integer> emplIdB;
	
	@FXML
	private TableColumn<Result, List<Integer>> projectId;
	
	@FXML
	private TableColumn<Result, Long> days;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		emplIdA.setCellValueFactory(new PropertyValueFactory<Result, Integer>("emplIdA"));
		emplIdB.setCellValueFactory(new PropertyValueFactory<Result, Integer>("emplIdB"));
		projectId.setCellValueFactory(new PropertyValueFactory<Result, List<Integer>>("projectsTogether"));
		days.setCellValueFactory(new PropertyValueFactory<Result, Long>("timeTogether"));
	}
	
	public void button1Action(ActionEvent event) throws ParseException {
		FileChooser fc = new FileChooser();
//		fc.setInitialDirectory(new File("C:\\Users\\cgg\\git\\Employees\\Employees"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
		File selectedFile = fc.showOpenDialog(null);
		
		if (selectedFile != null) {
			Employees empl = new Employees(selectedFile);
			
			ObservableList<Result> obsList = FXCollections.observableArrayList(empl.getData());
			table.setItems(obsList);
		} else {
			System.out.println("The file is not valid");
		}
		
	}
	
}
