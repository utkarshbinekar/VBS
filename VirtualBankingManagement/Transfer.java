package VirtualBankingManagement;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class Transfer extends JFrame {
    Transfer(String username) {
        Font f = new Font("Futura", Font.BOLD, 30);
        Font f2 = new Font("Calibri", Font.PLAIN, 18);

        JLabel title = new JLabel("Transfer Funds", JLabel.CENTER);
        JLabel l1 = new JLabel("Receiver:");
        JTextField t1 = new JTextField(10);

        JLabel l2 = new JLabel("Amount:");
        JTextField t2 = new JTextField(10);

        JButton b1 = new JButton("Transfer");
        JButton b2 = new JButton("Back");

        title.setFont(f);
        l1.setFont(f2);
        t1.setFont(f2);
        l2.setFont(f2);
        t2.setFont(f2);
        b1.setFont(f2);
        b2.setFont(f2);

        Container c = getContentPane();
        c.setLayout(null);

        int labelX = 200, fieldX = 400, yStart = 80, width = 150, height = 30, gap = 40;

        title.setBounds(250, 20, 300, 40);

        l1.setBounds(labelX, yStart, width, height);
        t1.setBounds(fieldX, yStart, width, height);

        l2.setBounds(labelX, yStart + gap, width, height);
        t2.setBounds(fieldX, yStart + gap, width, height);

        b1.setBounds(250, yStart + 2 * gap, 120, 40);
        b2.setBounds(400, yStart + 2 * gap, 120, 40);

        c.add(title);
        c.add(l1);
        c.add(t1);
        c.add(l2);
        c.add(t2);
        c.add(b1);
        c.add(b2);
        b1.addActionListener(
                a->{
                    //ROUND 1
                    double amount=0.0;
                    try{
                        amount = Double.parseDouble(t2.getText());
                    }

                    catch(Exception e){
                        System.out.println("Please enter correct amount");
                        t2.setText("");
                    }
                    if(amount<0){
                        JOptionPane.showMessageDialog(null,"why are u gay u are gay");
                        t2.setText("");
                        return;
                    }
                    String receiver = t1.getText();
                    if(username.equals(receiver)) JOptionPane.showMessageDialog(null,"juice pila do");
                    double balance = fetchBalance(username);
                     if(amount>balance){
                         JOptionPane.showMessageDialog(null,"Insufficeint balance");
                         t2.getText();
                         return;
                     }
                     else{
                         String url= "jdbc:mysql://localhost:3306/3dec";
                         try (Connection corn = DriverManager.getConnection(url, "root", "Utkarsh05")) {
                             String quer = "update users set balance=? where username=?";
                             try (PreparedStatement pst1 = corn.prepareStatement(quer)) {
                                 pst1.setDouble(1, balance-amount);
                                 pst1.setString(2, username);
                                 pst1.executeUpdate();
                                 updatePassbook(username,"Transfer to "+ receiver,-amount,balance-amount);
                                 t1.setText("");
                             }
                         } catch (Exception e) {
                             JOptionPane.showMessageDialog(null, e.getMessage());
                         }
                    }
                     balance = fetchBalance(receiver);
                     if(balance==-1) {
                         JOptionPane.showMessageDialog(null,"kaha se ate hai ye log");
                         t1.setText("");
                         t2.setText("");
                         return;
                     }
                    String url= "jdbc:mysql://localhost:3306/3dec";
                    try (Connection corn = DriverManager.getConnection(url, "root", "Utkarsh05")) {
                        String quer = "update users set balance=? where username=?";
                        try (PreparedStatement pst1 = corn.prepareStatement(quer)) {
                            pst1.setDouble(1, balance+amount);
                            pst1.setString(2, receiver);
                            pst1.executeUpdate();
                            JOptionPane.showMessageDialog(null,"success");
                            updatePassbook(receiver,"Transfer from "+ username,amount,balance+amount);
                            t1.setText("");
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                }
        );
        b2.addActionListener(
                a->{
                    new Home(username);
                    dispose();
                }
        );

        setVisible(true);
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Transfer Funds");
    }
    double fetchBalance(String username){
        double balance = 0.0;
        String url = "jdbc:mysql://localhost:3306/3dec";
        try (Connection con = DriverManager.getConnection(url, "root", "Utkarsh05")) {
            String sql = "select balance from users where username=?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, username);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    balance = rs.getDouble("balance");
                } else {
                   return -1;
                }
            }
        }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            return balance;
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
        new Transfer("utkarsh_binekar");
    }
}
