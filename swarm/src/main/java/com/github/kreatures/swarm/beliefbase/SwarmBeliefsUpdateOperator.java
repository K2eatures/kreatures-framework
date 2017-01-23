package com.github.kreatures.swarm.beliefbase;

import java.util.Set;








import org.slf4j.Logger;
import org.slf4j.LoggerFactory;








import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.NewAgent;
import com.github.kreatures.core.logic.Beliefs;
import com.github.kreatures.core.operators.BaseBeliefsUpdateOperator;
import com.github.kreatures.core.operators.parameters.PerceptionParameter;
import com.github.kreatures.swarm.basic.SwarmPerception;
import com.github.kreatures.swarm.predicates.transform.TransformPredicates;







import net.sf.tweety.logics.fol.syntax.FolFormula;


/**
 * 
 * @author donfack
 *
 */
public class SwarmBeliefsUpdateOperator extends BaseBeliefsUpdateOperator {
	/** reference to the logback logger instance */
	private Logger LOG = LoggerFactory.getLogger(SwarmBeliefsUpdateOperator.class);
	
	@SuppressWarnings("unused")
	@Override
	protected BaseBeliefbase processImpl(PerceptionParameter objParameter) {
		NewAgent nAgent=(NewAgent)objParameter.getAgent();
			// TODO Auto-generated method stub
			if(objParameter.getPerceptions()==null){
				nAgent.report("no Perceptions receive.");
				return objParameter.getBaseBeliefbase();
			}
			
			if(objParameter.getPerceptions().isEmpty()){
				nAgent.report("no Perceptions receive.");
				return objParameter.getBaseBeliefbase();
			}
			
			//Hier weiter
			
			if(objParameter!=null){
				Set<FolFormula> formulaSet=objParameter.getAgent().getBeliefs().getWorldKnowledge().infere();
				
				LOG.info("SwarmBeliefsUpdateOperator  ->"+formulaSet.toString());
			}else{
				LOG.info("SwarmBeliefsUpdateOperator");
			}
		return null;
	}
	
	
	
	@Override
	protected void prepare(PerceptionParameter params) {
		if(params==null)
			return;
		if(params.getPerceptions()==null)
			return;
		params.getPerceptions().stream().filter(swarmPercept->{
		SwarmPerception obj=(SwarmPerception) swarmPercept;
		
		if(obj.getFact()==null)
			return false;
		
		return true;
		}).forEach(swarmPercept->{
			SwarmPerception obj=(SwarmPerception) swarmPercept;
			try {
				params.getBaseBeliefbase().addKnowledge(TransformPredicates.getPredicate(obj.getFact()));
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
	}

}
