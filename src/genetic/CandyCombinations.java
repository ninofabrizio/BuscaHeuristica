package genetic;


public class CandyCombinations {

    private Combination[] combinations;

    public CandyCombinations(int size, boolean initialize) {
    	
    	combinations = new Combination[size];
        
        // Initialize population
        if(initialize) {
            
            for(int i = 0; i < combinations.length; ) {
            	
                Combination newComb = new Combination();
                newComb.generateCombination();
                
                if(newComb.combinationIsAble()) {
                	saveCombination(i, newComb);
                	i++;
                }
            }
        }
    }

    public int getCombinationsSize() {
    	
    	return combinations.length;
    }
    
    public Combination getCombination(int index) {
    	
        return combinations[index];
    }

    public Combination getFittest() {
    	
        Combination fittest = combinations[0];
       
        for(int i = 0; i < combinations.length; i++) {
        	
        	// Better fitness, less candies used and able combination
            if(fittest.getFitness() <= getCombination(i).getFitness()
            	&& (fittest.getCandies().size() >= getCombination(i).getCandies().size())
            	&& getCombination(i).combinationIsAble())
                fittest = getCombination(i);
            
        }
        
        return fittest;
    }

	public void saveCombination(int index, Combination combination) {
		
        combinations[index] = combination;
    }
}