package at.jku.dke.aisa.kg;

public interface ExternalModule extends VersionedModule {

	public void exportInput();
	public void importResults();
	
}
