package BankingControllers;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import BankingView.Customer;
import bankingmanagement.CustomerDAO;
import bankingmanagement.bankDAO;

@WebServlet("/CustomerDetailsServlet")
public class CustomerDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("account_no");

        try (Connection conn = bankDAO.getConnection()) {
            CustomerDAO customerDAO = new CustomerDAO(conn);
            Customer customer = customerDAO.getCustomerByAccountNo(accountNo);

            if (customer != null) {
                request.setAttribute("customer", customer);
                request.getRequestDispatcher("CustomerDetails.jsp").forward(request, response);
            } else {
                response.sendRedirect("CustomerUpdate.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("adminDash.jsp");
        }
    }
}
