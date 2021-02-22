package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame implements ActionListener, Runnable{
	private int row = 9;
	private int col = 16;
	private ButtonEvent graphicsPanel;
	private JPanel mainPanel;
	private final int MAX_TIME = 300;
	public int time = MAX_TIME; // biến time và lbScore cần để public nhằm truy cập từ các lớp khác
	public JLabel lbScore;
	private JProgressBar progressTime;
	
	
	public MainFrame() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("New Game");
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		add(mainPanel = createMainPanel());
		setTitle("Pikachu");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 700);
		setLocationRelativeTo(null); // chương trình khi chạy sẽ hiện ở giữa màn hình
		setVisible(true);
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		graphicsPanel = new ButtonEvent(this, row, col);
		JPanel subPanel = new JPanel(new GridBagLayout());
		subPanel.setBackground(Color.black);
		subPanel.add(graphicsPanel);
		panel.add(subPanel, BorderLayout.CENTER);
		panel.add(createControlPanel(), BorderLayout.NORTH);
		return panel;
	}
	
	private JButton createButton(String buttonName) {
		JButton btn = new JButton(buttonName);
		btn.addActionListener(this);
		return btn;
	}
	
	private JPanel createControlPanel() {
		// lbScore là 1 JLabel với giá trị ban đầu là 0
		lbScore = new JLabel("0");
		lbScore.setForeground(Color.white); // cài màu text cho label
		lbScore.setFont(new Font(lbScore.getFont().getName(), Font.PLAIN, 30)); // thay đổi font size cho label
		UIManager.put("ProgressBar.foreground", Color.yellow); // thay đổi màu của progressbar
		progressTime = new JProgressBar(0, 100);
		progressTime.setValue(100);

		
		
		// tạo panel phụ chứa score và time
		
		JPanel subPanel = new JPanel(new BorderLayout(70, 0));
		subPanel.setBackground(Color.black);
		subPanel.setBorder(new EmptyBorder(50,100,5,50)); // giống như padding top, left, bottom, right
		subPanel.add(lbScore, BorderLayout.EAST);
		subPanel.add(progressTime, BorderLayout.CENTER);
		
		return subPanel;
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
