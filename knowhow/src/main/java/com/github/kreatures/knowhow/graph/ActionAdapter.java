package com.github.kreatures.knowhow.graph;

import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.Action;
import com.github.kreatures.core.Agent;
import com.github.kreatures.core.Perception;
import com.github.kreatures.core.comm.Answer;
import com.github.kreatures.core.comm.Inform;
import com.github.kreatures.core.comm.Query;
import com.github.kreatures.core.logic.AnswerValue;
import com.github.kreatures.core.logic.Beliefs;
import com.github.kreatures.core.logic.KReaturesAnswer;
import com.github.kreatures.core.util.Utility;
import com.github.kreatures.knowhow.graph.parameter.Parameter;
import com.github.kreatures.knowhow.graph.parameter.Parameter.TYPE;

import net.sf.tweety.logics.fol.parser.FolParserB;
import net.sf.tweety.logics.fol.parser.ParseException;
import net.sf.tweety.logics.fol.syntax.FolFormula;
import net.sf.tweety.logics.fol.syntax.FolSignature;

/**
 * This action adapts another action, it acts as placeholder and can dynamically
 * generate the action for that it is the placeholder, therefore it stores the actions
 * name and the list of {@link Parameter} that can be used to generate the Action.
 * 
 * Instances of this class get generated by a planner, which might use
 * runtime variables like the result of a query on the agents belief base. The planner
 * might generate this action for step 1+n with n>1 in the plan, so that at plan creation time the
 * runtime variables cannot be evaluated, because the result of the query on the belief base
 * might have changed in the n steps beforehand.
 * 
 * @todo use factory pattern to create actions of the action adapter, add registration of those factories
 * 
 * @author Tim Janus
 */
public class ActionAdapter extends Action {
	/** logging facility */
	private static Logger LOG = LoggerFactory.getLogger(ActionAdapter.class);
	
	/** the name of the action that is adapted by this instance */
	private String actionName;
	
	/** the list of parameters used to generate the action */
	private List<Parameter> parameters;
	
	/** the context that this action can use: the context for an answer is a query for example */
	private Perception context;
	
	/** the currently used beliefs */
	private Beliefs curBeliefs;
	
	public ActionAdapter(Agent sender, String actionName, List<Parameter> parameters, Perception context) {
		super(sender);
		this.actionName = actionName;
		this.parameters = parameters;
		this.context = context;
	}
	
	/**
	 * Generates the action that instance adapts using the current agent beliefs
	 * @return	Reference to the generated action
	 */
	public Action evaluateAction() {
		return evaluateAction(this.agent.getBeliefs());
	}

	/**
	 * Generates the action that instance adapts using the beliefs that are given as parameter
	 * @param beliefs	The beliefs used for evaluation (for reasoner calls for example)
	 * @return	Reference to the generated action or null if the action is not planned yet.
	 */
	public Action evaluateAction(Beliefs beliefs) {
		curBeliefs = beliefs;
		Action reval = null;
		if(actionName.equals("Inform")) {
			reval = createInform(parameters);
		} else if(actionName.equals("Query")) {
			reval = createQuery(parameters);
		} else if(actionName.equals("QueryAnswer")) {
			reval = createAnswer(parameters, (Query)context);
		} else if(actionName.equals("TBD")) {
			return null;
		} else {
			throw new UnsupportedOperationException("Generation of Action '" + actionName + "' not implemented yet");
		}
		reval.setParent(getParent());
		return reval;
	}
	
	@Override
	public String toString() {
		if(actionName.equals("TBD"))
			return "<ActionAdapter(TBD)>";
		return "<ActionAdapter(" + evaluateAction() + ")>";
	}
	
