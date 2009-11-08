package info.reflectionsofmind.dicerobot.wrapper;

import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;

public class RollRequest
{
	private final IFormattedBufferedOutput output;
	private final String config;
	private final String request;
	
	public RollRequest(final IFormattedBufferedOutput output, final String config, final String request)
	{
		this.output = output;
		this.config = config;
		this.request = request;
	}
	
	public IFormattedBufferedOutput getOutput()
	{
		return this.output;
	}
	
	public String getConfig()
	{
		return this.config;
	}
	
	public String getRequest()
	{
		return this.request;
	}
}
