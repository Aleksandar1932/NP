//package mk.ukim.finki.np.vezbanjekol1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

interface Scalable {
    void scale(float scaleFactor);
}

interface Stackable {
    float weight();
}

abstract class Shape implements Scalable, Stackable {
    private String id;
    private Color color;

    public Shape(String id, Color color) {
        this.id = id;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("%-5s%-10s%10.2f", id, color, weight());
    }
}

class Circle extends Shape {
    private float radius;

    public Circle(String id, Color color, float radius) {
        super(id, color);
        this.radius = radius;
    }

    @Override
    public void scale(float scaleFactor) {
        this.radius = this.radius * scaleFactor;
    }

    @Override
    public float weight() {
        return (float) (Math.PI * radius * radius);
    }

    @Override
    public String toString() {
        return String.format("C: %s\n", super.toString());
    }
}

class Rectangle extends Shape {
    private float width;
    private float height;

    public Rectangle(String id, Color color, float width, float height) {
        super(id, color);
        this.width = width;
        this.height = height;
    }

    @Override
    public void scale(float scaleFactor) {
        this.width = this.width * scaleFactor;
        this.height = this.height * scaleFactor;
    }

    @Override
    public float weight() {
        return width * height;
    }

    public String toString() {
        return String.format("R: %s\n", super.toString());
    }
}

class Canvas {
    ArrayList<Shape> shapes;

    public Canvas() {
        this.shapes = new ArrayList<>();
    }

    private void addShape(Shape s) {
        if (shapes.size() == 0) {
            shapes.add(s);
            return;
        }

        for (int i = 0; i < shapes.size(); i++) {
            if (s.weight() > shapes.get(i).weight()) {
                shapes.add(i, s);
                return;
            }
        }

        shapes.add(s);
    }

    void add(String id, Color color, float radius) {
        Circle circle = new Circle(id, color, radius);
        addShape(circle);
        //Za dodavanje krug;
    }

    void add(String id, Color color, float width, float height) {
        Rectangle rectangle = new Rectangle(id, color, width, height);
        addShape(rectangle);
        //Za dodavanje pravoagolnik;
    }

    void scale(String id, float scaleFactor) {
//        Shape shapeToDelete = null;
//        for (int i = 0; i < shapes.size(); i++) {
//            if (shapes.get(i).getId() == id) {
//                shapeToDelete = shapes.get(i);
//            }
//        }
//
//        if (shapeToDelete == null) {
//            return;
//        } else {
//            shapes.remove(shapeToDelete);
//            shapeToDelete.scale(scaleFactor);
//            addShape(shapeToDelete);
//        }

        Optional<Shape> optional = shapes.stream().filter(s -> s.getId().equals(id)).findFirst();
        if (optional.isPresent()) {
            Shape shapetoDelete = optional.get();
            shapes.remove(shapetoDelete);
            shapetoDelete.scale(scaleFactor);
            addShape(shapetoDelete);
        }
    }

    @Override
    public String toString() {
        return shapes.stream().map(Shape::toString).collect(Collectors.joining());
    }

//    @Override
//    public String toString() {
//        String retValue = "";
//        for (int i = 0; i < shapes.size(); i++) {
//            retValue += shapes.get(i).toString();
//        }
//
//        return retValue;
//    }
}

enum Color {
    RED, GREEN, BLUE
}

public class ShapesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Canvas canvas = new Canvas();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int type = Integer.parseInt(parts[0]);
            String id = parts[1];
            if (type == 1) {
                Color color = Color.valueOf(parts[2]);
                float radius = Float.parseFloat(parts[3]);
                canvas.add(id, color, radius);
            } else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
                float width = Float.parseFloat(parts[3]);
                float height = Float.parseFloat(parts[4]);
                canvas.add(id, color, width, height);
            } else if (type == 3) {
                float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
                System.out.print(canvas);
                canvas.scale(id, scaleFactor);
                System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
                System.out.print(canvas);
            }

        }
    }
}
