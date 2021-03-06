package semanticMarkup.ling.extract.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import semanticMarkup.core.description.DescriptionTreatmentElement;
import semanticMarkup.core.description.DescriptionTreatmentElementType;
import semanticMarkup.know.ICharacterKnowledgeBase;
import semanticMarkup.know.IGlossary;
import semanticMarkup.know.IPOSKnowledgeBase;
import semanticMarkup.ling.chunk.Chunk;
import semanticMarkup.ling.chunk.ChunkType;
import semanticMarkup.ling.extract.AbstractChunkProcessor;
import semanticMarkup.ling.extract.ProcessingContext;
import semanticMarkup.ling.extract.ProcessingContextState;
import semanticMarkup.ling.learn.ITerminologyLearner;
import semanticMarkup.ling.parse.AbstractParseTree;
import semanticMarkup.ling.transform.IInflector;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * MyCharacterStateChunkProcessor processes chunks of ChunkType.CHARACTER_STATE
 * @author rodenhausen
 */
public class MyCharacterStateChunkProcessor extends AbstractChunkProcessor {

	private Pattern hyphenedCharacterPattern = Pattern.compile("\\w+-(\\w+)");

	/**
	 * @param inflector
	 * @param glossary
	 * @param terminologyLearner
	 * @param characterKnowledgeBase
	 * @param posKnowledgeBase
	 * @param baseCountWords
	 * @param locationPrepositions
	 * @param clusters
	 * @param units
	 * @param equalCharacters
	 * @param numberPattern
	 * @param times
	 */
	@Inject
	public MyCharacterStateChunkProcessor(IInflector inflector, IGlossary glossary, ITerminologyLearner terminologyLearner, 
			ICharacterKnowledgeBase characterKnowledgeBase, @Named("LearnedPOSKnowledgeBase") IPOSKnowledgeBase posKnowledgeBase,
			@Named("BaseCountWords")Set<String> baseCountWords, @Named("LocationPrepositionWords")Set<String> locationPrepositions, 
			@Named("Clusters")Set<String> clusters, @Named("Units")String units, @Named("EqualCharacters")HashMap<String, String> equalCharacters, 
			@Named("NumberPattern")String numberPattern, @Named("TimesWords")String times) {
		super(inflector, glossary, terminologyLearner, characterKnowledgeBase, posKnowledgeBase, baseCountWords, locationPrepositions, clusters, units, equalCharacters, 
				numberPattern, times);
	}

	@Override
	protected List<DescriptionTreatmentElement> processChunk(Chunk chunk, ProcessingContext processingContext) {
		ProcessingContextState processingContextState = processingContext.getCurrentState();
		LinkedList<DescriptionTreatmentElement> parents = lastStructures(processingContext, processingContextState);
		LinkedList<DescriptionTreatmentElement> characters = processCharacterState(chunk, parents, 
				processingContextState, processingContext);//apices of basal leaves spread 
		if(parents.isEmpty()) {
			processingContextState.getUnassignedCharacters().addAll(characters);
		}
		processingContextState.setLastElements(characters);
		processingContextState.setCommaAndOrEosEolAfterLastElements(false);
		return characters;
	}
	
