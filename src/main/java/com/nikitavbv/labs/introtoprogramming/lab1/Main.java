package com.nikitavbv.labs.introtoprogramming.lab1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

  private static final String INPUT_FILE_NAME = "students.csv";
  private static final String OUTPUT_FILE_NAME = "rating.csv";

  private static final double SCHOLARSHIP_PERCENTAGE = 0.4f;

  public static void main(String[] args) {
    try {
      List<Student> students = loadStudentList(new File(INPUT_FILE_NAME));
      List<Student> studentsWithScholarship =
              selectTopNonContractStudents(students, SCHOLARSHIP_PERCENTAGE);

      double minScoreForScholarship = studentsWithScholarship.stream()
              .mapToDouble(Student::getAverageScore)
              .min()
              .orElseThrow(() -> new RuntimeException("No students eligible for scholarship"));

      System.out.printf("Min score for scholarship: %f%n", minScoreForScholarship);
      System.out.printf("Students with scholarship (%d total):%n",
              studentsWithScholarship.size());
      studentsWithScholarship.forEach(System.out::println);

      saveStudentsList(studentsWithScholarship, new File(OUTPUT_FILE_NAME));
    } catch (IOException e) {
      System.err.println("Failed to read/write file");
      e.printStackTrace();
    }
  }

  private static List<Student> loadStudentList(File file) throws IOException {
    return new BufferedReader(new InputStreamReader(
            new FileInputStream(file), StandardCharsets.UTF_8))
            .lines()
            .skip(1)
            .map(Student::fromCsv)
            .collect(Collectors.toList());
  }

  private static void saveStudentsList(List<Student> students, File file) throws IOException {
    PrintWriter pw = new PrintWriter(new OutputStreamWriter(
            new FileOutputStream(file), StandardCharsets.UTF_8));
    students.stream().map(Student::toCSV).forEach(pw::println);
    pw.close();
  }

  static List<Student> selectTopNonContractStudents(List<Student> students, double topPercentage) {
    if (topPercentage < 0 || topPercentage > 1) {
      throw new IllegalArgumentException("Percentage of students to select should be in bounds of 0...1");
    }

    List<Student> rating = students.stream()
            .filter(s -> !s.isContract())
            .sorted(Comparator.comparingDouble(s -> -s.getAverageScore()))
            .collect(Collectors.toList());
    return rating.subList(0, (int) Math.floor(rating.size() * topPercentage));
  }
}
