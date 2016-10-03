package genetic;

public class GeneticAlgorithm {

    // Parameters that we use for accuracy
    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 10;
    private static final boolean elitism = true;
    
    // Here we evolve our combinations
    public static CandyCombinations evolveCombinations(CandyCombinations combinations) {
    	
        CandyCombinations newCandyCombinations = new CandyCombinations(combinations.getCombinationsSize(), false);

        // TODO Accuracy factor: keep our best combination as a priority
        if(elitism)
            newCandyCombinations.saveCombination(0, combinations.getFittest());

        // TODO Accuracy factor: crossover combinations based by priority of the best
        int elitismOffset = 0;
        if(elitism)
            elitismOffset = 1;
        
        // The crossover part
        for(int i = elitismOffset; i < combinations.getCombinationsSize(); i++) {
        	
            Combination comb1 = tournamentSelection(combinations);
            Combination comb2 = tournamentSelection(combinations);
            Combination newComb = crossover(comb1, comb2);
            newCandyCombinations.saveCombination(i, newComb);
        }

        // TODO Accuracy factor: mutate CandyCombinations
        for(int i = elitismOffset; i < newCandyCombinations.getCombinationsSize(); i++)
            mutate(newCandyCombinations.getCombination(i));

        for(int i = 0; i < newCandyCombinations.getCombinationsSize(); i++)
        	if(newCandyCombinations.getCombination(i).combinationIsAble() != false)
        		evolveCombinations(combinations);
        
        
        return newCandyCombinations;
    }

    // Here we do the crossover
    private static Combination crossover(Combination comb1, Combination comb2) {
    	
        Combination newSol = new Combination();
        
        // Remember that we have only 5 kinds of candy
        for(int i = 0; i < 5; i++) {
           
        	// TODO Accuracy factor: we see if it reaches a certain rate to determine which candies to switch
            if(Math.random() <= uniformRate)
                newSol.setCandy(i, comb1.getCandy(i));
            else
                newSol.setCandy(i, comb2.getCandy(i));
        }
        
        return newSol;
    }

    // Here we do the mutation
    private static void mutate(Combination comb) {

    	// Remember, 5 kinds of candy
        for (int i = 0; i < 5; i++) {
        	
        	// TODO Accuracy factor: we see if it reaches a certain rate to determine whether we mutate or not
            if(Math.random() <= mutationRate) {
                
            	// TODO Accuracy factor: a random choiece wether we include the candy or not
                byte candy = (byte) Math.round(Math.random());
                comb.setCandy(i, candy);
            }
        }
    }

    // Here we see which combination is the best on a group
    private static Combination tournamentSelection(CandyCombinations combinations) {

        CandyCombinations tournament = new CandyCombinations(tournamentSize, false);
        
        // TODO Accuracy factor: get a random combination to put in the tournament group
        for (int i = 0; i < tournamentSize; i++) {
        	
            int randomId = (int) (Math.random() * combinations.getCombinationsSize());
            tournament.saveCombination(i, combinations.getCombination(randomId));
        }
        
        // Get the best combination
        Combination fittest = tournament.getFittest();
        return fittest;
    }
}