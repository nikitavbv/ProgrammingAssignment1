package com.nikitavbv.labs.introtoprogramming.lab1;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class StudentTests {

  private static final List<Student> DUMMY_STUDENTS = Arrays.asList(
          new Student("John Doe", Arrays.asList(70, 80, 90, 60, 65), false),
          new Student("John Doe", Collections.emptyList(), true),
          new Student("", Arrays.asList(10, 10, 10, 10, 10), false),
          new Student("Hans Christian Andersen", Collections.singletonList(10), true)
  );

  private static final List<String> DUMMY_STUDENT_CSV = Arrays.asList(
          "John Doe,70,80,90,60,65,FALSE",
          ",70,80,90,60,65,FALSE",
          "John Doe,70,80,90,60,65,70,80,90,60,65,FALSE",
          "John Doe,FALSE",
          "John Doe,70,80,90,60,65,true",
          "John Doe,70,80,90,60,65,",
          "John Doe,70,80,90,60,65,+",
          "John Doe,70,80,90,60,65,-",
          "John Doe,70,80,90,60,65"
  );

  @Test
  public void testStudentToCsv() {
    assertEquals("John Doe,73.000", DUMMY_STUDENTS.get(0).toCsv());
  }

  @Test
  public void testStudentWithNoSubjectsToCsv() {
    assertEquals("John Doe,0.000", DUMMY_STUDENTS.get(1).toCsv());
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
    assertEquals("                     10.000", DUMMY_STUDENTS.get(2).toString());
  }

  @Test
  public void testStudentToStringWithLongName() {
    assertEquals("Hans Christian Andersen 10.000", DUMMY_STUDENTS.get(3).toString());
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

  @Test
  public void testParseStudent() {
    Student student = Student.fromCsv(DUMMY_STUDENT_CSV.get(0));
    assertEquals("John Doe", student.name);
    assertEquals(5, student.subjectsMarks.size());
    assertFalse(student.isContract());
  }

  @Test
  public void testParseStudentWithNoName() {
    Student student = Student.fromCsv(DUMMY_STUDENT_CSV.get(1));
    assertEquals("", student.name);
  }

  @Test
  public void testParseStudentWithLotsOfMarks() {
    Student student = Student.fromCsv(DUMMY_STUDENT_CSV.get(2));
    assertEquals(10, student.subjectsMarks.size());
  }

  @Test
  public void testParseStudentWithNoMarks() {
    Student student = Student.fromCsv(DUMMY_STUDENT_CSV.get(3));
    assertEquals(0, student.subjectsMarks.size());
  }

  @Test
  public void testParseStudentWithIncorrectFormatOfContract() {
    Student student = Student.fromCsv(DUMMY_STUDENT_CSV.get(4));
    assertFalse(student.isContract());
  }

  @Test
  public void testParseStudentWithEmptyContract() {
    Student student = Student.fromCsv(DUMMY_STUDENT_CSV.get(5));
    assertFalse(student.isContract());
  }

  @Test
  public void testParseStudentWithContractPlusSign() {
    Student student = Student.fromCsv(DUMMY_STUDENT_CSV.get(6));
    assertTrue(student.isContract());
    assertEquals(73.0, student.getAverageScore());
  }

  @Test
  public void testParseStudentContractMinusSign() {
    Student student = Student.fromCsv(DUMMY_STUDENT_CSV.get(7));
    assertFalse(student.isContract());
    assertEquals(73.0, student.getAverageScore());
  }

  @Test
  public void testParseStudentNoContractField() {
    Student student = Student.fromCsv(DUMMY_STUDENT_CSV.get(8));
    assertFalse(student.isContract());
    assertEquals(73.0, student.getAverageScore());
  }
}
