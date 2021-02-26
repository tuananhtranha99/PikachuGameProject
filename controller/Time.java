package controller;

import javax.swing.JOptionPane;

public class Time extends Thread{
	MainFrame frame;
	
	public void setFrame(MainFrame frame) {
		this.frame = frame;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			/**
			 * Khi hộp thoại showDialogueNewGame hiện lên thì thread Time sẽ phải đợi
			 * cho đến khi người dùng chọn Yes hoặc No (lúc đó mới có được giá trị 
			 * của biến pause và resume) thì method run() mới tiếp tục chạy
			 */
			
			// hộp thoại hiện lên và người dùng chọn No, thanh thời gian tiếp tục đếm
			if(frame.pause) {
				if(frame.resume) {
					frame.time--;
				}
			} else { // hộp thoại hiện lên và người dùng chọn Yes, trò chơi bắt đầu lại và thanh thời gian lại đếm
				frame.time--;
			}
			
			// Khi hết giờ, 1 hộp thoại hiện lên để người dùng chọn có chơi lại từ đầu hay không, nếu chọn No thì thoát game
			if(frame.time == 0) {
				int select = JOptionPane.showOptionDialog(null, "You Lose! Try again ?", "Time out", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if(select == 0) {
					frame.newGame();
				} else {
					System.exit(0);
				}
			}
		}
	}
}
