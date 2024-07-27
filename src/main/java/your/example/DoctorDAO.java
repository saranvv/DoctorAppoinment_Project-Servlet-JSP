package your.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {
    public List<Doctor> getAvailableDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors WHERE available = TRUE";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(rs.getInt("id"));
                doctor.setName(rs.getString("name"));
                doctor.setSpecialization(rs.getString("specialization"));
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }
}