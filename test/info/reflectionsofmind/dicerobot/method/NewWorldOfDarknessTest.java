package info.reflectionsofmind.dicerobot.method;

import static info.reflectionsofmind.dicerobot.TestingUtil.assertWrite;
import static info.reflectionsofmind.dicerobot.TestingUtil.mockDieRollerFactory;
import static org.junit.Assert.assertEquals;
import info.reflectionsofmind.dicerobot.method.impl.nwod.Parser;
import info.reflectionsofmind.dicerobot.method.impl.nwod.Request;
import info.reflectionsofmind.dicerobot.method.impl.nwod.Result;
import info.reflectionsofmind.dicerobot.method.impl.nwod.Roller;
import info.reflectionsofmind.dicerobot.method.impl.nwod.Writer;

import java.util.Arrays;

import org.junit.Test;

public class NewWorldOfDarknessTest
{
	@Test
	public void shouldParseRequests() throws Exception
	{
		final Parser parser = new Parser();
		final Request request = parser.parse("5");
		assertEquals(5, request.getDicePool());
	}
	
	@Test
	public void shouldRollNormalRequests() throws Exception
	{
		final IRollRoller<Request, Result> roller = new Roller(mockDieRollerFactory(2, 6, 4, 9));
		final Result result = roller.makeRoll(new Request(4));
		assertEquals(Arrays.asList(2, 6, 4, 9), result.getResults());
	}
	
	@Test
	public void shouldRollChanceRequests() throws Exception
	{
		final IRollRoller<Request, Result> roller = new Roller(mockDieRollerFactory(10, 4));
		final Result result = roller.makeRoll(new Request(0));
		assertEquals(Arrays.asList(10, 4), result.getResults());
	}
	
	@Test
	public void shouldWriteSuccess() throws Exception
	{
		final Writer writer = new Writer();
		assertWrite(writer, new Result(new Request(4)).add(2, 10, 8, 4, 7), "2 10 8 4 7 = 2 successes");
	}
	
	@Test
	public void shouldWriteFailure() throws Exception
	{
		final Writer writer = new Writer();
		assertWrite(writer, new Result(new Request(4)).add(2, 5, 1, 4, 7), "2 5 1 4 7 = failure");
	}
	
	@Test
	public void shouldWriteDramaticFailureOnBotchedChanceRolls() throws Exception
	{
		final Writer writer = new Writer();
		assertWrite(writer, new Result(new Request(0)).add(1), "1 = dramatic failure");
	}
	
	@Test
	public void shouldNotWriteDramaticFailureOnBotchedNormalRolls() throws Exception
	{
		final Writer writer = new Writer();
		assertWrite(writer, new Result(new Request(3)).add(1, 2, 4), "1 2 4 = failure");
	}
}
