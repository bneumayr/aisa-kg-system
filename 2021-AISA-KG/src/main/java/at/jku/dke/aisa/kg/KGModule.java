package at.jku.dke.aisa.kg;

public interface KGModule {
	
	public String getName();
	
	public void init();
	
	public void register(KGSystem kg);
	
	public String getModuleIri();

}
