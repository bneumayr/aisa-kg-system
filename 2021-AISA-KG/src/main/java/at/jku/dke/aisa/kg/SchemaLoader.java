package at.jku.dke.aisa.kg;

import java.io.File;

public class SchemaLoader extends AbstractSingleRunModule {

	public SchemaLoader() {
		super("schema");
	}
	
	public String getModuleIri() {
		return GLOBAL.SCHEMA_GRAPH_NAME ;
		
	}
	
	protected void doInit() {
		File dir = new File(getInputPath(""));     
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
		  File file = files[i];
		  kg.con.load(this.getModuleIri(), file.getAbsolutePath());
		}
	}

}
