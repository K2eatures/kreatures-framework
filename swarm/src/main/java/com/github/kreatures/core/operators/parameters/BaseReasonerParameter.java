package com.github.kreatures.core.operators.parameters;

import javax.management.AttributeNotFoundException;

import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.error.ConversionException;
import com.github.kreatures.core.operators.parameter.GenericOperatorParameter;
import com.github.kreatures.core.operators.parameter.ReasonerParameter;

public class BaseReasonerParameter extends ReasonerParameter {
	
	public static final String PARAMETER_NAME="ReasonerParameter";
	
	private String[] options= {""};

	/** Default Ctor: Used for dynamic instantiation */
	public BaseReasonerParameter() {}

	public BaseReasonerParameter(BaseBeliefbase owner) {
		super(owner);
		
	}
	
	public BaseReasonerParameter(BaseBeliefbase owner,String ... options) {
		super(owner);
		this.options=options;
		
	}
	
	@Override
	public void fromGenericParameter(GenericOperatorParameter input)
			throws ConversionException, AttributeNotFoundException {
		super.fromGenericParameter(input);
		
		Object obj=input.getParameter("options");
		if(obj==null || !(obj instanceof String[])) {
			throw conversionException("options",String[].class);
		}
		
		this.options=(String[])obj;
	}

	public String[] getOptions() {
		return options;
	}

}
