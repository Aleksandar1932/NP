package mk.ukim.finki.np.vezbanjekol1;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Scanner;

class InvalidExtraTypeException extends Exception {
    public InvalidExtraTypeException() {
    }
}

class EmptyOrderException extends Exception{
    public EmptyOrderException() {
        super();
    }
}

class InvalidPizzaTypeException extends Exception {
    public InvalidPizzaTypeException() {
        super();
    }
}

class OrderLockedException extends Exception {
    public OrderLockedException() {
        super();
    }
}

class ItemOutOfStockException extends Exception {
    Item item;

    public ItemOutOfStockException(Item item) {
        super();
    }

    @Override
    public String getMessage() {
        return "Invalid quantity for: " + item.getType();
    }
}

interface Item {
    int getPrice();

    String getType();
}

class ExtraItem implements Item {
    private String itemType;

    public ExtraItem(String itemType) throws InvalidExtraTypeException {
        if (!(itemType.equals("Coke") || itemType.equals("Ketchup"))) {
            throw new InvalidExtraTypeException();
        } else {
            this.itemType = itemType;
        }
    }

    @Override
    public int getPrice() {
        if (this.itemType.equals("Coke")) {
            return 5;
        } else {
            return 3;
        }
    }

    @Override
    public String getType() {
        return itemType;
    }
}

class PizzaItem implements Item {
    private String itemType;

    public PizzaItem(String itemType) throws InvalidPizzaTypeException {
        if (!(itemType.equals("Standard") || itemType.equals("Pepperoni") || itemType.equals("Vegetarian"))) {
            throw new InvalidPizzaTypeException();
        }
        this.itemType = itemType;
    }

    @Override
    public int getPrice() {
        if (itemType.equals("Standard")) {
            return 10;
        } else if (itemType.equals("Pepperoni")) {
            return 10;
        } else {
            return 8;
        }
    }

    @Override
    public String getType() {
        return itemType;
    }
}

class OrderItem {
    private Item item;
    private int quantity;

    public String getType() {
        return item.getType();
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return item.getPrice();
    }

    public OrderItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}

class Order {
    ArrayList<OrderItem> orderList;
    boolean locked;

    public Order() {
        this.orderList = new ArrayList<>();
        this.locked = false;
    }

    public void addItem(Item item, int quantity) throws ItemOutOfStockException, OrderLockedException {
        if (locked) {
            throw new OrderLockedException();
        }
        if (quantity > 10) {
            throw new ItemOutOfStockException(item);
        }

        OrderItem orderItem = new OrderItem(item, quantity);

        if (orderList.size() == 0) {
            orderList.add(orderItem);
        }

        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getType().equals(orderItem.getType())) {
                orderList.remove(orderList.get(i));
                orderList.add(i, orderItem);
            }
        }

        orderList.add(orderItem);
    }

    public int getPrice() {
        int price = 0;
        for (OrderItem orderItem : orderList) {
            price += (orderItem.getQuantity() * orderItem.getPrice());
        }
        return price;
    }

    public void displayOrder() {
        for (int i = 0; i < orderList.size(); i++) {
            //1.Standard       x 2   20$
            System.out.println(String.format("%d.%s %d %d\n",
                    i + 1
                    , orderList.get(i).getType()
                    , orderList.get(i).getQuantity()
                    , orderList.get(i).getPrice()
                    )
            );
        }
    }

    public void removeItem(int idx) throws OrderLockedException {
        if (locked) {
            throw new OrderLockedException();
        }
        if (idx > orderList.size()) {
            throw new ArrayIndexOutOfBoundsException(idx);
        } else {
            orderList.remove(orderList.get(idx));
        }
    }

    public void lock() throws EmptyOrderException {
        if (orderList.size() == 0) {
            throw new EmptyOrderException();
        } else {
            this.locked = true;
        }
    }

}

public class PizzaOrderTest {
    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Item
            try {
                String type = jin.next();
                String name = jin.next();
                Item item = null;
                if (type.equals("Pizza")) item = new PizzaItem(name);
                else item = new ExtraItem(name);
                System.out.println(item.getPrice());
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
        if (k == 1) { // test simple order
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 2) { // test order with removing
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (jin.hasNextInt()) {
                try {
                    int idx = jin.nextInt();
                    order.removeItem(idx);
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 3) { //test locking & exceptions
            Order order = new Order();
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new ExtraItem("Coke"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.removeItem(0);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }

}
