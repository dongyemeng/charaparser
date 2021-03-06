package semanticMarkup.ling.extract;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import semanticMarkup.core.TreatmentElement;
import semanticMarkup.core.description.DescriptionTreatmentElement;
import semanticMarkup.core.description.DescriptionTreatmentElementType;
import semanticMarkup.ling.chunk.Chunk;
import semanticMarkup.ling.chunk.ChunkCollector;
import semanticMarkup.ling.chunk.ChunkType;

/**
 * ProcessingContext provides contextual information e.g. chunkListIterator, chunkCollector, ... 
 * and stores ProcessingContextStates at each processing step of a chunk
 * @author rodenhausen
 */
public class ProcessingContext {

	private IChunkProcessorProvider chunkProcessorProvider;
	private List<DescriptionTreatmentElement> result;
	private ListIterator<Chunk> chunkListIterator;
	private ChunkCollector chunkCollector;
	private ProcessingContextState currentState = new ProcessingContextState();
	
	/**
	 * @return the current processingContextState
	 */
	public ProcessingContextState getCurrentState() {
		return currentState;
	}
	
	/**
	 * set the current ProcessingContextState to the state of a previously seen chunk
	 * @param previousChunk
	 */
	public void setCurrentState(Chunk previousChunk) {
		this.currentState = states.get(previousChunk);
	}

	/**
	 * set the current ProcessingContextState
	 * @param currentState
	 */
	public void setCurrentState(ProcessingContextState currentState) {
		this.currentState = currentState;
	}

	private HashMap<Chunk, ProcessingContextState> states = new HashMap<Chunk, ProcessingContextState>();

	/**
	 * @param chunk
	 * @return the ProcessingContextState of a previously seen chunk
	 */
	public ProcessingContextState getState(Chunk chunk) {
		return states.get(chunk);
	}

	/**
	 * @param chunk
	 * @return if a ProcessingContextState of a chunk is available
	 */
	public boolean containsState(Chunk chunk) {
		return states.containsKey(chunk);
	}

	/**
	 * @param chunk
	 * @param processingContextState to add for the chunk
	 */
	public void addState(Chunk chunk, ProcessingContextState processingContextState) {
		states.put(chunk, processingContextState);
	}
	
	/**
	 * @return the chunkCollector
	 */
	public ChunkCollector getChunkCollector() {
		return chunkCollector;
	}

	/**
	 * @return the chunkListIterator
	 */
	public ListIterator<Chunk> getChunkListIterator() {
		return chunkListIterator;
	}

	/**
	 * @param chunkCollector to set
	 */
	public void setChunkCollector(ChunkCollector chunkCollector) {
		this.chunkCollector = chunkCollector;
	}

	/**
	 * @param chunkListIterator to set
	 */
	public void setChunkListIterator(ListIterator<Chunk> chunkListIterator) {
		this.chunkListIterator = chunkListIterator;
	}
	
	/**
	 * @param chunkType
	 * @return the IChunkProcessor for the chunkType
	 */
	public IChunkProcessor getChunkProcessor(ChunkType chunkType) {
		return this.chunkProcessorProvider.getChunkProcessor(chunkType);
	}
	
	/**
	 * @param chunkProcessorProvider to set
	 */
	public void setChunkProcessorsProvider(IChunkProcessorProvider chunkProcessorProvider) {
		this.chunkProcessorProvider = chunkProcessorProvider;
	}

	/**
	 * @param result to set
	 */
	public void setResult(List<DescriptionTreatmentElement> result) {
		this.result = result;
	}
	
	/**
	 * @return result
	 */
	public List<DescriptionTreatmentElement> getResult() {
		return this.result;
	}

	/**
	 * @return the last DescriptionTreatmentElement of the result
	 */
	public DescriptionTreatmentElement getLastResult() {
		return result.get(result.size()-1);
	}

	/**
	 * @param descriptionTreatmentElementType
	 * @return the last DescriptionTreatmentElement of descriptionTreatmentElementType of the result
	 */
	public DescriptionTreatmentElement getLastResult(DescriptionTreatmentElementType descriptionTreatmentElementType) {
		DescriptionTreatmentElement result = null;
		for(int i=this.result.size()-1; i>=0; i--) {
			DescriptionTreatmentElement element = this.result.get(i);
			if(element.isOfDescriptionType(descriptionTreatmentElementType)) {
				result = element;
				break;
			}
		}
		return result;
	}
	
	/**
	 * @param descriptionTreatmentElement
	 * @return the parent DescriptionTreatmentElement of the descriptionTreatmentElement given within the result 
	 * or null if none exists
	 */
	public DescriptionTreatmentElement getParent(DescriptionTreatmentElement descriptionTreatmentElement) {
		for(DescriptionTreatmentElement resultElement : result) {
			TreatmentElement parent = resultElement.getParent(descriptionTreatmentElement);
			if(parent!=null && parent instanceof DescriptionTreatmentElement)
				return (DescriptionTreatmentElement)parent;
		}
		return null;
	}

	/**
	 * Reset the current ProcessingContextState
	 */
	public void reset() {
		 currentState = new ProcessingContextState();
	}
}
