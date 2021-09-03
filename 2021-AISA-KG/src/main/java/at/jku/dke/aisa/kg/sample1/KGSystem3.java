package at.jku.dke.aisa.kg.sample1;

import at.jku.dke.aisa.kg.GLOBAL;
import at.jku.dke.aisa.kg.KGModuleSystem;
import at.jku.dke.aisa.kg.SchemaLoader;

public class KGSystem3 {

	public KGSystem3() {}

	public static void main(String[] args) throws Exception {

		KGModuleSystem kg = 
				new KGModuleSystem(
//						GLOBAL.getNewDatasetConnection(),
						GLOBAL.getLocalFusekiConnection(),						
						GLOBAL.getPrefixMapping()
						);
		
		long time = 0;
		
		kg.setLogicalTime(time++);
		
		kg.cleanKG();
		kg.cleanOutputFolders();
		
		SchemaLoader schema = new SchemaLoader();
		PrologModule prolog = new PrologModule();
		DonlonLoader donlon = new DonlonLoader();
		FixmLoader fixm = new FixmLoader();
		PerformanceReport report = new PerformanceReport();
		
		
		kg.setLogicalTime(time++);

		kg.register(schema); 
		kg.register(fixm);
		kg.register(donlon); 
		kg.register(prolog); 
		kg.register(report); 
		
		kg.setLogicalTime(time++);

		kg.initAllModules();
			
		for(int i=0; i<5; i++) {
			kg.setLogicalTime(time++);
			donlon.run();
			kg.setLogicalTime(time++);
			fixm.run();
			kg.setLogicalTime(time++);
			prolog.run();
		}
		
		kg.setLogicalTime(time++);
		report.run();
		
	}
	
}
