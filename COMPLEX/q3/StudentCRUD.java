import java.sql.*;
import java.util.Scanner;

public class StudentCRUD {
    static final String URL = "jdbc:mysql://localhost:3306/student";
    static final String USER = "root";
    static final String PASS = "adhi123";

    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS); 
             Scanner sc = new Scanner(System.in)) {
             
            System.out.println("Database connected successfully!");
            
            while (true) {
                System.out.println("\n1.Insert 2.Update");
                System.out.println("3.Display 4.Delete 5.Exit");
                System.out.print("Enter the choice: ");
                
                int ch = Integer.parseInt(sc.nextLine());
                
                switch (ch) {
                    case 1:
                        System.out.print("Enter the Name: ");
                        String n = sc.nextLine();
                        System.out.print("Enter the Roll No: ");
                        int r = Integer.parseInt(sc.nextLine());
                        System.out.print("Marks: ");
                        double m = Double.parseDouble(sc.nextLine());
                        
                        String sql1 = "INSERT INTO stud (rno, name, marks) VALUES (?, ?, ?)";
                        try (PreparedStatement ps = con.prepareStatement(sql1)) {
                            ps.setInt(1, r);
                            ps.setString(2, n);
                            ps.setDouble(3, m);
                            ps.executeUpdate();
                            System.out.println("Record inserted successfully.");
                        }
                        break;
                        
                    case 2:
                        System.out.print("Enter roll number: ");
                        int ur = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter new marks: ");
                        double um = Double.parseDouble(sc.nextLine());
                        
                        String sql2 = "UPDATE stud SET marks = ? WHERE rno = ?";
                        try (PreparedStatement ps = con.prepareStatement(sql2)) {
                            ps.setDouble(1, um);
                            ps.setInt(2, ur);
                            System.out.println(ps.executeUpdate() + " Record(s) updated successfully.");
                        }
                        break;
                        
                    case 3:
                        System.out.println("Printing student Details");
                        String sql3 = "SELECT * FROM stud";
                        try (PreparedStatement ps = con.prepareStatement(sql3); 
                             ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                System.out.println(rs.getInt("rno") + "\t" + 
                                                   rs.getString("name") + "\t" + 
                                                   rs.getDouble("marks"));
                            }
                        }
                        break;
                        
                    case 4:
                        System.out.print("Enter roll number to delete: ");
                        int dr = Integer.parseInt(sc.nextLine());
                        
                        String sql4 = "DELETE FROM stud WHERE rno = ?"; 
                        try (PreparedStatement ps = con.prepareStatement(sql4)) {
                            ps.setInt(1, dr);
                            System.out.println(ps.executeUpdate() + " Record(s) deleted successfully.");
                        }
                        break;
                        
                    case 5:
                        System.out.println("Exiting application.");
                        return;
                        
                    default:
                        System.out.println("Invalid choice");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid numeric input.");
        }
    }
}
