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

public class KGModuleSystem {

	ArrayList<KGModule> moduleList; //in the order they were registered
	Map<String,KGModule> modules;
	Map<String,SingleRunModule> staticModules;
	Map<String,InternalModule> internalModules;
	Map<String,ExternalModule> externalModules;
	RDFConnection con;
	PrefixMapping prefixes;
	long logicalTime;

	public KGModuleSystem(RDFConnection con, PrefixMapping prefixes) {
		moduleList = new ArrayList<KGModule>();
		modules = new HashMap<String,KGModule>();
		staticModules = new HashMap<String,SingleRunModule>();
		internalModules = new HashMap<String,InternalModule>();
		externalModules = new HashMap<String,ExternalModule>();
		this.con = con;
		this.prefixes = prefixes;
	}
	
	public void register(KGModule mod) throws Exception  {
		
		if(modules.containsKey(mod.getName()))
			throw new Exception("Module with name" + mod.getName() + " already registered");
		
		moduleList.add(mod);
		
		modules.put(mod.getName(),mod);
		mod.register(this);

		if(mod instanceof ExternalModule)
			externalModules.put(mod.getName(),(ExternalModule) mod);
		else if(mod instanceof InternalModule)
			internalModules.put(mod.getName(),(InternalModule) mod);
		else if(mod instanceof SingleRunModule)
			staticModules.put(mod.getName(),(SingleRunModule) mod);
		else throw new Exception("Every model must be one of ExternalModule, InternalModule, StaticModule");
	}
	
	public void initAllModules() {
		for (KGModule mod : moduleList) {
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
        	logicalTime++;
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
            		externalModules.get(mod).exportInput();
            	else
            		out.println("There is no external module '"+mod+"' to start");
            }
            else if(s.equals("finish")) {
            	String mod = scanner.next();
            	if(externalModules.containsKey(mod))
            		externalModules.get(mod).importResults();
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
	
	public long getLogicalTime() {
		return logicalTime;
	}

	public void setLogicalTime(long logicalTime) {
		this.logicalTime = logicalTime;
	}
	
	public void setLogicalTimeToNow() {
		this.logicalTime = System.currentTimeMillis();
	}

	
}
