package at.jku.dke.aisa.kg;

/** Subclasses only implement doStartTurn(), export to the file system, and finishTurn(), i.e. import from the file system 
 * 
 * for external modules a turn consists of two transactions: 1. export/checkOut, 2. import/checkIn
 * */
public abstract class AbstractExternalModule extends AbstractVersionedModule implements ExternalModule {

	public AbstractExternalModule(String name) {
		super(name);
	}

	/**  */
	public final void exportInput() {
		resetTimestamp();
		initTurn();
		doStartTurn();		
	}

	abstract protected void doStartTurn();
	
	public final void importResults() {
		resetTimestamp();
		doFinishTurn();	
		commitTurn();
	}
	
	abstract protected void doFinishTurn();	

}
