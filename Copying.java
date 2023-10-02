package com.company;

import java.io.*;

public class Copying {
    public static void main(String[] args) {
        // Задайте пути и имена файлов для копирования
        String File1 = "file1";
        String File2 = "file2";
        String CopyLocation = "src";

        // Последовательное копирование файлов
        long startTimeSequential = System.currentTimeMillis();
        copyFileSeq(File1, CopyLocation + File1);
        copyFileSeq(File2, CopyLocation + File2);
        long endTimeSequential = System.currentTimeMillis();
        long sequentialExecutionTime = endTimeSequential - startTimeSequential;
        System.out.println("Последовательное копирование завершено за " + sequentialExecutionTime + " мс");

        // Параллельное копирование файлов
        long startTimeParallel = System.currentTimeMillis();
        Thread thread1 = new Thread(() -> copyFileSeq(File1, CopyLocation + File1));
        Thread thread2 = new Thread(() -> copyFileSeq(File2, CopyLocation + File2));
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTimeParallel = System.currentTimeMillis();
        long parallelExecutionTime = endTimeParallel - startTimeParallel;
        System.out.println("Параллельное копирование завершено за " + parallelExecutionTime + " мс");
    }

    private static void copyFileSeq(String sourcePath, String destinationPath) {
        try (FileInputStream fis = new FileInputStream(sourcePath);
             FileOutputStream fos = new FileOutputStream(destinationPath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
