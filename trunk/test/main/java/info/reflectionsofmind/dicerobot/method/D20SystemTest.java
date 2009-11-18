package info.reflectionsofmind.dicerobot.method;

import static info.reflectionsofmind.dicerobot.TestingUtil.assertWrite;
import static info.reflectionsofmind.dicerobot.TestingUtil.mockRolls;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import info.reflectionsofmind.dicerobot.method.impl.d20.D20Parser;
import info.reflectionsofmind.dicerobot.method.impl.d20.D20Request;
import info.reflectionsofmind.dicerobot.method.impl.d20.D20Result;
import info.reflectionsofmind.dicerobot.method.impl.d20.D20Roller;
import info.reflectionsofmind.dicerobot.method.impl.d20.D20Writer;
import info.reflectionsofmind.dicerobot.method.impl.sum.SumRequest;
import info.reflectionsofmind.dicerobot.method.impl.sum.SumResult;
import info.reflectionsofmind.dicerobot.method.impl.sum.SumRequest.Roll;

import java.util.Arrays;

import org.junit.Test;

public class D20SystemTest
{
	@Test
	public void shouldParseCharGenRequests() throws Exception
	{
		final D20Request request = new D20Parser().parse("cg");
		assertTrue(request instanceof D20Request.CharGen);
	}
	
	@Test
	public void shouldParseDiceRollRequests() throws Exception
	{
		final D20Request request = new D20Parser().parse("3d6+5");
		assertTrue(request instanceof D20Request.SumAllRolls);
	}
	
	@Test
	public void shouldRollSixAbilityValues() throws Exception
	{
		final D20Roller roller = new D20Roller(mockRolls(1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6));
		final D20Result.CharGen result = (D20Result.CharGen) roller.makeRoll(new D20Request.CharGen());
		assertEquals(Arrays.asList(9, 13, 15, 9, 13, 15), result.getValues());
	}
	
	@Test
	public void shouldRollSumOfAllRolls() throws Exception
	{
		final D20Roller roller = new D20Roller(mockRolls(5, 3, 6));
		final D20Request.SumAllRolls request = new D20Request.SumAllRolls(new SumRequest().add(3, 6).add(5));
		
		final D20Result.SumAllRolls result = (D20Result.SumAllRolls) roller.makeRoll(request);
		
		final Roll roll = (Roll) request.getRequest().getTokens().get(0);
		assertEquals(asList(5, 3, 6), result.getResult().getResults(roll));
	}
	
	@Test
	public void shouldWriteCharGenResults() throws Exception
	{
		final D20Request.CharGen request = new D20Request.CharGen();
		final D20Result result = new D20Result.CharGen(request).addValues(9, 12, 15, 11, 17, 18);
		
		assertWrite(new D20Writer(), result, "9, 12, 15, 11, 17, 18");
	}
	
	@Test
	public void shouldWriteSumAllRollsResults() throws Exception
	{
		final SumRequest sumRequest = new SumRequest().add(3, 5).add(5);
		final Roll roll = (Roll) sumRequest.getTokens().get(0);
		final D20Request.SumAllRolls request = new D20Request.SumAllRolls(sumRequest);
		final D20Result result = new D20Result.SumAllRolls(request, new SumResult(sumRequest).add(roll, 4, 2, 5));
		
		assertWrite(new D20Writer(), result, "11 + 5 = 16");
	}
}
