<%@ page import="java.util.List" %>
<%@ page import="your.example.Doctor" %>
<%@ page import="your.example.DoctorDAO" %>
<html>
<head>
    <title>Patient Details</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="container">
        <h2>Enter Patient Details</h2>
        <form action="submitPatient" method="post">
            <label>Name:</label>
            <input type="text" name="name" required><br>
            <label>Age:</label>
            <input type="number" name="age" required><br>
            <label>Gender:</label>
            <input type="text" name="gender" required><br>
            <label>Contact Number:</label>
            <input type="text" name="contact_number" required><br><br>

            <h3>Select Doctor</h3>
            <select name="doctor_id">
                <%
                    DoctorDAO doctorDAO = new DoctorDAO();
                    List<Doctor> doctors = doctorDAO.getAvailableDoctors();
                    for (Doctor doctor : doctors) {
                %>
                    <option value="<%= doctor.getId() %>"><%= doctor.getName() %> - <%= doctor.getSpecialization() %></option>
                <% } %>
            </select><br><br>

            <label>Appointment Date:</label>
            <input type="date" name="appointment_date" required><br><br>

            <input type="submit" value="Submit">
        </form>
    </div>
</body>
</html>