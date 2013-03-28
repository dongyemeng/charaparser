package semanticMarkup.ling.learn.lib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import semanticMarkup.core.Treatment;
import semanticMarkup.core.ValueTreatmentElement;
import semanticMarkup.know.IGlossary;
import semanticMarkup.ling.Token;
import semanticMarkup.ling.learn.ITerminologyLearner;
import semanticMarkup.ling.transform.ITokenizer;
import semanticMarkup.log.LogLevel;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.mysql.jdbc.log.Log;

public class PerlTerminologyLearner implements ITerminologyLearner {

	protected Set<String> sentences;
	protected Map<Treatment, LinkedHashMap<String, String>> sentencesForOrganStateMarker;
	protected List<String> adjnouns;
	protected Map<String, String> adjnounsent;
	protected Map<Treatment, LinkedHashMap<String, String>> sentenceTags;
	protected Set<String> bracketTags;
	protected Set<String> wordRoleTags;
	protected Map<String, Set<String>> wordSources;
	protected Map<String, Set<String>> roleToWords;
	protected Map<String, Set<String>> wordsToRoles;
	protected Map<String, String> heuristicNouns;
	protected Map<String, Set<String>> termCategories;
	protected Set<String> tags;
	protected Set<String> modifiers;
	protected Map<String, Set<String>> categoryTerms;
	
	private Connection connection;
	private String temporaryPath;
	private String databasePrefix;
	private String markupMode;
	private String databaseName;
	private String databasePassword;
	private String databaseUser;
	private IGlossary glossary;
	private ITokenizer tokenizer;
	private String descriptionSeparator;
	private Set<String> stopWords;
	private Set<String> selectedSources;
	private String glossaryTable;

