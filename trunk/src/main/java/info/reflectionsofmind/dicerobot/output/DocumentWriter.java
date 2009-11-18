package info.reflectionsofmind.dicerobot.output;

import info.reflectionsofmind.dicerobot.exception.output.OutputException;

import java.util.ArrayList;
import java.util.List;

import com.google.wave.api.Annotation;
import com.google.wave.api.Range;
import com.google.wave.api.TextView;

public class DocumentWriter implements IFormattedBufferedOutput
{
	private final TextView document;
	private final int start;
	private int previous;
	private int position;
	private final StringBuilder builder = new StringBuilder();
	private final List<Annotation> annotations = new ArrayList<Annotation>();
	
	public DocumentWriter(final TextView document, final int position)
	{
		this.document = document;
		this.start = position;
		this.position = position;
	}
	
	public DocumentWriter append(final Object object) throws OutputException
	{
		final String string = object.toString();
		this.builder.append(string);
		
		this.previous = this.position;
		this.position += string.length();
		
		return this;
	}
	
	@Override
	public DocumentWriter with(final IStyle style)
	{
		this.annotations.add(new Annotation(style.getName(), style.getValue(), // 
				new Range(this.previous, this.position)));
		
		return this;
	}
	
	public void flush()
	{
		this.document.insert(this.start, this.builder.toString());
		
		for (final Annotation annotation : this.annotations)
		{
			this.document.setAnnotation(annotation.getRange(), annotation.getName(), annotation.getValue());
		}
	}
}