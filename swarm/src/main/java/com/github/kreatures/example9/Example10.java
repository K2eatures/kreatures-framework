package com.github.kreatures.example9;

import java.nio.file.Paths;

import com.github.kreatures.core.serialize.CreateKReaturesXMLFileDefault;

public class Example10 {

//	public static void main(String... list) throws Exception {
//		
//		//System.out.format("Path is %s%n.", Paths.get("target/classes/dlv").toAbsolutePath().toString());
//		Path dlvPath=Paths.get("target/classes/dlv").toAbsolutePath();
//		if(!Files.isExecutable(dlvPath)){
//			Files.setPosixFilePermissions(dlvPath, PosixFilePermissions.fromString("r-xr-xr-x"));
//		}
//		DLV exeDLV = new DLV(dlvPath.toString());
//		Program programm = new Program();
//		Collection<Rule> ruleSets = new TreeSet<Rule>();
//		BufferedReader wbBuffer = Files.newBufferedReader(Paths.get("target/classes/wissensbasis.lp"));
//		DLPAtomTransform transformStrAtome = new DLPAtomTransform();
//		RuleTransform transformStrRule = new RuleTransform();
//		//Supprimes toutes les lignes vides et celles commencant par %
//		 wbBuffer.lines().filter(q->!q.equals("")).filter(p -> !p.startsWith("%")).map(str ->
//		 str.split("%")[0]).forEach(l -> {
//		 try {
//		 Rule rule = null;
////		 System.out.println("String ist > "+l);
//		 
//		 if (!l.contains(":-"))
//			 rule = new Rule(transformStrAtome.read(l));
//		 else
//			 rule = new Rule(transformStrRule.read(l));
//		
//		 ruleSets.add(rule);
////		 System.out.println("Rule ist > "+rule);
//		
//		 } catch (Exception e) {
//		 // TODO Auto-generated catch block
//		 e.printStackTrace();
//		 }
//		 });
//		
//		programm.addAll(ruleSets);
//		System.out.println(exeDLV.runDLV(programm, 100, "-nofacts","-filter=NumberItemToProductInStation"));//,"-filter=ItemProductInStation"));
//
//		// new CreateKReaturesXMLFileDefault();
//
//	}
	
	public static void main(String... list) {
		
		try {
			new CreateKReaturesXMLFileDefault();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
