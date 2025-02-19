package VirtualBankingManagement;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

class Profile extends JFrame {
    String usernam;
    Profile(String username) {
        usernam = username;
        Font f = new Font("Futura", Font.BOLD, 35);
        Font f2 = new Font("Calibri", Font.PLAIN, 20);

        JLabel title = new JLabel("Profile Settings", JLabel.CENTER);
        title.setFont(f);

        JLabel l1 = new JLabel("Select Field to Update:");
        JComboBox<String> box = new JComboBox<>(new String[]{"username", "password", "phone", "email"});

        JLabel l2 = new JLabel("Enter New Value:");
        JTextField t1 = new JTextField(15);

        JButton b1 = new JButton("Update");
        JButton b2 = new JButton("Back");

        l1.setFont(f2);
        box.setFont(f2);
        l2.setFont(f2);
        t1.setFont(f2);
        b1.setFont(f2);
        b2.setFont(f2);

        Container c = getContentPane();
        c.setLayout(null);

        title.setBounds(250, 20, 300, 40);
        l1.setBounds(200, 100, 200, 30);
        box.setBounds(400, 100, 200, 30);
        l2.setBounds(200, 160, 200, 30);
        t1.setBounds(400, 160, 200, 30);
        b1.setBounds(250, 220, 120, 40);
        b2.setBounds(400, 220, 120, 40);

        c.add(title);
        c.add(l1);
        c.add(box);
        c.add(l2);
        c.add(t1);
        c.add(b1);
        c.add(b2);
        b1.addActionListener(
                ef->{
                    String a=box.getSelectedItem().toString();
                    String url = "jdbc:mysql://localhost:3306/3dec";
                    try (Connection con = DriverManager.getConnection(url, "root", "Utkarsh05")) {
                         String sql = "update users set "+a+" = ? where username=?";

                         try(PreparedStatement pst = con.prepareStatement(sql)) {
                             if(t1.getText().equals("")){
                                 JOptionPane.showMessageDialog(null,"NULL");
                                 return;
                             }
                             pst.setString(1, t1.getText());
                             pst.setString(2, username);
                             pst.executeUpdate();
                             JOptionPane.showMessageDialog(null,"good");
                         }
                        if(box.getSelectedItem().toString().equals("username")) {
                            usernam = t1.getText();
                            dispose();
                            new Profile(usernam);

                        }
                        String sql1 = "update transactions set "+a+" = ? where username=?";
                        try(PreparedStatement pst = con.prepareStatement(sql1)) {
                            if(t1.getText().equals("")){
                                JOptionPane.showMessageDialog(null,"NULL");
                                return;
                            }
                            pst.setString(1, t1.getText());
                            pst.setString(2, username);
                            pst.executeUpdate();
                            JOptionPane.showMessageDialog(null,"good");
                            t1.setText("");
                        }
                    }
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e.getMessage());
                        return;
                    }


                }
        );


b2.addActionListener(
        a->{
            new Home(usernam);
            dispose();
        }
);

        setVisible(true);
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Profile Settings");
    }

    public static void main(String[] args) {
        new Profile("utkarsh_binekar");
    }
}
