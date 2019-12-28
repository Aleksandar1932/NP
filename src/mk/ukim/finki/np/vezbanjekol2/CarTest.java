package mk.ukim.finki.np.vezbanjekol2;

import java.util.*;

class Car {
    String manufacturer;
    String model;
    Integer price;
    Float power;

    public Car(String manufacturer, String model, Integer price, Float power) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.price = price;
        this.power = power;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public Integer getPrice() {
        return price;
    }

    public Float getPower() {
        return power;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%dKW) %d", manufacturer, model, power.intValue(), price);
    }
}

class CarCollection {

    Set<Car> carSet;
    Map<String, Set<Car>> manufacturerMap;
    boolean asc;

    public CarCollection() {
        carSet = new TreeSet<>(Comparator.comparing(Car::getPrice).thenComparing(Car::getPower));
        manufacturerMap = new HashMap<>();
        asc = true;
    }

    public void addCar(Car car) {
        String key = car.getManufacturer().toLowerCase();
        carSet.add(car);
        if(manufacturerMap.containsKey(key)){
            TreeSet<Car> tempSet = (TreeSet<Car>) manufacturerMap.get(key);
            tempSet.add(car);
            manufacturerMap.put(key,tempSet);
        }
        else{
            TreeSet<Car> tempSet = new TreeSet<>(Comparator.comparing(Car::getModel));
            tempSet.add(car);
            manufacturerMap.put(key,tempSet);
        }
    }

    public void sortByPrice(boolean ascending) {
        asc = ascending;
    }

    public List<Car> filterByManufacturer(String manufacturer) {
        return new ArrayList<>(manufacturerMap.get(manufacturer.toLowerCase()));
    }

    public List<Car> getList() {
        List<Car> returnList = new ArrayList<>(carSet);
        if (!asc) {
            Collections.reverse(returnList);
        }
        return returnList;
    }


}

public class CarTest {
    public static void main(String[] args) {
        CarCollection carCollection = new CarCollection();
        String manufacturer = fillCollection(carCollection);
        carCollection.sortByPrice(true);
        System.out.println("=== Sorted By Price ASC ===");
        print(carCollection.getList());
        carCollection.sortByPrice(false);
        System.out.println("=== Sorted By Price DESC ===");
        print(carCollection.getList());
        System.out.printf("=== Filtered By Manufacturer: %s ===\n", manufacturer);
        List<Car> result = carCollection.filterByManufacturer(manufacturer);
        print(result);
    }

    static void print(List<Car> cars) {
        for (Car c : cars) {
            System.out.println(c);
        }
    }

    static String fillCollection(CarCollection cc) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            if (parts.length < 4) return parts[0];
            Car car = new Car(parts[0], parts[1], Integer.parseInt(parts[2]),
                    Float.parseFloat(parts[3]));
            cc.addCar(car);
        }
        scanner.close();
        return "";
    }
}
