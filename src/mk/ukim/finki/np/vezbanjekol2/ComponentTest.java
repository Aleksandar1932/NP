package mk.ukim.finki.np.vezbanjekol2;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


class InvalidPositionException extends Exception {
    Integer position;

    public InvalidPositionException(Integer position) {
        this.position = position;
    }

    @Override
    public String getMessage() {
        return String.format("Invalid position %d, alredy taken!", position);
    }
}

class Component {
    String color;
    Integer weight;
    Set<Component> innerComponents;

    public Component(String color, Integer weight) {
        this.color = color;
        this.weight = weight;
        innerComponents = new TreeSet<>(Comparator.comparing(Component::getWeight).thenComparing(Component::getColor));
    }

    public void addComponent(Component component) {
        innerComponents.add(component);
    }

    public String getColor() {
        return color;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Set<Component> getInnerComponents() {
        return innerComponents;
    }

    public String toString(int indentation) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < indentation; i++)
            sb.append("-");

        sb.append(String.format("%d:%s\n", this.weight, this.color));
        for (Component c : innerComponents) {
            sb.append(c.toString(indentation + 3));
        }

        return sb.toString();
    }

    public Set<Component> flatMapAllInnerComponents() {
        Set<Component> returnSet = new TreeSet<>(Comparator.comparing(Component::getWeight).thenComparing(Component::getColor));
        returnSet.add(this);
        returnSet.addAll(this.getInnerComponents());

        for (Component c : innerComponents) {
            returnSet.addAll(c.getInnerComponents());
        }

        return returnSet;
    }
}

class Window {
    String name;
    Map<Integer, Component> components;

    public Window(String name) {
        this.name = name;
        components = new TreeMap<>();
    }

    public void addComponent(int position, Component component) throws InvalidPositionException {
        if (components.containsKey(position)) {
            throw new InvalidPositionException(position);
        } else {
            components.put(position, component);
        }
    }

    public void changeColor(int weight, String color) {
        components.values().stream().flatMap(component -> component.flatMapAllInnerComponents().stream())
                .filter(component -> component.getWeight() < weight)
                .forEach(component -> component.setColor(color));
    }

    public void switchComponents(int pos1, int pos2) {
        Component component1 = components.get(pos1);
        Component component2 = components.get(pos2);

        components.put(pos1, component2);
        components.put(pos2, component1);
    }


    @Override
    public String toString() {
        AtomicInteger counter = new AtomicInteger();
        counter.set(0);
        return "WINDOW " + this.name + "\n" +
                components.values()
                        .stream()
                        .map(component -> counter.incrementAndGet() + ":" + component.toString(0))
                        .collect(Collectors.joining(""));
    }
}

public class ComponentTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Window window = new Window(name);
        Component prev = null;
        while (true) {
            try {
                int what = scanner.nextInt();
                scanner.nextLine();
                if (what == 0) {
                    int position = scanner.nextInt();
                    window.addComponent(position, prev);
                } else if (what == 1) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    prev = new Component(color, weight);
                } else if (what == 2) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    assert prev != null;
                    prev.addComponent(component);
                    prev = component;
                } else if (what == 3) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    assert prev != null;
                    prev.addComponent(component);


                } else if (what == 4) {
                    break;
                }

            } catch (InvalidPositionException e) {
                System.out.println(e.getMessage());
            }
            scanner.nextLine();
        }

        System.out.println("=== ORIGINAL WINDOW ===");
        System.out.println(window);
        int weight = scanner.nextInt();
        scanner.nextLine();
        String color = scanner.nextLine();
        window.changeColor(weight, color);


        System.out.println(String.format("=== CHANGED COLOR (%d, %s) ===", weight, color));
        System.out.println(window);
        int pos1 = scanner.nextInt();
        int pos2 = scanner.nextInt();
        System.out.println(String.format("=== SWITCHED COMPONENTS %d <-> %d ===", pos1, pos2));
        window.switchComponents(pos1, pos2);
        System.out.println(window);
    }
}
