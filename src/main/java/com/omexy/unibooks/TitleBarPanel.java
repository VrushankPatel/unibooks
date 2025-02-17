package com.omexy.unibooks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TitleBarPanel extends JPanel {

    private Point initialClick;
    private JFrame parent;

    public TitleBarPanel(JFrame parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(parent.getWidth(), 30));
        setBackground(new Color(60, 63, 65));

        JLabel titleLabel = new JLabel("Unibooks");
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.WEST);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        controlPanel.setOpaque(false);

        JButton minimizeButton = new JButton("-");
        minimizeButton.addActionListener(e -> parent.setState(Frame.ICONIFIED));
        controlPanel.add(minimizeButton);

        JButton closeButton = new JButton("X");
        closeButton.addActionListener(e -> System.exit(0));
        controlPanel.add(closeButton);

        add(controlPanel, BorderLayout.EAST);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int thisX = parent.getLocation().x;
                int thisY = parent.getLocation().y;

                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                parent.setLocation(X, Y);
            }
        });
    }
} 