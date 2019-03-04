package com.nikitavbv.labs.introtoprogramming.lab1;

import java.util.List;
import java.util.OptionalDouble;

public class Student {
  private String name;
  private List<Integer> subjectsMarks;
  private boolean isContract;

  Student(String name, List<Integer> marks, boolean isContract) {
    this.name = name;
    subjectsMarks = marks;
    this.isContract = isContract;
  }

  public OptionalDouble getAverageScore() {
    return subjectsMarks.stream()
            .mapToDouble(a -> a)
            .average();
  }
}
