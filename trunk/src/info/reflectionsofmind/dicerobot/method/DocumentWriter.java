package info.reflectionsofmind.dicerobot.method;

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
	
	public DocumentWriter(final TextView document)
	{
		this(document, 0);
	}
	
	public IFormattedBufferedOutput append(final Object object)
	{
		final String string = object.toString();
		this.builder.append(string);
		
		this.previous = this.position;
		this.position += string.length();
		
		return this;
	}
	
	@Override
	public IFormattedBufferedOutput with(final String annotation, final String value)
	{
		this.annotations.add(new Annotation(annotation, value, new Range(this.previous, this.position)));
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