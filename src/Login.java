import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JDialog{
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JButton btnSignIn;
    private JButton btnSignUp;
    private JRadioButton rbShowPassword;
    private JPanel loginPanel;
    private JCheckBox cbShowPassword;


    public Login(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(800, 600));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);



        btnSignIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String password = String.valueOf(pfPassword.getPassword());

                user = getAuthenticatedUser(email, password);
                if (user != null) {
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(Login.this,
                            "email or password invalid",
                            "try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                RegistrationForm loginFrame = new RegistrationForm(null);
//                loginFrame.setVisible(true);
//                loginFrame.pack();
//                loginFrame.setLocationRelativeTo(null);
//                //this.dispose();
                dispose();
            }
        });



        cbShowPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox checkBox = (JCheckBox) e.getSource();
                pfPassword.setEchoChar(checkBox.isSelected() ? '\u0000' : '*');
            }
        });
        setVisible(true);
    }
    public User user;
    private User getAuthenticatedUser(String email, String password){
        User user = null;
        final String OB_URL = "jdbc:mysql://localhost/project?serverTimeZone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "peace@966721";
        try {
            Connection conn = DriverManager.getConnection(OB_URL, USERNAME, PASSWORD);

            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM user WHERE email=? AND password=?";
            PreparedStatement prepareStatement = conn.prepareStatement(sql);
            prepareStatement.setString(1, email);
            prepareStatement.setString(2, password);
            //this statement allows us to execute the sql query and the result will be stored in result set
            ResultSet resultset = prepareStatement.executeQuery();

            if (resultset.next()){
                user = new User();
                user.firstname = resultset.getString("firstname");
                user.lastname = resultset.getString("lastname");
                user.email = resultset.getString("email");
                user.homeAddress = resultset.getString("homeAddress");
                user.password = resultset.getString("password");
                user.phone = resultset.getString("phone");
                user.medical = resultset.getString("medical");
                user.issuingAuthority = resultset.getString("issuingAuthority");
                user.certificate = resultset.getString("certificate");
                user.gender = resultset.getString("gender");
                user.blood = resultset.getString("blood");
                user.name1 = resultset.getString("name1");
                user.name2 = resultset.getString("name2");
                user.name3 = resultset.getString("name3");
                user.affiliation1 = resultset.getString("affiliation1");
                user.affiliation2 = resultset.getString("affiliation2");
                user.affiliation3 = resultset.getString("affiliation3");
            }

            stat.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }


    public static void main(String[] args) {
        Login loginForm = new Login(null);
        User user = loginForm.user;
        if (user!= null){
            System.out.println("Successful Authentication of: " + user.firstname + " " + user.lastname);
            System.out.println("          Email " + user.email);
            System.out.println("          Phone " + user.phone);
            System.out.println("          Address " + user.homeAddress);
        }
        else {
            System.out.println("Authentication canceled");
        }
    }
}
