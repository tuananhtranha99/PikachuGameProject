package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame implements ActionListener, Runnable{
	private int row = 8;
	private int col = 8;
	private ButtonEvent graphicsPanel;
	private JPanel mainPanel;
	
	public MainFrame() {
		add(mainPanel = createMainPanel());
		setTitle("POKEMON GO");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 500);
		setLocationRelativeTo(null); // chương trình khi chạy sẽ hiện ở giữa màn hình
		setVisible(true);
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		graphicsPanel = new ButtonEvent(this, row, col);
		JPanel subPanel = new JPanel(new GridBagLayout());
		subPanel.setBackground(Color.ORANGE);
		subPanel.add(graphicsPanel);
		panel.add(subPanel, BorderLayout.CENTER);
		return panel;
	}
	
	
	@Override
	public void run() {
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
	}
	
}
