package info.reflectionsofmind.dicerobot.method;

import static info.reflectionsofmind.dicerobot.TestingUtil.assertWrite;
import static info.reflectionsofmind.dicerobot.TestingUtil.mockDieRollerFactory;
import static org.junit.Assert.assertEquals;
import info.reflectionsofmind.dicerobot.method.impl.RollResult;
import info.reflectionsofmind.dicerobot.method.impl.ditv.DitvParser;
import info.reflectionsofmind.dicerobot.method.impl.ditv.DitvRequest;
import info.reflectionsofmind.dicerobot.method.impl.ditv.DitvResult;
import info.reflectionsofmind.dicerobot.method.impl.ditv.DitvRoller;
import info.reflectionsofmind.dicerobot.method.impl.ditv.DitvWriter;

import java.util.Arrays;

import org.junit.Test;

public class DogsInTheVineyardTest
{
	@Test
	public void shouldParseRequests() throws Exception
	{
		final DitvParser parser = new DitvParser();
		final DitvRequest request = parser.parse("  3d6 +   2d8  +5d10  ");
		assertEquals(Arrays.asList(new DitvRequest.Roll(3, 6), new DitvRequest.Roll(2, 8), new DitvRequest.Roll(5, 10)), // 
				request.getRolls());
	}
	
	@Test
	public void shouldRollRequests() throws Exception
	{
		final IRollRoller<DitvRequest, DitvResult> roller = new DitvRoller(mockDieRollerFactory(2, 6, 4, 9));
		final DitvResult result = roller.makeRoll(new DitvRequest().add(2, 6).add(2, 10));
		assertEquals(Arrays.asList(new RollResult(2, 6), new RollResult(6, 6), new RollResult(4, 10), new RollResult(9, 10)), result.getResults());
	}
	
	@Test
	public void shouldWriteResults() throws Exception
	{
		final DitvWriter writer = new DitvWriter();
		assertWrite(writer, new DitvResult(null).add(6, 2, 6).add(10, 4, 9), "910 66 410 26");
	}
}
