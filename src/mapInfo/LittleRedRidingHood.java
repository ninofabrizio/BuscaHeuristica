package mapInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class LittleRedRidingHood {

	private static LittleRedRidingHood lrrh = null;
	
	private int totalTime;
	private int wolfZonesWalked;
	
	// Index position based in the Zone matrix
	private int xPos;
	private int yPos;
	
	private static Map<String, Double> candyKinds = new HashMap<String, Double>();
	private static Map<String, Integer> candyQuantity = new HashMap<String, Integer>();
	private static ArrayList<String> candyValueOrder = new ArrayList<String>();
	
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
		
		setCandyOrder();
	}
	
	// This method is ugly, but works...try to make it better
	private void setCandyOrder() {
		
		int i = 0;
		Map<String, Double> temp = new HashMap<String, Double>();
		Entry<String, Double> entry;
		
		Iterator<Entry<String, Double>> ckIterator = candyKinds.entrySet().iterator();
		while(ckIterator.hasNext()){
			
			entry = ckIterator.next();
			temp.put(entry.getKey(), entry.getValue());
		}
		
		for(i = 0; i < candyKinds.size(); i++) {
			
			String candy;
			Iterator<Entry<String, Double>> tempIterator = temp.entrySet().iterator();
			entry = tempIterator.next();
			candy = entry.getKey();
			
			if(tempIterator.hasNext()) {
				entry = tempIterator.next();
			
				if(!tempIterator.hasNext() && (entry.getValue() > temp.get(candy)))
					candy = entry.getKey();
			}
				
			while(tempIterator.hasNext()){

				if(entry.getValue() > temp.get(candy))
					candy = entry.getKey();
				
				entry = tempIterator.next();
			}
			
			candyValueOrder.add(candy);
			temp.remove(candy);
		}
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

	// TODO This method should work for THE zone she's standing (It's called by the path method)
	// For now, it sees them all in a loop (take the loop part out after)
	public void manageWolfZone() {
		
		int zoneDifficulty, timeSavedCalculated, j;
		double sum = 0;
		j = 0;
		
		for(int i = 0; i < 10; i++) {
		
			zoneDifficulty = Region.getWolfZoneDifficulty(wolfZonesWalked);
			timeSavedCalculated = zoneDifficulty;
			
			Map<String, Integer> candiesUsed = new HashMap<String, Integer>();
			
			candiesUsed.put("Torta de Amoras", candyQuantity.get("Torta de Amoras"));
			candiesUsed.put("Cupcakes de Marshmallow", candyQuantity.get("Cupcakes de Marshmallow"));
			candiesUsed.put("Bolo de Chocolate", candyQuantity.get("Bolo de Chocolate"));
			candiesUsed.put("Brigadeiro", candyQuantity.get("Brigadeiro"));
			candiesUsed.put("Doce de Coco", candyQuantity.get("Doce de Coco"));
			
			/*while(((double)(zoneDifficulty) * (0.2 - ((double)(wolfZonesWalked) * 0.01))) < timeSavedCalculated) {
				
				if(candiesUsed.get(candyValueOrder.get(j)) > 0) {
					sum += candyKinds.get(candiesUsed.get(candyValueOrder.get(j)));
					candiesUsed.replace(candyValueOrder.get(j), candiesUsed.get(candyValueOrder.get(j)) - 1);
				}
				else
					j++;
				
				timeSavedCalculated = (int)(zoneDifficulty / sum);
			}*/
			
			totalTime += timeSavedCalculated;
			
			wolfZonesWalked++;
			
			// TODO Test showing part, take it out after
			System.out.println("Zone: " + (i + 1));
			System.out.println("Difficulty: " + zoneDifficulty);
			
			System.out.println("Time = " + totalTime + "\n");
			
			Iterator<Entry<String, Integer>> cqIterator = candyQuantity.entrySet().iterator();
			while(cqIterator.hasNext()){
				
				Entry<String, Integer> entry = cqIterator.next();
				System.out.println(entry.getKey() + " = " + entry.getValue());
			}
			System.out.println("\n\n");
		}
	}
	
	// TODO here enters the path method
	
	
}