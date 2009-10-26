package info.reflectionsofmind.dicerobot;

import info.reflectionsofmind.dicerobot.method.IFormattedBufferedOutput;

public class MockOutput implements IFormattedBufferedOutput
{
	private final StringBuilder builder = new StringBuilder();
	
	public IFormattedBufferedOutput append(final Object object)
	{
		this.builder.append(object);
		return this;
	}
	
	public IFormattedBufferedOutput append(final Object object, final String annotation, final String value)
	{
		this.builder.append(object);
		return this;
	}
	
	public void flush()
	{
	}
	
	public String getString()
	{
		return this.builder.toString();
	}
}
