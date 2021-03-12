package at.jku.dke.aisa.kg;

import org.apache.jena.query.ParameterizedSparqlString;

public abstract class AbstractInternalModule extends AbstractMultipleRunModule implements InternalModule {
	
	public AbstractInternalModule(String name) {
		super(name);
	}
	
	public final void run() {
		resetTimestamp();
		initTurn();
		doRun();
		commitTurn();	
		
		ParameterizedSparqlString pss = preprocessSparql("""
				INSERT DATA { GRAPH ?TURN { ?TURN aisa:durationInMs ?DURATION. } }
			""");
		pss.setLiteral("DURATION",System.currentTimeMillis() - getPhysicalTime());
		executeSparqlUpdate(pss);
		
	}
	
	abstract protected void doRun();
	
	
	
}
