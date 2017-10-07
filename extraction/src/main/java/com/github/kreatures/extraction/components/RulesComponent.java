package com.github.kreatures.extraction.components;

import java.util.List;

import com.github.kreatures.core.BaseAgentComponent;
import com.github.kreatures.core.comp.Presentable;
import com.github.kreatures.extraction.HierarchicalKnowledgeBase;

/**
 * 
 * @author Manuel Barbi
 *
 */
public class RulesComponent<S, A> extends BaseAgentComponent implements Presentable {

	protected HierarchicalKnowledgeBase<S, A> hkb = new HierarchicalKnowledgeBase<>();

	@Override
	public void getRepresentation(final List<String> representation) {
		hkb.values().forEach((rules) -> rules.forEach((r) -> representation.add(r.toString())));
	}

	@Override
	public BaseAgentComponent clone() {
	//	RulesComponent<S, A> cln = new RulesComponent<>();
	//	hkb.values().forEach((rules) -> rules.forEach((r) -> cln.hkb.add(r)));
	//	return cln;
		return this;
	}

}
