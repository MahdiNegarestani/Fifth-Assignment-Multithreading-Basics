import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {
    static class TaskRunnable implements Runnable {
        private final String path;
        private double totalCost;
        private int totalAmount;
        private int totalDiscountSum;
        private int totalLines;
        private Product mostExpensiveProduct;
        private double highestCostAfterDiscount;

        public TaskRunnable(String path) {
            this.path = path;
            this.totalCost = 0;
            this.totalAmount = 0;
            this.totalDiscountSum = 0;
            this.totalLines = 0;
            this.highestCostAfterDiscount = 0;
            this.mostExpensiveProduct = null;
        }

        @Override
        public void run() {
            //TODO:
            // - Read all lines from the input file (path)
            // - For each line, parse product ID, amount, and discount
            // - The format of the files are like this:
            //      [productId],[amount],[discountAmount]
            // - Find the corresponding product from catalog
            // - Calculate discounted cost and update total stats (totalAmount, totalCost, totalDiscountSum, totalLines)
            // - Track the most expensive purchase after discount
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    int productId = Integer.parseInt(parts[0]);
                    int amount = Integer.parseInt(parts[1]);
                    int discount = Integer.parseInt(parts[2]);

                    Product product = getProductById(productId);
                    if (product != null) {
                        double price = product.getPrice();
                        double discountedCost = (price - discount) * amount;

                        totalCost += discountedCost;
                        totalAmount += amount;
                        totalDiscountSum += discount * amount;
                        totalLines++;

                        if (discountedCost > highestCostAfterDiscount) {
                            highestCostAfterDiscount = discountedCost;
                            mostExpensiveProduct = product;
                        }
                    }
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        private Product getProductById(int productId) {
            for (Product product: ReportGenerator.productCatalog) {
                if (productId == product.getProductID()) {
                    return product;
                }
            }
            return null;
        }

        public void makeReport() {
            // TODO:
            // - Print the filename
            // - Print total cost and total items bought
            // - Calculate and print average discount
            // - Display info about the most expensive purchase after discount
            DecimalFormat df = new DecimalFormat("#.00");
            System.out.println("Report for file: " + path);
            System.out.println("Total cost: " + df.format(totalCost));
            System.out.println("Total items bought: " + totalAmount);
            double averageDiscount = totalLines > 0 ? (double) totalDiscountSum / totalLines : 0;
            System.out.println("Average discount: " + df.format(averageDiscount));
            if (mostExpensiveProduct != null) {
                System.out.println("Most expensive product after discount: " + mostExpensiveProduct.getProductName() +
                        " (ID: " + mostExpensiveProduct.getProductID() + ", Cost: " + df.format(highestCostAfterDiscount) + ")");
            }
            System.out.println();
        }
    }

    static class Product {
        private int productID;
        private String productName;
        private double price;

        public Product(int productID, String productName, double price) {
            this.productID = productID;
            this.productName = productName;
            this.price = price;
        }

        public int getProductID() {
            return productID;
        }

        public String getProductName() {
            return productName;
        }

        public double getPrice() {
            return price;
        }
    }
    private static final String[] ORDER_FILES = {
            // TODO: Define the paths to the order detail text files in the resources folder
            "C:\\Users\\Sepehr\\exercise_5\\Fifth-Assignment-Multithreading-Basics\\src\\main\\resources\\2021_order_details.txt",
            "C:\\Users\\Sepehr\\exercise_5\\Fifth-Assignment-Multithreading-Basics\\src\\main\\resources\\2022_order_details.txt",
            "C:\\Users\\Sepehr\\exercise_5\\Fifth-Assignment-Multithreading-Basics\\src\\main\\resources\\2023_order_details.txt",
            "C:\\Users\\Sepehr\\exercise_5\\Fifth-Assignment-Multithreading-Basics\\src\\main\\resources\\2024_order_details.txt"
    };

    static Product[] productCatalog = new Product[10];

    public static void loadProducts() throws IOException {
        // TODO:
        // - Read lines from Products.txt
        // - For each line, parse product ID, name, and price
        // - The format of the file is like this:
        //      [productId],[name],[price]
        // - Store Product objects in the productCatalog array
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Sepehr\\exercise_5\\Fifth-Assignment-Multithreading-Basics\\src\\main\\resources\\Products.txt"))) {
                String line;
                int index = 0;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    int productId = Integer.parseInt(parts[0]);
                    String productName = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    productCatalog[index++] = new Product(productId, productName, price);
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // TODO:
        // - Create one TaskRunnable and Thread for each order file
        // - Start all threads
        // - Wait for all threads to finish
        // - After all threads are done, call makeReport() on each TaskRunnable
        try {
            loadProducts();
            List<TaskRunnable> tasks = new ArrayList<>();
            List<Thread> threads = new ArrayList<>();
            for (String path : ORDER_FILES) {
                TaskRunnable task = new TaskRunnable(path);
                Thread thread = new Thread(task);
                tasks.add(task);
                threads.add(thread);
                thread.start();
            }
            for (Thread thread : threads) {
                thread.join();
            }
            for (TaskRunnable task : tasks) {
                task.makeReport();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}