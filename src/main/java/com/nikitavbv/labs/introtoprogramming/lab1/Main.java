package com.nikitavbv.labs.introtoprogramming.lab1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Determine students eligible for scholarship based on subject scores.
 * Intro to programming - assignment 1
 *
 * @author Nikita Volobuev
 * @author Bohdan Fedorchenko
 */
public class Main {

  private static final String INPUT_FILE_NAME = "students.csv";
  private static final String OUTPUT_FILE_NAME = "rating.csv";

  private static final double SCHOLARSHIP_PERCENTAGE = 0.4f;

  /**
   * This starts application.
   * Input file is read and parsed. Then students eligible for scholarship are determined
   * and saved to the output file. Also, min score for scholarship and student rating are
   * print to System.out.
   **/
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

  /**
   * Load student list from file.
   *
   * @param file file with student data (csv)
   * @return list of students
   * @throws IOException if file read failed
   */
  private static List<Student> loadStudentList(File file) throws IOException {
    return loadStudentList(new FileInputStream(file));
  }

  /**
   * Load student list from csv data input stream
   *
   * @param in input stream with student data as csv
   * @return list of students
   */
  static List<Student> loadStudentList(InputStream in) {
    return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))
            .lines()
            .skip(1)
            .map(Student::fromCsv)
            .collect(Collectors.toList());
  }

  /**
   * Save student list to file (as csv).
   *
   * @param students student list to save
   * @param file file to save to
   * @throws IOException if file write failed
   */
  private static void saveStudentsList(List<Student> students, File file) throws IOException {
    PrintWriter pw = new PrintWriter(new OutputStreamWriter(
            new FileOutputStream(file), StandardCharsets.UTF_8));
    students.stream().map(Student::toCsv).forEach(pw::println);
    pw.close();
  }

  /**
   * Select top non-contract students from the list.
   *
   * @param students all students
   * @param topPercentage percentage of students to select (relative to the number of non-contract
   *                      students). Should be in bounds of 0...1
   * @return result student list
   */
  static List<Student> selectTopNonContractStudents(List<Student> students, double topPercentage) {
    if (topPercentage < 0 || topPercentage > 1) {
      throw new IllegalArgumentException(
              "Percentage of students to select should be in bounds of 0...1"
      );
    }

    List<Student> rating = students.stream()
            .filter(s -> !s.isContract())
            .sorted(Comparator.comparingDouble(s -> -s.getAverageScore()))
            .collect(Collectors.toList());
    return rating.subList(0, (int) Math.floor(rating.size() * topPercentage));
  }
}
