package info.reflectionsofmind.dicerobot.output;

import static org.junit.Assert.assertEquals;
import info.reflectionsofmind.dicerobot.MockOutput;

import org.junit.Test;

public class MockOutputTest
{
	@Test
	public void shouldWriteStyledText() throws Exception
	{
		final MockOutput output = new MockOutput().append("one").append("two").with(Style.BOLD).with(Style.RED).append("three").with(Style.GREEN).append("four");
		assertEquals("one<red><b>two</b></red><green>three</green>four", output.getString());
	}
}
