package com.nikitavbv.labs.introtoprogramming.lab1;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

import java.util.Arrays;
import java.util.Collections;

public class StudentTests {

  @Test
  public void testStudentToCSV() {
    Student student = new Student("John Doe", Arrays.asList(70, 80, 90, 60, 65), false);
    assertEquals("John Doe,73.000", student.toCSV());
  }

  @Test
  public void testStudentWithNoSubjectsToCSV() {
    Student student = new Student("John Doe", Collections.emptyList(), false);
    assertEquals("John Doe,0.000", student.toCSV());
  }

}
