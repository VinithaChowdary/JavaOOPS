import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

class Guest {
    private String name;
    private int roomNumber;
    private String[] foodOrder;
    private double totalBill;

    public Guest(String name, int roomNumber) {
        this.name = name;
        this.roomNumber = roomNumber;
        this.foodOrder = new String[5]; // Maximum 5 food items per guest
        this.totalBill = 0.0;
    }

    public String getName() {
        return name;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void addFoodItem(String foodItem, double price) {
        for (int i = 0; i < foodOrder.length; i++) {
            if (foodOrder[i] == null) {
                foodOrder[i] = foodItem;
                totalBill += price;
                break;
            }
        }
    }

    public String[] getFoodOrder() {
        return foodOrder;
    }

    public double getTotalBill() {
        return totalBill;
    }
}

public class HotelManagement {
    private static Stack<Integer> availableRooms = new Stack<>();
    private static Queue<Guest> occupiedRooms = new LinkedList<>();
    private static LinkedList<Guest> allGuests = new LinkedList<>();

    public static void main(String[] args) {
        initializeRooms();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("1. Check-in");
            System.out.println("2. Check-out");
            System.out.println("3. Display available rooms");
            System.out.println("4. Display occupied rooms");
            System.out.println("5. Search for a guest");
            System.out.println("6. Display all guests");
            System.out.println("7. Display total number of guests");
            System.out.println("8. Place food order");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    performCheckIn();
                    break;
                case 2:
                    performCheckOut();
                    break;
                case 3:
                    displayAvailableRooms();
                    break;
                case 4:
                    displayOccupiedRooms();
                    break;
                case 5:
                    searchGuest();
                    break;
                case 6:
                    displayAllGuests();
                    break;
                case 7:
                    displayTotalGuests();
                    break;
                case 8:
                    placeFoodOrder();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

            System.out.println();
        } while (choice != 0);

        scanner.close();
    }

    private static void initializeRooms() {
        for (int i = 1; i <= 10; i++) {
            availableRooms.push(i);
        }
    }

    private static void performCheckIn() {
        if (availableRooms.isEmpty()) {
            System.out.println("No available rooms.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter guest name: ");
        String name = scanner.nextLine();

        int roomNumber = availableRooms.pop();
        Guest guest = new Guest(name, roomNumber);
        occupiedRooms.offer(guest);
        allGuests.add(guest);

        System.out.println("Guest " + name + " checked in to Room " + roomNumber);
    }

    private static void performCheckOut() {
        if (occupiedRooms.isEmpty()) {
            System.out.println("No occupied rooms.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter guest name: ");
        String name = scanner.nextLine();

        Guest foundGuest = null;
        for (Guest guest : occupiedRooms) {
            if (guest.getName().equalsIgnoreCase(name)) {
                foundGuest = guest;
                break;
            }
        }

        if (foundGuest != null) {
            occupiedRooms.remove(foundGuest);
            availableRooms.push(foundGuest.getRoomNumber());
            allGuests.remove(foundGuest);
            System.out.println("Guest " + name + " checked out from Room " + foundGuest.getRoomNumber());
            System.out.println("Total Bill: $" + foundGuest.getTotalBill());
        } else {
            System.out.println("Guest not found!");
        }
    }

    private static void displayAvailableRooms() {
        if (availableRooms.isEmpty()) {
            System.out.println("No available rooms.");
        } else {
            System.out.println("Available Rooms:");
            for (int room : availableRooms) {
                System.out.print(room + " ");
            }
            System.out.println();
        }
    }

    private static void displayOccupiedRooms() {
        if (occupiedRooms.isEmpty()) {
            System.out.println("No occupied rooms.");
        } else {
            System.out.println("Occupied Rooms:");
            for (Guest guest : occupiedRooms) {
                System.out.println("Room: " + guest.getRoomNumber() + ", Guest: " + guest.getName());
            }
        }
    }

    private static void searchGuest() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter guest name: ");
        String name = scanner.nextLine();

        boolean found = false;
        for (Guest guest : allGuests) {
            if (guest.getName().equalsIgnoreCase(name)) {
                System.out.println("Guest found! Room: " + guest.getRoomNumber());
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Guest not found!");
        }
    }

    private static void displayAllGuests() {
        if (allGuests.isEmpty()) {
            System.out.println("No guests.");
        } else {
            System.out.println("All Guests:");
            for (Guest guest : allGuests) {
                System.out.println("Room: " + guest.getRoomNumber() + ", Guest: " + guest.getName());
            }
        }
    }

    private static void displayTotalGuests() {
        System.out.println("Total Guests: " + allGuests.size());
    }

    private static void placeFoodOrder() {
        if (occupiedRooms.isEmpty()) {
            System.out.println("No occupied rooms.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter guest name: ");
        String name = scanner.nextLine();

        Guest foundGuest = null;
        for (Guest guest : occupiedRooms) {
            if (guest.getName().equalsIgnoreCase(name)) {
                foundGuest = guest;
                break;
            }
        }

        if (foundGuest != null) {
            System.out.println("Available food items: ");
            System.out.println("1. Pizza - $10.0");
            System.out.println("2. Burger - $5.0");
            System.out.println("3. Salad - $7.0");
            System.out.println("4. Pasta - $8.0");
            System.out.println("5. Sandwich - $6.0");
            System.out.println("0. Done");

            boolean done = false;
            while (!done) {
                System.out.print("Enter food item number (0 to finish): ");
                int foodChoice = scanner.nextInt();

                switch (foodChoice) {
                    case 1:
                        foundGuest.addFoodItem("Pizza", 10.0);
                        System.out.println("Pizza added to the order.");
                        break;
                    case 2:
                        foundGuest.addFoodItem("Burger", 5.0);
                        System.out.println("Burger added to the order.");
                        break;
                    case 3:
                        foundGuest.addFoodItem("Salad", 7.0);
                        System.out.println("Salad added to the order.");
                        break;
                    case 4:
                        foundGuest.addFoodItem("Pasta", 8.0);
                        System.out.println("Pasta added to the order.");
                        break;
                    case 5:
                        foundGuest.addFoodItem("Sandwich", 6.0);
                        System.out.println("Sandwich added to the order.");
                        break;
                    case 0:
                        done = true;
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        } else {
            System.out.println("Guest not found!");
        }
    }
}
