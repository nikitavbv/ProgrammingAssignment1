package com.nikitavbv.labs.introtoprogramming.lab1;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Student {
  String name;
  List<Integer> subjectsMarks;
  private boolean isContract;

  Student(String name, List<Integer> marks, boolean isContract) {
    this.name = name;
    subjectsMarks = marks;
    this.isContract = isContract;
  }

  static Student fromCsv(String line) {
    String[] info = line.split(",");
    String name = info[0];
    List<Integer> marks = Arrays.stream(Arrays.copyOfRange(info, 1, info.length - 1))
            .map(Integer::parseInt)
            .collect(Collectors.toList());
    boolean isContract = info[info.length - 1].equals("TRUE");
    return new Student(name, marks, isContract);
  }

  public double getAverageScore() {
    return subjectsMarks.stream()
            .collect(Collectors.averagingDouble(el -> el));
  }

  public boolean isContract() {
    return isContract;
  }

  public String toCSV() {
    return String.format(Locale.ENGLISH, "%s,%.3f", name, getAverageScore());
  }

  public String toString() {
    return String.format(Locale.ENGLISH, "%-20s %.3f", name, getAverageScore());
  }
}
