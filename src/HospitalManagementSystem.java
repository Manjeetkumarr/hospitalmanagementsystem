import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class HospitalManagementSystem extends JFrame implements ActionListener {

    JTextField id, name, age, doctor, room, disease;
    JTextField admitDate, dischargeDate, period;
    JComboBox<String> dept, ward;
    JRadioButton male, female;
    JButton insert, update, search;

    public HospitalManagementSystem() {

        setTitle("Hospital Management System");
        setSize(650, 700);
        setLayout(null);

        JLabel l1 = new JLabel("Patient ID:");
        l1.setBounds(50, 50, 100, 30);
        add(l1);
        id = new JTextField();
        id.setBounds(150, 50, 150, 30);
        add(id);

        JLabel l2 = new JLabel("Name:");
        l2.setBounds(50, 90, 100, 30);
        add(l2);
        name = new JTextField();
        name.setBounds(150, 90, 150, 30);
        add(name);

        JLabel l3 = new JLabel("Age:");
        l3.setBounds(50, 130, 100, 30);
        add(l3);
        age = new JTextField();
        age.setBounds(150, 130, 150, 30);
        add(age);

        JLabel l4 = new JLabel("Gender:");
        l4.setBounds(50, 170, 100, 30);
        add(l4);

        male = new JRadioButton("Male");
        female = new JRadioButton("Female");

        male.setBounds(150, 170, 70, 30);
        female.setBounds(220, 170, 80, 30);

        ButtonGroup bg = new ButtonGroup();
        bg.add(male);
        bg.add(female);
        add(male);
        add(female);

        JLabel l5 = new JLabel("Department:");
        l5.setBounds(50, 210, 100, 30);
        add(l5);

        String d[] = {"Cardiology", "Neurology", "Orthopedic"};
        dept = new JComboBox<>(d);
        dept.setBounds(150, 210, 150, 30);
        add(dept);

        JLabel l6 = new JLabel("Doctor:");
        l6.setBounds(50, 250, 100, 30);
        add(l6);
        doctor = new JTextField();
        doctor.setBounds(150, 250, 150, 30);
        add(doctor);

        JLabel l7 = new JLabel("Ward:");
        l7.setBounds(50, 290, 100, 30);
        add(l7);

        String w[] = {"General", "ICU", "Private"};
        ward = new JComboBox<>(w);
        ward.setBounds(150, 290, 150, 30);
        add(ward);

        JLabel l8 = new JLabel("Room No:");
        l8.setBounds(50, 330, 100, 30);
        add(l8);
        room = new JTextField();
        room.setBounds(150, 330, 150, 30);
        add(room);

        JLabel l9 = new JLabel("Disease:");
        l9.setBounds(50, 370, 100, 30);
        add(l9);
        disease = new JTextField();
        disease.setBounds(150, 370, 150, 30);
        add(disease);

        // ADMIT DATE
        JLabel l10 = new JLabel("Admit Date:");
        l10.setBounds(50, 410, 100, 30);
        add(l10);
        admitDate = new JTextField();
        admitDate.setBounds(150, 410, 150, 30);
        add(admitDate);

        // DISCHARGE DATE
        JLabel l11 = new JLabel("Discharge Date:");
        l11.setBounds(50, 450, 120, 30);
        add(l11);
        dischargeDate = new JTextField();
        dischargeDate.setBounds(150, 450, 150, 30);
        add(dischargeDate);

        // PERIOD
        JLabel l12 = new JLabel("Period:");
        l12.setBounds(50, 490, 100, 30);
        add(l12);
        period = new JTextField();
        period.setBounds(150, 490, 150, 30);
        period.setEditable(false);
        add(period);

        // BUTTONS
        insert = new JButton("Insert");
        update = new JButton("Update");
        search = new JButton("Search");

        insert.setBounds(350, 150, 120, 30);
        update.setBounds(350, 200, 120, 30);
        search.setBounds(350, 250, 120, 30);

        add(insert);
        add(update);
        add(search);

        insert.addActionListener(this);
        update.addActionListener(this);
        search.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        try {
            Connection con = DBConnection.getConnection();

            if (con == null) {
                JOptionPane.showMessageDialog(this, "Database Not Connected!");
                return;
            }

            // CALCULATE PERIOD
            LocalDate start = LocalDate.parse(admitDate.getText());
            LocalDate end = LocalDate.parse(dischargeDate.getText());
            long days = ChronoUnit.DAYS.between(start, end);
            period.setText(String.valueOf(days));

            // INSERT
            if (e.getSource() == insert) {
                String query = "INSERT INTO patient VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(query);

                pst.setInt(1, Integer.parseInt(id.getText()));
                pst.setString(2, name.getText());
                pst.setInt(3, Integer.parseInt(age.getText()));
                pst.setString(4, male.isSelected() ? "Male" : "Female");
                pst.setString(5, dept.getSelectedItem().toString());
                pst.setString(6, doctor.getText());
                pst.setString(7, ward.getSelectedItem().toString());
                pst.setInt(8, Integer.parseInt(room.getText()));
                pst.setString(9, disease.getText());
                pst.setDate(10, java.sql.Date.valueOf(admitDate.getText()));
                pst.setDate(11, java.sql.Date.valueOf(dischargeDate.getText()));
                pst.setInt(12, Integer.parseInt(period.getText()));

                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Inserted Successfully!");
            }

            // UPDATE
            if (e.getSource() == update) {
                String query = "UPDATE patient SET name=?, age=?, doctor=?, disease=? WHERE patient_id=?";
                PreparedStatement pst = con.prepareStatement(query);

                pst.setString(1, name.getText());
                pst.setInt(2, Integer.parseInt(age.getText()));
                pst.setString(3, doctor.getText());
                pst.setString(4, disease.getText());
                pst.setInt(5, Integer.parseInt(id.getText()));

                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Updated Successfully!");
            }

            // SEARCH
            if (e.getSource() == search) {
                String query = "SELECT * FROM patient WHERE patient_id=?";
                PreparedStatement pst = con.prepareStatement(query);

                pst.setInt(1, Integer.parseInt(id.getText()));
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    name.setText(rs.getString("name"));
                    age.setText(rs.getString("age"));
                    doctor.setText(rs.getString("doctor"));
                    disease.setText(rs.getString("disease"));
                    admitDate.setText(rs.getString("admit_date"));
                    dischargeDate.setText(rs.getString("discharge_date"));
                    period.setText(rs.getString("period"));

                    JOptionPane.showMessageDialog(this, "Record Found!");
                } else {
                    JOptionPane.showMessageDialog(this, "Not Found!");
                }
            }

            con.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }

    public static void main(String[] args) {
        new HospitalManagementSystem();
    }
}