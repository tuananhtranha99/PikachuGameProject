package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame implements Runnable{
	private int row = 9;
	private int col = 16;
	private ButtonEvent graphicsPanel;
	private JPanel mainPanel;
	private final int MAX_TIME = 300;
	public double time = MAX_TIME; // biến time và lbScore cần để public nhằm truy cập từ các lớp khác
	public JLabel lbScore;
	private JProgressBar progressTime;
	private JMenu menu;
	private Thread t;
	
	
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
				
				//tạo ra thread và cho thread chạy để cho thanh progressTime chạy
				t = new Thread(this);
				t.start();
	}
	
	/**
	 * hàm này tạo ra 1 panel chứa các icon 
	 */
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
	
	/**
	 * tạo ra panel chứa score và progressTime
	 * @return
	 */
	private JPanel createControlPanel() {
		
		// lbScore là 1 JLabel với giá trị ban đầu là 0
		lbScore = new JLabel("0");
		lbScore.setForeground(Color.white); // cài màu text cho label
		lbScore.setFont(new Font(lbScore.getFont().getName(), Font.PLAIN, 30)); // thay đổi font size cho label
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
	
	/**
	 * Khi gọi đến hàm này thì thời gian, điểm, màn chơi được set lại từ đầu nhưng mà 
	 * vẫn chưa ra đâu vào đâu
	 */
	public void newGame() {
		graphicsPanel.removeAll();
		mainPanel = createMainPanel();
		add(mainPanel);
		lbScore.setText("1"); // tại sao chỗ score phải set text khác thì đống icon mới thay đổi ????
		t.interrupt();
		time = MAX_TIME;
		progressTime.setValue(100);
		
		t = new Thread();
		t.start();

		
	}
	
	/**
	 * sử dụng JOptionPane.YES_NO_OPTION để hiển thị ra hộp thoại message với 2 lựa chọn yes và no
	 * @param message: thông báo sẽ hiển thị trên hộp thoại
	 * @param title: tiêu đề hộp thoại
	 * @param t: t = 1 nếu win hoặc lose, t = 0 nếu đang chơi dở mà bấm vào nút new game
	 */
	public void showDialogueNewGame(String message, String title) {
		int select = JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if(select == 0) {
			newGame();
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
			// khi gọi hàm interrupt() của thread thì exception này sẽ xảy ra
			// tạm thời bỏ qua exception này ko xử lí vì chưa hiểu
		}
		int percent = (int) ((double) --time / MAX_TIME * 100);
		progressTime.setValue(percent);
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
	
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();

	}
	
}
