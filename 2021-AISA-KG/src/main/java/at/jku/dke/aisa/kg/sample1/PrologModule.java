package at.jku.dke.aisa.kg.sample1;


import java.io.IOException;

import org.apache.jena.rdfconnection.RDFConnection;

import at.jku.dke.aisa.kg.AbstractInternalModule;
import at.jku.dke.aisa.kg.AbstractPrologModule;
import at.jku.dke.aisa.kg.InternalModule;
import at.jku.dke.aisa.kg.KGModuleSystem;

import org.jpl7.Query;

/**
 *     
 * */
public final class PrologModule extends AbstractPrologModule   {
	
	
	public PrologModule() {
		super("prolog");
	}

	/* Override the log method per engine to easily specify the level of logging per engine 
	 * Having the log method is in development/debugging mode of the engine/module
	 * */
//	public void log(String str) {
//		System.out.println(str);
//	}
	
	
	public void doInit() {
		System.out.println("DO INIT: " + getName() + "-" + getTurn() + "-" + getLogicalTime());
		
//		try {
//			this.createOutputFolderIfNotExists();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		//replicate initial state of KG to RDF-DB
//		this.executeSparqlSelect(null, null);		
//		
//		//load program.pl
//		String programPath = this.getInputPath("program.pl");
//		System.out.println(programPath);
//		
//		new Query("consult('" + programPath +"')").hasSolution();
//
//		new Query("rdf_load('fileinput/adsb/input.trig')").hasSolution();
		
	}

	public void doRun() {
		System.out.println("DO RUN: " + getName() + "-" + getTurn() + "-" + getLogicalTime());

		new Query("run('"+ getTurnIri() +"',"+ getTurn() +")").hasSolution();
		
		
		
		//replicate new named graphs from KG to RDF-DB
		
		//call run(<turn>) in Prolog, which first writes to RDF-DB and from there exports to TTL-File
		//new Query("save('http://aisa-project.eu/graphs/g"+ (this.getTurn()-1) +"')").hasSolution();
//		new Query("run("+ this.getTurn() +")").hasSolution();
		
		
		//load TTL-File into AISA-KG
		

		
	}
}
