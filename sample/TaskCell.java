package sample;

import javafx.scene.control.ListCell;

public class TaskCell extends ListCell<Task> {

    public void updateItem(Task item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
            if (item.getPriority().equals("High")) {
                setStyle("-fx-control-inner-background:#ff5544;");
            } else if (item.getPriority().equals("Low")) {
                setStyle("-fx-control-inner-background:#25ff64;");
            } else if (item.getPriority().equals("Average")) {
                setStyle("-fx-control-inner-background:#fffaf1;");
            }
            setText(item.getTitle());

        } else {
            setText(null);
            setGraphic(null);
            setStyle(null);
        }
    }

}
