package mapInfo;

import java.util.HashMap;
import java.util.Map;

public class LittleRedRidingHood {

	private static LittleRedRidingHood lrrh = null;
	
	private int totalTime;
	private int wolfZonesWalked;
	
	// Index position based in the Zone matrix
	private int xPos;
	private int yPos;
	
	private static Map<String, Double> candyKinds = new HashMap<String, Double>();
	private static Map<String, Integer> candyQuantity = new HashMap<String, Integer>();
	
	private LittleRedRidingHood() { }
	
	private LittleRedRidingHood(int x, int y) {
		
		xPos = x;
		yPos = y;
		totalTime = 0;
		wolfZonesWalked = 0;
		
		candyKinds.put("Torta de Amoras", 1.5);
		candyKinds.put("Cupcakes de Marshmallow", 1.4);
		candyKinds.put("Bolo de Chocolate", 1.3);
		candyKinds.put("Brigadeiro", 1.2);
		candyKinds.put("Doce de Coco", 1.1);
		
		candyQuantity.put("Torta de Amoras", 5);
		candyQuantity.put("Cupcakes de Marshmallow", 5);
		candyQuantity.put("Bolo de Chocolate", 5);
		candyQuantity.put("Brigadeiro", 5);
		candyQuantity.put("Doce de Coco", 5);
	}
	
	public static LittleRedRidingHood getLittleRed(int x, int y) {
		
		if (lrrh == null)
			lrrh = new LittleRedRidingHood(x, y);
		return lrrh;
	}
	
	public int getTime() {
		
		return totalTime;
	}
	
	public void addTime(int t) {
		
		totalTime += t;
	}
	
	public int getWolfZones() {
		
		return wolfZonesWalked;
	}
	
	public int getX() {
		
		return xPos;
	}
	
	public void setX(int x) {
		
		xPos = x;
	}
	
	public int getY() {
		
		return yPos;
	}
	
	public void setY(int y) {
		
		yPos = y;
	}
	
	// TODO here enters the path and candy distribution methods
	
}