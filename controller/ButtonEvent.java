package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ButtonEvent extends JPanel implements ActionListener{
	private Point p1;
	private Point p2;
	private PointLine line;
	private int score = 0;
	private int item; // tổng số cặp icon
	private MainFrame frame;
	private int row;
	private int col;
	private JButton[][] btn;
	Controller controller;
	
	/**
	 * setup 1 số thứ cho GUI như số hàng, số cột, màu nền, sau đó gọi hàm newGame() để 
	 * tạo ra giao diện trò chơi
	 */
	public ButtonEvent(MainFrame frame,int row, int col) {
		this.frame = frame;
		this.row = row;
		this.col = col;
		item = row * col / 2;
		
		setLayout(new GridLayout(row, col, 2, 2)); // set layout for JPanel as a table
		setBackground(Color.BLACK); // set background color
		setBorder(new EmptyBorder(10, 10, 10, 10));
		
		newGame();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String btnIndex = e.getActionCommand(); // lấy ra toạ độ của button được click
		String[] indexDot = btnIndex.split(","); // tách thành 2 phần là toạ độ x và toạ độ y
		int x = Integer.parseInt(indexDot[0]);
		int y = Integer.parseInt(indexDot[1]);
		if(p1 == null) {
			p1 = new Point(x, y);
			btn[p1.x][p1.y].setBorder(new LineBorder(Color.RED));
		} else {
			p2 = new Point(x,y);
			line = controller.checkTwoPoint(p1, p2);
			if(line != null) {
				System.out.println("line != null");
				controller.getMatrix()[p1.x][p1.y] = 0;
				controller.getMatrix()[p2.x][p2.y] = 0;
				controller.showMatrix();
				execute(p1, p2);
				line = null;
				score += 10;
				item--;
				frame.lbScore.setText("" + score);
			}
			btn[p1.x][p1.y].setBorder(null);
			p1 = null;
			p2 = null;
		}
		
		
	}
	
	/**
	 * Lấy ra icon theo index, các icon có thể resize
	 */
	public Icon getIcon(int index) {
		Image image = new ImageIcon(getClass().getResource("/icon/" + index + ".png")).getImage();
		Icon icon = new ImageIcon(image.getScaledInstance(50, 50, image.SCALE_SMOOTH)); // icon có thể resize
		return icon;
	}
	
	/**
	 * Tạo ra ma trận các index thông qua class Controller
	 * sau đó gọi hàm addArrayButton() để chuyển các index thành các button với các icon tương ứng 
	 */
	public void newGame() {
		controller = new Controller(row, col);
		addArrayButton();
		
	}
	
	/**
	 * Lần lượt lấy ra các icon ứng với các index trong ma trận matrix,lần lượt
	 * gán icon đó cho các button, sau đó thêm button đã có icon vào JPanel
	 */
	public void addArrayButton() {
		btn = new JButton[row][col];
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				btn[i][j] = createButton(i + "," +j); // mỗi nút tự lưu toạ độ của chính nó, đã giải thích ở hàm createButton
				Icon icon = getIcon(controller.getMatrix()[i][j]);
				btn[i][j].setIcon(icon);
				add(btn[i][j]);
			}
		}
	}
	
	/**
	 * Tạo ra 1 button và setup 1 số thứ cho nó
	 */
	private JButton createButton(String action) {
		JButton btn = new JButton();
		
		// action ở đây đóng vai trò là  toạ độ của button
		// ở đây ta sử dụng hàm setActionCommand() để mỗi button tự lưu toạ độ của chính nó
		// và toạ độ này sẽ được lấy ra ở hàm actionPerformed() - lúc button được click
		btn.setActionCommand(action); 
		btn.setBorder(new LineBorder(Color.BLACK));
		btn.addActionListener(this);
		return btn;
	}
	
	public void execute(Point p1, Point p2) {
		setDisable(btn[p1.x][p1.y]);
		setDisable(btn[p2.x][p2.y]);
	}
	
	private void setDisable(JButton btn) {
		btn.setIcon(null);
		btn.setBackground(Color.black);
		btn.setEnabled(false);
	}

}
