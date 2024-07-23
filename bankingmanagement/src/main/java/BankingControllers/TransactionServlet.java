package BankingControllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import BankingView.Transaction;
import bankingmanagement.TransactionDAO;

@WebServlet("/TransactionServlet")
public class TransactionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("accountNo");

        if (accountNo != null && !accountNo.isEmpty()) {
            HttpSession session = request.getSession();
            session.setAttribute("accountNo", accountNo);

            TransactionDAO transactionDAO = new TransactionDAO();
            List<Transaction> transactions = transactionDAO.getTransactionsByAccountNo(accountNo);

            session.setAttribute("transactions", transactions);
            response.sendRedirect("transaction.jsp");
        } else {
            response.sendRedirect("CustomerDash.jsp");
        }
    }
}
