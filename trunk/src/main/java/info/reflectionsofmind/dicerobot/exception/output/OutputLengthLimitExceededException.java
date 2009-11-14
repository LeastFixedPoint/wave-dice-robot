package info.reflectionsofmind.dicerobot.exception.output;

public class OutputLengthLimitExceededException extends OutputException
{
	private final int maxOutLength;
	private final int outputLength;
	
	public OutputLengthLimitExceededException(final int outputLength, final int maximumOutputLength)
	{
		this.outputLength = outputLength;
		this.maxOutLength = maximumOutputLength;
	}
	
	public int getMaxOutLength()
	{
		return this.maxOutLength;
	}
	
	public int getOutputLength()
	{
		return this.outputLength;
	}
}
