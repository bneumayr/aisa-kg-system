package at.jku.dke.aisa.kg.sample1;

import at.jku.dke.aisa.kg.AbstractExternalModule;

public class SampleExternalModule extends AbstractExternalModule {

	public SampleExternalModule(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected
	void doInit() {
		System.out.println("DO INIT: " + getName() + "-" + getTurn() + "-" + getTimestamp());
	}

	
	@Override
	protected
	void doStartTurn() {
		System.out.println("DO START: " + getName() + "-" + getTurn() + "-" + getTimestamp());
	}

	@Override
	protected
	void doFinishTurn() {
		System.out.println("DO FINISH: " + getName() + "-" + getTurn() + "-" + getTimestamp());

	}


}
