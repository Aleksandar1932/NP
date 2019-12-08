package mk.ukim.finki.np.vezbanjekol2;

import java.util.*;
import java.util.stream.Collectors;

class InvalidPositionException extends Exception {
    Integer position;

    public InvalidPositionException(Integer position) {
        this.position = position;
    }

    @Override
    public String getMessage() {
        return String.format("Invalid position [%d], alredy taken!", position);
    }
}

class Component {
    String color;
    Integer weight;
    Set<Component> innerComponents;

    public Component(String color, int weight) {
        this.color = color;
        this.weight = weight;
        innerComponents = new TreeSet<>(
                Comparator.comparing(Component::getWeight).thenComparing(Component::getColor)
        );
    }

    public void addComponent(Component componentToAdd) {
        innerComponents.add(componentToAdd);
    }

    public String getColor() {
        return color;
    }

    public Set<Component> getInnerComponents() {
        return innerComponents;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        String retString = String.format("%d:%s", this.weight, this.color);
        return retString + innerComponents.stream()
                .map(component -> component.toString())
                .collect(Collectors.joining("\n"));
    }
}

class Window {
    String windowName;
    List<Component> components;

    public Window(String windowName) {
        this.windowName = windowName;
        components = new ArrayList<>(1000);
    }

    public void addComponent(int position, Component componentToAdd) throws InvalidPositionException {
        if (components.get(position) != null) {
            //Position is not empty,then throw an exception;
            throw new InvalidPositionException(position);
        } else {
            //Position is empty, so add the component;
            components.add(position, componentToAdd);
        }
    }

    public void changeColor(int weight, String color) {
        /*
            Logika: Gi strimam site komponenti gi filtriram spored tezina, potoa,
            za sekoj komponent mu ja menam bojata ako go pominal filterot, i mu ja zemam
            listata so vnatresni komponenti, i ja strimam i taa lista so istata logika,
            filtriram elementi so pomala tezina od dadenata i za sekoj od tie elementi
            mu ja menam bojata vo soodvetnata boja prosledena kako argument;
         */
        components.stream()
                .filter(component -> component.getWeight() < weight)
                .forEach(component -> {
                    component.setColor(color);
                    component.getInnerComponents().stream()
                            .filter(innerComponent -> innerComponent.getWeight() < weight)
                            .forEach(innerComponent -> {
                                innerComponent.setColor(color);
                            });
                });
    }

    public void swichComponents(int pos1, int pos2) {
        if (components.get(pos1) != null && components.get(pos2) != null) {
            //OK e postojat komponentite da ne menuvam null-ovi

            Collections.swap(components, pos1, pos2);
        }
    }

    @Override
    public String toString() {
        String retString = String.format("%s", this.windowName);
        return retString + components.stream().map(component -> component.toString()).collect(Collectors.joining("\n"));
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
                    Component component = new Component(color, weight);
                    prev = component;
                } else if (what == 2) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                    prev = component;
                } else if (what == 3) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
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
        window.swichComponents(pos1, pos2);
        System.out.println(window);
    }
}
