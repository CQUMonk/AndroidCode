package cqu.cqumonk.androidcode.model;

import java.util.Random;

import android.util.Log;
import cqu.cqumonk.androidcode.model.Dot.Status;
import cqu.cqumonk.androidcode.utils.Utils;


public class DotMatrix {

	private Dot[][] dotMatrix;

	public DotMatrix() {
		dotMatrix = new Dot[Utils.ROW][Utils.COL];
		// 初始化所有点为通路
		for (int i = 0; i < Utils.ROW; i++) {
			for (int j = 0; j < Utils.COL; j++) {
				// 最左上角坐标为0，0
				dotMatrix[i][j] = new Dot(i, j);
				dotMatrix[i][j].setStatus(Status.OFF);
			}

		}
		// 随机初始化10个障碍点
		Random random = new Random();
		int x = 0, y = 0;
		for (int i = 0; i < Utils.BLOCK;) {
			x = random.nextInt(Utils.ROW);
			y = random.nextInt(Utils.COL);
			if (this.dotMatrix[x][y].getStatus() == Status.OFF) {
				this.dotMatrix[x][y].setStatus(Status.ON);
				++i;
			}
		}
	}

	public Dot getDot(int x, int y) {
		if (x < Utils.ROW && x >= 0 && y >= 0 && y < Utils.COL) {
			return dotMatrix[x][y];
		}
		return null;
	}

	/**
	 * 获取dot的邻居
	 * 
	 * @param dot
	 * @param direction
	 *            方向
	 * @return
	 */
	public Dot getNeiborDot(Dot dot, int direction) {
		Dot neiborDot = null;
		if (dot==null) {
			return null;
		}

		switch (direction) {
		case 1:
			neiborDot = getDot(dot.getX(), dot.getY() - 1);
			break;
		case 2:
			if (dot.getX() % 2 == 0) {
				// 偶数行，有缩进
				neiborDot = getDot(dot.getX() - 1, dot.getY());
			} else {
				neiborDot = getDot(dot.getX() - 1, dot.getY() - 1);
			}
			break;
		case 3:
			if (dot.getX() % 2 == 0) {
				// 偶数行，有缩进
				neiborDot = getDot(dot.getX() - 1, dot.getY() + 1);
			} else {
				neiborDot = getDot(dot.getX() - 1, dot.getY());
			}
			break;
		case 4:
			neiborDot = getDot(dot.getX(), dot.getY() + 1);
			break;
		case 5:
			if (dot.getX() % 2 == 0) {
				// 偶数行，有缩进
				neiborDot = getDot(dot.getX() + 1, dot.getY() + 1);
			} else {
				neiborDot = getDot(dot.getX() + 1, dot.getY());
			}
			break;
		case 6:
			if (dot.getX() % 2 == 0) {
				// 偶数行，有缩进
				neiborDot = getDot(dot.getX() + 1, dot.getY());
			} else {
				neiborDot = getDot(dot.getX() + 1, dot.getY() - 1);
			}

			break;
		}
		return neiborDot;
	}

	/**
	 * 返回点dot在指定方向上距离边缘或者障碍点的距离
	 * 如果在指定方向上无路障，则返回距离为正，有路障则返回负数距离
	 * @param dot
	 * @param direction
	 * @return
	 */
	public int getDistance(Dot dot, int direction) {
		int distance=0;

		dot=getNeiborDot(dot, direction);
		while(dot!=null){
			//如果是障碍则返回负距离
			if (dot.getStatus()==Status.ON) {
				return -distance;
			}
			distance++;
			dot=getNeiborDot(dot, direction);
		}
		
	
		return distance;

	}
	public int getMoveDirection(Dot dot){
		//判断移动方向
		boolean isTong=false;
		boolean isWin=true;
		//此时代表四周都是障碍
		int moveDirection=1;
		int minDistance=getDistance(dot, 1);
		for(int i=1;i<7;i++){
			int cur=getDistance(dot, i);
			if (cur!=0) {
				isWin=false;
			}
			
			if (cur>0&&cur<minDistance) {
				minDistance=cur;
				moveDirection=i;
			}
			if (cur>0&&minDistance<=0) {
				minDistance=cur;
				moveDirection=i;
			}
			if (cur<minDistance&&minDistance<=0) {
				minDistance=cur;
				moveDirection=i;
			}
			
		}
		if (isWin) {
			moveDirection=0;
		}
		return moveDirection;
	}

}
