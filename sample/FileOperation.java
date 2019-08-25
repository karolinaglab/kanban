package sample;

import java.io.*;
import java.time.LocalDate;

public class FileOperation {
    private static final String CSV_SEPARATOR = ",";

    public static void serialize(String fileName, TaskList t) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(t);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static void deserialize(String fileName) {
        TaskList t = null;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            t = (TaskList) in.readObject();
            in.close();
            fileIn.close();

            Controller.observToDoList.setAll(t.getToDoArrList());
            Controller.observProgressList.setAll(t.getInProgressArrList());
            Controller.observDoneList.setAll(t.getDoneArrList());

        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("TaskArray class not found");
            c.printStackTrace();
            return;
        }
    }

    public static void exportToCSV(String fileName, TaskList listsToExport) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
            String listName = new String();
            listName = "To do:";
            bw.write(listName);
            bw.newLine();
            for (Task task : listsToExport.getToDoArrList()) {
                String oneLine = task.getTitle() + "," + task.getPriority() + "," + task.getDate() + "," + task.getDescription();
                bw.write(oneLine);
                bw.newLine();
            }

            listName = "In progress:";
            bw.write(listName);
            bw.newLine();
            for (Task task : listsToExport.getInProgressArrList()) {
                String oneLine = task.getTitle() + "," + task.getPriority() + "," + task.getDate() + "," + task.getDescription();
                bw.write(oneLine);
                bw.newLine();
            }

            listName = "Done:";
            bw.write(listName);
            bw.newLine();
            for (Task task : listsToExport.getDoneArrList()) {
                String oneLine = task.getTitle() + "," + task.getPriority() + "," + task.getDate() + "," + task.getDescription();
                bw.write(oneLine);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (UnsupportedEncodingException e) {
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }


    public static void importFromCSV(String fileName) {
        // TaskList t = null;
        try {
            FileReader info = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(info);
            String title = new String();

            while (true) {
                String line = reader.readLine();
                //System.out.println(line);
                if (line == null) {
                    break;
                }
                if (line.equals("To do:")) {
                    title = "To do:";
                    line = reader.readLine();
                } else if (line.equals("In progress:")) {
                    title = "In progress:";
                    line = reader.readLine();
                } else if (line.equals("Done:")) {
                    title = "Done:";
                    line = reader.readLine();
                }

                String[] atributes = line.split(",");
                LocalDate date = LocalDate.parse(atributes[2]);
                if (title.equals("To do:")) {
                    Controller.observToDoList.add(new Task(atributes[0], atributes[3], atributes[1], date));
                } else if (title.equals("In progress:")) {
                    Controller.observProgressList.add(new Task(atributes[0], atributes[3], atributes[1], date));
                } else if (title.equals("Done:")) {
                    Controller.observDoneList.add(new Task(atributes[0], atributes[3], atributes[1], date));
                }
            }
            reader.close();

        } catch (IOException e) {
        }
    }

}
