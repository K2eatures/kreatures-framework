package com.github.kreatures.extraction;

import static com.github.kreatures.extraction.Utils.dominant;
import static com.github.kreatures.extraction.Utils.intersect;
import static com.github.kreatures.extraction.Utils.isMergeable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * An implementation of the AprioriMem-extraction-algorithm from my Master-thesis.
 * The main idea is, to reuse the set of supporting state-actions-pairs and the dominant rules
 * from the last iteration instead of processing the whole input sequence over and over again.
 * 
 * Both state and action should override the hashCode() and equals() methods in order to work properly.
 * 
 * @author Manuel Barbi
 *
 * @param <S> state
 * @param <A> action
 */
public class AprioriMemExtraction<S, A> {

	protected final Translator<S> translator;
	protected final Comparator<A> comparator;
	protected int dim = -1;

	protected double minSupport = 0;
	protected double minConfidence = 0;
	protected boolean filter = true;

	/**
	 * If no explicit comparator is given, than compare the actions lexically.
	 */
	public AprioriMemExtraction() {
		this(new TranslatorRefl<>(), Utils::lexicalOrder);
	}

	public AprioriMemExtraction(Translator<S> translator, Comparator<A> comparator) {
		this.translator = Objects.requireNonNull(translator, "translator must not be null");
		this.comparator = Objects.requireNonNull(comparator, "comparator must not be null");
	}

	public HierarchicalKnowledgeBase<S, A> extract(Collection<StateActionPair<S, A>> seq) {
		final HierarchicalKnowledgeBase<S, A> hkb = new HierarchicalKnowledgeBase<>(translator);

		if (seq.isEmpty())
			return hkb;

		// determine the most frequent action within the input sequence
		Entry<A, Integer> action = maxAction(seq);
		double confidence = (double) action.getValue() / seq.size();
		List<Rule<A>> r0 = new ArrayList<>(1);

		if (confidence > minConfidence) {
			// create the rule of thumb
			Rule<A> ruleOfThumb = new Rule<>(new Premise(), action.getKey(), confidence);
			hkb.add(ruleOfThumb);
			r0.add(ruleOfThumb);
		}

		List<Premise> seqAsPremisses = new ArrayList<>(seq.size());
		// calculate the initial premise-tuples for elementary premisses containing the rule of thumb 
		List<PremiseTuple<S, A>> premiseTuples = init(seq, seqAsPremisses, r0);

		Set<Rule<A>> rules;
		int level = 1;
		while (!premiseTuples.isEmpty()) {
			rules = new TreeSet<>();

			// iterate over all premise-tuples from this level
			for (PremiseTuple<S, A> mu : premiseTuples) {
				action = maxAction(mu.getSupporting());
				confidence = (double) action.getValue() / mu.getSupporting().size();

				// if the potential rule has a higher confidence than the dominant rules
				// and the rule's action is not already present in the dominant rules
				// create a new rule for this premise
				if (confidence > getDomConfidence(mu.getDominant()) && !isInDomActions(action.getKey(), mu.getDominant())) {
					Rule<A> rule = new Rule<>(mu.getPremise(), action.getKey(), confidence);
					rules.add(rule);
					mu.setDominant(rule.wrap());
				}
			}

			if (!rules.isEmpty())
				hkb.put(level, rules);

			// merge premise-tuples for next iteration
			premiseTuples = level < this.dim ? merge(premiseTuples, seq) : Collections.emptyList();
			level++;
		}

		// apply final filters
		if (filter)
			filter(hkb, seqAsPremisses);

		return hkb;
	}

	/**
	 * Determines the most frequent action within the given supporting pairs
	 * and count the action's number of occurrences.
	 * 
	 * @param supp the supporting pairs
	 * @return the most frequent action within the supporting pairs and its number of occurrences
	 */
	protected Entry<A, Integer> maxAction(Collection<StateActionPair<S, A>> supp) {
		int maxOccurrence = 0;
		final Map<A, Integer> actionCount = new TreeMap<>(comparator);

		for (StateActionPair<S, A> sap : supp) {
			// count occurrences of each action
			A action = sap.getAction();
			int occurrences = actionCount.containsKey(action) ? actionCount.get(action) + 1 : 1;
			if (occurrences > maxOccurrence)
				maxOccurrence = occurrences;
			actionCount.put(action, occurrences);
		}

		// determine the action with highest number of occurrences
		// respectively the smallest item according to order of actions
		for (Entry<A, Integer> e : actionCount.entrySet()) {
			if (e.getValue() == maxOccurrence)
				return e;
		}

		return null;
	}

