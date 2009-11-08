package info.reflectionsofmind.dicerobot.output;

import info.reflectionsofmind.dicerobot.exception.output.OutputException;
import info.reflectionsofmind.dicerobot.exception.output.OutputLengthLimitExceededException;

public class LimitingWriter extends WrappingWriter
{
	private final int maxLength;
	private int currentLength;
	
	public LimitingWriter(final IFormattedBufferedOutput output, final int maxLength)
	{
		super(output);
		this.maxLength = maxLength;
	}
	
	@Override
	public LimitingWriter append(final Object object) throws OutputException
	{
		this.currentLength += object.toString().length();
		
		if (this.currentLength > this.maxLength)
			throw new OutputLengthLimitExceededException(this.currentLength, this.maxLength);
		
		return (LimitingWriter) super.append(object);
	}
}
