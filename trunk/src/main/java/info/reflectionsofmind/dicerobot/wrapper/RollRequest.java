package info.reflectionsofmind.dicerobot.wrapper;

import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;

public class RollRequest
{
	private final IFormattedBufferedOutput output;
	private final String methodCode;
	private final String request;
	
	public RollRequest(final IFormattedBufferedOutput output, final String methodCode, final String request)
	{
		this.output = output;
		this.methodCode = methodCode;
		this.request = request;
	}
	
	public IFormattedBufferedOutput getOutput()
	{
		return this.output;
	}
	
	public String getMethodCode()
	{
		return this.methodCode;
	}
	
	public String getRequest()
	{
		return this.request;
	}
}
