package main.view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Frame implements ActionListener {
    JTextArea ta;
    JTextField textbox;
    JButton b1,b2,b3,b4;
    JButton play, login;
    JPanel controlp, main;
    JFrame f;
    JScrollPane sp;
    public Frame(){
        f = new JFrame("Cryptogrammer");

        f.getContentPane().setBackground(Color.blue);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //f.setLocationRelativeTo(null);
        Container con = f.getContentPane();
        con.setLayout(new BorderLayout());

        controlp = new JPanel();
        main = new JPanel();

        ta = new JTextArea(10,20);
        sp = new JScrollPane(ta);

        textbox = new JTextField(20);
        textbox.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void changedUpdate(DocumentEvent e){

            }
            @Override
            public void insertUpdate(DocumentEvent e){
                updateLabel(e);
            }
            @Override
            public void removeUpdate(DocumentEvent e){

            }

            private void updateLabel(DocumentEvent e) {
                java.awt.EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        ta.append(textbox.getText());
                        textbox.setText("");
                    }
                });
            }
        });

        b1 = new JButton("Check");
        b2 = new JButton("Hint");
        b3 = new JButton("HelloButton3");
        b4 = new JButton("Reset");
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);

        play = new JButton("play");
        login = new JButton("Login");
        play.addActionListener(this);
        login.addActionListener(this);

        controlp.add(play);
        controlp.add(login);

        con.add(controlp, BorderLayout.PAGE_END);
        con.add(main, BorderLayout.CENTER);


        f.setVisible(true);
        f.setSize(new Dimension(300,300));

        Dimension windowSize = f.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();

        int dx = centerPoint.x - windowSize.width/2;
        int dy = centerPoint.y - windowSize.height/2;
        f.setLocation(dx,dy);

    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==play){
            FlowLayout flow = new FlowLayout(FlowLayout.LEFT);
            main.setLayout(flow);
            main.add(sp);
            main.add(textbox);
            main.add(b1);
            main.add(b2);
            main.add(b3);
            main.add(b4);
            f.pack();
        }
        if(e.getSource()==login){
            //GridLayout grid = new GridLayout(2,3);
            //main.setLayout(grid);
            main.remove(ta);
            main.remove(b1);
            main.remove(b2);
            main.remove(b3);
            main.remove(b4);
            //main.validate();
            //main.updateUI();
            f.pack();
        }
        if(e.getSource()==b1){
        }
        if(e.getSource()==b2){
            ta.append("testtest");
            ta.replaceRange("test",1,4);
            main.updateUI();
        }
        if(e.getSource()==b3){

        }
        if(e.getSource()==b4){
            ta.setText("");
            textbox.setText("");
        }
    }
}
