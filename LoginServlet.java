
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        try {
            resp.setContentType("text/html");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "krishna04");
            PreparedStatement psmt;
            ResultSet rs;
            String email = req.getParameter("email").trim();
            String password = req.getParameter("password").trim();
            psmt = con.prepareStatement("select * from admin where email = ?");
            psmt.setString(1, email);
            rs = psmt.executeQuery();
            // checking for email in Admin DataBase
            if (rs.next()) {
                if (password.equals(rs.getString("password"))) {
                    RequestDispatcher rd = req.getRequestDispatcher("/Admin_Home.html");
                    rd.include(req, resp);
                } else {
                    RequestDispatcher rd = req.getRequestDispatcher("/index.html");
                    rd.include(req, resp);
                    out.println("<script>document.getElementById('msg-block').innerText = 'Invalid password'; document.getElementById('msg-block').style.color = 'red';</script>");
                }
            } // here checking for email in client DataBase
            else {
                psmt = con.prepareStatement(" select * from clients where email = ?");
                psmt.setString(1, email);
                rs = psmt.executeQuery();
                if (rs.next()) {
                    if (password.equals(rs.getString("password"))) {
                        RequestDispatcher rd = req.getRequestDispatcher("/Client_Home.html");
                        rd.include(req, resp);
                    } else {
                        RequestDispatcher rd = req.getRequestDispatcher("/index.html");
                        rd.include(req, resp);
                        out.println("<script>document.getElementById('msg-block').innerText = 'Invalid password'; document.getElementById('msg-block').style.color = 'red';</script>");
                    }
                } else {
                    RequestDispatcher rd = req.getRequestDispatcher("/index.html");
                    rd.include(req, resp);
                    out.println("<script>document.getElementById('msg-block').innerText = 'Email Not Registered'; document.getElementById('msg-block').style.color = 'red';</script>");
                }
            }
            psmt.close();
            con.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}
