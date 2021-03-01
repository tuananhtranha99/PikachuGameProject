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
			return new PointLine(p1, p2);
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
	
	
	private boolean checkRectX(Point p1, Point p2) {
		System.out.println("check rect x");
		
		Point pMinY = p1, pMaxY = p2;
		if(p1.y > p2.y) {
			pMinY = p2;
			pMaxY = p1;
		}
		for(int y = pMinY.y; y <= pMaxY.y; y++) {
			if(y > pMinY.y && matrix[pMinY.x][y] != 0) {
				return false;
			}
			
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
