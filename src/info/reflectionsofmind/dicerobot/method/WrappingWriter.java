package info.reflectionsofmind.dicerobot.method;

import java.util.ArrayList;
import java.util.List;

public class WrappingWriter implements IFormattedBufferedOutput
{
	private final IFormattedBufferedOutput output;
	private final List<Append> appends = new ArrayList<Append>();
	
	public WrappingWriter(final IFormattedBufferedOutput output)
	{
		this.output = output;
	}
	
	@Override
	public IFormattedBufferedOutput append(final Object object)
	{
		this.appends.add(new Append(object.toString()));
		return this;
	}
	
	@Override
	public IFormattedBufferedOutput with(final String annotation, final String value)
	{
		this.appends.add(new Append(null, annotation, value));
		return this;
	}
	
	@Override
	public void flush()
	{
		for (final Append append : this.appends)
		{
			if (append.getObject() == null)
			{
				this.output.with(append.getAnnotation(), append.getValue());
			}
			else if (append.getAnnotation() != null)
			{
				this.output.append(append.getObject()).with(append.getAnnotation(), append.getValue());
			}
			else
			{
				this.output.append(append.getObject());
			}
		}
		
		reset();
	}
	
	public void reset()
	{
		this.appends.clear();
	}
	
	private final class Append
	{
		private final Object object;
		private final String annotation;
		private final String value;
		
		public Append(final Object object)
		{
			this(object, null, null);
		}
		
		public Append(final Object object, final String annotation, final String value)
		{
			this.object = object;
			this.annotation = annotation;
			this.value = value;
		}
		
		public Object getObject()
		{
			return this.object;
		}
		
		public String getAnnotation()
		{
			return this.annotation;
		}
		
		public String getValue()
		{
			return this.value;
		}
	}
}
