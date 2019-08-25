package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

public class Controller2 implements Initializable {


    @FXML
    private ComboBox<String> priority;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea details;
    @FXML
    private Button addButton;
    @FXML
    private AnchorPane ap;
    boolean edit;
    int id;

    ObservableList<String> options = FXCollections.observableArrayList("Low", "Average", "High");

    public void setObservableList(ObservableList<Task> list, Task taskToAdd) {
        list.add(taskToAdd);
    }


    @FXML
    public void addTask(Event event) {
        Task taskToAdd = new Task(titleField.getText(), details.getText(), priority.getSelectionModel().getSelectedItem().toString(), datePicker.getValue());

        if (!edit) {
            setObservableList(Controller.observToDoList, taskToAdd);
        } else {
            switch (id) {
                case 1:
                    Controller.observToDoList.set(Controller.index, taskToAdd);
                    break;
                case 2:
                    Controller.observProgressList.set(Controller.index, taskToAdd);
                    break;
                case 3:
                    Controller.observDoneList.set(Controller.index, taskToAdd);
                    break;
            }
        }

        ((Stage) ap.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        priority.setItems(options);
        edit = Controller.isEdited;
        id = Controller.listID;

        if (edit) {

            switch (id) {
                case 1:
                    titleField.setText(Controller.observToDoList.get(Controller.index).getTitle());
                    details.setText(Controller.observToDoList.get(Controller.index).getDescription());
                    datePicker.setValue(Controller.observToDoList.get(Controller.index).getDate());
                    priority.setValue(Controller.observToDoList.get(Controller.index).getPriority());
                    break;
                case 2:
                    titleField.setText(Controller.observProgressList.get(Controller.index).getTitle());
                    details.setText(Controller.observProgressList.get(Controller.index).getDescription());
                    datePicker.setValue(Controller.observProgressList.get(Controller.index).getDate());
                    priority.setValue(Controller.observProgressList.get(Controller.index).getPriority());
                    break;
                case 3:
                    titleField.setText(Controller.observDoneList.get(Controller.index).getTitle());
                    details.setText(Controller.observDoneList.get(Controller.index).getDescription());
                    datePicker.setValue(Controller.observDoneList.get(Controller.index).getDate());
                    priority.setValue(Controller.observDoneList.get(Controller.index).getPriority());
            }

        }
    }


}
