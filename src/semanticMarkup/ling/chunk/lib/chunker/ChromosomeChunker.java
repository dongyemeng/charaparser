package semanticMarkup.ling.chunk.lib.chunker;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import semanticMarkup.know.IGlossary;
import semanticMarkup.know.IOrganStateKnowledgeBase;
import semanticMarkup.ling.chunk.AbstractChunker;
import semanticMarkup.ling.chunk.Chunk;
import semanticMarkup.ling.chunk.ChunkCollector;
import semanticMarkup.ling.chunk.ChunkType;
import semanticMarkup.ling.learn.ITerminologyLearner;
import semanticMarkup.ling.parse.AbstractParseTree;
import semanticMarkup.ling.parse.IParseTreeFactory;
import semanticMarkup.ling.transform.IInflector;

/**
 * ChromosomeChunker chunks by handling chromosome describing terminals
 * @author rodenhausen
 */
public class ChromosomeChunker extends AbstractChunker {

	/**
	 * @param parseTreeFactory
	 * @param prepositionWords
	 * @param stopWords
	 * @param units
	 * @param equalCharacters
	 * @param glossary
	 * @param terminologyLearner
	 * @param inflector
	 * @param organStateKnowledgeBase
	 */
	@Inject
	public ChromosomeChunker(IParseTreeFactory parseTreeFactory, @Named("PrepositionWords")String prepositionWords,
			@Named("StopWords")Set<String> stopWords, @Named("Units")String units, @Named("EqualCharacters")HashMap<String, String> equalCharacters, 
			IGlossary glossary, ITerminologyLearner terminologyLearner, IInflector inflector, IOrganStateKnowledgeBase organStateKnowledgeBase) {
		super(parseTreeFactory, prepositionWords, stopWords, units, equalCharacters, glossary, terminologyLearner, 
				inflector, organStateKnowledgeBase);
	}
	
	@Override
	public void chunk(ChunkCollector chunkCollector) {
		List<AbstractParseTree> terminals = chunkCollector.getTerminals();
		for(int i=0; i<terminals.size()-1; i++) {
			AbstractParseTree terminal = terminals.get(i);
			AbstractParseTree nextTerminal = terminals.get(i+1);
			
			if(terminal.getTerminalsText().matches("\\d{0,1}[xn]=") && 
					nextTerminal.getTerminalsText().matches("\\d+")) {
				//chromosome count 2n=, FNA specific
				Chunk chromosomeChunk = new Chunk(ChunkType.CHROM);
				LinkedHashSet<Chunk> chromosomeChunkChildren = new LinkedHashSet<Chunk>();
				chromosomeChunkChildren.add(terminal);
				chromosomeChunkChildren.add(nextTerminal);
				chromosomeChunk.setChunks(chromosomeChunkChildren);
				chunkCollector.addChunk(chromosomeChunk);
			}
		}
	}
	//TODO
			/*String l = "";
			String t = this.chunkedtokens.get(pointer++);
			while(t.indexOf("SG")<0){
				l +=t+" ";
				t= this.chunkedtokens.get(pointer++);				
			}
			l = l.replaceFirst("\\d[xn]=", "").trim();
			chunk = new ChunkChrom(l);
			return chunk;
		}
	}*/

}