	@Inject
	public PerlTerminologyLearner(@Named("temporaryPath") String temporaryPath, 
			@Named("descriptionSeparator") String descriptionSeparator, 
			@Named("markupMode") String markupMode,
			@Named("databaseName") String databaseName,
			@Named("GlossaryTable") String glossaryTable,
			@Named("databasePrefix") String databasePrefix, 
			@Named("databaseUser") String databaseUser, 
			@Named("databasePassword") String databasePassword, 
			@Named("StopWords") Set<String> stopWords,
			@Named("selectedSources") Set<String> selectedSources,
			IGlossary glossary, 
			@Named("WordTokenizer") ITokenizer tokenizer) throws Exception {
		this.temporaryPath = temporaryPath;
		this.descriptionSeparator = descriptionSeparator;
		this.markupMode = markupMode;
		this.databaseName = databaseName;
		this.databasePrefix = databasePrefix;
		this.databaseUser = databaseUser;
		this.databasePassword = databasePassword;
		this.glossary = glossary;
		this.tokenizer = tokenizer;
		this.stopWords = stopWords;
		this.selectedSources = selectedSources;
		this.glossaryTable = glossaryTable;
		
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, databaseUser, databasePassword);
	}
	
	
	@Override
	public void learn(List<Treatment> treatments) {
		File directory = new File(temporaryPath);
		
		try {
			File inDirectory = new File(directory.getAbsolutePath() + "//in//");
			
			//create the files
			writeTreatmentsToFiles(treatments, inDirectory);
			
			//run the perl script	
			runPerl(inDirectory);
			
			this.readResults(treatments);
			
		}catch(Exception e) {
			log(LogLevel.ERROR, e);
		}
	}


	protected Set<String> readModifiers() {
		Set<String> modifiers = new HashSet<String>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select modifier from " + this.databasePrefix + "_sentence");
			while(resultSet.next()) {
				String modifier = resultSet.getString("modifier");
				modifiers.add(modifier);
			}
		} catch(Exception e) {
			log(LogLevel.ERROR, "sentence table not found");
		}
		return modifiers;
	}


	protected Set<String> readTags() {
		Set<String> tags = new HashSet<String>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select tag from " + this.databasePrefix + "_sentence");
			while(resultSet.next()) {
				String tag = resultSet.getString("tag");
				tags.add(tag);
			}
		} catch(Exception e) {
			log(LogLevel.ERROR, "sentence table not found");
		}
		return tags;
	}


	protected Map<String, Set<String>> readTermCategories() {
		Map<String, Set<String>> termCategories = new HashMap<String, Set<String>>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select term, category from " + this.databasePrefix + "_term_category");
			while(resultSet.next()) {
				String term = resultSet.getString("term");
				String category = resultSet.getString("category");
				if(!termCategories.containsKey(term))
					termCategories.put(term, new HashSet<String>());
				termCategories.get(term).add(category);
			}
		} catch (Exception e) {
			log(LogLevel.ERROR, "term_category table not found");
		}
		return termCategories;
	}
	
	protected Map<String, Set<String>> readCategoryTerms() {
		Map<String, Set<String>> categoryNames = new HashMap<String, Set<String>>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select term, category from " + this.databasePrefix + "_term_category");
			while(resultSet.next()) {
				String term = resultSet.getString("term");
				String category = resultSet.getString("category");
				if(!categoryNames.containsKey(category))
					categoryNames.put(category, new HashSet<String>());
				categoryNames.get(category).add(term);
			}
		} catch (Exception e) {
			log(LogLevel.ERROR, "term_category table not found");
		}
		return categoryNames;
	}

	protected Map<String, Set<String>> readWordsToRoles() {
		Map<String, Set<String>> wordsToRoles = new HashMap<String, Set<String>>();
		//TODO wordroles table is populated by the GUI User interaction see MainForm.java. Therefore simply left as empty for now
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select word, semanticrole from " + this.databasePrefix + "_wordroles");
			while(resultSet.next()) {
				String word = resultSet.getString("word");
				//perl treated hyphens as underscores
				word = word.replaceAll("_", "-");
				String semanticRole = resultSet.getString("semanticrole");
				if(!wordsToRoles.containsKey(word))
					wordsToRoles.put(word, new HashSet<String>());
				wordsToRoles.get(word).add(semanticRole);
			}
		} catch (Exception e) {
			log(LogLevel.ERROR, e);
		}
		return wordsToRoles;
	}

	protected Map<String, String> readHeuristicNouns() {
		Map<String, String> heuristicNouns = new HashMap<String, String>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select word, type from " + this.databasePrefix + "_heuristicnouns");
			while(resultSet.next()) {
				heuristicNouns.put(resultSet.getString("word"), resultSet.getString("type"));
			}
		} catch (Exception e) {
			log(LogLevel.ERROR, "heuristicnouns table not found");
		}
		return heuristicNouns;
	}

	protected Map<String, Set<String>> readRoleToWords() {
		Map<String, Set<String>> roleToWords = new HashMap<String, Set<String>>();
		//TODO wordroles table is populated by the GUI User interaction see MainForm.java. Therefore simply left as empty for now
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select word, semanticrole from " + this.databasePrefix + "_wordroles");
			
			while(resultSet.next()) {
				String word = resultSet.getString("word");
				//perl treated hyphens as underscores
				word = word.replaceAll("_", "-");
				String semanticRole = resultSet.getString("semanticrole");
				if(!roleToWords.containsKey(semanticRole))
					roleToWords.put(semanticRole, new HashSet<String>());
				roleToWords.get(semanticRole).add(word);
			}
		} catch (Exception e) {
			log(LogLevel.ERROR, e);
		}
		return roleToWords;
	}

	protected Map<String, Set<String>> readWordToSourcesMap() {
		Map<String, Set<String>> wordToSources = new HashMap<String, Set<String>>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select source, sentence from " + this.databasePrefix + "_sentence");
			while(resultSet.next()) {
				String source = resultSet.getString("source");
				String sentence = resultSet.getString("sentence");
				List<Token> tokens = tokenizer.tokenize(sentence);
				for(Token token : tokens) {
					String word = token.getContent();
					if(!wordToSources.containsKey(word))
						wordToSources.put(word, new HashSet<String>());
					wordToSources.get(word).add(source);
				}
			}
		} catch (Exception e) {
			log(LogLevel.ERROR, e);
		}
		return wordToSources;
	}

	protected Set<String> readWordRoleTags()  {
		Set<String> tags = new HashSet<String>();
		//TODO wordroles table is populated by the GUI User interaction see MainForm.java. Therefore simply left as empty for now
		try {
			Statement statement = connection.createStatement();
			String wordroletable = this.databasePrefix + "_wordroles";
			ResultSet resultSet = statement.executeQuery("select word from "+wordroletable+" where semanticrole in ('op', 'os')");
			while(resultSet.next()) {
				//perl treated hyphens as underscores
				String tag = resultSet.getString("word").replaceAll("_", "-").trim();
				if(!tag.isEmpty())
					tags.add(tag);
			}
		} catch (Exception e) {
			log(LogLevel.ERROR, e);
		}
		return tags;
	}

	protected Set<String> readBracketTags(List<Treatment> treatments) {
		Set<String> tags = new HashSet<String>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select modifier, tag from " + this.databasePrefix + "_sentence where tag  like '[%]'"); //inner [tepal]
			while(resultSet.next()){
				String modifier = resultSet.getString("modifier");
				modifier = modifier.replaceAll("\\[^\\[*\\]", ""); 
				if(!modifier.equals("")){
					String tag;
					if(modifier.lastIndexOf(" ")<0) {
						tag = modifier;
					} else {
						tag = modifier.substring(modifier.lastIndexOf(" ")+1); //last word from modifier
					}
					if(tag.indexOf("[")>=0 || stopWords.contains(tag) || tag.matches(".*?(\\d).*")) 
						continue;
					if(!tag.trim().isEmpty())
						tags.add(tag);
				}
			}
		} catch (Exception e) {
			log(LogLevel.ERROR, e);
		}
		return tags;
	}

	protected Map<Treatment, LinkedHashMap<String, String>> readSentenceTags(List<Treatment> treatments) {
		Map<Treatment, LinkedHashMap<String, String>> tags = new HashMap<Treatment, LinkedHashMap<String, String>>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select source, tag from " + this.databasePrefix + "_sentence order by sentid");
			String previousTag = null;
			int listId = -1;
			String previousSourceId = "-1";
			while(resultSet.next()) {
				String source = resultSet.getString("source");
				if(selectedSources.isEmpty() || selectedSources.contains(source)) {
					String[] sourceIds = source.split(".txt-");
					String sourceId = sourceIds[0];//String.valueOf(Integer.valueOf(sourceIds[0])-1);
					if(!sourceId.equals(previousSourceId)) {
						previousSourceId = sourceId;
						listId++;
					}
					
					String tag = resultSet.getString("tag").replaceAll("\\W", "");
					Treatment treatment = treatments.get(listId);
					
					if(!tags.containsKey(treatment)) 
						tags.put(treatment, new LinkedHashMap<String, String>());
					if(!tag.equals("ditto")) {
						tags.get(treatment).put(source, tag);
						previousTag = tag;
					} else {
						tags.get(treatment).put(source, previousTag);
					}			
				}
			}
			resultSet.close();
			statement.close();
		} catch(Exception e) {
			log(LogLevel.ERROR, e);
		}
		return tags;
	}

	protected HashMap<Treatment,  LinkedHashMap<String, String>> readSentencesForOrganStateMarker(List<Treatment> treatments) {
		HashMap<Treatment, LinkedHashMap<String, String>> sentences = new  HashMap<Treatment, LinkedHashMap<String, String>>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select source, modifier, tag, sentence, originalsent from " + this.databasePrefix + "_sentence");
			// order by sentid desc");
			
			int listId = -1;
			String previousSourceId = "-1";
			
			//leave ditto as it is
			while(rs.next()){ //read sent in in reversed order
				String tag = rs.getString("tag");
				String sent = rs.getString("sentence").trim();
				
				if(sent.length()!=0){
					String source = rs.getString("source");
					if(selectedSources.isEmpty() || selectedSources.contains(source)) {
						String[] sourceIds = source.split(".txt-");
						
						String sourceId = sourceIds[0]; //String.valueOf(Integer.valueOf(sourceIds[0])-1);
						if(!sourceId.equals(previousSourceId)) {
							previousSourceId = sourceId;
							listId++;
						}
						
						String osent = rs.getString("originalsent");
						String text = sent;
						text = text.replaceAll("[ _-]+\\s*shaped", "-shaped").replaceAll("(?<=\\s)�\\s+m\\b", "um");
						text = text.replaceAll("&#176;", "�");
						text = text.replaceAll("\\bca\\s*\\.", "ca");
						text = rs.getString("modifier")+"##"+tag+"##"+text;
						
						Treatment treatment = treatments.get(listId);
						if(!sentences.containsKey(treatment))
							sentences.put(treatment, new LinkedHashMap<String, String>());
						sentences.get(treatment).put(source, text);
					}
				}
			}
		} catch(Exception e) {
			log(LogLevel.ERROR, e);
		}
		
		return sentences;
	}
	
	protected Map<String, String> readAdjNounSent() {
		Map<String, String> result = new HashMap<String, String>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT source, tag, modifier FROM " + this.databasePrefix + "_sentence s where modifier != \"\" and tag like \"[%\"");
			while(resultSet.next()){
				String modifier = resultSet.getString(3);
				String tag = resultSet.getString(2);
						
				modifier = modifier.replaceAll("\\[|\\]|>|<|(|)", "");
				tag = tag.replaceAll("\\[|\\]|>|<|(|)", "");
				
				result.put(tag, modifier);
			}
		} catch (Exception e) {
			log(LogLevel.ERROR, e);
		}
		return result;
	}

	protected List<String> readAdjNouns() {
		List<String> result = new ArrayList<String>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT distinct modifier FROM " + this.databasePrefix + "_sentence s where modifier != \"\" and tag like \"[%\"");
			while(resultSet.next()) {
				String modifier = resultSet.getString(1).replaceAll("\\[.*?\\]", "").trim();
				result.add(modifier);
			}
		} catch(Exception e) {
			log(LogLevel.ERROR, e);
		}
		return result;
	}
	
	protected Set<String> readSentences() {
		Set<String> result = new HashSet<String>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT sentence FROM " + this.databasePrefix + "_sentence order by sentid");
			
			while(resultSet.next()) {
				String sentence = resultSet.getString(1);
				result.add(sentence);
			}
		} catch(Exception e) {
			log(LogLevel.ERROR, e);
		}
		return result;
	}



	@Override
	public List<String> getAdjNouns() {
		return this.adjnouns;
	}
	
	@Override
	public Map<String, String> getAdjNounSent() {
		return this.adjnounsent;
	}

	@Override
	public Set<String> getBracketTags() {
		return this.bracketTags;
	}

	@Override
	public Set<String> getWordRoleTags() {
		return this.wordRoleTags;
	}


	@Override
	public Map<String, Set<String>> getRoleToWords() {
		return this.roleToWords;
	}

	@Override
	public Map<String, Set<String>> getWordsToRoles() {
		return this.wordsToRoles;
	}


	@Override
	public Map<String, Set<String>> getWordToSources() {
		return this.wordSources;
	}



	@Override
	public Map<String, String> getHeuristicNouns() {
		return this.heuristicNouns;
	}
	
	private void runPerl(File inDirectory) throws Exception {
		String command = "perl src/perl/unsupervisedClauseMarkupBenchmarked.pl " + "\"" + inDirectory.getAbsolutePath() + "//"
				+ "\" "+ this.databaseName + " " + this.markupMode + " " + this.databasePrefix + " " + this.glossaryTable;
		createTablesNeededForPerl();
		runCommand(command);
	}
	
	private void createTablesNeededForPerl() {
        try {
            Statement stmt = connection.createStatement();
            String cleanupQuery = "DROP TABLE IF EXISTS " + 
									//this.databasePrefix + "_allwords, " +  //allwords table is created outside of perl by dehypenizer
									this.databasePrefix + "_discounted, " + 
									this.databasePrefix + "_heuristicnouns, " + 
									this.databasePrefix + "_isa, " + 
									this.databasePrefix + "_modifiers, " + 
									this.databasePrefix + "_sentence, " + 
									this.databasePrefix + "_sentinfile, " + 
									this.databasePrefix + "_singularplural, " + 
									this.databasePrefix + "_substructure, " + 
									this.databasePrefix + "_unknownwords, " + 
									this.databasePrefix + "_wordpos, " + 
									this.databasePrefix + "_wordroles;";
            stmt.execute(cleanupQuery);
            stmt.execute("create table if not exists " + this.databasePrefix + "_allwords (word varchar(150) unique not null primary key, count int, dhword varchar(150), inbrackets int default 0)");
    		stmt.execute("create table if not exists " + this.databasePrefix + "_wordroles (word varchar(50), semanticrole varchar(2), savedid varchar(40), primary key(word, semanticrole))");			
        } catch(Exception e) {
        	log(LogLevel.ERROR, e);
	    }
	}


	private void runCommand(String command) throws Exception {
		long time = System.currentTimeMillis();
		
		Process p = Runtime.getRuntime().exec(command);
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p
				.getInputStream()));
		
		BufferedReader errInput = new BufferedReader(new InputStreamReader(p
				.getErrorStream()));
		
		// read the output from the command
		String s = "";
		int i = 0;
		while ((s = stdInput.readLine()) != null) {
			log(LogLevel.DEBUG, s + " at " + (System.currentTimeMillis() - time)
					/ 1000 + " seconds");
		}
		
		// read the errors from the command
		String e = "";
		while ((e = errInput.readLine()) != null) {
			log(LogLevel.DEBUG, e + " at " + (System.currentTimeMillis() - time)
					/ 1000 + " seconds");
		}
	}
	
	private void writeTreatmentsToFiles(List<Treatment> treatments, File file) throws IOException {
		int i = 0;
		for(Treatment treatment : treatments) {
			File treatmentFile = new File(file.getAbsolutePath() + "//" + i++ + ".txt");
			log(LogLevel.DEBUG, treatmentFile.getAbsolutePath());
			treatmentFile.createNewFile();
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(treatmentFile));
			
			List<ValueTreatmentElement> descriptions = treatment.getValueTreatmentElements("description");	
			for(ValueTreatmentElement description : descriptions) {		           
	            fileWriter.write(description.getValue() + "\n");
	            fileWriter.write(this.descriptionSeparator + "\n");
	            fileWriter.close();
			}
		}
	}


	@Override
	public Set<String> getSentences() {
		return this.sentences;
	}


	@Override
	public Map<Treatment, LinkedHashMap<String, String>> getSentencesForOrganStateMarker() {
		return this.sentencesForOrganStateMarker;
	}


	@Override
	public Map<Treatment, LinkedHashMap<String, String>> getSentenceTags() {
		return this.sentenceTags;
	}


	@Override
	public Map<String, Set<String>> getTermCategories() {
		return this.termCategories;
	}


	@Override
	public Set<String> getTags() {
		return this.tags;
	}


	@Override
	public Set<String> getModifiers() {
		return this.modifiers;
	}


	@Override
	public Map<String, Set<String>> getCategoryTerms() {
		return this.categoryTerms;
	}


	@Override
	public void readResults(List<Treatment> treatments) {
		this.sentences = readSentences();
		this.sentencesForOrganStateMarker = readSentencesForOrganStateMarker(treatments);
		this.adjnouns = readAdjNouns();
		this.adjnounsent = readAdjNounSent();
		this.sentenceTags = readSentenceTags(treatments);
		this.bracketTags = readBracketTags(treatments);
		this.wordRoleTags = readWordRoleTags(); 
		this.wordSources = readWordToSourcesMap();
		this.roleToWords = readRoleToWords();
		this.wordsToRoles = readWordsToRoles();
		this.heuristicNouns = readHeuristicNouns();
		this.termCategories = readTermCategories();
		this.categoryTerms = readCategoryTerms();
		this.tags = readTags();
		this.modifiers = readModifiers();
	}
}