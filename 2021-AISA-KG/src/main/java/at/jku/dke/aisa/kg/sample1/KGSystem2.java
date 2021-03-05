package at.jku.dke.aisa.kg.sample1;

import at.jku.dke.aisa.kg.GLOBAL;
import at.jku.dke.aisa.kg.KnowledgeGraphManager;

public class KGSystem2 {

	public KGSystem2() {}

	public static void main(String[] args) throws Exception {

		KnowledgeGraphManager kg = 
				new KnowledgeGraphManager(
//						GLOBAL.getNewDatasetConnection(),
						GLOBAL.getLocalFusekiConnection(),						
						GLOBAL.getPrefixMapping()
						);
		
		kg.cleanKG();
		kg.cleanOutputFolders();
		
		kg.register(new SampleStaticModule("ModuleA"));
		kg.register(new SampleStaticModule("ModuleB"));
		kg.register(new SampleExternalModule("ExternalA"));
		kg.register(new SampleExternalModule("ExternalB"));
		kg.register(new SampleInternalModule("InternalA"));
		kg.register(new SampleInternalModule("InternalB"));
		kg.register(new ADSBLoader()); //module name: adsb
		kg.register(new QueryADSB());  //module name: qadsb
		kg.register(new ADSBProcessor1());  //module name: adsbP1
		kg.register(new ADSBProcessor2());  //module name: adsbP2
		kg.register(new FlightPairs());  //module name: pairs
		
		kg.initAllModules();
			
		kg.startConsoleApplication(System.in, System.out);		
		
	}
	
}
