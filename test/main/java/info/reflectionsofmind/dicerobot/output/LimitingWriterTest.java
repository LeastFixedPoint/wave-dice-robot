package info.reflectionsofmind.dicerobot.output;

import info.reflectionsofmind.dicerobot.MockOutput;
import info.reflectionsofmind.dicerobot.exception.output.OutputException;
import info.reflectionsofmind.dicerobot.exception.output.OutputLengthLimitExceededException;

import org.junit.Test;

public class LimitingWriterTest
{
	@Test(expected = OutputLengthLimitExceededException.class)
	public void shouldThrowExceptionWhenLimitExceeded() throws OutputException
	{
		final LimitingWriter wrapper = new LimitingWriter(new MockOutput(), 10);
		
		wrapper.append("12345").append("678901");
	}
	
	@Test
	public void shouldNotThrowExceptionWhenWithinLimit() throws OutputException
	{
		final LimitingWriter wrapper = new LimitingWriter(new MockOutput(), 10);
		
		wrapper.append("1234567890");
	}
}
