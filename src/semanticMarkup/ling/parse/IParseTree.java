package semanticMarkup.ling.parse;

import java.util.List;
import java.util.Set;

import semanticMarkup.ling.pos.POS;
import semanticMarkup.ling.pos.POSedToken;

/**
 * An IParseTree poses a parse tree generated by a syntactic parser (e.g. stanford parser)
 * A IParseTree consists of nodes which themselves constitute IParseTrees. 
 * @author rodenhausen
 */
public interface IParseTree extends Iterable<IParseTree> {
	
	/**
	 * @return the children of this IParseTree
	 */
	public List<AbstractParseTree> getChildren();
	
	/**
	 * @return the terminals of this IParseTree
	 */
	public List<AbstractParseTree> getTerminals();
	
	/**
	 * @param text
	 * @return the terminal IParseTrees of the given text
	 */
	public List<IParseTree> getTerminalsOfText(String text);
	
	/**
	 * @param height
	 * @param root that covers ancestor and this parseTree
	 * @return the ancestor at a certain height of this parseTree
	 */
	public IParseTree getAncestor(int height, IParseTree root);
	
	/**
	 * @param root that covers ancestor and this parseTree
	 * @return the parent of this parseTree
	 */
	public AbstractParseTree getParent(IParseTree root);
	
	/**
	 * @return the POS of this parseTree. If this parseTree is not an inner node (i.e. it contains children) null is returned
	 */
	public POS getPOS();
	
	/**
	 * @return a String representation of the text of all terminals of this parseTree
	 */
	public String getTerminalsText();
	
	/**
	 * @return the posed tokens of this parseTree
	 */
	public List<POSedToken> getSentence();
	
	/**
	 * @param pos
	 * @return a list of children filtered by part of speech
	 */
	public List<IParseTree> getChildrenOfPOS(POS pos);
	
	/**
	 * @return if this parseTree is a terminl (i.e. does not contain any children)
	 */
	public boolean isTerminal();
	
	/**
	 * @param terminal
	 * @return the terminal id of the given terminal
	 */
	public int getTerminalID(IParseTree terminal);
	
	/**
	 * @param subtree to remove from children
	 */
	public void removeChild(IParseTree subtree);
	
	/**
	 * removes all children
	 */
	public void removeAllChildren();
	
	/**
	 * @param pos to set
	 */
	public void setPOS(POS pos);
	
	/**
	 * @param text to set
	 */
	public void setTerminalsText(String text);
	
	/**
	 * @param child to add
	 */
	public void addChild(IParseTree child);
	
	/**
	 * @param child
	 * @return the index of the given child in all children
	 */
	public int indexOf(IParseTree child);
	
	/**
	 * @param index to add the child at
	 * @param child
	 */
	public void addChild(int index, IParseTree child);
	
	/**
	 * Implementation has to ensure that the order of subtrees is according to the sentence word order 
	 * @param posA
	 * @param posB
	 * @return list of subtrees that match the first and second part of speech in direct consecutive order
	 */
	public List<AbstractParseTree> getDescendants(POS posA, POS posB);
	
	/**
	 * @param posA
	 * @param posBs
	 * @return list of subtrees that match the first and any of the second part of speechs in direct consecutive order
	 */
	public List<AbstractParseTree> getDescendants(POS posA, Set<POS> posBs);
	
	/**
	 * @param pos
	 * @return list of subtrees that match the part of speech
	 */
	public List<IParseTree> getDescendants(POS pos);
	
	/**
	 * @param posA
	 * @param posB
	 * @param terminalText
	 * @return list of subtrees that match the first and second part of speech in direct consecutive order and match the given terminalText
	 */
	public List<IParseTree> getDescendants(POS posA, POS posB, String terminalText);
	
	/**
	 * @return a pretty formated string of this parsetree
	 */
	public String prettyPrint();
	
	/**
	 * @return if this parsetree is the root
	 */
	public boolean isRoot();
	
	/**
	 * @param pos
	 * @return if this parseTree is of the given part of speech
	 */
	public boolean is(POS pos);
	
	/**
	 * @param children to add
	 */
	public void addChildren(List<AbstractParseTree> children);
	
	/**
	 * @param descendant to remove
	 */
	public void removeDescendant(IParseTree descendant);
	
	/**
	 * @param text
	 * @return list of terminal parsetrees that match the given text
	 */
	public List<IParseTree> getTerminalsThatContain(String text);
	
	/**
	 * @return if this parseTree has children
	 */
	public boolean hasChildren();
	
	/**
	 * @param beforePreviousParentIndex to start inserting at
	 * @param children to insert
	 */
	public void addChildren(int beforePreviousParentIndex, List<AbstractParseTree> children);
	
	/**
	 * @param parseTree
	 * @return the depth of the given parseTree starting from this parseTree
	 */
	public int getDepth(IParseTree parseTree);
}