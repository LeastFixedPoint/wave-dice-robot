package info.reflectionsofmind.dicerobot;

import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;
import info.reflectionsofmind.dicerobot.output.IStyle;

public class MockOutput implements IFormattedBufferedOutput
{
	private final StringBuilder builder = new StringBuilder();
	private int position = 0;
	
	public MockOutput append(final Object object)
	{
		this.position = this.builder.length();
		this.builder.append(object);
		return this;
	}
	
	@Override
	public MockOutput with(final IStyle style)
	{
		this.builder.insert(this.position, "<" + style.getCode() + ">");
		this.builder.append("</" + style.getCode() + ">");
		return this;
	}
	
	public void flush()
	{
	}
	
	public String getFormatted()
	{
		return this.builder.toString();
	}
}
