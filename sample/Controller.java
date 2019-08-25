package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button addButton;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private GridPane gridPane;
    @FXML
    private MenuItem close;
    @FXML
    private ListView<Task> ToDoList;
    @FXML
    private ListView<Task> progressList;
    @FXML
    private ListView<Task> doneList;
    public static ObservableList<Task> observToDoList = FXCollections.observableArrayList();
    public static ObservableList<Task> observProgressList = FXCollections.observableArrayList();
    public static ObservableList<Task> observDoneList = FXCollections.observableArrayList();
    public static int index;
    public static int listID;
    public static boolean isEdited;
    Alert alert = new Alert(AlertType.INFORMATION);


    @FXML
    public void aboutAuthorPressed(Event event) {
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Something about author");

        alert.showAndWait();
    }

    @FXML
    public void aboutKanbanPressed(Event event) {
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Colors: \n Red - high priority\n White - average priority\n Green - low priority");

        alert.showAndWait();
    }

    public void runWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample2.fxml"));
        try {
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root, 400.0, 400.0);
            stage.setScene(scene);
            stage.setTitle("Add new task");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void addPressed(Event event) {

        runWindow();

    }

    @FXML
    public void closePressed(Event event) {
        System.exit(0);
    }

    @FXML
    public void savePressed(Event event) {
        TaskList t = new TaskList(observToDoList, observProgressList, observDoneList);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new FileChooser.ExtensionFilter("Csv Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showSaveDialog(anchorPane.getScene().getWindow());

        if (file != null) {
            FileOperation.serialize(file.toString(), t);
        }
    }

    @FXML
    public void openPressed(Event event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new FileChooser.ExtensionFilter("Csv Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showOpenDialog(anchorPane.getScene().getWindow());
        if (file != null) {
            FileOperation.deserialize(file.toString());
        }

    }

    @FXML
    public void exportPressed(Event event) {
        TaskList listsToExport = new TaskList(observToDoList, observProgressList, observDoneList);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Csv Files", "*.csv"));
        File file = fileChooser.showSaveDialog(anchorPane.getScene().getWindow());
        if (file != null) {
            FileOperation.exportToCSV(file.toString(), listsToExport);
        }
    }

    @FXML
    public void importPressed(Event event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Csv Files", "*.csv"));
        File file = fileChooser.showOpenDialog(anchorPane.getScene().getWindow());
        if (file != null) {
            FileOperation.importFromCSV(file.toString());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listInit(ToDoList, observToDoList, 1);
        listInit(progressList, observProgressList, 2);
        listInit(doneList, observDoneList, 3);
    }

    public void listInit(ListView<Task> listView, ObservableList<Task> observableList, int id) {
        listView.setItems(observableList);
        listView.setCellFactory((Callback<ListView<Task>, ListCell<Task>>) list -> {

            ListCell<Task> cell = new TaskCell();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem editItem = new MenuItem("edit");
            editItem.setOnAction(event -> {
                Task taskToEdit = cell.getItem();
                index = cell.getIndex();
                isEdited = true;
                listID = id;
                runWindow();
                isEdited = false;
            });

            MenuItem deleteItem = new MenuItem("delete");
            deleteItem.setOnAction(event -> {
                Task thisTask = cell.getItem();
                listView.getItems().remove(thisTask);
                // ArrayList<Task> arrToDoList = new ArrayList<Task>(listView.getItems());
                cell.setStyle("-fx-control-inner-background:#fcfffa;");
            });

            MenuItem moveToInProgress = new MenuItem("move to IN PROGRESS");
            moveToInProgress.setOnAction(event -> {
                Task thisTask = cell.getItem();
                observProgressList.add(thisTask);
                listView.getItems().remove(thisTask);
                cell.setStyle("-fx-control-inner-background:#fcfffa;");
            });

            MenuItem moveToDone = new MenuItem("move to DONE");
            moveToDone.setOnAction(event -> {
                Task thisTask = cell.getItem();
                observDoneList.add(thisTask);
                listView.getItems().remove(thisTask);
                cell.setStyle("-fx-control-inner-background:#fcfffa;");
            });

            MenuItem moveToToDo = new MenuItem("move to TO DO");
            moveToToDo.setOnAction(event -> {
                Task thisTask = cell.getItem();
                observToDoList.add(thisTask);
                listView.getItems().remove(thisTask);
                cell.setStyle("-fx-control-inner-background:#fcfffa;");
            });

            switch (id) {
                case 1:
                    contextMenu.getItems().addAll(editItem, deleteItem, moveToInProgress, moveToDone);
                    break;
                case 2:
                    contextMenu.getItems().addAll(editItem, deleteItem, moveToToDo, moveToDone);
                    break;
                case 3:
                    contextMenu.getItems().addAll(editItem, deleteItem, moveToToDo, moveToInProgress);
                    break;
            }
            cell.setOnMouseEntered(event -> {
                if (cell.getIndex() < observableList.size()) {
                    final Tooltip tooltip = new Tooltip();
                    tooltip.setText("Priority: " + observableList.get(cell.getIndex()).getPriority() +
                            "\n Details: " + observableList.get(cell.getIndex()).getDescription());
                    cell.setTooltip(tooltip);
                }
            });
            cell.setOnMouseClicked(mouseEvent -> {
                if (cell.getItem() != null) {
                    cell.setContextMenu(contextMenu);
                }
            });
            return cell;
        });
    }
}
