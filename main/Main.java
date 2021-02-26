package main;

import controller.MainFrame;
import controller.Time;

public class Main {
	public static void main(String[] args) {
		/**
		 * Sử dụng 2 Thread khác nhau, 1 Thread sẽ giảm giá trị thời gian đi
		 * Thread còn lại sẽ cập nhật giá trị thời gian lên thanh thời gian
		 * Mỗi thread khi thực hiện xong 1 lần của mình sẽ chuyển vào trạng thái
		 * sleep để tới lượt thread kia làm việc, việc này diễn ra lần lượt đến khi
		 * trò chơi hết giờ. Vì thế mà việc giảm thời gian và cập nhật thời gian 
		 * lên giao diện được diễn ra cùng lúc.
		 * Nhưng tại sao phải dùng 2 thread, có thể dùng 1 thread mà vừa giảm 
		 * thời gian đi vừa cập nhật nó lên giao diện được không????
		 */
		MainFrame frame = new MainFrame();
		Time time = new Time();
		time.setFrame(frame);
		time.start();
		Thread t = new Thread(frame);
		t.start();
	}
}
