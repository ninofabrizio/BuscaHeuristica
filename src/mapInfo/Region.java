package mapInfo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public class Region extends JPanel {

	private Zone zone[][] = new Zone[41][41];
	public Zone littleRedZone;
	
	private static ArrayList<Integer> wolfZones = new ArrayList<Integer>();
	
	private static Map<Character, Integer> zonesCosts = new HashMap<Character, Integer>();
	
	private int zoneWidth;
	private int zoneHeight;
	
	public Region(int w, int h) {
		
		zoneWidth = w;
		zoneHeight = h;
		
		zonesCosts.put('D', 200);
		zonesCosts.put('.', 1);
		zonesCosts.put('G', 5);
		
		// Remember that it follows order (i.e., index 0 is the exact first entry, 1 is second and so on...)
		// It's also the order of the wolf zones by encounter
		wolfZones.add(150);
		wolfZones.add(140);
		wolfZones.add(130);
		wolfZones.add(120);
		wolfZones.add(110);
		wolfZones.add(100);
		wolfZones.add(95);
		wolfZones.add(90);
		wolfZones.add(85);
		wolfZones.add(80);
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
				
				if(line[j] == 'I') {
					
					zone[i][j].setLittleRed(LittleRedRidingHood.getLittleRed(i, j));
					littleRedZone = zone[i][j];
				}
				else
					zone[i][j].setLittleRed(null);
			}
		}
		
		// TODO Tests
		littleRedZone.getLittleRed().manageWolfZone();
		
	/*	for(int i = 0; i < 41; i++) {
			for(int j = 0; j < 41 ; j++) {
				System.out.print(zone[i][j].getType());
			}
			System.out.println();
		}*/
	}
	
	public static int getWolfZoneDifficulty(int zone) {
		
		return wolfZones.get(zone);
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
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
				else if(zone[i][j].getType() == '.') {
					
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
				else if(zone[i][j].getType() == 'I') {
					
					rt = new Rectangle2D.Double(xPos, yPos, zoneWidth, zoneHeight);
					g2d.setPaint(Color.RED);
					g2d.fill(rt);
				}
				else { // Final
					
					rt = new Rectangle2D.Double(xPos, yPos, zoneWidth, zoneHeight);
					g2d.setPaint(Color.BLUE);
					g2d.fill(rt);
				}
			}
		}
	}
}