import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyDashBoard extends JFrame{
    private JTextField jtxtField;
    private JButton btnRegister;
    private JPanel dashboardPanel;

    public MyDashBoard(){
        setTitle("Peace Dashboard");
        setContentPane(dashboardPanel);
        setMinimumSize(new Dimension(500, 429));
        setSize(600,500);
        setResizable(false);
        //setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        boolean hasRegisteredUsers = connectToDatabase();
        if (hasRegisteredUsers){
            Login loginForm = new Login(this);
            User user = loginForm.user;

            if (user != null){
                jtxtField.setText(user.firstname + " " + user.lastname);
                setLocationRelativeTo(null);
                setVisible(true);
            }else{
                dispose();
            }
        }
        else {
            RegistrationForm registrationForm = new RegistrationForm(this);
            User user = registrationForm.user;

            if (user!= null) {
                jtxtField.setText(user.firstname + " " + user.lastname);
                setLocationRelativeTo(null);
                setVisible(true);
            }
            else {
                dispose();
            }
        }
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationForm registrationForm = new RegistrationForm(MyDashBoard.this);
                User user = registrationForm.user;
                if (user != null) {
                    JOptionPane.showMessageDialog(MyDashBoard.this,
                            "New user: " + user.firstname + user.lastname,
                            "Successful Registration",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
    private boolean connectToDatabase() {
        boolean hasRegisteredUsers = false;
        //  sql query that allows us to create a database if the database does not exist

        final String MYSQL_SERVER_URL = "jdbc:mysql://localhost/";
        final String OB_URL = "jdbc:mysql://localhost/project?serverTimeZone=UTC";
        // final String OB_URL = "jdbc:mysql://localhost/MyStore?serverTimeZone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "peace@966721";

        try {
            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS project");
            statement.close();
            conn.close();
            conn = DriverManager.getConnection(OB_URL, USERNAME, PASSWORD);
            statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS user ("
                    + "id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "firstname VARCHAR(200) NOT NULL,"
                    + "lastname VARCHAR(200) NOT NULL,"
                    + "email VARCHAR(200) NOT NULL UNIQUE,"
                    + "homeAddress VARCHAR(200) NOT NULL,"
                    + "password VARCHAR(200) NOT NULL,"
                    + "phone VARCHAR(200),"
                    + "medical VARCHAR(200) NOT NULL,"
                    + "issuingAuthority VARCHAR(200) NOT NULL,"
                    + "certificate VARCHAR(200) NOT NULL,"
                    + "gender VARCHAR(200) NOT NULL,"
                    + "blood VARCHAR(200) NOT NULL,"
                    + "name1 VARCHAR(200) NOT NULL,"
                    + "name2 VARCHAR(200) NOT NULL,"
                    + "name3 VARCHAR(200) NOT NULL,"
                    + "affiliation1 VARCHAR(200) NOT NULL,"
                    + "affiliation2 VARCHAR(200) NOT NULL,"
                    + "affiliation3 VARCHAR(200) NOT NULL"
                    + ")";
            statement.executeUpdate(sql);

            // check if we have registered users in the table or not
            statement = conn.createStatement();
            ResultSet resultset = statement.executeQuery("SELECT COUNT(*) FROM user");

            if(resultset.next()){
                int numUsers = resultset.getInt(1);
                if(numUsers > 0){
                    hasRegisteredUsers = true;
                }
            }
            statement.close();
            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return hasRegisteredUsers;
    }
    public static void main(String[] args) {
        MyDashBoard myForm = new MyDashBoard();
    }
}

