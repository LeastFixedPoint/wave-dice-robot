package info.reflectionsofmind.dicerobot.method;

import static info.reflectionsofmind.dicerobot.TestingUtil.assertWrite;
import static info.reflectionsofmind.dicerobot.TestingUtil.mockDieRollerFactory;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import info.reflectionsofmind.dicerobot.method.impl.sum.AdditiveRoll;
import info.reflectionsofmind.dicerobot.method.impl.sum.SumParser;
import info.reflectionsofmind.dicerobot.method.impl.sum.SumRequest;
import info.reflectionsofmind.dicerobot.method.impl.sum.SumResult;
import info.reflectionsofmind.dicerobot.method.impl.sum.SumRoller;
import info.reflectionsofmind.dicerobot.method.impl.sum.SumWriter;
import info.reflectionsofmind.dicerobot.method.impl.sum.SumRequest.Number;
import info.reflectionsofmind.dicerobot.method.impl.sum.SumRequest.Roll;
import info.reflectionsofmind.dicerobot.method.impl.sum.SumWriter.CollapseMode;

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
		final IRollingMethod method = new AdditiveRoll().setDieRollerFactory(mockDieRollerFactory(1, 6, 3));
		method.writeResult("3d6", this.output);
		assertEquals("10", this.output.getString());
	}
	
	@Test
	public void shouldAllowNumbers() throws Exception
	{
		final IRollingMethod method = new AdditiveRoll().setDieRollerFactory(mockDieRollerFactory(1, 6, 3));
		method.writeResult("5", this.output);
		assertEquals("5", this.output.getString());
	}
	
	@Test
	public void shouldAdd() throws Exception
	{
		final IRollingMethod method = new AdditiveRoll().setDieRollerFactory(mockDieRollerFactory(1, 6, 3));
		method.writeResult("3d6+3", this.output);
		assertEquals("10 + 3 = 13", this.output.getString());
	}
	
	@Test
	public void shouldSubtract() throws Exception
	{
		final IRollingMethod method = new AdditiveRoll().setDieRollerFactory(mockDieRollerFactory(1, 6, 3));
		method.writeResult("3d6-3", this.output);
		assertEquals("10 - 3 = 7", this.output.getString());
	}
	
	@Test
	public void shouldRollOneDieIfNumberNotSpecified() throws Exception
	{
		final IRollingMethod method = new AdditiveRoll().setDieRollerFactory(mockDieRollerFactory(15));
		method.writeResult("d20", this.output);
		assertEquals("15", this.output.getString());
	}
	
	@Test
	public void shouldParseRollsWithoutLeadingSign() throws Exception
	{
		final SumRequest request = new SumParser().parse("3d6-4+d20");
		assertEquals(asList(new Roll(3, 6), new Number(-4), new Roll(1, 20)), request.getTokens());
	}
	
	@Test
	public void shouldParseRollsWithLeadingSign() throws Exception
	{
		final SumRequest request = new SumParser().parse("-3d6-4+d20");
		assertEquals(asList(new Roll(-3, 6), new Number(-4), new Roll(1, 20)), request.getTokens());
	}
	
	@Test
	public void shouldSplitRollRequestWithoutLeadingSign() throws Exception
	{
		final List<String> tokens = SumParser.split("3d6-4+d20", Pattern.compile("[-+]"));
		assertEquals(asList("3d6", "-4", "+d20"), tokens);
	}
	
	@Test
	public void shouldSplitRollRequestWithLeadingSign() throws Exception
	{
		final List<String> tokens = SumParser.split("-3d6-4+d20", Pattern.compile("[-+]"));
		assertEquals(asList("-3d6", "-4", "+d20"), tokens);
	}
	
	@Test
	public void shouldRollRolls() throws Exception
	{
		final SumRequest request = new SumRequest().add(-3, 6).add(-4).add(1, 20);
		final SumResult result = new SumRoller(mockDieRollerFactory(2, 1, 6, 16)).makeRoll(request);
		assertEquals(asList(-2, -1, -6, -4, 16), result.getResults());
	}
	
	@Test
	public void shouldWriteResultsWithLeadingSign() throws Exception
	{
		final SumRequest request = new SumRequest().add(-3, 6).add(-4).add(1, 20);
		
		final SumResult result = new SumResult(request)
				.add((Roll) request.getTokens().get(0), -2, -1, -6)
				.add((Roll) request.getTokens().get(2), 16);
		
		assertWrite(new SumWriter(), result, "-9 - 4 + 16 = 3");
	}
	
	@Test
	public void shouldWriteResultsWithoutLeadingSign() throws Exception
	{
		final SumRequest request = new SumRequest().add(3, 6).add(-4).add(1, 20);
		
		final SumResult result = new SumResult(request)
				.add((Roll) request.getTokens().get(0), 2, 1, 6)
				.add((Roll) request.getTokens().get(2), 16);
		
		assertWrite(new SumWriter(), result, "9 - 4 + 16 = 21");
	}
	
	@Test
	public void shouldWriteExpandedResults() throws Exception
	{
		final SumRequest request = new SumRequest().add(-3, 6).add(-4).add(1, 20);
		
		final SumResult result = new SumResult(request)
				.add((Roll) request.getTokens().get(0), -2, -1, -6)
				.add((Roll) request.getTokens().get(2), 16);
		
		final SumWriter writer = new SumWriter().setCollapseMode(CollapseMode.NONE);
		
		assertWrite(writer, result, "-2 - 1 - 6 - 4 + 16 = 3");
	}
	
	@Test
	public void shouldWriteCollapsedResults() throws Exception
	{
		final SumRequest request = new SumRequest().add(-3, 6).add(-4).add(1, 20);
		
		final SumResult result = new SumResult(request)
				.add((Roll) request.getTokens().get(0), -2, -1, -6)
				.add((Roll) request.getTokens().get(2), 16);
		
		final SumWriter writer = new SumWriter().setCollapseMode(CollapseMode.ALL);
		
		assertWrite(writer, result, "3");
	}
	
	@Test
	public void shouldAllowSpacesAroundSigns() throws Exception
	{
		final SumRequest request = new SumParser().parse("- 2d4 -  5+  3d8");
		assertEquals(asList(
				new SumRequest.Roll(-2, 4),
				new SumRequest.Number(-5),
				new SumRequest.Roll(3, 8)
				), request.getTokens());
	}
}