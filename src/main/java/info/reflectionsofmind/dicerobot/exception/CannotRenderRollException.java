package info.reflectionsofmind.dicerobot.exception;

import info.reflectionsofmind.dicerobot.exception.output.OutputException;
import info.reflectionsofmind.dicerobot.exception.output.OutputLengthLimitExceededException;
import info.reflectionsofmind.dicerobot.exception.write.ResultTooLongException;

public class CannotRenderRollException extends UserReadableException
{
	public CannotRenderRollException(final String string)
	{
		super(string);
	}
	
	public static CannotRenderRollException wrap(final OutputException exception)
	{
		if (exception instanceof OutputLengthLimitExceededException)
		{
			final int maxOutLength = ((OutputLengthLimitExceededException) exception).getMaxOutLength();
			return new ResultTooLongException(maxOutLength);
		}
		else
		{
			return new CannotRenderRollException("unknown rendering error");
		}
	}
}
