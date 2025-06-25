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

@WebServlet("Registration_Form")
public class Registration_Form extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
           PrintWriter out = resp.getWriter();
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","krishna04");
        PreparedStatement psmt = con.prepareStatement("INSERT INTO clients (name, email, mobile, address) VALUES (?, ?, ?, ?)");
        psmt.setString(1, req.getParameter("name"));
        psmt.setString(2, req.getParameter("email"));
        psmt.setString(3, req.getParameter("mobile"));
        psmt.setString(4, req.getParameter("address"));
        boolean b = psmt.execute();
        if(b==false){
            resp.setContentType("text/html");
            RequestDispatcher rd = req.getRequestDispatcher("/index.html");
            rd.include(req, resp);
            out.println("<script>document.getElementById('msg-block').innerText = 'Registration Successful'; document.getElementById('msg-block').style.color = 'green';</script>");
            
        }else{
            out.println("Sorry somthing went wrong");
        }
        
                psmt.close();
                con.close();
       }catch(Exception ex){
           System.out.println(ex.getMessage());
       }
        
    }
    
}