	/**
	 * @param content: m[usually] coloration[dark brown]: there is only one character states and several modifiers
	 * @param parents: of the character states
	 */
	protected LinkedList<DescriptionTreatmentElement> processCharacterState(Chunk content,
			LinkedList<DescriptionTreatmentElement> parents, ProcessingContextState processingContextState, 
			ProcessingContext processingContext) {
		LinkedList<DescriptionTreatmentElement> results = new LinkedList<DescriptionTreatmentElement>();
		List<Chunk> modifiers = new LinkedList<Chunk>();
		List<Chunk> unassignedModifiers = processingContextState.getUnassignedModifiers();
		modifiers.addAll(unassignedModifiers);
		modifiers.addAll(content.getChunks(ChunkType.MODIFIER));	
		
		String character = content.getPropertyBFS("characterName");
		Chunk characterState = content.getChunkDFS(ChunkType.STATE);
		String characterStateString = characterState.getTerminalsText();
		
		String characterStateText = characterState.getTerminalsText();
		Matcher matcher = hyphenedCharacterPattern.matcher(characterStateText);
		if(matcher.matches()) {
			ListIterator<Chunk> chunkListIterator = processingContext.getChunkListIterator();
			int backupNextIndex = chunkListIterator.nextIndex();
			String suffix = matcher.group(1);
			results.addAll(findPreviousCharacterList(processingContext, character, suffix));
			while(chunkListIterator.nextIndex() < backupNextIndex)
				chunkListIterator.next();
		}
		
		List<Chunk> characters = new ArrayList<Chunk>();
		characters.add(content);
		if(character != null) {
			//LinkedList<DescriptionTreatmentElement> characterElement = this.processCharacterText(characters, parents, character, processingContextState);
			if(processingContextState.getUnassignedCharacter()!=null) {
				character = processingContextState.getUnassignedCharacter();
				processingContextState.setUnassignedCharacter(null);
			}
			
			if(characterStateString.contains(" to "))
				results.addAll(createRangeCharacterElement(parents, modifiers, characterStateString, character, processingContextState));
			else {
				DescriptionTreatmentElement characterElement = createCharacterElement(parents, modifiers, characterStateString, character, "", processingContextState);
				if(characterElement!=null)
					results.add(characterElement);
			}
			/*for(AbstractParseTree state : characterState.getTerminals()) {
				String stateText = state.getTerminalsText().trim();
				if(!stateText.equals(",") && !stateText.equals("and") && !stateText.equals("or")) {
					DescriptionTreatmentElement characterElement = createCharacterElement(parents, modifiers, state.getTerminalsText(), character, "", processingContextState);
					if(characterElement!=null)
						results.add(characterElement);
				}
			}*/
		} else {
			if(characterState.size() > 1) {
				LinkedList<DescriptionTreatmentElement> result = this.processCharacterText(characters, parents, character, processingContextState, 
						processingContext);
				//results = this.processCharacterList(content, parents, processingContextState);
				return results;
			}
			
			if(processingContextState.getUnassignedCharacter()!=null) {
				character = processingContextState.getUnassignedCharacter();
				processingContextState.setUnassignedCharacter(null);
			}
			
			String newState = equalCharacters.get(characterState);
			if(newState != null){
				characterStateString = newState;
				if(characterKnowledgeBase.containsCharacterState(characterStateString))
					character = characterKnowledgeBase.getCharacterName(characterStateString);
			}
			if(character.equals("character") && modifiers.size() == 0) {
				//high relief: character=relief, reset the character of "high" to "relief"
				DescriptionTreatmentElement lastElement = processingContextState.getLastElements().getLast();
				if(lastElement.isOfDescriptionType(DescriptionTreatmentElementType.CHARACTER)) 
					for(DescriptionTreatmentElement element : processingContextState.getLastElements()) 
						element.setAttribute("name", characterStateString);
				else if(lastElement.isOfDescriptionType(DescriptionTreatmentElementType.STRUCTURE))
					processingContextState.setUnassignedCharacter(characterStateString);
				results.addAll(processingContextState.getLastElements());
			} else if(characterStateString.length() > 0) {
				DescriptionTreatmentElement characterElement = createCharacterElement(parents, modifiers, characterStateString, character, "", processingContextState);
				if(characterElement!=null)
					results.add(characterElement);
			}
		}
		unassignedModifiers.clear();
		
		return results;
	}
	

	private List<DescriptionTreatmentElement> findPreviousCharacterList(ProcessingContext processingContext, String characterName, String suffix) {
		List<DescriptionTreatmentElement> result = new LinkedList<DescriptionTreatmentElement>();
		ListIterator<Chunk> listIterator = processingContext.getChunkListIterator();
		listIterator.previous();
		
		while(true) {
			if(listIterator.hasPrevious()) {
				Chunk previousChunk = listIterator.previous();

				if(previousChunk.isOfChunkType(ChunkType.OR) || previousChunk.isOfChunkType(ChunkType.AND) || 
					previousChunk.isOfChunkType(ChunkType.COMMA)) {
					continue;
				} else if((previousChunk instanceof AbstractParseTree) && previousChunk.getTerminalsText().endsWith("-") && previousChunk.getTerminalsText().length() > 1) {
					AbstractParseTree previousTerminal = (AbstractParseTree)previousChunk;
					previousTerminal.setTerminalsText(previousTerminal.getTerminalsText() + suffix);
					Chunk stateChunk = new Chunk(ChunkType.STATE, previousChunk);
					Chunk characterStateChunk = new Chunk(ChunkType.CHARACTER_STATE, stateChunk);
					characterStateChunk.setProperty("characterName", characterName);
					result.addAll(processingContext.getChunkProcessor(ChunkType.CHARACTER_STATE).process(characterStateChunk, processingContext));
				}
			}
			break;
		}
		
		
		//String previousElement = ""; //modifier, character, connector, hyphen
		
		
		
/*		while(true) {
			if(listIterator.hasPrevious()) {
				Chunk previousChunk = listIterator.previous();

				if(previousChunk.isOfChunkType(ChunkType.OR) || previousChunk.isOfChunkType(ChunkType.AND) || 
					previousChunk.isOfChunkType(ChunkType.COMMA)) {
					previousElement = "connector";
				} else if(previousChunk.getTerminalsText().equals("-")) {
					previousElement = "hyphen";
				} else if(previousChunk.getTerminalsText().endsWith("-")) {
					Chunk characterChunk = new Chunk(ChunkType.CHARACTER_STATE, previousChunk);
					characterChunk.setProperty("characterName", characterName);
					result.addAll(processingContext.getChunkProcessor(ChunkType.CHARACTER_STATE).process(characterChunk, processingContext));
				} else if(previousChunk.isOfChunkType(ChunkType.UNASSIGNED) && previousElement.equals("hyphen")) {
					
					
				} else {
					break;
				}
			} else {
				break;
			}
		}*/
		return result;
	}
}