	/**
	 * Creates an Answer action
	 * @param parameters	The parameters that are used for creation currently the signatures:
	 * 						(T_HONESTY) and (T_CONSTANT in {YES, NO, UNKNOWN, REJECT}) are supported.
	 * @param context
	 * @return
	 */
	private Answer createAnswer(List<Parameter> parameters, Query context) {
		if(parameters.size() != 1 && parameters.size() != 3) {
			throw new IllegalStateException("The parameter count for an Answer must be '1' or '3' not '" + parameters.size() + "'");
		}
		
		if(parameters.size() == 3) {
			TYPE t0 = parameters.get(0).getType();
			TYPE t1 = parameters.get(1).getType();
			TYPE t2 = parameters.get(2).getType();
			
			if(t0 != TYPE.T_AGENT || t1 != TYPE.T_FORMULA || t2 != TYPE.T_HONESTY) {
				throw new IllegalStateException("Invalid Signature (" + t0.toString() + ", " + t1.toString() + ", " +
						t2.toString() + ") awaitng: (" + TYPE.T_AGENT.toString() + ", " + TYPE.T_FORMULA.toString() + ", " +
						TYPE.T_HONESTY + ").");
			}

			String receiver = mapAgent(parameters.get(0));
			FolFormula question = mapFormula(parameters.get(1));
			String honesty = parameters.get(2).getIdentifier();
			
			KReaturesAnswer answer = curBeliefs.getWorldKnowledge().reason(question);
			if(honesty.equals("p_lie")) {
				answer = Utility.lie(answer);
			} else if(honesty.equals("p_withholding")) {
				return new Answer(this.agent, receiver, question, AnswerValue.AV_REJECT);
			}
			return new Answer(this.agent, receiver, question, answer);
		} else {
			if(! (context instanceof Query))
				throw new IllegalStateException("Context of Answer is no 'Query' but '" + context.getClass().getSimpleName() + "'");
			
			Parameter param = parameters.get(0);
			if(param.getType() == TYPE.T_HONESTY) {
				// TODO this bunch of code is duplo, create a factory somewhere:
				if(context.getQuestion().isGround()) {
					AnswerValue simpleAnswer = curBeliefs.getWorldKnowledge().reason(context.getQuestion()).getAnswerValue();		
					if(param.getIdentifier().equals("p_honest")) {
						// do nothing
						LOG.info("Honest");
					} else if(param.getIdentifier().equals("p_lie")) {
						simpleAnswer = Utility.lie(simpleAnswer);
					} else {
						throw new UnsupportedOperationException("The honesty type: '" + param.getIdentifier().substring(2) + "' is not implemented yet.");
					}
					
					return new Answer(this.agent, context.getSenderId(), context.getQuestion(), 
							new KReaturesAnswer(context.getQuestion(), simpleAnswer));
				}
				
				throw new UnsupportedOperationException("Cannot handle the answer on open queries yet.");
			} else if(param.getType() == TYPE.T_CONSTANT) {
				String constant = param.getIdentifier().substring(2);
				AnswerValue av = null;
				if(constant.equalsIgnoreCase("YES")) {
					av = AnswerValue.AV_TRUE;
				} else if(constant.equalsIgnoreCase("NO")) {
					av = AnswerValue.AV_FALSE;
				} else if(constant.equalsIgnoreCase("UNKNOWN")) {
					av = AnswerValue.AV_UNKNOWN;
				} else if(constant.equalsIgnoreCase("REJECT")) {
					av = AnswerValue.AV_REJECT;
				} else {
					throw new IllegalArgumentException("The constant '" + constant + "' is not allowed as argument for an answer.");
				}
				return new Answer(this.agent, context.getSenderId(), context.getQuestion(), av);
			} else {
				throw new IllegalArgumentException("The parameter type '" + param.getType() + "' is not supported for an Answer");
			}
		}
	}
	
	/**
	 * Creates a query action
	 * @param parameters	The parameters that are used for creation, currently the signatures:
	 * 						(T_AGENT, T_FORMULA) is supported
	 * @return
	 */
	private Query createQuery(List<Parameter> parameters) {
		if(parameters.size() != 2) {
			throw new IllegalStateException("A Query has '2' Parameters not '" + parameters.size() + "'");
		}
		
		Parameter recvP = parameters.get(0);
		Parameter questionP = parameters.get(1);
		
		Query query = new Query(agent, mapAgent(recvP), mapFormula(questionP));
		LOG.info("Created Action: '" + query.toString() + "'");
		return query;
	}
	
	/**
	 * Creates an inform action
	 * @param parameters	The parameters that are used for creation, currently the signature:
	 * 						(T_AGENT, T_FORMULA) is supported.
	 * @return
	 */
	private Inform createInform(List<Parameter> parameters) {
		if(parameters.size() != 2) {
			throw new IllegalStateException("An Inform has '2' Parameters not '" + parameters.size() + "'");
		}
		
		Parameter recvP = parameters.get(0);
		Parameter formulaP = parameters.get(1);
		
		Set<FolFormula> formulas = new HashSet<>();
		formulas.add(createFormula(formulaP));
		Inform inform = new Inform(agent.getName(), 
				mapAgent(recvP), formulas);
		

		return inform;
	}
	
	private FolFormula createFormula(Parameter param) {
		FolParserB parser = new FolParserB(new StringReader(param.getIdentifier()));
		FolFormula reval = null;
		try {
			reval = parser.formula(new FolSignature());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reval;
	}
	
	private FolFormula mapFormula(Parameter param) {
		if(param.getIdentifier().startsWith("q_")) {
			FolFormula question = createFormula(new Parameter(param.getIdentifier().substring(2)));
			if(question.isClosed()) {
				throw new IllegalStateException("The query prefix 'q_' for formulas only allows open queries but" +
						" '" + question.toString() + "' is not open.");
			}
			
			KReaturesAnswer answer = curBeliefs.getWorldKnowledge().reason(question);
			if(!answer.getAnswers().isEmpty()) {
				FolFormula reval = answer.getAnswers().iterator().next();
				return reval;
			}
			
			LOG.warn("There was no match found for the query '" + question.toString() + "' using question for query");
			return question;
		} else {
			return createFormula(param);
		}
	}
	
	private String mapAgent(Parameter param) {
		String reval = param.getIdentifier().substring(2);
		return reval.equals("SELF") ? agent.getName() : reval;
	}
}
