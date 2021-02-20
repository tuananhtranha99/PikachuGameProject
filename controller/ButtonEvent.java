package controller;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ButtonEvent extends JPanel implements ActionListener{
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
		
		setLayout(new GridLayout(row, col, 2, 2)); // set layout for JPanel as a table
		setBackground(Color.BLACK); // set background color
		setBorder(new EmptyBorder(10, 10, 10, 10));
		
		newGame();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Lấy ra icon theo index, các icon có thể resize
	 */
	public Icon getIcon(int index) {
		Image image = new ImageIcon(getClass().getResource("/icon/" + index + ".png")).getImage();
		Icon icon = new ImageIcon(image.getScaledInstance(48, 48, image.SCALE_SMOOTH)); // icon có thể resize
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
				btn[i][j] = createButton(i + "," +j); // chưa hiểu chỗ này?
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
		btn.setActionCommand(action); // what does this mean?
		btn.setBorder(null);
		btn.addActionListener(this);
		return btn;
	}

}
