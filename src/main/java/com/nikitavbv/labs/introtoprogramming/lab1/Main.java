package com.nikitavbv.labs.introtoprogramming.lab1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

  private static final String INPUT_FILE_NAME = "students.csv";

  public static void main(String[] args) {
    try {
      List<Student> students = loadStudentList(new File(INPUT_FILE_NAME));
    } catch (IOException e) {
      System.err.println("Failed to read file");
      e.printStackTrace();
    }
  }

  public static List<Student> loadStudentList(File file) throws IOException {
    return new BufferedReader(new FileReader(file))
            .lines()
            .skip(1)
            .map(Main::parseStudent)
            .collect(Collectors.toList());
  }

  private static Student parseStudent(String line) {
    String[] info = line.split(",");
    String name = info[0];
    List<Integer> marks = Arrays.stream(Arrays.copyOfRange(info, 1, info.length - 2))
            .map(Integer::parseInt)
            .collect(Collectors.toList());
    boolean isContract = info[info.length - 1].equals("TRUE");
    return new Student(name, marks, isContract);
  }
}
