package info.reflectionsofmind.dicerobot;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import info.reflectionsofmind.dicerobot.wrapper.RollRequest;

import java.util.List;

import org.junit.Test;

public class ExtractTest
{
	private static void assertRequest(final String config, final String input, final int start, final IWritableText textMock, final RollRequest request)
	{
		assertEquals(config, request.getConfig());
		assertEquals(input, request.getRequest());
		verify(textMock).createOutput(eq(start));
	}
	
	@Test
	public void shouldExtractQualifiedRequests()
	{
		final DiceRobot robot = new DiceRobot(null);
		
		final IWritableText text = mock(IWritableText.class);
		when(text.getText()).thenReturn("blah blah [sum:2d6+3] blah blah");
		
		final List<RollRequest> requests = robot.extractRequests(text);
		
		assertEquals(1, requests.size());
		assertRequest("sum", "2d6+3", 20, text, requests.get(0));
	}
	
	@Test
	public void shouldExtractNonQualifiedRequests()
	{
		final DiceRobot robot = new DiceRobot(null);
		
		final IWritableText text = mock(IWritableText.class);
		when(text.getText()).thenReturn("blah blah [2d6+3] blah blah");
		
		final List<RollRequest> requests = robot.extractRequests(text);
		
		assertEquals(1, requests.size());
		assertRequest(null, "2d6+3", 16, text, requests.get(0));
	}
	
	@Test
	public void shouldNotExtractEvaluatedRequests()
	{
		final DiceRobot robot = new DiceRobot(null);
		
		final IWritableText text = mock(IWritableText.class);
		when(text.getText()).thenReturn("blah blah [2d6+3 = 11 + 3 = 14] blah blah");
		
		final List<RollRequest> requests = robot.extractRequests(text);
		
		assertEquals(0, requests.size());
	}
}
