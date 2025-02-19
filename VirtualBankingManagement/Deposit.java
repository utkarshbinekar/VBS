package VirtualBankingManagement;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.InputMismatchException;

class Deposit extends JFrame
{
    Deposit(String username) {

        Font f = new Font("Futura", Font.BOLD, 40);
        Font f2 = new Font("Calibri", Font.PLAIN, 22);

        JLabel title = new JLabel("Deposit Money", JLabel.CENTER);
        JLabel label = new JLabel("Enter Amount:");
        JTextField t1 = new JTextField(10);
        JButton b1 = new JButton("Deposit");
        JButton b2 = new JButton("Back");

        title.setFont(f);
        label.setFont(f2);
        t1.setFont(f2);
        b1.setFont(f2);
        b2.setFont(f2);

        Container c = getContentPane();
        c.setLayout(null);

        title.setBounds(200, 30, 400, 50);
        label.setBounds(250, 120, 300, 30);
        t1.setBounds(250, 160, 300, 30);
        b1.setBounds(300, 220, 200, 40);
        b2.setBounds(300, 280, 200, 40);

        c.add(title);
        c.add(label);
        c.add(t1);
        c.add(b1);
        c.add(b2);
        b2.addActionListener(
                a->{
                    new Home(username);
                    dispose();
                }
        );
        b1.addActionListener(
                a -> {
                    double balance = 0.0;
                    double total = 0.0;
                    double amount=0.0;
                    String url = "jdbc:mysql://localhost:3306/3dec";
                    try (Connection con = DriverManager.getConnection(url, "root", "Utkarsh05")) {
                        String sql = "select balance from users where username=?";
                        try (PreparedStatement pst = con.prepareStatement(sql)) {
                            pst.setString(1, username);
                            ResultSet rs = pst.executeQuery();

                            if (rs.next()) {
                                balance = rs.getDouble("balance");
                            } else {
                                System.out.println("user name not found");
                            }
                        }
                        try {
                            String s1 = t1.getText();

                             amount = Double.parseDouble(s1);
                            if (amount >= 0)
                                total = amount + balance;
                            else {

                                JOptionPane.showMessageDialog(null, "Invalid input");
                                t1.setText("");
                                return;
                            }
                        }
                         catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "invalid input");
                            t1.setText("");
                            return;
                        }
                        try (Connection corn = DriverManager.getConnection(url, "root", "Utkarsh05")) {
                            String quer = "update users set balance=? where username=?";
                            try (PreparedStatement pst1 = corn.prepareStatement(quer)) {
                                pst1.setDouble(1, total);
                                pst1.setString(2, username);
                                pst1.executeUpdate();
                                JOptionPane.showMessageDialog(null,"success");
                                updatePassbook(username,"Deposit",amount,balance+amount);
                                t1.setText("");
                                //transactions
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e.getMessage());
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                }
                    );
                    setVisible(true);
                    setSize(800, 550);
                    setLocationRelativeTo(null);
                    setDefaultCloseOperation(EXIT_ON_CLOSE);
                    setTitle("Deposit Money");
                }
                public void updatePassbook(String username,String desc,double amount,double balance){
                    String url = "jdbc:mysql://localhost:3306/3dec";
                    try (Connection con = DriverManager.getConnection(url, "root", "Utkarsh05")){
                        String qur ="insert into transactions (username,description,amount,balance) values(?,?,?,?)";
                        try(PreparedStatement pst = con.prepareStatement(qur)){
                            pst.setString(1,username);
                            pst.setString(2,desc);
                            pst.setDouble(3,amount);
                            pst.setDouble(4,balance);
                            pst.executeUpdate();
                        }
                    }
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e.getMessage());
                    }

}
    public static void main(String[] args) {
        new Deposit("utkarsh_binekar");
    }
}
