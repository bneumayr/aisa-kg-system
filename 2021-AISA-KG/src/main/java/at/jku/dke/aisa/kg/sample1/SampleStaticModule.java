package at.jku.dke.aisa.kg.sample1;

import at.jku.dke.aisa.kg.AbstractStaticModule;
import at.jku.dke.aisa.kg.StaticModule;

public class SampleStaticModule extends AbstractStaticModule implements StaticModule {

	public SampleStaticModule(String name) {
		super(name);
	}

	@Override
	protected
	void doInit() {
		System.out.println("DO INIT: " + getName() + "-" + getLogicalTime());		
	}

	
	
	
}
