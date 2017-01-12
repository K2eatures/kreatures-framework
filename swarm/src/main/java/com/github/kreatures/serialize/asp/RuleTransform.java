package com.github.kreatures.serialize.asp;

import java.io.StringReader;

import org.simpleframework.xml.transform.Transform;

import net.sf.tweety.lp.asp.parser.ASPParser;
import net.sf.tweety.lp.asp.parser.InstantiateVisitor;
import net.sf.tweety.lp.asp.syntax.Rule;
//TODO comments
public class RuleTransform implements Transform<Rule>{

	@Override
	public Rule read(String value) throws Exception {
		ASPParser parser = new ASPParser(new StringReader(value));
		
		return new InstantiateVisitor().visit(parser.Rule(), null);
		
	}

	@Override
	public String write(Rule value) throws Exception {
		
		return value.toString();
	}

}
