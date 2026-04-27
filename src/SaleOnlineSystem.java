import java.util.*;

// Product Class
class Product {
    int id;
    String name;
    double price;
    int stock; 

    Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
}

// Customer Class
class Customer {
    String username;
    String password;

    Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

// Order Class
class Order {
    int orderId;
    String customerName;
    Product product;
    String status;

    Order(int orderId, String customerName, Product product) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.product = product;
        this.status = "Pending";
    }
}

public class SaleOnlineSystem {

    static Scanner sc = new Scanner(System.in);

    static List<Product> products = new ArrayList<>();
    static List<Customer> customers = new ArrayList<>();
    static List<Order> orders = new ArrayList<>();

    static int productId = 1;
    static int orderId = 1;

    //  ADMIN LOGIN 
    static boolean adminLogin() {
        System.out.print("Enter Admin Username: ");
        String user = sc.next();
        System.out.print("Enter Password: ");
        String pass = sc.next();

        return user.equals("admin") && pass.equals("1234");
    }

    // REGISTER
    static void registerCustomer() {
        System.out.print("Enter Username: ");
        String user = sc.next();
        System.out.print("Enter Password: ");
        String pass = sc.next();

        customers.add(new Customer(user, pass));
        System.out.println(" Registration Successful!");
    }

    //  LOGIN
    static Customer customerLogin() {
        System.out.print("Enter Username: ");
        String user = sc.next();
        System.out.print("Enter Password: ");
        String pass = sc.next();

        for (Customer c : customers) {
            if (c.username.equals(user) && c.password.equals(pass)) {
                return c;
            }
        }
        System.out.println("Invalid Login!");
        return null;
    }

    // ADD PRODUCT 
    static void addProduct() {
        System.out.print("Enter Product Name: ");
        String name = sc.next();
        System.out.print("Enter Price: ");
        double price = sc.nextDouble();
        System.out.print("Enter Stock: ");
        int stock = sc.nextInt();

        products.add(new Product(productId++, name, price, stock));
        System.out.println(" Product Added!");
    }

    //  VIEW PRODUCTS 
    static void viewProducts() {
        System.out.println("\n--- Product List ---");
        for (Product p : products) {
            System.out.println(p.id + ". " + p.name + " - $" + p.price + " | Stock: " + p.stock);
        }
    }

    //UPDATE PRODUCT
    static void updateProduct() {
        viewProducts();
        System.out.print("Enter Product ID to Update: ");
        int id = sc.nextInt();

        for (Product p : products) {
            if (p.id == id) {
                System.out.print("New Name: ");
                p.name = sc.next();
                System.out.print("New Price: ");
                p.price = sc.nextDouble();
                System.out.print("New Stock: ");
                p.stock = sc.nextInt();

                System.out.println(" Product Updated!");
                return;
            }
        }
        System.out.println(" Product not found!");
    }

    // DELETE PRODUCT 
    static void deleteProduct() {
        viewProducts();
        System.out.print("Enter Product ID to Delete: ");
        int id = sc.nextInt();

        Iterator<Product> it = products.iterator();
        while (it.hasNext()) {
            if (it.next().id == id) {
                it.remove();
                System.out.println("Product Deleted!");
                return;
            }
        }
        System.out.println(" Product not found!");
    }

    // CREATE ORDER
    static void createOrder(Customer customer) {
        viewProducts();
        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();

        for (Product p : products) {
            if (p.id == id) {

                if (p.stock <= 0) { // NEW
                    System.out.println(" Out of stock!");
                    return;
                }

                p.stock--; // NEW reduce stock

                orders.add(new Order(orderId++, customer.username, p));
                System.out.println("Order Created!");
                return;
            }
        }
        System.out.println(" Product not found!");
    }

    //  VIEW ORDERS
    static void viewOrders() {
        System.out.println("\n--- Orders ---");
        for (Order o : orders) {
            System.out.println("OrderID: " + o.orderId +
                    ", Customer: " + o.customerName +
                    ", Product: " + o.product.name +
                    ", Status: " + o.status);
        }
    }

    // PROCESS ORDER 
    static void processOrders() {
        viewOrders();
        System.out.print("Enter Order ID to Process: ");
        int id = sc.nextInt();

        for (Order o : orders) {
            if (o.orderId == id) {
                o.status = "Completed";
                System.out.println("Order Processed!");
                return;
            }
        }
        System.out.println("Order not found!");
    }

    //CANCEL ORDER 
    static void cancelOrder(Customer customer) {
        viewOrders();
        System.out.print("Enter Order ID to Cancel: ");
        int id = sc.nextInt();

        for (Order o : orders) {
            if (o.orderId == id && o.customerName.equals(customer.username)) {

                if (o.status.equals("Completed")) {
                    System.out.println("Cannot cancel completed order!");
                    return;
                }

                o.status = "Cancelled";
                o.product.stock++; // restore stock

                System.out.println(" Order Cancelled!");
                return;
            }
        }
        System.out.println("Order not found!");
    }

    // MAIN
    public static void main(String[] args) {

        while (true) {
            System.out.println("\n=== ONLINE SALE SYSTEM ===");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Register");
            System.out.println("3. Customer Login");
            System.out.println("4. Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    if (adminLogin()) {
                        int adminChoice;
                        do {
                            System.out.println("\n--- ADMIN MENU ---");
                            System.out.println("1. Add Product");
                            System.out.println("2. View Products");
                            System.out.println("3. Update Product"); 
                            System.out.println("4. Delete Product"); 
                            System.out.println("5. View Orders");
                            System.out.println("6. Process Orders");
                            System.out.println("7. Logout");

                            adminChoice = sc.nextInt();

                            switch (adminChoice) {
                                case 1: addProduct(); break;
                                case 2: viewProducts(); break;
                                case 3: updateProduct(); break;
                                case 4: deleteProduct(); break;
                                case 5: viewOrders(); break;
                                case 6: processOrders(); break;
                            }

                        } while (adminChoice != 7);
                    } else {
                        System.out.println("Wrong Admin Login!");
                    }
                    break;

                case 2:
                    registerCustomer();
                    break;

                case 3:
                    Customer c = customerLogin();
                    if (c != null) {
                        int custChoice;
                        do {
                            System.out.println("\n--- CUSTOMER MENU ---");
                            System.out.println("1. View Products");
                            System.out.println("2. Create Order");
                            System.out.println("3. View Orders");
                            System.out.println("4. Cancel Order"); // NEW
                            System.out.println("5. Logout");

                            custChoice = sc.nextInt();

                            switch (custChoice) {
                                case 1: viewProducts(); break;
                                case 2: createOrder(c); break;
                                case 3: viewOrders(); break;
                                case 4: cancelOrder(c); break;
                            }

                        } while (custChoice != 5);
                    }
                    break;

                case 4:
                    System.exit(0);
            }
        }
    }
}