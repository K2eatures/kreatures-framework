package com.github.kreatures.extraction.wrapper;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.github.kreatures.core.Action;
import com.github.kreatures.core.Agent;
import com.github.kreatures.core.KReatures;
import com.github.kreatures.core.KReaturesEnvironment;
import com.github.kreatures.core.Perception;
import com.github.kreatures.core.def.DefaultBehavior;
import com.github.kreatures.extraction.learning.BasicEnvironment;
import com.github.kreatures.extraction.learning.RLAgent;

/**
 * 
 * @author Manuel Barbi
 *
 */
public abstract class ENVIRONMENT<P, A> extends DefaultBehavior {

	protected BasicEnvironment<P, A> env;
	protected BlockingQueue<A> queue = new LinkedBlockingQueue<>();

	@SuppressWarnings("unchecked")
	@Override
	public void sendAction(KReaturesEnvironment kre, Action act) {
		this.queue.add(((ACTION<A>) act).getAction());
	}

	@Override
	public void receivePerception(KReaturesEnvironment kre, Perception percept) {}

	@Override
	public boolean runOneTick(KReaturesEnvironment kre) {
		doingTick = true;

		// simulation stuff

		while (!isSimulationReady()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		kreaturesReady = false;
		KReatures.getInstance().onTickStarting(kre);

		// actual execution

		if (this.env == null)
			this.env = createEnv(wrapAgents(kre.getAgents()));

		somethingHappens = this.env.runSingleStep();

		// synchronization stuff

		kreaturesReady = true;

		doingTick = false;
		KReatures.getInstance().onTickDone(kre);

		return somethingHappens;
	}

	protected abstract BasicEnvironment<P, A> createEnv(List<RLAgent<P, A>> agents);

	protected List<RLAgent<P, A>> wrapAgents(Collection<Agent> krAgents) {
		final List<RLAgent<P, A>> agents = new LinkedList<>();
		krAgents.forEach((agt) -> agents.add(new AGENT<>(agt, this.queue)));
		return agents;
	}

}
