package info.reflectionsofmind.dicerobot.output;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.mockito.InOrder;

public class JoiningWriterTest
{
	@Test
	public void shouldWriteJoinedText() throws Exception
	{
		final IFormattedBufferedOutput output = mock(IFormattedBufferedOutput.class);
		final JoiningWriter joiner = new JoiningWriter(output, " + ");
		joiner.append("1").append("2").with(Style.RED).append("3");
		
		final InOrder inOrder = inOrder(output);
		
		inOrder.verify(output).append("1");
		inOrder.verify(output).append(" + ");
		inOrder.verify(output).append("2");
		inOrder.verify(output).with(Style.RED);
		inOrder.verify(output).append("3");
	}
}
