package com.github.kreatures.example9;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.TreeSet;

import com.github.kreatures.core.asp.solver.DLV;
import com.github.kreatures.serialize.asp.DLPAtomTransform;
import com.github.kreatures.serialize.asp.RuleTransform;

//import com.github.kreatures.core.serialize.CreateKReaturesXMLFileDefault;

import net.sf.tweety.lp.asp.solver.Solver;
import net.sf.tweety.lp.asp.syntax.Program;
import net.sf.tweety.lp.asp.syntax.Rule;

public class Example10 {

	public static void main(String... list) throws Exception {
		File wb = new File("wissensbasis.lp");
		File dlv = new File("dlv");
		DLV exeDLV = new DLV(dlv.getAbsolutePath());

		Program programm = new Program();
		Collection<Rule> ruleSets = new TreeSet<Rule>();
		FileReader inputWB = new FileReader(wb);
		BufferedReader wbBuffer = new BufferedReader(inputWB);
		DLPAtomTransform transformStrAtome = new DLPAtomTransform();
		RuleTransform transformStrRule = new RuleTransform();

		 wbBuffer.lines().filter(p -> !p.startsWith("%")).map(str ->
		 str.split("%")[0]).forEach(l -> {
		 try {
		 Rule rule = null;
//		 System.out.println("String ist > "+l);
		 
		 if (!l.contains(":-"))
			 rule = new Rule(transformStrAtome.read(l));
		 else
			 rule = new Rule(transformStrRule.read(l));
		
		 ruleSets.add(rule);
//		 System.out.println("Rule ist > "+rule);
		
		 } catch (Exception e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		 });
		
		programm.addAll(ruleSets);
		System.out.println(exeDLV.runDLV(programm, 100, "-nofacts"));

		// new CreateKReaturesXMLFileDefault();

	}

}
