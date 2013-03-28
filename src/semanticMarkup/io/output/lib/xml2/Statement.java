package semanticMarkup.io.output.lib.xml2;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class Statement {

	private String text;
	private ArrayList<Structure> structure = new ArrayList<Structure>();
	private ArrayList<Relation> relation = new ArrayList<Relation>();
	private String source;
	
	public Statement() { }

	public Statement(String text, ArrayList<Structure> structure,
			ArrayList<Relation> relation) {
		super();
		this.text = text;
		this.structure = structure;
		this.relation = relation;
	}
	
	
	@XmlElement
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@XmlElement
	public ArrayList<Structure> getStructure() {
		return structure;
	}

	public void setStructure(ArrayList<Structure> structure) {
		this.structure = structure;
	}

	@XmlElement
	public ArrayList<Relation> getRelation() {
		return relation;
	}

	public void setRelation(ArrayList<Relation> relation) {
		this.relation = relation;
	}

	@XmlElement
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	
}