package semanticMarkup.markup;

import java.util.List;

import semanticMarkup.core.Treatment;
import semanticMarkup.core.transformation.lib.description.DescriptionTreatmentTransformer;
import semanticMarkup.core.transformation.lib.description.GUIDescriptionTreatmentTransformer;
import semanticMarkup.io.input.IVolumeReader;
import semanticMarkup.io.output.IVolumeWriter;
import semanticMarkup.log.LogLevel;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * DescriptionMarkupCreator creates a markup by reading treatments, transforming them using GUIDescriptionTreatmentTransformer and writing them out
 * @author thomas rodenhausen
 */
public class DescriptionMarkupCreator implements IMarkupCreator {

	private DescriptionTreatmentTransformer inputTransformer;
	private IVolumeWriter volumeWriter;
	private IVolumeReader volumeReader;
	private List<Treatment> treatments;

	/**
	 * @param volumeReader
	 * @param inputTransformer
	 * @param volumeWriter
	 */
	@Inject
	public DescriptionMarkupCreator(@Named("MarkupCreator_VolumeReader")IVolumeReader volumeReader, 
			DescriptionTreatmentTransformer inputTransformer, 
			@Named("MarkupCreator_VolumeWriter")IVolumeWriter volumeWriter) {	
		this.volumeReader = volumeReader;
		this.inputTransformer = inputTransformer;
		this.volumeWriter = volumeWriter;
	}
	
	@Override
	public void create() {
		try {
			log(LogLevel.DEBUG, "reading treatments using " + volumeReader.getClass());
			treatments = volumeReader.read();
			
			treatments = inputTransformer.transform(treatments);

			log(LogLevel.DEBUG, "writing result using " + volumeWriter.getClass());
			volumeWriter.write(treatments);
				
		} catch (Exception e) {
			e.printStackTrace();
			log(LogLevel.ERROR, "Problem reading, transforming or writing treatments", e);
		}
	}

	@Override
	public String getDescription() {
		return "Perl Blackbox";
	}

	@Override
	public List<Treatment> getResult() {
		return treatments;
	}
}
