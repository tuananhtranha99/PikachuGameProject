package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame implements Runnable{
	private int row = 16;
	private int col = 9;
	private ButtonEvent graphicsPanel;
	private JPanel mainPanel;
	private final int MAX_TIME = 300;
	public double time = MAX_TIME; // biến time và lbScore cần để public nhằm truy cập từ các lớp khác
	public JLabel lbScore;
	private JProgressBar progressTime;
	private JMenu menu;
	public boolean pause = false;
	public boolean resume = false;
	
	
	public MainFrame() {
		// tạo thanh menu cho giao diện, có menu New Game để bắt đầu màn mới
				JMenuBar menuBar = new JMenuBar();
				menu = new JMenu("Menu");
				JMenuItem menuItem = new JMenuItem("New Game");
				menuItem.addActionListener(new newGameListener());
				menu.add(menuItem);
				menuBar.add(menu);
				this.setJMenuBar(menuBar);
						
				
				
				
				mainPanel = createMainPanel();
				this.getContentPane().add(mainPanel);
				setTitle("Pikachu");
				setResizable(false);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setSize(900, 700);
				setLocationRelativeTo(null); // chương trình khi chạy sẽ hiện ở giữa màn hình
				setVisible(true);
				

	}
	
	/**
	 * tạo ra Panel chính, panel này sẽ chứa graphicsPanel(các icon) 
	 * và chứa controlPanel(thanh thời gian và điểm)
	 */
	private JPanel createMainPanel() {
		Border padding = BorderFactory.createLineBorder(Color.black, 50);
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(padding); // padding: 50px
		panel.add(createGraphicsPanel(), BorderLayout.CENTER);
		panel.add(createControlPanel(), BorderLayout.NORTH);
		return panel;
	}
	
	/**
	 * panel này chứa các icon và có background màu đen
	 */
	private JPanel createGraphicsPanel() {
		graphicsPanel = new ButtonEvent(this, row, col);
		graphicsPanel.setBackground(Color.black);

		return graphicsPanel;
	}
	
	/**
	 * panel chứa score và thanh thời gian
	 * @return
	 */
	private JPanel createControlPanel() {
		
		// lbScore là 1 JLabel với giá trị ban đầu là 0
		lbScore = new JLabel("0");
		lbScore.setForeground(Color.white); // cài màu text cho label
		lbScore.setFont(new Font("serif", Font.BOLD, 30)); // thay đổi font size cho label
		progressTime = new JProgressBar(0, 100);
		progressTime.setValue(100);
		
		
		// tạo panel phụ chứa score và time
		
		JPanel subPanel = new JPanel(new BorderLayout(70, 0));
		subPanel.setBackground(Color.black);
		subPanel.setBorder(new EmptyBorder(0,100,5,50)); // giống như padding top, left, bottom, right
		subPanel.add(lbScore, BorderLayout.EAST);
		subPanel.add(progressTime, BorderLayout.CENTER);
		
		// padding: 30px
		Border padding = BorderFactory.createLineBorder(Color.black, 30);
		subPanel.setBorder(padding);
		
		
		return subPanel;
	}
	
	/**
	 * Khi gọi đến hàm này thì thời gian, điểm, màn chơi được set lại từ đầu nhưng mà 
	 * vẫn chưa ra đâu vào đâu
	 */
	public void newGame() {
		graphicsPanel.removeAll(); // xoá hết icon hiện tại
		mainPanel.add(createGraphicsPanel()); // tạo ra các icon mới và add vào mainPanel
		mainPanel.validate(); // cập nhật cửa sổ giao diện
//		mainPanel.setVisible(true); // dòng này có tác dụng gì đây? không có thì vẫn hiển thị các icon mới mà
		lbScore.setText("0"); 
		time = MAX_TIME;	
	}
	
	/**
	 * sử dụng JOptionPane.YES_NO_OPTION để hiển thị ra hộp thoại message với 2 lựa chọn yes và no
	 * khi hộp thoại bật lên thì thời gian sẽ dừng lại, chọn yes thì màn mới hiện ra và thời gian bắt đầu 
	 * đếm, chọn no thì thời gian tiếp tục đếm bình thường
	 * @param message: thông báo sẽ hiển thị trên hộp thoại
	 * @param title: tiêu đề hộp thoại
	 */
	public void showDialogueNewGame(String message, String title) {
		pause = true; 
		resume = false;
		int select = JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if(select == 0) {
			pause = false;
			newGame();
		} else {
			resume = true;
			}
		}
	
	
	/**
	 * cứ sau 1 giây thì giảm giá trị biến time đi 1 và cập nhật lại phần trăm của 
	 * thanh progressTime
	 */
	@Override
	public void run() {
		while(true) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		progressTime.setValue((int) ((double) time / MAX_TIME * 100));
		}
		
	}

	/**
	 * sử dụng inner class để tạo ra listener cho nút new game
	 *
	 */
	class newGameListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			showDialogueNewGame("Your game hasn't done. Do you want to create a new game?", "Warning");	
		}
		
	}
	
	
}
