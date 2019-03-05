package com.nikitavbv.labs.introtoprogramming.lab1;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StudentTests {

  private static final List<Student> DUMMY_STUDENTS = Arrays.asList(
          new Student("John Doe", Arrays.asList(70, 80, 90, 60, 65), false),
          new Student("John Doe", Collections.emptyList(), true),
          new Student("", Arrays.asList(10, 10, 10, 10, 10), false),
          new Student("Hans Christian Andersen", Collections.singletonList(10), true)
  );

  @Test
  public void testStudentToCSV() {
    assertEquals("John Doe,73.000", DUMMY_STUDENTS.get(0).toCSV());
  }

  @Test
  public void testStudentWithNoSubjectsToCSV() {
    assertEquals("John Doe,0.000", DUMMY_STUDENTS.get(1).toCSV());
  }

  @Test
  public void testStudentToString() {
    assertEquals("John Doe             73.000", DUMMY_STUDENTS.get(0).toString());
  }

  @Test
  public void testStudentToStringWithNoMarks() {
    assertEquals("John Doe             0.000", DUMMY_STUDENTS.get(1).toString());
  }

  @Test
  public void testStudentToStringWithEmptyName() {
    assertEquals("                     73.000", DUMMY_STUDENTS.get(2).toString());
  }

  @Test
  public void testStudentToStringWithLongName() {
    assertEquals("Hans Christian Andersen 73.000", DUMMY_STUDENTS.get(3).toString());
  }

  @Test
  public void testStudentGetAverageScore() {
    assertEquals(73.0, DUMMY_STUDENTS.get(0).getAverageScore());
  }

  @Test
  public void testStudentGetAverageScoreWithNoMarks() {
    assertEquals(0.0, DUMMY_STUDENTS.get(1).getAverageScore());
  }

  @Test
  public void testStudentGetAverageScoreWithSameMarks() {
    assertEquals(10.0, DUMMY_STUDENTS.get(2).getAverageScore());
  }

  @Test
  public void testStudentGetAverageScoreWithOneMarks() {
    assertEquals(10.0, DUMMY_STUDENTS.get(3).getAverageScore());
  }
}
