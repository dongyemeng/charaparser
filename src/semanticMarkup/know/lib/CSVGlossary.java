package semanticMarkup.know.lib;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import semanticMarkup.know.IGlossary;
import au.com.bytecode.opencsv.CSVReader;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * A CSVGlossary creates an IGlossary from a CSV file; expected CSV file format: word;category
 * @author rodenhausen
 */
public class CSVGlossary extends InMemoryGlossary {
	
	/**
	 * @param filePath
	 * @throws IOException
	 */
	@Inject
	public CSVGlossary(@Named("CSVGlossary_filePath") String filePath) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(filePath));
		
		List<String[]> lines = reader.readAll();
		for(String[] line : lines) {
			
			String term = line[1];
			String category = line[2];
			
			//if(term.equals("rhizome")) 
			//	System.out.println("here");
			
			this.addEntry(term, category);
			
			//if(category.equalsIgnoreCase("structure")) 
			//	System.out.println(reverseGlossary.get(category));
		}
		
		reader.close();
	}
}
