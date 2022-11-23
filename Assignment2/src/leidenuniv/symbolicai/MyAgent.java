package leidenuniv.symbolicai;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;

import leidenuniv.symbolicai.logic.KB;
import leidenuniv.symbolicai.logic.Predicate;
import leidenuniv.symbolicai.logic.Sentence;
import leidenuniv.symbolicai.logic.Term;

public class MyAgent extends Agent {
	
	

	@Override
	public KB forwardChain(KB kb) {
		//This method should perform a forward chaining on the kb given as argument, until no new facts are added to the KB.
		//It starts with an empty list of facts. When ready, it returns a new KB of ground facts (bounded).
		//The resulting KB includes all deduced predicates, actions, additions and deletions, and goals.
		//These are then processed by processFacts() (which is already implemented for you)
		//HINT: You should assume that forwardChain only allows *bound* predicates to be added to the facts list for now.
		KB newKB = new KB(); 
		KB factsKB = new KB(); 

		HashMap<String, Predicate> facts = new HashMap<>();
		for(Sentence sentence:kb.rules()){
			for(Predicate element:sentence.conclusions){
				System.out.println(element.bound());
				if(element.bound()){
					facts.put(element.toString(), element); 
				}
			}
		
		}
		for(Sentence sentence:kb.rules()){
			System.out.println(sentence); 
			System.out.println(facts);
			HashMap<String, String> substitution = new HashMap<>();
			Collection<HashMap<String, String>> collection = new HashSet<>();
			boolean allSubs = findAllSubstitions(collection, substitution,sentence.conditions,facts);
			for(HashMap<String,String> hashmap:collection){
				for(Predicate conclusion:sentence.conclusions){
					if(!conclusion.bound()){
						Predicate substitutedPredicate = substitute(conclusion, hashmap); 
						Sentence newSentence = new Sentence(substitutedPredicate.toString()); 
						newKB.add(newSentence);
					}
				}
				System.out.println(sentence);
				System.out.println(newKB.rules()); 
				
			}
		}
		processFacts(newKB, kb ,null, null, DEBUG);
		System.out.println("ikweethetniet: "+kb); 
		return kb;
	}

	@Override
	public boolean findAllSubstitions(Collection<HashMap<String, String>> allSubstitutions,
			HashMap<String, String> substitution, Vector<Predicate> conditions, HashMap<String, Predicate> facts) {
		//Recursive method to find *all* valid substitutions for a vector of conditions, given a set of facts
		//The recursion is over Vector<Predicate> conditions (so this vector gets shorter and shorter, the farther you are with finding substitutions)
		//It returns true if at least one substitution is found (can be the empty substitution, if nothing needs to be substituted to unify the conditions with the facts)
		//allSubstitutions is a list of all substitutions that are found, which was passed by reference (so you use it build the list of substitutions)
		//substitution is the one we are currently building recursively.
		//conditions is the list of conditions you  still need to find a subst for (this list shrinks the further you get in the recursion).
		//facts is the list of predicates you need to match against (find substitutions so that a predicate form the conditions unifies with a fact)
		if(!conditions.isEmpty()){
			for(Predicate unifiedValue:facts.values()){
				Vector<Predicate> newConditions = new Vector<>(conditions);
				HashMap<String, String> newHashmap = new HashMap<>(substitution);
				HashMap<String, String> unifiedMap = new HashMap<>();

				if(newConditions.get(0).eql){
					Predicate result = substitute(newConditions.get(0), newHashmap);
					if(!result.eql()){
						continue;
					}
				} else if(newConditions.get(0).not){
					Predicate result = substitute(newConditions.get(0), newHashmap);
					if(!result.not()){
						continue;
					}
				}
				else{
					//Result by substitute with given substitutions
					Predicate result = substitute(newConditions.get(0), newHashmap); 
					unifiedMap= unifiesWith(result, unifiedValue);
				}
				if(unifiedMap!=null){
					newHashmap.putAll(unifiedMap);
					newConditions.remove(0); 
					findAllSubstitions(allSubstitutions, newHashmap, newConditions, facts);
				}
			}
		} else{
			allSubstitutions.add(substitution);
			System.out.println(allSubstitutions);
		}
		return !allSubstitutions.isEmpty();
	} 

	@Override
	public HashMap<String, String> unifiesWith(Predicate p, Predicate f) {
		//Returns the valid substitution for which p predicate unifies with f
		//You may assume that Predicate f is fully bound (i.e., it has no variables anymore)
		//The result can be an empty substitution, if no subst is needed to unify p with f (e.g., if p an f contain the same constants or do not have any terms)
		//Please note because f is bound and p potentially contains the variables, unifiesWith is NOT symmetrical
		//So: unifiesWith("human(X)","human(joost)") returns X=joost, while unifiesWith("human(joost)","human(X)") returns null 
		//If no subst is found it returns null
		HashMap<String, String> map = new HashMap<>();
		int index = 0;
		if(p.getName().equals(f.getName())&&p.getTerms().size()==f.getTerms().size()){
			for(Term pterm:p.getTerms()){
				Term fterm = f.getTerm(index);
				if(!pterm.var && (!pterm.toString().equals(fterm.toString()))){
					return null;
				} if(!pterm.toString().equals(fterm.toString())){
					if (pterm.toString().length() == 1) {
						map.put(pterm.toString(), fterm.toString());
					}
				}
				index++;
			}return map; 
		} else{
			return null; 
		}
	}

	@Override
	public Predicate substitute(Predicate old, HashMap<String, String> s) {
		// Substitutes all variable terms in predicate <old> for values in substitution <s>
		//(only if a key is present in s matching the variable name of course)
		//Use Term.substitute(s) 
		Predicate newPredicate = new Predicate(old.toString());
		boolean hasTerms =newPredicate.hasTerms();
		
		if(s==null){return null;}
		
		if(hasTerms){
			for(Term term:newPredicate.getTerms()){
				term.substitute(s); 
			}
		}
		return newPredicate; 
	}

	@Override
	public Plan idSearch(int maxDepth, KB kb, Predicate goal) {
		//The main iterative deepening loop
		//Returns a plan, when the depthFirst call returns a plan for depth d.
		//Ends at maxDepth
		//Predicate goal is the goal predicate to find a plan for.
		//Return null if no plan is found.
		return null;
	}

	@Override
	public Plan depthFirst(int maxDepth, int depth, KB state, Predicate goal, Plan partialPlan) {
		//Performs a depthFirst search for a plan to get to Predicate goal
		//Is a recursive function, with each call a deeper action in the plan, building up the partialPlan
		//Caps when maxDepth=depth
		//Returns (bubbles back through recursion) the plan when the state entails the goal predicate
		//Returns null if capped or if there are no (more) actions to perform in one node (state)
		//HINT: make use of think() and act() using the local state for the node in the search you are in.
		return null;
	}
}
