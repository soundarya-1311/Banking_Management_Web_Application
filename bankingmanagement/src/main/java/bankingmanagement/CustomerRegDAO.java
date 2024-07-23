package bankingmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class CustomerRegDAO {
  

    public boolean registerCustomer(String full_name, String address, String mobile_no, String email_id, String account_type, String dob, String id_proof, String account_no, String temp_password) {
        boolean isRegistered = false;

        try (Connection conn = bankDAO.getConnection()){
            
            String sql = "INSERT INTO customerac (full_name, address, mobile_no, email_id, account_type, dob, id_proof, account_no, temp_password,username) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, full_name);
            stmt.setString(2, address);
            stmt.setString(3, mobile_no);
            stmt.setString(4, email_id);
            stmt.setString(5, account_type);
            stmt.setString(6, dob);
            stmt.setString(7, id_proof);
            stmt.setString(8, account_no);
            stmt.setString(9, temp_password);
            stmt.setString(10, full_name);
            

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                isRegistered = true;
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isRegistered;
    }

	
}
