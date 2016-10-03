package genetic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import mapInfo.LittleRedRidingHood;

public class Combination {

	private byte[] candies = new byte[5];
	
    private double fitness = 0.0;

    // Create a random Combination
    public void generateCombination() {
    	
        for (int i = 0; i < candies.length; i++) {
        	
            byte candy = (byte) Math.round(Math.random());
            candies[i] = candy;
        }
    }
    
    public byte getCandy(int index) {
    	
        return candies[index];
    }
    
    public ArrayList<String> getCandies() {
    	
    	ArrayList<String> candiesList = new ArrayList<String>();
    	
    	for(int i = 0; i < candies.length; i++) {
    		
    		if(i == 0 && candies[i] == 1)
    			candiesList.add("Torta de Amoras");
    		else if(i == 1 && candies[i] == 1)
    			candiesList.add("Cupcakes de Marshmallow");
    		else if(i == 2 && candies[i] == 1)
    			candiesList.add("Bolo de Chocolate");
    		else if(i == 3 && candies[i] == 1)
    			candiesList.add("Brigadeiro");
    		else if(i == 4 && candies[i] == 1)
    			candiesList.add("Doce de Coco");
    	}
    	
    	return candiesList;
    }

    public void setCandy(int index, byte value) {
    	
        candies[index] = value;
        fitness = 0.0;
    }

    // Here we calculate the fitness of the combination
    public double getFitness() {
    	
        fitness = 0.0;
        ArrayList<String> list = getCandies();
        Map<String, Double> candyKinds = LittleRedRidingHood.getCandyKinds();
        
        for(int i = 0; i < list.size(); i++)
        	fitness += candyKinds.get(list.get(i));
        
        if(list.size() < 5)
        	return fitness;
        else
        	return 0.0;
    }
    
    // Here we chek if the combination is valid for us
    public boolean combinationIsAble() {
		
    	Map<String, Integer> candyQuantity = LittleRedRidingHood.getCandyQuantities();
    	int totalCandies = 0, total;
    	boolean isEmpty = true;
    	
    	Iterator<Entry<String, Integer>> cqIterator = candyQuantity.entrySet().iterator();
		while(cqIterator.hasNext()){
			
			Entry<String, Integer> entry = cqIterator.next();
			totalCandies += entry.getValue();
		}
    	
		total = totalCandies;
		
		for(int i = 0; i < candies.length; i++)
			if(getCandy(i) == 1){
    			isEmpty = false;
    			break;
			}
		
    	for(int i = 0; i < getCandies().size(); i++) {
    		
    		// To avoid "candy debt"
    		if((candyQuantity.get(getCandies().get(i)) - 1) < 0)
    			return false;
    		totalCandies--;
    	}
    	
    	// To avoid combination of no candy used or using more candies that we have
    	// or to avoid possibility of ending in a zone with just 1 candy (infinite loop)
    	if((totalCandies < 1) || isEmpty || (total - totalCandies >= 4))
    		return false;
    	
		return true;
	}
}