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
		this.row = row;
		this.col = col;
		createMatrix();
		showMatrix();
	}
	/**
	 * Tạo list các toạ độ cùa từng icon, kích thước = kích thước ma trận
	 */
	public void createListPoint(){
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				listPoint.add(new Point(i,j));
			}
		}
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
		for(int i = 0; i < (row*col)/2; i++) {
			int index = (rand.nextInt(imgCount) + 1); // giá trị số đại diện cho icon tương ứng
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
	}
	
	/**
	 * Hiển thị ma trận icon
	 */
	public void showMatrix() {
		for(int i = 0; i < this.row; i++) {
			for(int j = 0; j < this.col; j++) {
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
		Controller c = new Controller(16, 9);
		c.showMatrix();
	}
	
	
}
