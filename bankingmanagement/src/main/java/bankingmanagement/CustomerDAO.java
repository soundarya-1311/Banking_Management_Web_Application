package bankingmanagement;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import BankingView.Customer;

public class CustomerDAO {
    private Connection conn;

    public CustomerDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean updateBalance(String accountNo, double amount) {
        boolean rowUpdated = false;
        String updateBalanceSQL = "UPDATE customerac SET balance = balance + ? WHERE account_no = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(updateBalanceSQL)) {
            preparedStatement.setDouble(1, amount);
            preparedStatement.setString(2, accountNo);
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    public Customer getCustomerByAccountNo(String accountNo) {
        Customer customer = null;
        String getCustomerSQL = "SELECT * FROM customerac WHERE account_no = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(getCustomerSQL)) {
            preparedStatement.setString(1, accountNo);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customer = new Customer();
                customer.setAccountNo(resultSet.getString("account_no"));
                customer.setFullName(resultSet.getString("full_name"));
                customer.setEmailId(resultSet.getString("email_id"));
                customer.setMobileNo(resultSet.getString("mobile_no"));
                customer.setAddress(resultSet.getString("address"));
                customer.setAccountType(resultSet.getString("account_type"));
                customer.setDob(resultSet.getString("dob"));
                customer.setIdProof(resultSet.getString("id_proof"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public boolean updateCustomer(Customer customer) {
        boolean rowUpdated = false;
        String updateCustomerSQL = "UPDATE customerac SET full_name=?, email_id=?, mobile_no=?, address=?, account_type=?, dob=?, id_proof=? WHERE account_no=?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(updateCustomerSQL)) {
            preparedStatement.setString(1, customer.getFullName());
            preparedStatement.setString(2, customer.getEmailId());
            preparedStatement.setString(3, customer.getMobileNo());
            preparedStatement.setString(4, customer.getAddress());
            preparedStatement.setString(5, customer.getAccountType());
            preparedStatement.setString(6, customer.getDob());
            preparedStatement.setString(7, customer.getIdProof());
            preparedStatement.setString(8, customer.getAccountNo());

            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    public boolean deleteCustomer(String accountNo) {
        boolean rowDeleted = false;
        String deleteTransactionSQL = "DELETE FROM transaction WHERE account_no = ?";
        String deleteCustomerSQL = "DELETE FROM customerac WHERE account_no = ?";

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement deleteTransactionStmt = conn.prepareStatement(deleteTransactionSQL);
                 PreparedStatement deleteCustomerStmt = conn.prepareStatement(deleteCustomerSQL)) {

                deleteTransactionStmt.setString(1, accountNo);
                deleteTransactionStmt.executeUpdate();

                deleteCustomerStmt.setString(1, accountNo);
                rowDeleted = deleteCustomerStmt.executeUpdate() > 0;

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    public BigDecimal getBalance(String accountNo) {
        BigDecimal balance = null;
        String selectBalanceSQL = "SELECT balance FROM customerac WHERE account_no = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(selectBalanceSQL)) {
            preparedStatement.setString(1, accountNo);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                balance = resultSet.getBigDecimal("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }
}
