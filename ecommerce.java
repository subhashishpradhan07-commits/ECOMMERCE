import java.sql.*;

public class ecommerce {


    static Connection getCon() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/subha",
                "root",
                "subhashish1234"
        );
    }


    static void addProducts() throws Exception {
        Connection con = getCon();
        Statement st = con.createStatement();


        st.executeUpdate("DELETE FROM orders");
        st.executeUpdate("DELETE FROM customers");
        st.executeUpdate("DELETE FROM products");

        st.executeUpdate("ALTER TABLE customers AUTO_INCREMENT = 1");
        st.executeUpdate("ALTER TABLE products AUTO_INCREMENT = 1");
        st.executeUpdate("ALTER TABLE orders AUTO_INCREMENT = 1");


        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO products (product_name, category, price, stock) VALUES (?,?,?,?)"
        );

        ps.setString(1, "Laptop");
        ps.setString(2, "Electronics");
        ps.setDouble(3, 55000);
        ps.setInt(4, 10);
        ps.executeUpdate();

        ps.setString(1, "Smartphone");
        ps.setString(2, "Electronics");
        ps.setDouble(3, 25000);
        ps.setInt(4, 20);
        ps.executeUpdate();

        ps.setString(1, "Headphones");
        ps.setString(2, "Accessories");
        ps.setDouble(3, 2000);
        ps.setInt(4, 30);
        ps.executeUpdate();

        con.close();
        System.out.println("3 Products Added Successfully");
    }


    static void addCustomers() throws Exception {
        Connection con = getCon();
        Statement st = con.createStatement();

        st.executeUpdate("DELETE FROM orders");
        st.executeUpdate("DELETE FROM customers");
        st.executeUpdate("DELETE FROM products");

        st.executeUpdate("ALTER TABLE customers AUTO_INCREMENT = 1");
        st.executeUpdate("ALTER TABLE products AUTO_INCREMENT = 1");
        st.executeUpdate("ALTER TABLE orders AUTO_INCREMENT = 1");



        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO customers (name, email, phone, city) VALUES (?,?,?,?)"
        );

        ps.setString(1, "Rahul Sharma");
        ps.setString(2, "rahul@gmail.com");
        ps.setString(3, "9876543210");
        ps.setString(4, "Delhi");
        ps.executeUpdate();

        ps.setString(1, "Anita Verma");
        ps.setString(2, "anita@gmail.com");
        ps.setString(3, "9123456789");
        ps.setString(4, "Mumbai");
        ps.executeUpdate();

        ps.setString(1, "Amit Kumar");
        ps.setString(2, "amit@gmail.com");
        ps.setString(3, "9012345678");
        ps.setString(4, "Bhubaneswar");
        ps.executeUpdate();

        con.close();
        System.out.println("3 Customers Added Successfully");
    }


    static void addOrders() throws Exception {
        Connection con = getCon();
        Statement st = con.createStatement();


        st.executeUpdate("DELETE FROM orders");
        st.executeUpdate("DELETE FROM customers");
        st.executeUpdate("DELETE FROM products");

        st.executeUpdate("ALTER TABLE customers AUTO_INCREMENT = 1");
        st.executeUpdate("ALTER TABLE products AUTO_INCREMENT = 1");
        st.executeUpdate("ALTER TABLE orders AUTO_INCREMENT = 1");



        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO orders (customer_id, product_id) VALUES (?,?)"
        );


        ps.setInt(1, 4); // use actual customer_id
        ps.setInt(2, 4); // use actual product_id

        ps.executeUpdate();

        ps.setInt(1, 4); // use actual customer_id
        ps.setInt(2, 4); // use actual product_id

        ps.executeUpdate();

        ps.setInt(1, 4); // use actual customer_id
        ps.setInt(2, 4); // use actual product_id

        ps.executeUpdate();


        ps.setInt(1, 4); // use actual customer_id
        ps.setInt(2, 4); // use actual product_id

        ps.executeUpdate();

        con.close();
        System.out.println("Orders Added Successfully");
    }


    static void showReports() throws Exception {
        Connection con = getCon();
        Statement st = con.createStatement();

        ResultSet rs1 = st.executeQuery("SELECT COUNT(*) FROM orders");
        rs1.next();
        System.out.println("\nNumber of Orders: " + rs1.getInt(1));

        ResultSet rs2 = st.executeQuery(
                "SELECT COUNT(DISTINCT customer_id) FROM orders");
        rs2.next();
        System.out.println("Number of Customers Who Ordered: " + rs2.getInt(1));

        System.out.println("\nCustomers Who Ordered:");
        ResultSet rs3 = st.executeQuery(
                "SELECT DISTINCT c.name FROM customers c " +
                        "JOIN orders o ON c.customer_id = o.customer_id");
        while (rs3.next()) {
            System.out.println("- " + rs3.getString(1));
        }

        System.out.println("\nCustomers who ordered ONE item:");
        ResultSet rs4 = st.executeQuery(
                "SELECT c.name FROM customers c JOIN orders o " +
                        "ON c.customer_id = o.customer_id " +
                        "GROUP BY c.customer_id HAVING COUNT(*) = 1");
        while (rs4.next()) {
            System.out.println("- " + rs4.getString(1));
        }

        System.out.println("\nCustomers who ordered MORE THAN ONE item:");
        ResultSet rs5 = st.executeQuery(
                "SELECT c.name FROM customers c JOIN orders o " +
                        "ON c.customer_id = o.customer_id " +
                        "GROUP BY c.customer_id HAVING COUNT(*) > 1");
        while (rs5.next()) {
            System.out.println("- " + rs5.getString(1));
        }

        System.out.println("\nCustomers per Product:");
        ResultSet rs6 = st.executeQuery(
                "SELECT p.product_name, COUNT(DISTINCT o.customer_id) " +
                        "FROM products p JOIN orders o " +
                        "ON p.product_id = o.product_id " +
                        "GROUP BY p.product_id");
        while (rs6.next()) {
            System.out.println(
                    rs6.getString(1) + " → " + rs6.getInt(2) + " customer(s)"
            );
        }

        con.close();
    }


    public static void main(String[] args) {
        try {
            addProducts();
            addCustomers();
            addOrders();
            showReports();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}