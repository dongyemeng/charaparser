package semanticMarkup.ling.transform.lib;

import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import semanticMarkup.know.IPOSKnowledgeBase;
import semanticMarkup.ling.transform.IInflector;
import semanticMarkup.log.LogLevel;

import com.google.inject.Inject;

public class SomeInflector implements IInflector {

	private Hashtable<String, String> singulars = new Hashtable<String, String>();
	private Hashtable<String, String> plurals = new Hashtable<String, String>();
	private Pattern lyAdverbPattern = Pattern.compile("[a-z]{3,}ly");
	private Pattern p1 = Pattern.compile("(.*?[^aeiou])ies$");
	private Pattern p2 = Pattern.compile("(.*?)i$");
	private Pattern p3 = Pattern.compile("(.*?)ia$");
	private Pattern p4 = Pattern.compile("(.*?(x|ch|sh|ss))es$");
	private Pattern p5 = Pattern.compile("(.*?)ves$");
	private Pattern p6 = Pattern.compile("(.*?)ices$");
	private Pattern p7 = Pattern.compile("(.*?a)e$");
	private Pattern p75 = Pattern.compile("(.*?)us$");
	private Pattern p8 = Pattern.compile("(.*?)s$");
	private IPOSKnowledgeBase posKnowledgeBase;

	@Inject
	public SomeInflector(IPOSKnowledgeBase posKnowledgeBase) {
		this.posKnowledgeBase = posKnowledgeBase;
		singulars.put("axis", "axis");
		singulars.put("axes", "axis");
		singulars.put("bases", "base");
		singulars.put("boss", "boss");
		singulars.put("buttress", "buttress");
		singulars.put("callus", "callus");
		singulars.put("frons", "frons");
		singulars.put("grooves", "groove");
		singulars.put("interstices", "interstice");
		singulars.put("lens", "len");
		singulars.put("media", "media");
		singulars.put("midnerves", "midnerve");
		singulars.put("process", "process");
		singulars.put("series", "series");
		singulars.put("species", "species");
		singulars.put("teeth", "tooth");
		singulars.put("valves", "valve");
		
		plurals.put("axis", "axes");
		plurals.put("base", "bases");
		plurals.put("groove", "grooves");
		plurals.put("interstice", "interstices");
		plurals.put("len", "lens");
		plurals.put("media", "media");
		plurals.put("midnerve", "midnerves");
		plurals.put("tooth", "teeth");
		plurals.put("valve", "valves");
		plurals.put("boss", "bosses");
		plurals.put("buttress", "buttresses");
		plurals.put("callus", "calluses");
		plurals.put("frons", "fronses");
		plurals.put("process", "processes");
		plurals.put("series", "series");
		plurals.put("species", "species");
	}
	
	@Override
	public String getSingular(String word) {
		if(word.equals("leaves"))
			log(LogLevel.DEBUG, "leaves");
		
		String s = "";
		word = word.toLowerCase().replaceAll("\\W", "").trim();

		// check cache
		s = singulars.get(word);
		if (s != null)
			return s;

		// adverbs
		Matcher matcher = lyAdverbPattern.matcher(word);
		if (matcher.matches()) {
			singulars.put(word, word);
			plurals.put(word, word);
			return word;
		}

		String wordcopy = new String(word);
		wordcopy = checkWN4Singular(wordcopy);
		if (wordcopy != null && wordcopy.length() == 0) {
			return word;
		} else if (wordcopy != null) {
			singulars.put(word, wordcopy);
			if (!wordcopy.equals(word))
				plurals.put(wordcopy, word);
			return wordcopy;
		} else {// word not in wn
			Matcher m1 = p1.matcher(word);
			Matcher m2 = p2.matcher(word);
			Matcher m3 = p3.matcher(word);
			Matcher m4 = p4.matcher(word);
			Matcher m5 = p5.matcher(word);
			Matcher m6 = p6.matcher(word);
			Matcher m7 = p7.matcher(word);
			Matcher m75 = p75.matcher(word);
			Matcher m8 = p8.matcher(word);
			// Matcher m9 = p9.matcher(word);

			if (m1.matches()) {
				s = m1.group(1) + "y";
			} else if (m2.matches()) {
				s = m2.group(1) + "us";
			} else if (m3.matches()) {
				s = m3.group(1) + "ium";
			} else if (m4.matches()) {
				s = m4.group(1);
			} else if (m5.matches()) {
				s = m5.group(1) + "f";
			} else if (m6.matches()) {
				s = m6.group(1) + "ex";
			} else if (m7.matches()) {
				s = m7.group(1);
			} else if (m75.matches()) {
				s = word;
			} else if (m8.matches()) {
				s = m8.group(1);
			}// else if(m9.matches()){
				// s = m9.group(1)+"um";
				// }

			if (s != null) {
				singulars.put(word, s);
				if (!s.equals(word))
					plurals.put(s, word);
				return s;
			}
		}
		return word;
	}
	
	public String checkWN4Singular(String word) {
		word = word.trim().toLowerCase();
		
		List<String> singulars = posKnowledgeBase.getSingulars(word);
		for(String singular : singulars)
			return singular;
		return null;
	}

	@Override
	public String getPlural(String word) {
		word = word.trim().toLowerCase();
		if(word.endsWith("s"))
			return word + "es";
		else
			return word + "s";
	}

	@Override
	public boolean isPlural(String word) {
		word = word.trim().toLowerCase();
		word = word.replaceAll("\\W", "");
		if(word.matches("series|species|fruit")){
			return true;
		}
		if(!word.equals(getSingular(word))) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isSingular(String word) {
		return false;
	}

}
