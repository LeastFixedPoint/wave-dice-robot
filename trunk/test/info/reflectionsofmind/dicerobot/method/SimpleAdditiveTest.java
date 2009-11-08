package info.reflectionsofmind.dicerobot.method;

import static info.reflectionsofmind.dicerobot.TestingUtil.assertWrite;
import static info.reflectionsofmind.dicerobot.TestingUtil.mockDieRollerFactory;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import info.reflectionsofmind.dicerobot.method.impl.sum.AdditiveRoll;
import info.reflectionsofmind.dicerobot.method.impl.sum.Parser;
import info.reflectionsofmind.dicerobot.method.impl.sum.Request;
import info.reflectionsofmind.dicerobot.method.impl.sum.Result;
import info.reflectionsofmind.dicerobot.method.impl.sum.Roller;
import info.reflectionsofmind.dicerobot.method.impl.sum.Writer;
import info.reflectionsofmind.dicerobot.method.impl.sum.Request.Number;
import info.reflectionsofmind.dicerobot.method.impl.sum.Request.Roll;
import info.reflectionsofmind.dicerobot.method.impl.sum.Writer.CollapseMode;

import java.util.List;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

/** Test of {@link AdditiveRoll}. */
public class SimpleAdditiveTest
{
	private MockOutput output;
	
	@Before
	public void setUp()
	{
		this.output = new MockOutput();
	}
	
	@Test
	public void shouldRollDice() throws Exception
	{
		final AdditiveRoll method = new AdditiveRoll(mockDieRollerFactory(1, 6, 3));
		method.writeResult("3d6", this.output);
		assertEquals("10", this.output.getString());
	}
	
	@Test
	public void shouldAllowNumbers() throws Exception
	{
		final AdditiveRoll method = new AdditiveRoll(mockDieRollerFactory(1, 6, 3));
		method.writeResult("5", this.output);
		assertEquals("5", this.output.getString());
	}
	
	@Test
	public void shouldAdd() throws Exception
	{
		final AdditiveRoll method = new AdditiveRoll(mockDieRollerFactory(1, 6, 3));
		method.writeResult("3d6+3", this.output);
		assertEquals("10 + 3 = 13", this.output.getString());
	}
	
	@Test
	public void shouldSubtract() throws Exception
	{
		final AdditiveRoll method = new AdditiveRoll(mockDieRollerFactory(1, 6, 3));
		method.writeResult("3d6-3", this.output);
		assertEquals("10 - 3 = 7", this.output.getString());
	}
	
	@Test
	public void shouldRollOneDieIfNumberNotSpecified() throws Exception
	{
		final AdditiveRoll method = new AdditiveRoll(mockDieRollerFactory(15));
		method.writeResult("d20", this.output);
		assertEquals("15", this.output.getString());
	}
	
	@Test
	public void shouldParseRollsWithoutLeadingSign() throws Exception
	{
		final Request request = new Parser().parse("3d6-4+d20");
		assertEquals(asList(new Roll(3, 6), new Number(-4), new Roll(1, 20)), request.getTokens());
	}
	
	@Test
	public void shouldParseRollsWithLeadingSign() throws Exception
	{
		final Request request = new Parser().parse("-3d6-4+d20");
		assertEquals(asList(new Roll(-3, 6), new Number(-4), new Roll(1, 20)), request.getTokens());
	}
	
	@Test
	public void shouldSplitRollRequestWithoutLeadingSign() throws Exception
	{
		final List<String> tokens = Parser.split("3d6-4+d20", Pattern.compile("[-+]"));
		assertEquals(asList("3d6", "-4", "+d20"), tokens);
	}
	
	@Test
	public void shouldSplitRollRequestWithLeadingSign() throws Exception
	{
		final List<String> tokens = Parser.split("-3d6-4+d20", Pattern.compile("[-+]"));
		assertEquals(asList("-3d6", "-4", "+d20"), tokens);
	}
	
	@Test
	public void shouldRollRolls() throws Exception
	{
		final Request request = new Request().add(-3, 6).add(-4).add(1, 20);
		final Result result = new Roller(mockDieRollerFactory(2, 1, 6, 16)).makeRoll(request);
		assertEquals(asList(-2, -1, -6, -4, 16), result.getResults());
	}
	
	@Test
	public void shouldWriteResultsWithLeadingSign() throws Exception
	{
		final Request request = new Request().add(-3, 6).add(-4).add(1, 20);
		
		final Result result = new Result(request)
				.add((Roll) request.getTokens().get(0), -2, -1, -6)
				.add((Roll) request.getTokens().get(2), 16);
		
		assertWrite(new Writer(), result, "-9 - 4 + 16 = 3");
	}
	
	@Test
	public void shouldWriteResultsWithoutLeadingSign() throws Exception
	{
		final Request request = new Request().add(3, 6).add(-4).add(1, 20);
		
		final Result result = new Result(request)
				.add((Roll) request.getTokens().get(0), 2, 1, 6)
				.add((Roll) request.getTokens().get(2), 16);
		
		assertWrite(new Writer(), result, "9 - 4 + 16 = 21");
	}
	
	@Test
	public void shouldWriteExpandedResults() throws Exception
	{
		final Request request = new Request().add(-3, 6).add(-4).add(1, 20);
		
		final Result result = new Result(request)
				.add((Roll) request.getTokens().get(0), -2, -1, -6)
				.add((Roll) request.getTokens().get(2), 16);
		
		final Writer writer = new Writer().setCollapseMode(CollapseMode.NONE);
		
		assertWrite(writer, result, "-2 - 1 - 6 - 4 + 16 = 3");
	}
	
	@Test
	public void shouldWriteCollapsedResults() throws Exception
	{
		final Request request = new Request().add(-3, 6).add(-4).add(1, 20);
		
		final Result result = new Result(request)
				.add((Roll) request.getTokens().get(0), -2, -1, -6)
				.add((Roll) request.getTokens().get(2), 16);
		
		final Writer writer = new Writer().setCollapseMode(CollapseMode.ALL);
		
		assertWrite(writer, result, "3");
	}
	
	@Test
	public void shouldAllowSpacesAroundSigns() throws Exception
	{
		final Request request = new Parser().parse("- 2d4 -  5+  3d8");
		assertEquals(asList(
				new Request.Roll(-2, 4),
				new Request.Number(-5),
				new Request.Roll(3, 8)
				), request.getTokens());
	}
}
