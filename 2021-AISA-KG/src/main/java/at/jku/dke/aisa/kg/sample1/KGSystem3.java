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
		ADSBLoader adsb = new ADSBLoader();
		PrologModule prolog = new PrologModule();
		QueryADSB qadsb = new QueryADSB();
		ADSBProcessor1 adsbP1 = new ADSBProcessor1();
		ADSBProcessor2 adsbP2 = new ADSBProcessor2();
		FlightPairs pairs = new FlightPairs();
		PerformanceReport report = new PerformanceReport();
		
		
		kg.setLogicalTime(time++);

		kg.register(schema); 
		kg.register(adsb); 
		kg.register(prolog);
		kg.register(qadsb); 
		kg.register(adsbP1); 
		kg.register(adsbP2); 
		kg.register(pairs);  
		kg.register(report);  
		
		kg.setLogicalTime(time++);

		kg.initAllModules();
			
		for(int i=0; i<5; i++) {
			kg.setLogicalTime(time++);
			adsb.run();
			kg.setLogicalTime(time++);
			prolog.run();
			kg.setLogicalTime(time++);  
			qadsb.run();
			kg.setLogicalTime(time++);
			adsbP1.run();
			kg.setLogicalTime(time++);
			adsbP2.run();
			kg.setLogicalTime(time++);
			pairs.run();
		}
		
		kg.setLogicalTime(time++);
		report.run();
		
	}
	
}
