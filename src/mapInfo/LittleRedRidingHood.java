package mapInfo;

import genetic.CandyCombinations;
import genetic.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Math.abs;

import java.util.Comparator;


public class LittleRedRidingHood {

	private static LittleRedRidingHood lrrh = null;
	
	private double totalTime;
	private int wolfZonesWalked;
	
	// Index position based in the Zone matrix
	private int xPos;
	private int yPos;
	
	private static Map<String, Double> candyKinds = new HashMap<String, Double>();
	private static Map<String, Integer> candyQuantity = new HashMap<String, Integer>();
	
	private static Zone grid[][] = Region.getZones();
	private static Map<Character, Double>mapCosts = Region.getZonesCosts();	

	static LinkedList<Zone> frontier = new LinkedList<Zone>();
	
	static ArrayList<Zone> cost_so_far = new ArrayList<Zone>(); // G(N)
	static int startI = -1, startJ;
    static int endI = -1, endJ;
	
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

	public static Map<String, Double> getCandyKinds() {
		
		return candyKinds;
	}
	
	public static Map<String, Integer> getCandyQuantities() {
		
		return candyQuantity;
	}
	
	public static LittleRedRidingHood getLittleRed(int x, int y) {
		
		if (lrrh == null)
			lrrh = new LittleRedRidingHood(x, y);
		return lrrh;
	}
	
