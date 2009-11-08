package info.reflectionsofmind.dicerobot.method;

import static info.reflectionsofmind.dicerobot.TestingUtil.assertWrite;
import static info.reflectionsofmind.dicerobot.TestingUtil.mockDieRollerFactory;
import static org.junit.Assert.assertEquals;
import info.reflectionsofmind.dicerobot.method.impl.Roll;
import info.reflectionsofmind.dicerobot.method.impl.RollResult;
import info.reflectionsofmind.dicerobot.method.impl.ditv.Parser;
import info.reflectionsofmind.dicerobot.method.impl.ditv.Request;
import info.reflectionsofmind.dicerobot.method.impl.ditv.Result;
import info.reflectionsofmind.dicerobot.method.impl.ditv.Roller;
import info.reflectionsofmind.dicerobot.method.impl.ditv.Writer;

import java.util.Arrays;

import org.junit.Test;

public class DogsInTheVineyardTest
{
	@Test
	public void shouldParseRequests() throws Exception
	{
		final Parser parser = new Parser();
		final Request request = parser.parse("  3d6 +   2d8  +5d10  ");
		assertEquals(Arrays.asList(new Roll(3, 6), new Roll(2, 8), new Roll(5, 10)), // 
				request.getRolls());
	}
	
	@Test
	public void shouldRollRequests() throws Exception
	{
		final IRollRoller<Request, Result> roller = new Roller(mockDieRollerFactory(2, 6, 4, 9));
		final Result result = roller.makeRoll(new Request().add(2, 6).add(2, 10));
		assertEquals(Arrays.asList(new RollResult(2, 6), new RollResult(6, 6), new RollResult(4, 10), new RollResult(9, 10)), result.getResults());
	}
	
	@Test
	public void shouldWriteResults() throws Exception
	{
		final Writer writer = new Writer();
		assertWrite(writer, new Result(null).add(6, 2, 6).add(10, 4, 9), "910 66 410 26");
	}
}
