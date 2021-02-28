package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame implements ActionListener {
    JTextArea ta;
    JTextField tf;
    JButton b1,b2,b3,b4;
    JButton play, login;
    JPanel controlp, main;
    JFrame f;
    public Frame(){
        f = new JFrame();
        f.getContentPane().setBackground(Color.BLACK);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container con = f.getContentPane();
        con.setLayout(new BorderLayout());

        controlp = new JPanel();
        main = new JPanel();

        ta = new JTextArea(10,20);
        JScrollPane sp = new JScrollPane(ta);

        tf = new JTextField("Enter here",  10);
        b1 = new JButton("HelloButton1");
        b2 = new JButton("HelloButton2");
        b3 = new JButton("HelloButton3");
        b4 = new JButton("HelloButton4");
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);

        main.add(sp);
        main.add(tf);
        main.add(b1);
        main.add(b2);
        main.add(b3);
        main.add(b4);

        play = new JButton("play");
        login = new JButton("Login");
        play.addActionListener(this);
        login.addActionListener(this);

        controlp.add(play);
        controlp.add(login);

        con.add(controlp, BorderLayout.PAGE_END);
        con.add(main, BorderLayout.CENTER);

        f.setVisible(true);
        f.pack();
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==play){
            FlowLayout flow = new FlowLayout(FlowLayout.LEFT);
            main.setLayout(flow);
            main.add(ta);
            main.add(b1);
            main.add(b2);
            main.add(b3);
            main.add(b4);
            main.validate();
            main.updateUI();
        }
        if(e.getSource()==login){
            GridLayout grid = new GridLayout(2,3);
            main.setLayout(grid);
            main.remove(ta);
            main.remove(b1);
            main.remove(b2);
            main.remove(b3);
            main.remove(b4);
            main.validate();
            main.updateUI();
        }
        if(e.getSource()==b1){
            ta.append("aaaaa");
            main.updateUI();
        }
        if(e.getSource()==b2){
            ta.replaceRange("test",1,4);
            main.updateUI();
        }
    }
}
