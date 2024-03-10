import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class RegistrationForm extends JDialog{
    private JTextField tfFName;
    private JTextField tfLName;
    private JTextField tfEmail;
    private JTextField tfPhone;
    private JTextField tfAddress;
    private JTextField tfMedical;
    private JTextField tfIssuingAuthority;
    private JTextField tfCertificate;
    private JComboBox cbGender;
    private JTextField tf1Name;
    private JTextField tf2Name;
    private JTextField tf3Name;
    private JTextField tf1Affiliation;
    private JTextField tf2Affiliation;
    private JTextField tf3Affiliation;
    private JPasswordField pfPassword;
    private JPasswordField pfConfirmPassword;
    private JCheckBox showPasswordCheckBox;

    private JButton btnSignUp;
    private JButton btnsignin;
    private JPanel signuppanel;
    private JComboBox cbBlood;

    public RegistrationForm(JFrame parent){
        super(parent);
        setTitle("Create a new account");
        setContentPane(signuppanel);
        setMinimumSize(new Dimension(1200,800));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }


        });
        btnsignin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login loginFrame = new Login(null);
                loginFrame.setVisible(true);
                loginFrame.pack();
                loginFrame.setLocationRelativeTo(null);
                dispose();
            }
        });

        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox checkBox = (JCheckBox) e.getSource();
                pfPassword.setEchoChar(checkBox.isSelected() ? '\u0000' : '*');
                pfConfirmPassword.setEchoChar(checkBox.isSelected() ? '\u0000' : '*');


            }
        });


        setVisible(true);

    }
    private void registerUser() {
   String firstname = tfFName.getText();
   String lastname = tfLName.getText();
   String email = tfEmail.getText();
        if (isValidEmail(email)) {
            // Email is valid, proceed with registration
            // Your registration logic here

        } else {            // Invalid email format
            JOptionPane.showMessageDialog(this, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);        }

        String phone = tfPhone.getText();
        if (isValidPhoneNumber(phone)) {
            // Phone number is valid, proceed with registration
            // Your registration logic here
        } else {
            // Invalid phone number format
            JOptionPane.showMessageDialog(this, "Invalid phone number format.", "Error", JOptionPane.ERROR_MESSAGE);
        }

   String homeAddress = tfAddress.getText();
   String medical = tfMedical.getText();
        if (medical.isEmpty()) {
            // Medical field is empty, show an error message
            JOptionPane.showMessageDialog(null, "Medical field cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Medical field is not empty, proceed with further validation or processing
            // For example, check if it contains only letters and digits
            if (!medical.matches("[a-zA-Z0-9\\s]+")) {
                JOptionPane.showMessageDialog(null, "Medical field can only contain letters, digits, and spaces.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Medical field is valid
                // You can proceed with using the medical string
            }
        }
   String issuingAuthority = tfIssuingAuthority.getText().trim();
        if (issuingAuthority.isEmpty()) {
            // Issuing authority is empty, show an error message
            JOptionPane.showMessageDialog(null, "Issuing authority cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Issuing authority is not empty, proceed with further validation or processing
            // For example, check if it contains only letters and spaces
            if (!issuingAuthority.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(null, "Issuing authority can only contain letters and spaces.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Issuing authority is valid
                // You can proceed with using the issuingAuthority string
            }
        }
        String certificate = tfCertificate.getText();
        String gender = (String) cbGender.getSelectedItem();
        //JOptionPane.showMessageDialog(RegistrationForm.this, "Selected Gender:" + gender);
        String blood = (String) cbBlood.getSelectedItem();
        //JOptionPane.showMessageDialog(RegistrationForm.this, "Selected Gender:" + BloodGroup);

        String name1 = tf1Name.getText();
   String name2 = tf2Name.getText();
   String name3 = tf3Name.getText();
   String affiliation1 = tf1Affiliation.getText();
   String affiliation2 = tf2Affiliation.getText();
   String affiliation3 = tf3Affiliation.getText();
   String password = String.valueOf(pfPassword.getPassword());
   if (isValidPassword(password.toCharArray())){

   }else {
       JOptionPane.showMessageDialog(RegistrationForm.this, "Password is not strong enough, Please try again.", "Error", JOptionPane.ERROR_MESSAGE);

   }
   String confirmPassword = String.valueOf(pfConfirmPassword.getPassword());
        if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty()
                || phone.isEmpty() ||homeAddress.isEmpty() || medical.isEmpty()
                || issuingAuthority.isEmpty() || certificate.isEmpty() || name1.isEmpty() || name2.isEmpty() || name3.isEmpty()
                || affiliation1.isEmpty() || affiliation2.isEmpty() || affiliation3.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "please enter all fields",
                    "try again",
                    JOptionPane.ERROR_MESSAGE);
            return;

        }
        if (!password.equals(confirmPassword)){
            JOptionPane.showMessageDialog(this,
                    "confirm password does not match",
                    "try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to register with the following details?\nName: " + firstname + "\nEmail: " + email, "Confirm Details", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // Proceed with registration
            // Your registration logic here
            dispose(); // Close the dialog after registration
        }
        user = addUserToDatabase(firstname, lastname, email,  homeAddress, password, phone, medical, issuingAuthority, certificate, gender, blood, name1
        , name2, name3, affiliation1, affiliation2,affiliation3);
        if (user != null){
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "failed to register new user",
                    "try again",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhoneNumber(String phone) {
        // Phone number must be in the format XXX-XXX-XXXX
        String phoneRegex = "\\d{3}-\\d{3}-\\d{4}";
        return phone.matches(phoneRegex);
    }
    private boolean isValidPassword(char[] password){
     String StringPassword = new String(password) ;
     // check if at least 8 characters long and contains uppercase, lowercase, and digits
     return StringPassword.length() >= 8 && StringPassword.matches(".*[A-Z].*") && StringPassword.matches(".*[a-z].*") && StringPassword.matches(".*\\d.*");
    }

    public User user;
    private User addUserToDatabase(String firstname,String lastname,String email, String homeAddress, String password, String phone, String medical,
                                   String issuingAuthority, String certificate, String gender, String blood, String name1
            , String name2,String name3,String affiliation1,String affiliation2, String affiliation3) {
        User user = null;
        // let's define some variables that allows us to connect to a database
        final String OB_URL = "jdbc:mysql://localhost/project?serverTimeZone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try{
            Connection conn = DriverManager.getConnection(OB_URL,USERNAME,PASSWORD);
            // connected to database successful......

            //we need to create a sql statement that allows us to add new user
            Statement stat = conn.createStatement();
            String sql = "INSERT INTO users (firstname, lastname, email, homeAddress,  password, phone, medical, issuingAuthority, certificate, gender, blood, name1, name2, name3, affiliation1, affiliation2, affiliation3 ) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prepareStatement = conn.prepareStatement(sql);
            prepareStatement.setString(1, firstname);
            prepareStatement.setString(2, lastname);
            prepareStatement.setString(3, email);
            prepareStatement.setString(4, homeAddress);
            prepareStatement.setString(5, password);
            prepareStatement.setString(6, phone);
            prepareStatement.setString(7, medical);
            prepareStatement.setString(8, issuingAuthority);
            prepareStatement.setString(9, certificate);
            prepareStatement.setString(10,  gender);
            prepareStatement.setString(11,  blood);
            prepareStatement.setString(12, name1);
            prepareStatement.setString(13, name2);
            prepareStatement.setString(14, name3);
            prepareStatement.setString(15, affiliation1);
            prepareStatement.setString(16, affiliation2);
            prepareStatement.setString(17, affiliation3);
            //Insert row into the table
            int addedRow = prepareStatement.executeUpdate();
            if(addedRow > 0){
                user = new User();
                user.firstname = firstname;
                user.lastname = lastname;
                user.email = email;
                user.homeAddress = homeAddress;
                user.password = password;
                user.phone = phone;
                user.medical = medical;
                user.issuingAuthority = issuingAuthority;
                user.certificate = certificate;
                user.gender = gender;
                user.blood = blood;
                user.name1 = name1;
                user.name2 = name2;
                user.name3 = name3;
                user.affiliation1 = affiliation1;
                user.affiliation2 = affiliation2;
                user.affiliation3 = affiliation3;

            }
            stat.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }


        public static void main (String[]args){
            RegistrationForm myForm = new RegistrationForm(null);

            //RegistrationForm myForm = new RegistrationForm(null);
            User user = myForm.user;
            if(user!= null){
                System.out.println("Successful registration of: " + user.firstname + " " + user.lastname);
            }else {
                System.out.println("Registration canceled");
            }
        }
    }
