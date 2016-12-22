/**
 * 
 */
package com.github.kreatures.swarm;

import com.github.kreatures.swarm.exceptions.SwarmException;

/**
 * @author donfack
 *
 */
public interface Swarm {
	/**
	 * The init method creates all the needed files of AbstractSwarm in the corresponding Kreatures' folder.
	 *  @return true if the process has successful done.
	 * @throws T if no success, the exception T has the reason.
	 */
	<T extends SwarmException> boolean init() throws T ;
	/**
	 * The loading method creates all the needed components of AbstractSwarm in Kreatures. This happens during the first loading.
	 * @return true if the process has successful done.
	 * @throws T if no success, the exception T has the reason.
	 */
	<T extends SwarmException> boolean loading()throws T;
	/**
	 * The unloading method deletes all the needed components of AbstractSwarm in Kreatures. This happens during the closing process.
	 * @return true if the process has successful done.
	 * @throws T if no success, the exception T has the reason.
	 */
	<T extends SwarmException> boolean unloading() throws T;
	/**
	 * The reloading method is used when the application is running and the user is creating the new Abstractswarm scenario. 
	 * When He is terminated the creation of the scenario, this must must load the scenario in KReatures without close the application.
	 * @return true if the process has successful done.
	 * @throws T if no success, the exception T has the reason.
	 */
	<T extends SwarmException> boolean reloading() throws T;

}
