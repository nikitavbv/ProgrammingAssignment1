package com.nikitavbv.labs.introtoprogramming.lab1;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;

public class MainTests {

  private static final List<Student> DUMMY_STUDENTS = Arrays.asList(
          new Student("John Doe", Arrays.asList(70, 80, 90, 60, 65), false),
          new Student("Sandra Reeves", Arrays.asList(72, 80, 91, 65, 91), true),
          new Student("Ricky Edwards", Arrays.asList(64, 71, 87, 92, 63), false),
          new Student("Lucas Sanders", Arrays.asList(62, 78, 77, 92, 100), true),
          new Student("Edgar Turner", Arrays.asList(74, 81, 97, 62, 63), false),
          new Student("Emily Thomas", Arrays.asList(70, 62, 98, 77, 84), false)
  );

  private static final String DUMMY_CSV_INPUT = "2\n" +
          "Ivanov,78,61,95,87,90,FALSE\n" +
          "Petrov,85,66,70,99,100,TRUE";

  @Test
  public void testSelectNonContractStudents() {
    List<Student> selectedStudents = Main.selectTopNonContractStudents(DUMMY_STUDENTS, 0.5f);
    Set<String> studentNames = selectedStudents
            .stream()
            .map(s -> s.name)
            .collect(Collectors.toSet());

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
  public void testLoadStudentList() {
    List<Student> studentList = Main.loadStudentList(
            new ByteArrayInputStream(DUMMY_CSV_INPUT.getBytes())
    );
    assertEquals(2, studentList.size());
    assertEquals("Ivanov", studentList.get(0).name);
    assertEquals(82.2, studentList.get(0).getAverageScore());
    assertFalse(studentList.get(0).isContract());
    assertEquals("Petrov", studentList.get(1).name);
    assertEquals(84.0, studentList.get(1).getAverageScore());
    assertTrue(studentList.get(1).isContract());
  }
}
