package info.reflectionsofmind.dicerobot.wrapper;

import info.reflectionsofmind.dicerobot.method.IFormattedBufferedOutput;

public class RollRequest
{
	private final IFormattedBufferedOutput output;
	private final String method;
	private final String roll;
	
	public RollRequest(final IFormattedBufferedOutput output, final String method, final String roll)
	{
		this.output = output;
		this.method = method;
		this.roll = roll;
	}
	
	public IFormattedBufferedOutput getOutput()
	{
		return this.output;
	}
	
	public String getMethod()
	{
		return this.method;
	}
	
	public String getRoll()
	{
		return this.roll;
	}
}
