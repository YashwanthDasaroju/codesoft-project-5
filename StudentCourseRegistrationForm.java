 import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

// Course class to store course details
class Course {
    String courseCode;
    String title;
    String description;
    int capacity;
    String schedule;
    int availableSlots;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.availableSlots = capacity; // Initially all slots are available
    }

    public void displayCourseInfo() {
        System.out.println(courseCode + " - " + title + ": " + description);
        System.out.println("Capacity: " + capacity + ", Available Slots: " + availableSlots + ", Schedule: " + schedule);
    }
}

// Student class to store student information
class Student {
    String studentID;
    String name;
    List<Course> registeredCourses = new ArrayList<>();

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
    }

    public void registerCourse(Course course) {
        if (course.availableSlots > 0) {
            registeredCourses.add(course);
            course.availableSlots--;
            System.out.println("Registered for course: " + course.title);
        } else {
            System.out.println("Course " + course.title + " is full!");
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.availableSlots++;
            System.out.println("Dropped course: " + course.title);
        } else {
            System.out.println("You are not registered for this course.");
        }
    }

    public void displayRegisteredCourses() {
        System.out.println("Courses registered by " + name + ":");
        for (Course c : registeredCourses) {
            System.out.println(c.title);
        }
    }
}

// Course Registration System to manage all operations
class CourseRegistrationSystem {
    HashMap<String, Course> courseDatabase = new HashMap<>();
    HashMap<String, Student> studentDatabase = new HashMap<>();

    // Add course to the system
    public void addCourse(Course course) {
        courseDatabase.put(course.courseCode, course);
    }

    // Add student to the system
    public void addStudent(Student student) {
        studentDatabase.put(student.studentID, student);
    }

    // Display available courses
    public void displayCourses() {
        System.out.println("Available Courses:");
        for (Course c : courseDatabase.values()) {
            c.displayCourseInfo();
        }
    }

    // Register student for a course
    public void registerStudentForCourse(String studentID, String courseCode) {
        Student student = studentDatabase.get(studentID);
        Course course = courseDatabase.get(courseCode);
        if (student != null && course != null) {
            student.registerCourse(course);
        } else {
            System.out.println("Invalid student ID or course code.");
        }
    }

    // Remove course for a student
    public void removeCourseForStudent(String studentID, String courseCode) {
        Student student = studentDatabase.get(studentID);
        Course course = courseDatabase.get(courseCode);
        if (student != null && course != null) {
            student.dropCourse(course);
        } else {
            System.out.println("Invalid student ID or course code.");
        }
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        CourseRegistrationSystem system = new CourseRegistrationSystem();

        // Adding sample courses
        Course course1 = new Course("C101", "Java Programming", "Intro to Java", 30, "Mon-Wed-Fri 10 AM");
        Course course2 = new Course("C102", "Database Systems", "Intro to Databases", 25, "Tue-Thu 11 AM");
        system.addCourse(course1);
        system.addCourse(course2);

        // Adding sample students
        Student student1 = new Student("S1001", "Alice");
        Student student2 = new Student("S1002", "Bob");
        system.addStudent(student1);
        system.addStudent(student2);

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        // Simple menu for interaction
        while (!exit) {
            System.out.println("1. Display Courses\n2. Register for a Course\n3. Drop a Course\n4. Show Registered Courses\n5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    system.displayCourses();
                    break;
                case 2:
                    System.out.print("Enter Student ID: ");
                    String studentID = scanner.next();
                    System.out.print("Enter Course Code: ");
                    String courseCode = scanner.next();
                    system.registerStudentForCourse(studentID, courseCode);
                    break;
                case 3:
                    System.out.print("Enter Student ID: ");
                    studentID = scanner.next();
                    System.out.print("Enter Course Code: ");
                    courseCode = scanner.next();
                    system.removeCourseForStudent(studentID, courseCode);
                    break;
                case 4:
                    System.out.print("Enter Student ID: ");
                    studentID = scanner.next();
                    Student student = system.studentDatabase.get(studentID);
                    if (student != null) {
                        student.displayRegisteredCourses();
                    } else {
                        System.out.println("Invalid Student ID");
                    }
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}