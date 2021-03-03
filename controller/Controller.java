package controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Controller {
	private Random rand = new Random();
	private int row ;
	private int col ;
	private int[][] matrix; // ma trận chứa giá trị số tương ứng với từng icon
	private ArrayList<Point> listPoint = new ArrayList<>(); // arraylist chứa vị trí của các Point
	
	public Controller(int row, int col) {
		this.row = row +2;
		this.col = col +2;
		createMatrix();
		showMatrix();
	}
	/**
	 * Tạo list các toạ độ cùa từng icon, kích thước = kích thước ma trận
	 */
	public void createListPoint(){
		for(int i = 1; i < row-1; i++) {
			for(int j = 1; j < col-1; j++) {
				listPoint.add(new Point(i,j));
			}
		}
		System.out.println(listPoint.size());
	}
	
	/**
	 * Lấy ra vị trí cho các icon từ listPoint 1 cách ngẫu nhiên và gán index cho vị trí đó 
	 * 1 giá trị index được gán ở 2 vị trí (theo quy luật của game)
	 * sau khi vị trí đã được gán giá trị thì vị trí đó sẽ được xoá khỏi listPoint để không lặp lại việc gán giá trị cho vị trí đó lần nữa
	 */
	public void createMatrix() {
		createListPoint();
		matrix = new int[row][col];
		
		
		int imgCount = 21; // số lượng icon
		int max = 8;
		int []arr = new int[imgCount + 1]; // mảng để theo dõi các icon đã vượt quá 4 lần xuất hiện hay chưa, nếu chưa thì gán giá trị cho 2 vị trí
		for(int i = 0; i < (row-2)*(col-2)/2; i++) {
			int index = rand.nextInt(imgCount) + 1; // giá trị số đại diện cho icon tương ứng
			if(arr[index] < max) {
				for(int j = 0; j < 2; j++) {
					int pointIndex = rand.nextInt(listPoint.size());
					matrix[listPoint.get(pointIndex).x][listPoint.get(pointIndex).y] = index;
					listPoint.remove(pointIndex);
				}
				arr[index] += 2;
			}
			else i--; // phải giảm i đi vì khi arr[index] >= max thì sẽ không có vị trí nào được gán giá trị index (mỗi lần lặp i thì phải có 2 vị trí được gán giá trị)
		}
		for(int i = 0; i < col; i++) {
			matrix[0][i] = matrix[row - 1][i] = 0;
		}
		for (int i = 0; i < row; i++) {
            matrix[i][0] = matrix[i][col - 1] = 0;
        }
	}
	
	/**
	 * Hiển thị ma trận icon
	 */
	public void showMatrix() {
		for(int i = 1; i < this.row-1; i++) {
			for(int j = 1; j < this.col-1; j++) {
				System.out.printf("%3d", this.matrix[i][j] );
			}
			System.out.println();
		}
	}
	
	public PointLine checkTwoPoint(Point p1, Point p2) {
		if(!p1.equals(p2) && matrix[p1.x][p1.y] == matrix[p2.x][p2.y]) {
			
			// 2 item nằm cùng 1 hàng
			if(p1.x == p2.x) {
				if(checkLineX(p1.y, p2.y, p1.x)) {
					return new PointLine(p1, p2);
				}
			}
			 // 2 item nằm cùng 1 cột
			if(p1.y == p2.y) {
				if(checkLineY(p1.x, p2.x, p1.y)) {
					return new PointLine(p1, p2);
				}
			}
			
			// đường nối 2 item nằm trong hình chữ nhật check theo chiều ngang
			if(checkRectX(p1, p2)) {
				return new PointLine(p1, p2);
			}
			
			// đường nối 2 item nằm trong hình chữ nhật check theo chiều dọc
			if(checkRectY(p1, p2)) {
				return new PointLine(p1, p2);
			}
			
			// đường nối 2 item vượt ra ngoài bên phải 
			if(checkMoreLineX(p1, p2, 1)) {
				return new PointLine(p1, p2);
			}
			
			// // đường nối 2 item vượt ra ngoài bên trái 
			if(checkMoreLineX(p1, p2, -1)) {
				return new PointLine(p1, p2);
			}
			
			// // đường nối 2 item vượt ra ngoài bên dưới 
			if(checkMoreLineY(p1, p2, 1)) {
				return new PointLine(p1, p2);
			}
			
			// // đường nối 2 item vượt ra ngoài bên trên 
			if(checkMoreLineY(p1, p2, -1)) {
				return new PointLine(p1, p2);
			}
		}
		return null;
	}
	
	
	/**
	 * nếu 2 item nằm trên cùng 1 hàng thì chúng sẽ cùng x
	 * @param y1: cột của item 1
	 * @param y2: cột của item 2
	 * @param x: hàng mà item nằm
	 * @return true nếu từ item1 sang item2 toàn 0, ngược lại return false
	 */
	private boolean checkLineX(int y1, int y2, int x) {
		System.out.println("check line x");
		
		int min = Math.min(y1, y2);
		int max = Math.max(y1, y2);
		
		for(int y = min+1; y < max; y++) {
			if(matrix[x][y] != 0) {
				System.out.println("die: " + x + y);
				return false;
			}
			System.out.println("ok: " + x + y);
		}
		return true;
	}
	
	/**
	 * nếu 2 item nằm trên cùng 1 cột thì chúng sẽ cùng y, cách hoạt động tương tự 
	 * hàm checkLineX()
	 */
	private boolean checkLineY(int x1, int x2, int y) {
		System.out.println("check line y");
		int min = Math.min(x1, x2);
		int max = Math.max(x1, x2);
		for(int x = min+1; x < max; x++) {
			if(matrix[x][y] != 0) {
				System.out.println("die: " + x + y);
				return false;
			}
			System.out.println("ok: " + x + y);
		}
		return true;
	}
	
	
	/**
	 * Đây là trường hợp 2 item được nối với nhau theo chiều ngang
	 * bằng tối đa 3 đường trong phạm vi hình chữ nhật 
	 * @param p1: điểm có y nhỏ hơn (điểm nằm bên trái)
	 * @param p2: điểm có y lớn hơn (điểm nằm bên phải)
	 * @return true nếu 3 đường đều thoả mãn không bị chặn
	 */
	private boolean checkRectX(Point p1, Point p2) {
		System.out.println("check rect x");
		
		Point pMinY = p1, pMaxY = p2;
		if(p1.y > p2.y) {
			pMinY = p2;
			pMaxY = p1;
		}
		for(int y = pMinY.y; y <= pMaxY.y; y++) {
			
			// kiểm tra đoạn đầu tiên (đoạn ngang) có bị chặn không
			if(y > pMinY.y && matrix[pMinY.x][y] != 0) {
				return false;
			}
			
			// kiểm tra đoạn thứ 2 (đoạn dọc) và đoạn thứ 3 (đoạn ngang) có bị chặn không
			if(matrix[pMaxY.x][y] == 0 
				&& checkLineY(pMinY.x, pMaxY.x, y)
				&& checkLineX(y, pMaxY.y, pMaxY.x)) {
				System.out.println("Rect x");
                System.out.println("(" + pMinY.x + "," + pMinY.y + ") -> ("
                        + pMinY.x + "," + y + ") -> (" + pMaxY.x + "," + y
                        + ") -> (" + pMaxY.x + "," + pMaxY.y + ")");
                return true;
			}
		}
		return false;
	}
	
	/**
	 * Đây là trường hợp 2 item được nối với nhau theo chiều dọc
	 * bằng tối đa 3 đường trong phạm vi hình chữ nhật 
	 * @param p1: điểm có x nhỏ hơn (điểm nằm bên trên)
	 * @param p2: điểm có x lớn hơn (điểm nằm bên dưới)
	 * @return true nếu 3 đường đều thoả mãn không bị chặn
	 */
	private boolean checkRectY(Point p1, Point p2) {
		System.out.println("check rect y");
		
		Point pMinX = p1, pMaxX = p2;
		if(p1.x > p2.x) {
			pMinX = p2;
			pMaxX = p1;
		}
		for(int x = pMinX.x; x <= pMaxX.x; x++) {
			
			// kiểm tra đoạn đầu tiên (đoạn dọc) có bị chặn không
			if(x > pMinX.x && matrix[x][pMinX.y] != 0) {
				return false;
			}
			
			// kiểm tra đoạn thứ 2 (đoạn ngang) và đoạn thứ 3 (đoạn dọc) có bị chặn không
			if(matrix[x][pMaxX.y] == 0 
					&& checkLineX(pMinX.y, pMaxX.y, x) 
					&& checkLineY(x, pMaxX.x, pMaxX.y)) {
				
				System.out.println("Rect y");
                System.out.println("(" + pMinX.x + "," + pMinX.y + ") -> (" + x
                        + "," + pMinX.y + ") -> (" + x + "," + pMaxX.y
                        + ") -> (" + pMaxX.x + "," + pMaxX.y + ")");
                return true;
			} 
		}
		return false;
	}
	
	/**
	 * Đầu tiên phải check xem đoạn nối từ p1 đến điểm nằm ở góc trên bên phải của hình chữ nhật có bị chặn không
	 * nếu đoạn ấy không bị chặn thì tiếp tục kiểm tra xem đường nối bên ngoài hình chữ nhật(đoạn thứ 2 - nằm dọc) và đoạn thứ 3(nằm ngang) 
	 * có bị chặn không 
	 * @param p1: điểm nằm bên trái	
	 * @param p2: điểm nằm bên phải
	 * @param type: xác định đường nối vượt ra ngoài bên phải hay ngoài bên trái
	 * @return true nếu không đoạn nào bị chặn
	 */
	private boolean checkMoreLineX(Point p1, Point p2, int type) {
		System.out.println("check more x");
		
		Point pMinY = p1, pMaxY = p2;
		if(p1.y > p2.y) {
			pMinY = p2;
			pMaxY = p1;
		}
		
		int y = pMaxY.y + type;
		int row = pMinY.x;
		int colFinish = pMaxY.y;
		if(type == -1) {
			colFinish = pMinY.y;
			y = pMinY.y + type;
			row = pMaxY.x;
			System.out.println("colFinish = " + colFinish);
		}
		
		if((matrix[row][colFinish] == 0 || pMinY.y == pMaxY.y)
				&& checkLineX(pMinY.y, pMaxY.y, row)) {
			while(matrix[pMinY.x][y] == 0 
					&& matrix[pMaxY.x][y] == 0) {
				if(checkLineY(pMinY.x, pMaxY.x, y)) {
					System.out.println("TH X " + type);
                    System.out.println("(" + pMinY.x + "," + pMinY.y + ") -> ("
                            + pMinY.x + "," + y + ") -> (" + pMaxY.x + "," + y
                            + ") -> (" + pMaxY.x + "," + pMaxY.y + ")");
				return true;
				}
				y += type;
			}
		}
		return false;
	}
	
	/**
	 * Đầu tiên phải check xem đoạn nối từ p1 đến điểm nằm ở góc dưới bên trái của hình chữ nhật có bị chặn không
	 * nếu đoạn ấy không bị chặn thì tiếp tục kiểm tra xem đường nối bên ngoài hình chữ nhật(đoạn thứ 2 - nằm ngang) và đoạn thứ 3(nằm dọc) 
	 * có bị chặn không 
	 * @param p1: điểm nằm bên trái	
	 * @param p2: điểm nằm bên phải
	 * @param type: xác định đường nối vượt ra ngoài bên phải hay ngoài bên trái
	 * @return true nếu không đoạn nào bị chặn
	 */
	private boolean checkMoreLineY(Point p1, Point p2, int type) {
		System.out.println("check more y");
		Point pMinX = p1, pMaxX = p2;
		if(p1.x > p2.x) {
			pMinX = p2;
			pMaxX = p1;
		}
		int x = pMaxX.x + type;
		int col = pMinX.y;
		int rowFinish = pMaxX.x;
		if(type == -1) {
			x = pMinX.x + type;
			rowFinish = pMinX.x;
			col = pMaxX.y;
		}
		if((matrix[rowFinish][col] == 0 ) || pMinX.x == pMaxX.x
				&& checkLineY(pMinX.x, pMaxX.x, col)) {
			while(matrix[x][pMinX.y] == 0 
					&& matrix[x][pMaxX.y] == 0) {
				if(checkLineX(pMinX.y, pMaxX.y, x)) {
					System.out.println("TH Y " + type);
                    System.out.println("(" + pMinX.x + "," + pMinX.y + ") -> ("
                            + x + "," + pMinX.y + ") -> (" + x + "," + pMaxX.y
                            + ") -> (" + pMaxX.x + "," + pMaxX.y + ")");
					return true;
				}
				x += type;
			}
				
			}
		return false;
		}
	
	
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public int[][] getMatrix() {
		return matrix;
	}
	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}
	public static void main(String[] args) {
		Controller c = new Controller(4, 4);
	}
	
	
}
