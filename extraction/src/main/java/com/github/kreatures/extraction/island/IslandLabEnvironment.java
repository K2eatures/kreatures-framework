package com.github.kreatures.extraction.island;

import static com.github.kreatures.extraction.island.IslandLocation.AT_HQ;
import static com.github.kreatures.extraction.island.IslandLocation.AT_SITE;
import static com.github.kreatures.extraction.island.IslandLocation.IN_CAVE;
import static com.github.kreatures.extraction.island.IslandLocation.ON_THE_WAY_1;
import static com.github.kreatures.extraction.island.IslandLocation.ON_THE_WAY_2;
import static com.github.kreatures.extraction.island.IslandLocation.ON_THE_WAY_3;
import static com.github.kreatures.extraction.island.IslandWeather.CLOUDS;
import static com.github.kreatures.extraction.island.IslandWeather.STORM_OR_RAIN;
import static com.github.kreatures.extraction.island.IslandWeather.SUN;
import static com.github.kreatures.extraction.island.IslandWeather.THUNDERSTORM;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.extraction.learning.RLAgent;
import com.github.kreatures.extraction.learning.RLEnvironment;

/**
 * 
 * @author Manuel Barbi
 *
 */
public class IslandLabEnvironment extends RLEnvironment<IslandPerception, IslandAction> {

	private static final Logger LOG = LoggerFactory.getLogger(IslandLabEnvironment.class);

	protected Random rnd = new Random();

	public static final int FULL_BATTERY = 32;

	public static final int NUM_PARTS = 8;
	public static final int PART = 8;

	public static final int SITE_COMPLETE = NUM_PARTS * PART;

	public static final int RESOLUTION = 4;

	protected int site;
	protected boolean secured;

	protected IslandWeather weather;
	protected IslandWeather prediction;

	protected int battery;
	protected IslandLocation location;
	protected double reward;

	public IslandLabEnvironment(List<RLAgent<IslandPerception, IslandAction>> agents) {
		super(agents);

		if (agents.size() != 1)
			throw new IllegalArgumentException("this environment does support only one agent at the moment");
	}

	protected IslandWeather generateWeather() {
		switch (rnd.nextInt(4)) {
		case 1:
			return SUN;
		case 2:
			return STORM_OR_RAIN;
		case 3:
			return THUNDERSTORM;
		default:
			return CLOUDS;
		}
	}

	@Override
	public IslandPerception createPerception(int agentId) {
		return new IslandPerception( //
				(site * RESOLUTION) / SITE_COMPLETE, //
				secured, //
				(battery * RESOLUTION) / FULL_BATTERY, //
				location, //
				weather, //
				prediction);
	}

	@Override
	public void executeAction(int agentId, IslandAction action) {
		// discharge battery
		battery = Math.max(battery - 1, 0);

		// keep state of site
		int keepSiteState = site % PART;

		exec(action);
		env(agentId, keepSiteState);
	}

	@SuppressWarnings("incomplete-switch")
	protected void exec(IslandAction action) {

		boolean slow = weather == STORM_OR_RAIN || weather == THUNDERSTORM;;

		switch (location) {
		case AT_HQ:
			switch (action) {
			case CHARGE_BATTERY:
				battery = Math.min(battery + 8, FULL_BATTERY);
				break;
			case MOVE_TO_SITE:
				location = (slow ? ON_THE_WAY_1 : ON_THE_WAY_2);
				break;
			}
			break;
		case AT_SITE:
			switch (action) {
			case ASSEMBLE_PARTS:
				site = Math.min(site + (slow ? 1 : 2), SITE_COMPLETE);
				break;
			case COVER_SITE:
				secured = true;
				break;
			case UNCOVER_SITE:
				secured = false;
				break;
			case MOVE_TO_HQ:
				location = (slow ? ON_THE_WAY_3 : ON_THE_WAY_2);
				break;
			case ENTER_CAVE:
				location = IN_CAVE;
				break;
			}
			break;
		case IN_CAVE:
			switch (action) {
			case LEAVE_CAVE:
				location = AT_SITE;
				break;
			}
			break;
		case ON_THE_WAY_1:
			switch (action) {
			case MOVE_TO_HQ:
				location = AT_HQ;
				break;
			case MOVE_TO_SITE:
				location = (slow ? ON_THE_WAY_2 : ON_THE_WAY_3);
				break;
			}
			break;
		case ON_THE_WAY_2:
			switch (action) {
			case MOVE_TO_HQ:
				location = (slow ? ON_THE_WAY_1 : AT_HQ);
				break;
			case MOVE_TO_SITE:
				location = (slow ? ON_THE_WAY_3 : AT_SITE);
				break;
			}
			break;
		case ON_THE_WAY_3:
			switch (action) {
			case MOVE_TO_HQ:
				location = (slow ? ON_THE_WAY_2 : ON_THE_WAY_1);
				break;
			case MOVE_TO_SITE:
				location = AT_SITE;
				break;
			}
			break;
		default:
			LOG.warn("unhandled action");
		}
	}

	protected void env(int agentId, int keepSiteState) {

		boolean shelter = (location == AT_HQ || location == IN_CAVE);

		// assembled one component
		reward = ((site % PART) > keepSiteState) ? 0.0 : -1.0;

		// charge battery with solar panel
		if (!shelter && weather == SUN)
			battery = Math.min(battery + 2, FULL_BATTERY);

		if (weather == THUNDERSTORM) {
			// generate lightning
			IslandLocation[] values = IslandLocation.values();
			int loc = rnd.nextInt(2 * values.length);
			IslandLocation lightning = (loc < values.length) ? values[loc] : null;

			// site was struck by lightning
			if (!secured && this.site < SITE_COMPLETE && AT_SITE.equals(lightning)) {
				int damage = rnd.nextInt(2 * PART);
				this.site = Math.max(this.site - damage, 0);
				LOG.debug("site was damaged");
				reward = -10.0;
			}

			// agent was struck by lightning
			if (!shelter && location.equals(lightning)) {
				LOG.debug("agent {} was damaged", agentId);
				location = AT_HQ;
				battery = FULL_BATTERY;
				reward = -100.0;
			}
		}

		// low energy
		if (battery == 0) {
			LOG.debug("agent {} ran out of energy", agentId);
			location = AT_HQ;
			battery = FULL_BATTERY;
			reward = -100.0;
		}

		// generate next weather
		if (tick % 4 == 0) {
			weather = (rnd.nextDouble() > 0.2) ? prediction : generateWeather();
			prediction = generateWeather();
		}
	}

	@Override
	public double getReward(int agentId) {
		return !terminationCriterion(agentId) ? reward : 0;
	}

	@Override
	public boolean terminationCriterion(int agentId) {
		// agent has to return to HQ after finishing work
		return this.site == SITE_COMPLETE && location == AT_HQ;
	}

	@Override
	public void reboot() {
		super.reboot();

		this.site = 0;
		this.secured = true;

		this.weather = CLOUDS;
		this.prediction = generateWeather();

		this.battery = FULL_BATTERY;
		this.location = AT_HQ;

		this.reward = 0.0;
	}

}
