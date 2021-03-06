package taskmanager;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    public static String[][] downloadData() {

        String[] tasksRowsTemp;
        String[] tasksColumnsTemp;
        String[] tasksTemp;

        File newFile = new File("tasks.csv");
        StringBuilder readNewFile = new StringBuilder();

        try {
            Scanner scannerNewFile = new Scanner(newFile);
            while (scannerNewFile.hasNextLine()) {
                readNewFile.append(scannerNewFile.nextLine()).append(",\n");

            }
        } catch (FileNotFoundException e) {
            System.out.println("Błąd" + e.getMessage());
        }

        String arraysString = readNewFile.toString();
        System.out.println("");

        tasksTemp = arraysString.split(",");

        tasksRowsTemp = arraysString.split("\n");

        int columnsNumber = tasksRowsTemp.length;

        tasksColumnsTemp = tasksRowsTemp[1].split(",");

        int rowsNumber = tasksColumnsTemp.length;

        String[][] tasks = new String[columnsNumber][rowsNumber];

        int z = 0;

        for (int i = 0; i < tasks.length; i++) {
            for (int j = 0; j < tasks[i].length; j++) {
                tasks[i][j] = tasksTemp[z].replaceAll("\n", "");
                z++;
            }
        }

        return tasks;
    }

    public static void showOption(String[][] taskTable) {

        System.out.println(ConsoleColors.BLUE_BRIGHT + "Please select an option:");

        String[] options = {"add", "remove", "list", "exit"};

        for (String option : options) {
            System.out.println(ConsoleColors.WHITE_BRIGHT + option);
        }

        Scanner inputScanner = new Scanner(System.in);

        String input = inputScanner.nextLine();


        switch (input) {
            case "add" -> addtask(taskTable);
            case "list" -> listTask(taskTable);
            case "remove" -> removeTask(taskTable);
            case "exit" -> {
                saveToFile(taskTable);
                System.out.println(ConsoleColors.RED_BRIGHT + "bye bye");
            }
            default -> {
                System.out.println("Please select a correct option");
                showOption(taskTable);
            }
        }

    }

    public static void addtask(String[][] tasks) {

        System.out.println("Please add task description ");
        Scanner scannerNewTask = new Scanner(System.in);
        String newTask = scannerNewTask.nextLine();

        System.out.println("Please add task due date ");
        Scanner scannerNewTaskDate = new Scanner(System.in);
        String newTaskDate = scannerNewTaskDate.nextLine();

        System.out.println("Is your task is important: true/false ");
        Scanner scannerNewTaskImportant = new Scanner(System.in);
        String newTaskImportant = scannerNewTaskImportant.nextLine();

        String[][] TaskTemp = new String[tasks.length + 1][tasks[1].length];

        for (int i = 0; i < tasks.length; i++) {
            System.arraycopy(tasks[i], 0, TaskTemp[i], 0, tasks[i].length);
        }

        TaskTemp[TaskTemp.length - 1][0] = newTask;
        TaskTemp[TaskTemp.length - 1][1] = newTaskDate;
        TaskTemp[TaskTemp.length - 1][2] = newTaskImportant;

        tasks = Arrays.copyOf(TaskTemp, TaskTemp.length);

        showOption(tasks);

    }

    public static void listTask(String[][] tasks) {
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + ": ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j] + " ");
            }
            System.out.println();

        }
        showOption(tasks);
    }

    public static void removeTask(String[][] tasks) {
        int numberTask;
        System.out.println("Please select number to remove: ");
        Scanner removeScanner = new Scanner(System.in);
        numberTask = removeScanner.nextInt();

        if (numberTask < 0) {
            System.out.println(ConsoleColors.RED_BRIGHT + "Incorrect argument passed. Please give number greater or equal 0");
            showOption(tasks);
        }

        try {
            tasks = (String[][]) ArrayUtils.remove(tasks, numberTask);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(ConsoleColors.RED_BRIGHT + "Incorrect argument passed");
        }

        showOption(tasks);
    }

    public static void saveToFile(String[][] tasks) {

        try (PrintWriter printWriter = new PrintWriter("tasks.csv")) {
            for (int i = 0; i < tasks.length; i++) {
                printWriter.println(String.join(",",tasks[i]));
            }
        } catch (Exception e) {
            System.out.println(ConsoleColors.RED + "Błąd w zapisie do pliku");
        }
    }


    public static void main(String[] args) throws IOException {


        String[][] taskTable = downloadData();
        showOption(taskTable);


    }
}

