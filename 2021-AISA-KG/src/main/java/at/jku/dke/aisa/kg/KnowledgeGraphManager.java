package at.jku.dke.aisa.kg;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.shared.PrefixMapping;

public class KnowledgeGraphManager {

	ArrayList<Module> moduleList; //in the order they were registered
	Map<String,Module> modules;
	Map<String,StaticModule> staticModules;
	Map<String,InternalModule> internalModules;
	Map<String,ExternalModule> externalModules;
	RDFConnection con;
	PrefixMapping prefixes;

	public KnowledgeGraphManager(RDFConnection con, PrefixMapping prefixes) {
		moduleList = new ArrayList<Module>();
		modules = new HashMap<String,Module>();
		staticModules = new HashMap<String,StaticModule>();
		internalModules = new HashMap<String,InternalModule>();
		externalModules = new HashMap<String,ExternalModule>();
		this.con = con;
		this.prefixes = prefixes;
	}
	
	public void register(Module mod) throws Exception  {
		
		if(modules.containsKey(mod.getName()))
			throw new Exception("Module with name" + mod.getName() + " already registered");
		
		moduleList.add(mod);
		
		modules.put(mod.getName(),mod);
		mod.register(this);

		if(mod instanceof ExternalModule)
			externalModules.put(mod.getName(),(ExternalModule) mod);
		else if(mod instanceof InternalModule)
			internalModules.put(mod.getName(),(InternalModule) mod);
		else if(mod instanceof StaticModule)
			staticModules.put(mod.getName(),(StaticModule) mod);
		else throw new Exception("Every model must be one of ExternalModule, InternalModule, StaticModule");
	}
	
	public void initAllModules() {
		for (Module mod : moduleList) {
			mod.init();
		}		
	}
	
	public RDFConnection getConnection() {
		return con;
	}
	
	public PrefixMapping getPrefixes() {
		return prefixes;
	}
	
	public void cleanKG() {
		System.out.println("Clean KG ...");
		con.update("""
				DELETE { GRAPH ?g { ?s ?p ?o } }
				WHERE { GRAPH ?g { ?s ?p ?o } };
		
				DELETE { ?s ?p ?o }
				WHERE { ?s ?p ?o }
				""");
	} 
	
	public void cleanOutputFolders() throws IOException {
		System.out.println("Clean Output Folders ...");
		FileUtils.cleanDirectory(new File("fileoutput"));
	}
	
	public void startConsoleApplication(InputStream in, PrintStream out) {
		Scanner scanner = new Scanner(in);
        out.print("Your commands: > ");
        while (true){
            String s = scanner.next();
            if(s.equals("exit")) break;
            else if(s.equals("run")) {
            	String mod = scanner.next();
            	if(internalModules.containsKey(mod))
            		internalModules.get(mod).run();
            	else
            		out.println("There is no internal module '"+mod+"'");
            }
            else if(s.equals("start")) {
            	String mod = scanner.next();
            	if(externalModules.containsKey(mod))
            		externalModules.get(mod).startTurn();
            	else
            		out.println("There is no external module '"+mod+"' to start");
            }
            else if(s.equals("finish")) {
            	String mod = scanner.next();
            	if(externalModules.containsKey(mod))
            		externalModules.get(mod).finishTurn();
            	else
            		out.println("There is no external module '"+mod+"' to finish");
            }
            else {
            	out.println("Wrong input: "+ s);
            	out.println("type one of the following:");
            	out.println("   exit");
            	out.println("   run <internal module>");
            	out.println("   start <external module>");
            	out.println("   finish <external module>");
            }
            System.out.print("> ");            
        }
        System.out.println("Bye...");
        scanner.close(); 
	}
	
}