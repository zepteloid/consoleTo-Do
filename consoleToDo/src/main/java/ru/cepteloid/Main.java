package ru.cepteloid;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean outOfProgram = true;

        List<Task> taskList = new ArrayList<>();


        while (outOfProgram) {
            System.out.println("1 - Создать новую задачу");
            System.out.println("2 - Просмотреть список всех задач");
            System.out.println("3 - Показать календарь");
            System.out.println("4 - Удалить все записи");
            System.out.println("5 - Выйти из программы");
            System.out.println("Что вы хотите сделать: ");

            String input = scanner.nextLine(); // Читаем весь ввод как строку
            int chooseProgram = Integer.parseInt(input); // Преобразуем в число

            switch (chooseProgram) {
                case 1:
                    Task.loadLastId();
                    Task task = new Task();
                    System.out.print("Введите название задачи: ");
                    task.setName(scanner.nextLine());
                    System.out.print("Введите описание задачи: ");
                    task.setDescription(scanner.nextLine());
                    taskList.add(task);
                    System.out.println("Задача добавлена: " + task.getName());
                    try (FileWriter writer = new FileWriter("arrayTask.txt", true)) {
                        writer.write(task.toString());
                        writer.append('\n');
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    task.saveLastId();
                    break;
                case 2:
                    try {
                        File file = new File("arrayTask.txt");
                        FileReader fr = new FileReader(file);
                        //создаем BufferedReader с существующего FileReader для построчного считывания
                        BufferedReader reader = new BufferedReader(fr);
                        // считаем сначала первую строку
                        String line = reader.readLine();
                        while (line != null) {
                            System.out.println(line);
                            // считываем остальные строки в цикле
                            line = reader.readLine();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        // Создаём процесс для выполнения команды "cal"
                        ProcessBuilder processBuilder = new ProcessBuilder("cal", "-m");
                        processBuilder.redirectErrorStream(true); // Перенаправляем ошибки в стандартный вывод
                        // Запускаем процесс
                        Process process = processBuilder.start();
                        // Читаем вывод команды
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                        }
                        // Ожидаем завершения процесса
                        int exitCode = process.waitFor();
                        System.out.println("Команда завершена с кодом: " + exitCode);

                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    try {
                        Path pathToArrayTask = Paths.get("arrayTask.txt");
                        Path pathToLastID = Paths.get("lastId.txt");

                        Files.writeString(pathToArrayTask, "");
                        Files.writeString(pathToLastID, "");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 5:
                    outOfProgram = false;
                    System.out.println("Выход из программы.");
                    break;
                default:
                    System.out.println("Некорректный выбор. Попробуйте еще раз");
            }

        }
    }
}