	public double getTime() {
		
		return totalTime;
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

	// Method that calculates new times for wolf zones, uses genetic algorithm to generate random
	// combinations and see which are more fit in a poplation of combinations
	public void manageWolfZone() {
		
		double timeSavedCalculated, zoneDifficulty, goal;
		int generation;
		
		for(int i = 0; i < 10; i++) {
		
			double diff = 0;
			generation = 0;
			
			zoneDifficulty = Region.getWolfZoneDifficulty(wolfZonesWalked);
			goal = (zoneDifficulty * (0.4 - ((double)wolfZonesWalked * 0.01)));
			
	        CandyCombinations combs = new CandyCombinations(50, true);
			
			Iterator<Entry<String, Integer>> cqIterator = candyQuantity.entrySet().iterator();
			
			// Here we consider a percentage that gets  a bit lower for each wolf zone walked
			// Looks like 40% is the best candidate for this case
			while(combs.getFittest().getFitness() > goal) {

				combs = GeneticAlgorithm.evolveCombinations(combs);
				generation++;
				
				// Candy saving part (residue)
				/*if(Double.doubleToRawLongBits(diff) < 0) {
					
					if(j == 0)
						j = 4;
					else
						j--;
					
					sum -= candyKinds.get(candyValueOrder.get(j));
					candiesUsed.replace(candyValueOrder.get(j), candiesUsed.get(candyValueOrder.get(j)) + 1);

					totalCandy++;
					
					timeSavedCalculated = zoneDifficulty / sum;
					
				}*/
				
			}
			
			timeSavedCalculated = zoneDifficulty / combs.getFittest().getFitness();
			totalTime += timeSavedCalculated;
			
			diff = timeSavedCalculated - goal;
			
			Region.setWolfZoneDifficulty(wolfZonesWalked, timeSavedCalculated);
			
			wolfZonesWalked++;
			
			// TODO Test showing part, take it out after
			System.out.println("\nZone: " + (i + 1));
			System.out.println("Difficulty: " + zoneDifficulty);
			
			System.out.println("New time = " + timeSavedCalculated + "\nTotal = " + totalTime + "\n");
			System.out.println("Residue(time saved - goal) = " + diff + "\n");
			
			ArrayList<String> list = combs.getFittest().getCandies();
			
			for(int j = 0; j < combs.getCombinationsSize(); j++)
				if(combs.getFittest().equals(combs.getCombination(j))) {
					System.out.println("Generation " + generation + "\nFittest position " + j);
					break;
				}
			
			System.out.println("\nCandies used:");
			
			for(int j = 0; j < list.size(); j++) {
				candyQuantity.replace(list.get(j), candyQuantity.get(list.get(j)) - 1);
				System.out.println(list.get(j));
			}
			
			System.out.println();
			cqIterator = candyQuantity.entrySet().iterator();
			while(cqIterator.hasNext()){
				
				Entry<String, Integer> entry = cqIterator.next();
				System.out.println(entry.getKey() + " = " + entry.getValue());
			}
			System.out.println("\n\n");
		}
		
		wolfZonesWalked = 0;
		
		AStar();
	}
    	

	
/////////////////////////////////////////////////////// ASTAR ///////////////////////////////////////////////////////////////
	
	
    public static void setStartAndEndCell() {
      
    	for ( int i = 0; i < 41; ++i )     
    		for ( int j = 0; j < 41; ++j ) {
    	      
    	    	if ( grid[i][j].getType() == 'I' ) {    
    	    		startI = i;
    	            startJ = j;    	
    	    	}
    			
    	    	if ( grid[i][j].getType() == 'F' ) {
    				endI = i;
    				endJ = j;
    			}     
    	     
    	    	if ( startI != -1 && endI != -1) break;	
    	    }
    }
    
    public static double heuristic( Zone x ) {
    	
    	return abs( x.getI() - endI ) + abs( x.getJ() - endJ );  	    	
    }
    
    
    
    public static ArrayList<Zone> neighbors( Zone x ) {
    	
    	ArrayList<Zone>neighbors = new ArrayList<Zone>();
    
    	if (x.getJ() + 1 < 41)
    		neighbors.add(grid[x.getI()][x.getJ() + 1]);
    	
    	if (x.getJ() - 1 > -1)
    		neighbors.add(grid[x.getI()][x.getJ() - 1]);
    	
    	if (x.getI() + 1 < 41)
    		neighbors.add(grid[x.getI()  + 1][x.getJ()]);
    	
    	if (x.getI() - 1 > -1)
    		neighbors.add(grid[x.getI() - 1][x.getJ()]);
    	
    	
    	return neighbors;   	
    }
     
    
	public static void AStar() {
		
		setStartAndEndCell();
		
		Zone start = grid[startI][startJ];
		Zone end = grid[endI][endJ];
		
		Zone current = null;
		ArrayList<Zone>neighbors;
		
		start.setCost(0);
		start.setFinalCost(heuristic(start));
		start.setParent(null);
		
		frontier.add(start);		
		cost_so_far.add(start);
			
		while ( frontier.size() != 0 ) {
			
			frontier.sort(new Comparator<Zone>() {
				   
				@Override
				   public int compare(Zone o1, Zone o2) {
			        
					if(o1.finalCost < o2.finalCost){
			           return -1; 
				        }
				        if(o1.finalCost > o2.finalCost){
				           return 1; 
				        }
			        return 0;
				   }
				}); 
				
			
			while ( true ) {
				
				current = frontier.poll();
				
				if (current.getParent() != null ) {
				
				if ( (current.i == current.getParent().i) && ( (current.j == current.getParent().j - 1) 
				|| ( current.j == current.getParent().j + 1 )) ) 	
					break;
				
				if ( (current.j == current.getParent().j) && ((current.i == current.getParent().i - 1) 
				|| ( current.i == current.getParent().i + 1 )) ) 	
					break;	
				
				} else break;
						
			} 
			
			System.out.println("\n\n: AAAA" + frontier.size());
			
			System.out.println("\ncurrent...." + current.getType() + "  "+ current.i + "   " + current.j);
					
			if ( current  == end ) {		
				System.out.println("\ncurrent...." + current.getType() + "  "+ current.i + "   " + current.j);
				break;
			}
			
			neighbors = neighbors(current);
						
			for (Zone next : neighbors) {
						
				double new_cost = cost_so_far.get(cost_so_far.indexOf(current)).getCost() + mapCosts.get(next.getType());
						
				if ( !cost_so_far.contains(next) || new_cost <  cost_so_far.get(cost_so_far.lastIndexOf(next)).getCost() ) {
					
					double priority = new_cost + heuristic(next);
					
					next.setCost(new_cost);
					cost_so_far.add(next);
					next.setFinalCost(priority);
					next.setParent(current);
				
					if ( next.getType() != 'D')
						frontier.add(next);
						
				}					
			}		
			
		}	
		
	}	

		
}