package mapInfo;

import genetic.CandyCombinations;
import genetic.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Math.abs;

import java.util.Comparator;

import javax.swing.SwingUtilities;


public class LittleRedRidingHood extends Thread {

	private static LittleRedRidingHood lrrh = null;
	private Region region;
	
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
	
	private LittleRedRidingHood(int x, int y, Region r) {
		
		xPos = x;
		yPos = y;
		region = r;
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
	
	public static LittleRedRidingHood getLittleRed(int x, int y, Region r) {
		
		if (lrrh == null)
			lrrh = new LittleRedRidingHood(x, y, r);
		
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
			
	        CandyCombinations combs = new CandyCombinations(500, true);
			
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
			System.out.println("\nWolf Zone: " + (i + 1));
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
		
	}
    	

	
/////////////////////////////////////////////////////// ASTAR ///////////////////////////////////////////////////////////////
	
	public int estimateDistance(Zone z1, Zone z2) {
	    return Math.abs(z1.x - z2.x) + Math.abs(z1.y - z2.y);
	}
    
    
    
    public ArrayList<Zone>neighbors( Zone x ) {
    	
    	ArrayList<Zone>neighbors = new ArrayList<Zone>();
    
    	if (x.y + 1 < 41)
    		neighbors.add(grid[x.x][x.y + 1]);
    	
    	if (x.y - 1 > -1)
    		neighbors.add(grid[x.x][x.y - 1]);
    	
    	if (x.x + 1 < 41)
    		neighbors.add(grid[x.x  + 1][x.y]);
    	
    	if (x.x - 1 > -1)
    		neighbors.add(grid[x.x - 1][x.y]);
    	  	
    	return neighbors;   	
    }
     
    
    public List<Zone> aStar() {
  	  
    	Zone start = grid[36][4];
		Zone goal = grid[36][36];
		
    	Set<Zone> open = new HashSet<Zone>();
    	Set<Zone> closed = new HashSet<Zone>();

    	start.g = 0;
    	start.h = estimateDistance(start, goal);
    	start.f = start.h;

    	open.add(start);

    	while (true) {
    	        
    		Zone current = null;

    	    
    		if (open.size() == 0) {
    	    	throw new RuntimeException("no route");
    	    }

    	    
    	    for (Zone node : open) {
    	    	
    	    	if (current == null || node.f < current.f) {
    	    		
    	    		current = node;
    	        }
    	    }
    	    
    	    if (current == goal) {
    	            break;
    	    }

    	    open.remove(current);
    	    closed.add(current);

    	    current.neighbors = neighbors(current);
    	    
    	    for (Zone neighbor : current.neighbors) {
    	    	
    	    	if (neighbor == null) {
    	    		continue;
    	        }

    	        double nextG = current.g + getCost(grid[neighbor.x][neighbor.y].getType());

    	        if (nextG < neighbor.g) {
    	               
    	        	open.remove(neighbor);
    	            closed.remove(neighbor);
    	        }

    	        if (!open.contains(neighbor) && !closed.contains(neighbor)) {
    	        	
    	        	neighbor.g = nextG;
    	            neighbor.h = estimateDistance(neighbor, goal);
    	            neighbor.f = neighbor.g + neighbor.h;
    	            neighbor.parent = current;
    	            open.add(neighbor);
    	            
    	        }        
    	    }
    	    
    	   
    	}

    	    
    	List<Zone> nodes = new ArrayList<Zone>();
    	Zone current = goal;
    	int i = 0;
    	    
    	while (current.parent != null) {
    		nodes.add(current);
    		
    		nodes.get(i).isPath = true;
    		
    		region.repaint();
     	    
     	    try {
 				sleep(30);
 			} catch (InterruptedException e) {
 				e.printStackTrace();
 			}
    		
     	    if(i == 0)
     	    	System.out.println("Final Cost: " + nodes.get(i).f);
     	    i++;
     	    
    	    current = current.parent;  	    
    	}
    	
    	nodes.add(start);

    	return nodes;

    }

    
    double getCost(char c) {
    	
    	if ( c == 'D') return 200.0;
    	
    	else if ( c == '.') return 1.0;
    	
    	else if ( c == 'G') return 5.0;
    	 		
    	else return 0.0; 
    	
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	
}