package com.github.kreatures.extraction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 
 * @author Manuel Barbi
 *
 */
public class Utils {

	/**
	 * Determines whether two premises are mergeable.
	 * 
	 * @param p1 1st premise
	 * @param p2 2nd premise
	 * @return whether the two premises are mergeable
	 */
	public static boolean isMergeable(Premise p1, Premise p2) {

		// a premise is not mergeable with itself
		if (p1 == p2)
			return false;

		// two premises with different length are not mergeable
		if (p1.size() != p2.size())
			return false;

		Iterator<Entry<String, Object>> it1 = p1.entrySet().iterator();
		Iterator<Entry<String, Object>> it2 = p2.entrySet().iterator();

		// check whether the 1st to (m-1)-th partial states are equal
		Entry<String, Object> e1, e2;
		for (int count = 0; count < p1.size() - 1; count++) {
			e1 = it1.next();
			e2 = it2.next();

			if (!e1.getKey().equals(e2.getKey()))
				return false;

			if (!e1.getValue().equals(e2.getValue()))
				return false;
		}

		// check whether the m-th partial states have different state dimensions
		if (it1.next().getKey().equals(it2.next().getKey()))
			return false;

		return true;
	}

	/**
	 * Calculates the intersection of two sets of supporting pairs.
	 * 
	 * @param supp1 1st set of supporting pairs
	 * @param supp2 2nd set of supporting pairs
	 * @return the intersection of both sets of supporting pairs
	 */
	public static <S, A> Set<StateActionPair<S, A>> intersect(Set<StateActionPair<S, A>> supp1, Set<StateActionPair<S, A>> supp2) {

		// if the 2nd set contains fewer items than the 1st, swap arguments
		if (supp2.size() < supp1.size())
			return intersect(supp2, supp1);

		// check which pairs of the 1st set are also contained in the 2nd set
		final Set<StateActionPair<S, A>> intersection = new HashSet<>();
		supp1.forEach((sap) -> {
			if (supp2.contains(sap))
				intersection.add(sap);
		});

		return intersection;
	}

	/**
	 * Calculates the combined list of dominant rules.
	 * 
	 * @param dom1 1th list of dominant rules
	 * @param dom2 2th list of dominant rules
	 * @return the combined list of dominant rules
	 */
	public static <A> List<Rule<A>> dominant(List<Rule<A>> dom1, List<Rule<A>> dom2) {

		// if one list is empty return the other one
		// if the lists are the same object return one of them
		if (dom1.isEmpty() || dom1 == dom2)
			return dom2;

		if (dom2.isEmpty())
			return dom1;

		// determine the list that contains the rules with higher confidence
		switch (Double.compare(dom1.iterator().next().getConfidence(), dom2.iterator().next().getConfidence())) {
		case -1:
			return dom2;
		case 1:
			return dom1;
		}

		// otherwise combine lists
		List<Rule<A>> rules = new ArrayList<>(dom1.size() + dom2.size());
		rules.addAll(dom1);
		rules.addAll(dom2);

		return rules;
	}

	/**
	 * Compares the {@link String} representation of to objects lexicographically.
	 * 
	 * @param obj1 1th object
	 * @param obj2 2nd object
	 * @return the usual semantics for comparison
	 */
	public static int lexicalOrder(Object obj1, Object obj2) {
		return obj1.toString().compareTo(obj2.toString());
	}

}
