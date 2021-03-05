package at.jku.dke.aisa.kg.sample1;

import at.jku.dke.aisa.kg.GLOBAL;
import at.jku.dke.aisa.kg.KnowledgeGraphManager;

public class KGSystem1 {

	public KGSystem1() {}

	public static void main(String[] args) throws Exception {

		KnowledgeGraphManager kg = 
				new KnowledgeGraphManager(
//						GLOBAL.getNewDatasetConnection(),
						GLOBAL.getLocalFusekiConnection(),						
						GLOBAL.getPrefixMapping()
						);
		
		kg.cleanKG();
		kg.cleanOutputFolders();
		
		ADSBLoader adsb = new ADSBLoader();
		QueryADSB qadsb = new QueryADSB();
		ADSBProcessor1 adsbP1 = new ADSBProcessor1();
		ADSBProcessor2 adsbP2 = new ADSBProcessor2();
		FlightPairs pairs = new FlightPairs();
		PerformanceReport report = new PerformanceReport();
		
		kg.register(adsb); 
		kg.register(qadsb); 
		kg.register(adsbP1); 
		kg.register(adsbP2); 
		kg.register(pairs);  
		kg.register(report);  
		
		kg.initAllModules();
			
		for(int i=0; i<5; i++) {
			adsb.run();
			qadsb.run();
			adsbP1.run();
			adsbP2.run();
			pairs.run();
		}
		
		report.run();
		
	}
	
}
