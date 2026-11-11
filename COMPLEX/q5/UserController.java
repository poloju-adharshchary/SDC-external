
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/UserController")
public class UserController extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        response.setContentType("text/html");

        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/shopping",
                "root",
                "adhi123"
            );

            if (action.equals("register")) {

                String name = request.getParameter("name");

                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO users(name,email,password) VALUES(?,?,?)"
                );

                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, password);

                ps.executeUpdate();

                response.sendRedirect("login.html");

            } else if (action.equals("login")) {

                PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE email=? AND password=?"
                );

                ps.setString(1, email);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    response.sendRedirect("catalog.html");
                } else {
                    response.getWriter().println("Invalid Email or Password");
                }
            }

            con.close();

        } catch (Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
