package ru.cepteloid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class Task {
    private String uuid;
    private int id;
    private boolean executionStatus = false;
    private String description;
    private String name;
    private static int nextId = 1;

    public Task() {
        this.uuid = UUID.randomUUID().toString();
        this.id = nextId++;
    }

    @Override
    public String toString() {
        return "Task{" +
                "uuid='" + uuid + '\'' +
                ", id=" + id +
                ", executionStatus=" + executionStatus +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void loadLastId() {
        File file = new File("lastId.txt");
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                if (scanner.hasNextInt()) {
                    nextId = scanner.nextInt(); // Читаем последний ID из файла
                }
            } catch (FileNotFoundException e) {
                System.out.println("Файл last_id.txt не найден. Начинаем с ID = 1.");
                nextId = 1;
            }
        } else {
            nextId = 1; // Если файла нет, начинаем с 1
        }
    }

    public void saveLastId() {
        try (FileWriter writer = new FileWriter("lastId.txt")) {
            writer.write(String.valueOf(nextId));
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении last_id.txt: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
