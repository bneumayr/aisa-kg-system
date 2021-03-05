package at.jku.dke.aisa.kg;

public interface Module {
	
	public String getName();
	
	public void init();
	
	public void register(KnowledgeGraphManager kg);
	
	public String getModuleIri();

}
