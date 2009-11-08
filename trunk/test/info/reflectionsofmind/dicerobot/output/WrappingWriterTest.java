package info.reflectionsofmind.dicerobot.output;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Test;

public class WrappingWriterTest
{
	@Test
	public void shouldNotAppendImmediately() throws Exception
	{
		final IFormattedBufferedOutput output = mock(IFormattedBufferedOutput.class);
		final WrappingWriter writer = new WrappingWriter(output);
		
		writer.append("test");
		
		verifyZeroInteractions(output);
	}
	
	@Test
	public void shouldNotAnnotateImmediately() throws Exception
	{
		final IFormattedBufferedOutput output = mock(IFormattedBufferedOutput.class);
		final WrappingWriter writer = new WrappingWriter(output);
		
		writer.append("test").with("style/color", "red");
		
		verifyZeroInteractions(output);
	}
	
	@Test
	public void shouldSequentiallyAppendAndAnnotateOnFlush() throws Exception
	{
		final IFormattedBufferedOutput output = mock(IFormattedBufferedOutput.class);
		final WrappingWriter writer = new WrappingWriter(output);
		
		writer
				.append("first").with("style/color", "red")
				.append("second").with("style/color", "blue").with("style/fontWidth", "bold")
				.flush();
		
		verify(output).append(eq("first"));
		verify(output).with(eq("style/color"), eq("red"));
		verify(output).append(eq("second"));
		verify(output).with(eq("style/color"), eq("blue"));
		verify(output).with(eq("style/fontWidth"), eq("bold"));
		verifyNoMoreInteractions(output);
	}
}
