package tomcatServerlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 2: Simple Login Servlet
 * Uses Servlet annotations to map to /login URL.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    /**
     * Renders the login form
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<head><title>Login Page</title></head>");
        out.println("<body>");
        out.println("<h2>Login to Access Dashboard</h2>");
        out.println("<form method='POST' action='login'>");
        out.println("  <label for='username'>Username:</label><br>");
        out.println("  <input type='text' id='username' name='username' required><br><br>");
        out.println("  <label for='password'>Password:</label><br>");
        out.println("  <input type='password' id='password' name='password' required><br><br>");
        out.println("  <input type='submit' value='Login'>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Processes login credentials
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("username");
        String pass = req.getParameter("password");

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<head><title>Login Status</title></head>");
        out.println("<body>");

        // Simple hardcoded check for the demo
        if ("admin".equals(user) && "password".equals(pass)) {
            out.println("<h2 style='color: green;'>Login Successful! Welcome, " + user + ".</h2>");
            out.println("<p>You have accessed the secure section.</p>");
        } else {
            out.println("<h2 style='color: red;'>Invalid Credentials!</h2>");
            out.println("<p>Please <a href='login'>try again</a>.</p>");
        }

        out.println("</body>");
        out.println("</html>");
    }
}
