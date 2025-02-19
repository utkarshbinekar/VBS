package VirtualBankingManagement;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class Alogin extends JFrame {
    Alogin() {
        Font f = new Font("Futura", Font.BOLD, 40);
        Font f2 = new Font("Calibri", Font.PLAIN, 22);

        JLabel title = new JLabel("Admin Login", JLabel.CENTER);
        JLabel l1 = new JLabel("Enter Username");
        JTextField t1 = new JTextField(10);
        JLabel l2 = new JLabel("Enter Password");
        JPasswordField p1 = new JPasswordField(10);
        JButton b1 = new JButton("Submit");
        JButton b2 = new JButton("Back");

        title.setFont(f);
        l1.setFont(f2);
        t1.setFont(f2);
        l2.setFont(f2);
        p1.setFont(f2);
        b1.setFont(f2);
        b2.setFont(f2);

        Container c = getContentPane();
        c.setLayout(null);

        title.setBounds(250, 30, 300, 50);
        l1.setBounds(250, 100, 300, 30);
        t1.setBounds(250, 140, 300, 30);
        l2.setBounds(250, 200, 300, 30);
        p1.setBounds(250, 240, 300, 30);
        b1.setBounds(300, 300, 200, 40);
        b2.setBounds(300, 360, 200, 40);

        c.add(title);
        c.add(l1);
        c.add(t1);
        c.add(l2);
        c.add(p1);
        c.add(b1);
        c.add(b2);

        b1.addActionListener(
                a->{
                    String url = "jdbc:mysql://localhost:3306/3dec";
                    try(Connection con = DriverManager.getConnection(url,"root","Jishnu@2005")){
                        String sql = "select * from admin where username=? and password=?";
                        try(PreparedStatement pst = con.prepareStatement(sql)){
                            String s = new String(p1.getPassword());
                            pst.setString(1,t1.getText());
                            pst.setString(2,s);

                            ResultSet rs = pst.executeQuery();

                            if(rs.next()){
                                JOptionPane.showMessageDialog(null, "Successful");
                                new Adashboard();
                                dispose();
                            }
                            else{
                                JOptionPane.showMessageDialog(null,"user does not exist");
                            }
                        }
                    }
                    catch (Exception e){
                        JOptionPane.showMessageDialog(null,e.getMessage());
                    }
                }
        );
        b2.addActionListener(
                a->{
                    new Landing();
                    dispose();
                }
        );

        setVisible(true);
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Admin Login");
    }

    public static void main(String[] args) {
        new Alogin();
    }
}
