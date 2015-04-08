package cqu.cqumonk.androidcode.CreazyCat;

import cqu.cqumonk.androidcode.model.Dot;



public class Utils {

	public final static int COL = 10;
	public final static int ROW = 10;
	public final static int BLOCK = 20;

	public static boolean isEdge(Dot dot) {
		if (dot == null) {
			return false;
		}
		if (dot.getX() * dot.getY() == 0 || dot.getX() + 1 == COL
				|| dot.getY() + 1 == ROW) {
			return true;
		}
		return false;
	}
}
