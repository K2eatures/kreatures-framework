package com.github.kreatures.extraction;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * A hierarchical knowledge base containing one set of rules per state dimension.
 * Both state and action should override the hashCode() method in order to work properly.
 * 
 * @author Manuel Barbi
 *
 * @param <S> state
 * @param <A> action
 */
public class HierarchicalKnowledgeBase<S, A> extends TreeMap<Integer, Set<Rule<A>>> {

	private static final long serialVersionUID = 850804747218686028L;
	protected final Translator<S> translator;

	public HierarchicalKnowledgeBase() {
		this(new TranslatorRefl<>());
	}

	public HierarchicalKnowledgeBase(Translator<S> translator) {
		this.translator = Objects.requireNonNull(translator, "translator must not be null");
	}

	public Rule<A> add(Rule<A> rule) {
		int dim = rule.getPremise().size();
		Set<Rule<A>> rules = get(dim);

		if (rules == null)
			put(dim, rules = new TreeSet<>());

		rules.add(rule);
		return rule;
	}

	/**
	 * Reasons, which rules are suitable for the current state.
	 * 
	 * @param knowledge the current state
	 * @return the firing rules
	 */
	public List<Rule<A>> reasoning(S state) {
		return reasoning(translator.stateToPremise(state));
	}

	public List<Rule<A>> reasoning(Premise knowledge) {
		List<Rule<A>> firingRules = new LinkedList<>();

		// iterate over the rule-sets in descending order
		for (Set<Rule<A>> rules : descendingMap().values()) {
			double maxConf = 0;

			// collect firing rules
			for (Rule<A> rule : rules) {
				double conf = rule.getConfidence();
				if (conf >= maxConf && knowledge.contains(rule.getPremise())) {
					firingRules.add(rule);

					if (conf > maxConf)
						maxConf = conf;
				}
			}

			if (firingRules.isEmpty())
				continue;

			// remove rules with less confidence
			final Iterator<Rule<A>> each = firingRules.iterator();
			while (each.hasNext()) {
				if (each.next().getConfidence() < maxConf)
					each.remove();
			}

			return firingRules;
		}

		return Collections.emptyList();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("--------------------\n");

		for (Set<Rule<A>> rules : values()) {
			for (Rule<A> r : rules) {
				sb.append(r);
				sb.append('\n');
			}
			sb.append("--------------------\n");
		}

		return sb.toString();
	}

}
