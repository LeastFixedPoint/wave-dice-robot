package info.reflectionsofmind.dicerobot.method;

import java.util.ArrayList;
import java.util.List;

import com.google.wave.api.Annotation;
import com.google.wave.api.Range;
import com.google.wave.api.TextView;

public final class DocumentWriter implements IFormattedBufferedOutput
{
	private final TextView document;
	private final int start;
	private int position;
	private final StringBuilder builder = new StringBuilder();
	private final List<Annotation> annotations = new ArrayList<Annotation>();
	
	public DocumentWriter(final TextView document, final int position)
	{
		this.document = document;
		this.start = position;
		this.position = position;
	}
	
	public DocumentWriter append(final Object object)
	{
		final String string = object.toString();
		this.builder.append(string);
		this.position += string.length();
		return this;
	}
	
	public DocumentWriter append(final Object object, final String annotation, final String value)
	{
		final int oldPosition = this.position;
		append(object.toString());
		this.annotations.add(new Annotation(annotation, value, new Range(oldPosition, this.position)));
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