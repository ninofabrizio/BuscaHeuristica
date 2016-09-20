package mapInfo;

public class Zone {

	private char type;
	
	private LittleRedRidingHood lrrh;
	
	public void setType(char t) {
		
		type = t;
	}
	
	public char getType(){
		
		return type;
	}
	
	public void setLittleRed(LittleRedRidingHood l) {
		
		lrrh = l;
	}
	
	public LittleRedRidingHood getLittleRed() {
		
		return lrrh;
	}
}