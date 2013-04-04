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
public class CSVGlossary implements IGlossary {

	private HashMap<String, Set<String>> glossary = new HashMap<String, Set<String>>();
	private HashMap<String, Set<String>> reverseGlossary = new HashMap<String, Set<String>>();
	
	/**
	 * @param filePath
	 * @throws IOException
	 */
	@Inject
	public CSVGlossary(@Named("CSVGlossary_filePath") String filePath) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(filePath));
		
		List<String[]> lines = reader.readAll();
		for(String[] line : lines) {
			
			String term = line[1].toLowerCase();
			String category = line[2].toLowerCase();
			
			//if(term.equals("rhizome")) 
			//	System.out.println("here");
			
			if(!glossary.containsKey(term))
				glossary.put(term, new HashSet<String>());
			glossary.get(term).add(category);
			
			if(!reverseGlossary.containsKey(category))
				reverseGlossary.put(category, new HashSet<String>());
			reverseGlossary.get(category).add(term);
			
			//if(category.equalsIgnoreCase("structure")) 
			//	System.out.println(reverseGlossary.get(category));
		}
		
		reader.close();
	}
	
	@Override
	public Set<String> getWords(String category) {
		category = category.toLowerCase();
		if(reverseGlossary.containsKey(category))
			return reverseGlossary.get(category);
		else
			return new HashSet<String>();
	}

	@Override
	public boolean contains(String word) {
		word = word.toLowerCase();
		return glossary.containsKey(word);
	}

	@Override
	public Set<String> getCategories(String word) {
		word = word.toLowerCase();
		if(glossary.containsKey(word))
			return glossary.get(word);
		else
			return new HashSet<String>();
	}

	@Override
	public Set<String> getWordsNotInCategories(Set<String> categories) {
		Set<String> result = new HashSet<String>();
		for(String category : this.reverseGlossary.keySet()) {
			category = category.toLowerCase();
			if(!categories.contains(category))
				result.addAll(reverseGlossary.get(category));
		}
		return result;
	}
}