package mapInfo;

public class Zone {

	private char type;
	private LittleRedRidingHood lrrh;
	
    double cost = -1.0; // G
    double finalCost = -1.0; // G + H
    Zone parent;
    int i, j;
    
	public int getI() { return i; }
	public int getJ() { return j; }
	public void setI(int i) { this.i = i; }
	public void setJ(int j) { this.j = j; }
    public void setCost(double hc) { cost = hc; } 
    public void setFinalCost(double fc) { finalCost = fc; } 
	public double getCost() {	return cost; } 
	public double getFinalCost() { return finalCost; }
	public void setParent(Zone p) { parent = p; }
    public Zone getParent() { return parent; }	
    
    
	public void setType(char t) { type = t; }
	public char getType() { return type; }
	public void setLittleRed(LittleRedRidingHood l) { lrrh = l; }
	public LittleRedRidingHood getLittleRed() { return lrrh; }

}