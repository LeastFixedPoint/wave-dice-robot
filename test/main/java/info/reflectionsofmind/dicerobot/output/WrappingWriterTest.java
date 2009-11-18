package info.reflectionsofmind.dicerobot.output;

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
		
		writer.append("test").with(Style.RED);
		
		verifyZeroInteractions(output);
	}
	
	@Test
	public void shouldSequentiallyAppendAndAnnotateOnFlush() throws Exception
	{
		final IFormattedBufferedOutput output = mock(IFormattedBufferedOutput.class);
		final WrappingWriter writer = new WrappingWriter(output);
		
		writer.append("first").with(Style.RED).append("second").with(Style.GREEN).with(Style.BOLD).flush();
		
		verify(output).append("first");
		verify(output).with(Style.RED);
		verify(output).append("second");
		verify(output).with(Style.GREEN);
		verify(output).with(Style.BOLD);
		verifyNoMoreInteractions(output);
	}
}
