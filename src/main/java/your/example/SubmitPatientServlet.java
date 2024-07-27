package your.example;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/submitPatient")
public class SubmitPatientServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String gender = request.getParameter("gender");
        String contactNumber = request.getParameter("contact_number");
        int doctorId = Integer.parseInt(request.getParameter("doctor_id"));
        Date appointmentDate = Date.valueOf(request.getParameter("appointment_date"));

        try (Connection conn = DBUtil.getConnection()) {
            String insertPatientSQL = "INSERT INTO patients (name, age, gender, contact_number) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertPatientSQL, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, name);
                pstmt.setInt(2, age);
                pstmt.setString(3, gender);
                pstmt.setString(4, contactNumber);
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int patientId = rs.getInt(1);

                    String insertAppointmentSQL = "INSERT INTO appointments (patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
                    try (PreparedStatement pstmt2 = conn.prepareStatement(insertAppointmentSQL)) {
                        pstmt2.setInt(1, patientId);
                        pstmt2.setInt(2, doctorId);
                        pstmt2.setDate(3, appointmentDate);
                        pstmt2.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("success.jsp");
    }
}