package com.nikitavbv.labs.introtoprogramming.lab1;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MainTests {

  private static final List<Student> DUMMY_STUDENTS = Arrays.asList(
          new Student("John Doe", Arrays.asList(70, 80, 90, 60, 65), false),
          new Student("Sandra Reeves", Arrays.asList(72, 80, 91, 65, 91), true),
          new Student("Ricky Edwards", Arrays.asList(64, 71, 87, 92, 63), false),
          new Student("Lucas Sanders", Arrays.asList(62, 78, 77, 92, 100), true),
          new Student("Edgar Turner", Arrays.asList(74, 81, 97, 62, 63), false),
          new Student("Emily Thomas", Arrays.asList(70, 62, 98, 77, 84), false)
  );

  private static final List<String> DUMMY_STUDENT_INFO = Arrays.asList(
          "John Doe,70,80,90,60,65,FALSE",
          ",70,80,90,60,65,FALSE",
          "John Doe,70,80,90,60,65,70,80,90,60,65,FALSE",
          "John Doe,FALSE",
          "John Doe,70,80,90,60,65,true",
          "John Doe,70,80,90,60,65,"
  );

  @Test
  public void testSelectNonContractStudents() {
    List<Student> selectedStudents = Main.selectTopNonContractStudents(DUMMY_STUDENTS, 0.5f);
    Set<String> studentNames = selectedStudents.stream().map(s -> s.name).collect(Collectors.toSet());

    selectedStudents.forEach(student -> assertFalse(student.isContract()));
    assertEquals(2, studentNames.size());
    assertTrue(studentNames.contains("Ricky Edwards"));
    assertTrue(studentNames.contains("Emily Thomas"));
  }

  @Test
  public void testSelectNoStudents() {
    List<Student> selectedStudents = Main.selectTopNonContractStudents(DUMMY_STUDENTS, 0f);
    assertEquals(0, selectedStudents.size());
  }

  @Test
  public void testSelectAllStudents() {
    List<Student> selectedStudents = Main.selectTopNonContractStudents(DUMMY_STUDENTS, 1f);
    assertEquals(4, selectedStudents.size());
  }

  @Test
  public void testSelectOnContractStudents() {
    List<Student> selectedStudents = Main.selectTopNonContractStudents(
            DUMMY_STUDENTS.stream().filter(Student::isContract).collect(Collectors.toList()),
            0.5f
    );
    assertEquals(0, selectedStudents.size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSelectNegativePercentage() {
    Main.selectTopNonContractStudents(DUMMY_STUDENTS, -0.5f);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSelectMoreThan100Percents() {
    Main.selectTopNonContractStudents(DUMMY_STUDENTS, 1.05f);
  }

  @Test
  public void testParseStudent() {
    Student student = Main.parseStudent(DUMMY_STUDENT_INFO.get(0));
    assertEquals("John Doe", student.name);
    assertEquals(5, student.subjectsMarks.size());
    assertFalse(student.isContract());
  }

  @Test
  public void testParseStudentWithNoName() {
    Student student = Main.parseStudent(DUMMY_STUDENT_INFO.get(1));
    assertEquals("", student.name);
  }

  @Test
  public void testParseStudentWithLotsOfMarks() {
    Student student = Main.parseStudent(DUMMY_STUDENT_INFO.get(2));
    assertEquals(10, student.subjectsMarks.size());
  }

  @Test
  public void testParseStudentWithNoMarks() {
    Student student = Main.parseStudent(DUMMY_STUDENT_INFO.get(3));
    assertEquals(0, student.subjectsMarks.size());
  }

  @Test
  public void testParseStudentWithIncorrectFormatOfContract() {
    Student student = Main.parseStudent(DUMMY_STUDENT_INFO.get(4));
    assertFalse(student.isContract());
  }

  @Test
  public void testParseStudentWithEmptyContract() {
    Student student = Main.parseStudent(DUMMY_STUDENT_INFO.get(5));
    assertFalse(student.isContract());
  }
}