	/**
	 * Extracts elementary premisses from the given state-actions-pairs.
	 * 
	 * @param seq the input sequence of state-action-pairs
	 * @param seqAsPremisses a list to output the state-actions-pairs as premisses
	 * @param rules the rule of thumb
	 * @return initial premise-tuples for elementary premisses
	 */
	protected List<PremiseTuple<S, A>> init(Collection<StateActionPair<S, A>> seq, List<Premise> seqAsPremisses, List<Rule<A>> rules) {
		final Map<Premise, Set<StateActionPair<S, A>>> map = new TreeMap<>();

		// collect literals and supporting pairs
		seq.forEach((sap) -> {
			Premise premise = translator.stateToPremise(sap.getState());
			seqAsPremisses.add(premise);

			if (this.dim == -1)
				this.dim = premise.size();

			premise.forEach((k, v) -> {
				// create literal
				Premise prem = new Premise(k, v);

				// create set of supporting state action pairs
				Set<StateActionPair<S, A>> supp = map.get(prem);

				if (supp == null)
					map.put(prem, supp = new HashSet<>());

				supp.add(sap);
			});
		});

		// create a list of premise-tuples for the collected literals
		final List<PremiseTuple<S, A>> premiseTupels = new LinkedList<>();
		map.forEach((prem, supp) -> {
			if (minSupport == 0 || (double) supp.size() / seq.size() > minSupport)
				premiseTupels.add(new PremiseTuple<>(prem, supp, rules));
		});

		return premiseTupels;
	}

	/**
	 * @param dominant the dominant rules
	 * @return the confidence of the given dominant rules
	 */
	protected double getDomConfidence(List<Rule<A>> dominant) {
		return !dominant.isEmpty() ? dominant.get(0).getConfidence() : minConfidence;
	}

	/**
	 * Determines, whether the given action is already present as conclusion within the list of dominant rules.
	 * 
	 * @param action
	 * @param dominant
	 * @return whether the action is already present as conclusion within the dominant rules
	 */
	protected boolean isInDomActions(A action, List<Rule<A>> dominant) {
		for (Rule<A> r : dominant) {
			if (action.equals(r.getConclusion()))
				return true;
		}

		return false;
	}

	/**
	 * Merges the premise-tuples from the last iteration.
	 * 
	 * @param premiseTuples the premise-tuples to be merged
	 * @param seq the input sequence
	 * @return the list of merged premise-tuples
	 */
	protected List<PremiseTuple<S, A>> merge(List<PremiseTuple<S, A>> premiseTuples, Collection<StateActionPair<S, A>> seq) {
		final List<PremiseTuple<S, A>> merged = new LinkedList<>();

		Premise prem;
		Set<StateActionPair<S, A>> supp;

		// iterate pairwise over the given premise-tuples
		for (PremiseTuple<S, A> mu1 : premiseTuples) {
			ListIterator<PremiseTuple<S, A>> it = premiseTuples.listIterator(premiseTuples.size());
			PremiseTuple<S, A> mu2;
			while (it.hasPrevious() && (mu2 = it.previous()) != mu1) {

				// check pairwise whether the premisses are mergeable
				if (!isMergeable(mu1.getPremise(), mu2.getPremise()))
					continue;

				// calculate the intersection of supporting pairs
				supp = intersect(mu1.getSupporting(), mu2.getSupporting());

				if (supp.isEmpty() || !((double) supp.size() / seq.size() > minSupport))
					continue;

				// in case of sufficient support, merge the premisses
				prem = new Premise();
				prem.putAll(mu1.getPremise());
				prem.putAll(mu2.getPremise());

				merged.add(new PremiseTuple<>(prem, supp, dominant(mu1.getDominant(), mu2.getDominant())));
			}
		}

		return merged;
	}

	/**
	 * Removes unused and unnecessary rules.
	 * 
	 * @param hkb the hierarchical knowledge base to be filtered
	 * @param seqAsPremisses the input sequence as premisses
	 */
	protected void filter(HierarchicalKnowledgeBase<S, A> hkb, List<Premise> seqAsPremisses) {
		final Set<Rule<A>> firingRules = new TreeSet<>();
		for (Premise premise : seqAsPremisses) {
			firingRules.addAll(hkb.reasoning(premise));
		}

		remove(hkb, firingRules);

		List<Rule<A>> currentRules;
		for (Premise premise : seqAsPremisses) {
			currentRules = hkb.reasoning(premise);

			for (Rule<A> current : currentRules) {
				for (Rule<A> rule : hkb.get(current.getPremise().size())) {
					if ((rule != current) && (!currentRules.contains(rule)) && rule.getConclusion().equals(current.getConclusion())
							&& premise.contains(rule.getPremise())) {

						firingRules.remove(current);
						break;
					}
				}
			}
		}

		remove(hkb, firingRules);
	}

	protected void remove(HierarchicalKnowledgeBase<S, A> hkb, final Set<Rule<A>> firingRules) {
		hkb.values().removeIf((rules) -> {
			rules.removeIf((rule) -> {
				return !firingRules.contains(rule);
			});

			return rules.isEmpty();
		});
	}

	public double getMinSupport() {
		return minSupport;
	}

	public AprioriMemExtraction<S, A> setMinSupport(double minSupport) {
		this.minSupport = minSupport;
		return this;
	}

	public double getMinConfidence() {
		return minConfidence;
	}

	public AprioriMemExtraction<S, A> setMinConfidence(double minConfidence) {
		this.minConfidence = minConfidence;
		return this;
	}

	public boolean isFilter() {
		return filter;
	}

	/**
	 * Define whether the final filters will be applied
	 * 
	 * @param filter whether the final filters will be applied
	 * @return this instance
	 */
	public AprioriMemExtraction<S, A> setFilter(boolean filter) {
		this.filter = filter;
		return this;
	}

}
