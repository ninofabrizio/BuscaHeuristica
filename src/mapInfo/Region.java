package mapInfo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Region extends JPanel {

	private Graphics g = null;
	
	private static Zone zone[][] = new Zone[41][41];
	public Zone littleRedZone;
	
	private static ArrayList<Double> wolfZones = new ArrayList<Double>();
	
	public static Map<Character, Double> zonesCosts = new HashMap<Character, Double>();
	
	private int zoneWidth;
	private int zoneHeight;
	
	public Region(int w, int h) {
		
		zoneWidth = w;
		zoneHeight = h;
		
		zonesCosts.put('D', 200.0);
		zonesCosts.put('.', 1.0);
		zonesCosts.put('G', 5.0);
		zonesCosts.put('I', 0.0);
		zonesCosts.put('F', 0.0);
		zonesCosts.put('C', 0.0);
		
		
		// Remember that it follows order (i.e., index 0 is the exact first entry, 1 is second and so on...)
		// It's also the order of the wolf zones by encounter
		wolfZones.add(150.0);
		wolfZones.add(140.0);
		wolfZones.add(130.0);
		wolfZones.add(120.0);
		wolfZones.add(110.0);
		wolfZones.add(100.0);
		wolfZones.add(95.0);
		wolfZones.add(90.0);
		wolfZones.add(85.0);
		wolfZones.add(80.0);
	}
	
	public void loading(BufferedReader br) throws IOException {
		
		String s;
		char line[];
		
		for(int i = 0; i < 41; i++) {
			
			s = br.readLine();
			line = s.toCharArray();
			
			for(int j = 0; j < 41; j++) {
				
				zone[i][j] = new Zone();
				zone[i][j].setType(line[j]);
				zone[i][j].x = i;
				zone[i][j].y = j;
				
				if(line[j] == 'I') {
					
					zone[i][j].setLittleRed(LittleRedRidingHood.getLittleRed(i, j, this));
					littleRedZone = zone[i][j];
				}
				else
					zone[i][j].setLittleRed(null);
			}
		}
		
		littleRedZone.getLittleRed().manageWolfZone();
	}
	
	public static double getWolfZoneDifficulty(int zone) {
		
		return wolfZones.get(zone);
	}
	
	public static void setWolfZoneDifficulty(int zone, double newTime) {
		
		wolfZones.set(zone, newTime);
	}
	
	public Region repaintZone() {
		
		return this;
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		this.g = g;
		Graphics2D g2d = (Graphics2D) g;
		Rectangle2D rt;
		
		double yPos;
		double xPos;
		int i, j;
		
		for(i = 0, yPos = 0.0; i < 41; i++, yPos += zoneHeight) {
			for(j = 0, xPos = 0.0; j < 41; j++, xPos += zoneWidth) {
				
				if(zone[i][j].getType() == 'D') {
					
					rt = new Rectangle2D.Double(xPos, yPos, zoneWidth, zoneHeight);
					g2d.setPaint(Color.GREEN);
					g2d.fill(rt);
				}
				else if(zone[i][j].getType() == '.' || zone[i][j].getType() == 'I') {
					
					rt = new Rectangle2D.Double(xPos, yPos, zoneWidth, zoneHeight);
					g2d.setPaint(Color.WHITE);
					g2d.fill(rt);
				}
				else if(zone[i][j].getType() == 'G') {
					
					rt = new Rectangle2D.Double(xPos, yPos, zoneWidth, zoneHeight);
					g2d.setPaint(Color.ORANGE);
					g2d.fill(rt);
				}
				else if(zone[i][j].getType() == 'C') {
					
					rt = new Rectangle2D.Double(xPos, yPos, zoneWidth, zoneHeight);
					g2d.setPaint(Color.YELLOW);
					g2d.fill(rt);
				}
				else if(zone[i][j].getType() == 'F') {
					
					rt = new Rectangle2D.Double(xPos, yPos, zoneWidth, zoneHeight);
					g2d.setPaint(Color.BLUE);
					g2d.fill(rt);
				}
				if(zone[i][j].getLittleRed() != null && zone[i][j].getType() != 'F' && zone[i][j].getType() != 'I') {
					
					rt = new Rectangle2D.Double(xPos, yPos, zoneWidth, zoneHeight);
					g2d.setPaint(Color.BLUE);
					g2d.fill(rt);
				}
				else if(zone[i][j].getLittleRed() != null) {
					
					rt = new Rectangle2D.Double(xPos, yPos, zoneWidth, zoneHeight);
					g2d.setPaint(Color.RED);
					g2d.fill(rt);
				}
			
				if(zone[i][j].isPath()){
					
					rt = new Rectangle2D.Double(xPos, yPos, zoneWidth, zoneHeight);
					g2d.setPaint(Color.BLACK);
					g2d.fill(rt);				
					
				}
								
			}
		}
	}

	
	public static Map<Character, Double> getZonesCosts() {
		
		return zonesCosts;		
	}
		
	public static Zone[][] getZones() {	

		return zone;
	}

	public void activateAStar() {
		
		List<Zone>nodes = littleRedZone.getLittleRed().aStar();
		int i = 0;
		Zone x = null;
		
		while( i < nodes.size() ) {
			
			x = nodes.remove(i);
			//System.out.println("\nNo..I= " + x.x + "   J=.." + x.y);
			
			i++;
			
		}
		
		//System.out.println("Final cost:" + x.cost);
	}
}