package sample;

import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskList implements Serializable {
    private ArrayList<Task> toDoArrList = new ArrayList<>();
    private ArrayList<Task> inProgressArrList = new ArrayList<>();
    private ArrayList<Task> doneArrList = new ArrayList<>();

    public TaskList(ObservableList<Task> toDoList, ObservableList<Task> inProgressList, ObservableList<Task> doneList) {
        // for (int i = 0; i < toDoList.size(); i++) {
        // this.toDoArrList.add(toDoList.get(i));
        // }
        toDoArrList.addAll(toDoList);
        inProgressArrList.addAll(inProgressList);
        doneArrList.addAll(doneList);
    }

    public ArrayList<Task> getToDoArrList() {
        return toDoArrList;
    }

    public ArrayList<Task> getInProgressArrList() {
        return inProgressArrList;
    }

    public ArrayList<Task> getDoneArrList() {
        return doneArrList;
    }


}
