package mk.ukim.finki.np.vezbanjekol1;

import java.util.*;
import java.util.stream.Collectors;

class UnsupportedStudentTypeException extends Exception{
    public UnsupportedStudentTypeException() {
        super("Unsupported Type of student, try again later!");
    }

}

interface Prioritizable {
    //Ako studentot e graduate priority = prosek*0.12
    //Ako studentot e postgraduate priority = prosek*0.13
    public double getPriority();
}

class ClassRoom {
    String name;
    private ArrayList<Student> studentsList;


    public ClassRoom(String name) {
        this.name = name;
        studentsList = new ArrayList<>();
    }

    public void addStudent(Student s) {
        studentsList.add(s);
    }

    public void printClassRoom() {
        System.out.println(studentsList.stream().map(Student::toString).collect(Collectors.joining("\n")));
    }

    public void printWithGPA() {
        for (Student student : studentsList) {
            System.out.println(String.format("%-15s GPA: %5.2f", student.getName(), student.getGPA()));
        }
    }

    public void printWithPriority() {
        for (Student student : studentsList) {
            System.out.println(String.format("%-15s Priority: %5.2f",
                    student.getName(),
                    student.getPriority())
            );
        }
    }


}

abstract class Student {
    private String name;
    private ArrayList<Integer> grades;

    public Student(String name, ArrayList<Integer> grades) {
        this.name = name;
        this.grades = grades;
    }

    public double getGPA() {
        OptionalDouble average = grades.stream().mapToDouble(a -> a).average();
        return average.isPresent() ? average.getAsDouble() : 0;
    }

    public String getName() {
        return name;
    }

    abstract double getPriority();

    @Override
    public String toString() {
        return "" + name + " Grades:" + grades + " ";
    }
}

class GraduateStudent extends Student {
    private int labPoints;

    public GraduateStudent(String name, ArrayList<Integer> grades, int labPoints) {
        super(name, grades);
        this.labPoints = labPoints;
    }

    @Override
    double getPriority() {
        Prioritizable p;
        p = ()->{
          return this.getGPA()*0.12;
        };

        return p.getPriority();
    }

    @Override
    public String toString() {
        return "GraduateStudent: " + super.toString() + "LabPoints: " + labPoints;
    }
}

class PostGraduateStudent extends Student {
    private int projectPoints;

    public PostGraduateStudent(String name, ArrayList<Integer> grades, int projectPoints) {
        super(name, grades);
        this.projectPoints = projectPoints;
    }

    @Override
    double getPriority() {
        Prioritizable p;
        p = ()->{
            return this.getGPA()*0.13;
        };

        return p.getPriority();
    }

    @Override
    public String toString() {
        return "PostGraduateStudent: " + super.toString() + "ProjectPoints: " + projectPoints;
    }
}

public class AleksandarProblemTest {
    public static void main(String[] args) throws UnsupportedStudentTypeException {
        Scanner in = new Scanner(System.in);
        ClassRoom cR = new ClassRoom("Ucilnica");
        while (in.hasNextLine()) {
            String[] tokens = in.nextLine().split("\\s+");
            if (tokens[0].charAt(0) == 'X') {
                break;
            }
            try {
                cR.addStudent(studentGenerator(tokens));
            }
            catch (UnsupportedStudentTypeException e){
                System.out.println(e.getMessage());
            }
        }

        System.out.println("=== Printing Complete Classroom ===");
        cR.printClassRoom();

        System.out.println("=== Printing Students and their GPAs ===");
        cR.printWithGPA();

        System.out.println("=== Printing Students and their Priorities ===");
        cR.printWithPriority();
    }

    public static Student studentGenerator(String[] studentInfo) throws UnsupportedStudentTypeException {
        String name = studentInfo[0];
        String additionalPoints = studentInfo[1];

        ArrayList<Integer> grades = new ArrayList<>();
        for (int i = 2; i < studentInfo.length; i++) {
            grades.add(Integer.parseInt(studentInfo[i]));
        }

        int totalPoints = Integer.parseInt(additionalPoints.substring(0, additionalPoints.length() - 1));

        if (additionalPoints.charAt(additionalPoints.length() - 1) == 'L') {
            return new GraduateStudent(name, grades, totalPoints);
        } else if (additionalPoints.charAt(additionalPoints.length() - 1) == 'P') {
            return new PostGraduateStudent(name, grades, totalPoints);
        } else {
            throw new UnsupportedStudentTypeException();
        }
    }
}
