package mainStuff;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JPanel;

public class Region extends JPanel {

	private Zone zone[][] = new Zone[41][41];
	
	private int zoneWidth;
	private int zoneHeight;
	
	public Region(int w, int h) {
		
		zoneWidth = w;
		zoneHeight = h;
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
			}
		}
		
/*TEST	for(int i = 0; i < 41; i++) {
			for(int j = 0; j < 41 ; j++) {
				System.out.print(zone[i][j].getType());
			}
			System.out.println();
		}*/
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