package com.github.kreatures.swarm.basic;

import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.fol.syntax.FOLAtom;

import com.github.kreatures.core.Desire;
import com.github.kreatures.core.logic.Desires;

/**
 * 
 * @author donfack
 *
 */
public class SwarmDesires extends Desires {
	
	
	/**
	 * A agent want to select a station as next destination.
	 */
	public static final Desire CHOICE_STATION= new Desire(new FOLAtom(new Predicate("choice_station")));
	/**
	 * A agent want to change the current's station.
	 */
	public static final Desire CHANGE_STATION = new Desire(new FOLAtom(new Predicate("change_station")));
	/**
	 * A agent want to quit the station and will be not enter a other station.
	 */
	public static final Desire QUIT_STATION= new Desire(new FOLAtom(new Predicate("quit_station")));
	/**
	 * A agent si visiting a current station and want to stay into in oder to do its job.
	 */
	public static final Desire STAY_STATION = new Desire(new FOLAtom(new Predicate("stay_station")));

	/**
	 * @author Manuel Barbi
	 * @param fst
	 * @param snd
	 * @return
	 */
	public static final boolean isEqual(Desire fst, Desire snd) {
		if (fst == null && snd == null) {
			return true;
		}

		if (fst != null && snd != null) {
			if (fst.getFormula() == null && snd.getFormula() == null) {
				return true;
			}

			if (fst.getFormula() != null && snd.getFormula() != null) {
				return fst.getFormula().toString().equalsIgnoreCase(snd.getFormula().toString());
			}
		}

		return false;
	}

}