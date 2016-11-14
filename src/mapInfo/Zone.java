package mapInfo;

import java.util.ArrayList;
import java.util.List;

public class Zone {

	private char type;
	private LittleRedRidingHood lrrh;
	public boolean isPath = false;
    
	public Zone parent;
	public double g = -1.0; // G
    public double f = -1.0; // G + H
    public double h;
    public double cost;
    public int x, y;
    public List<Zone> neighbors = new ArrayList<Zone>();
	
	public void setType(char t) { type = t; }
	public char getType() { return type; }
	public void setLittleRed(LittleRedRidingHood l) { lrrh = l; }
	public LittleRedRidingHood getLittleRed() { return lrrh; }
	
	public boolean isPath() {		
		return isPath;
	}

}