package info.reflectionsofmind.dicerobot.method;

import static info.reflectionsofmind.dicerobot.TestingUtil.assertWrite;
import static info.reflectionsofmind.dicerobot.TestingUtil.mockRolls;
import static org.junit.Assert.assertEquals;
import info.reflectionsofmind.dicerobot.method.impl.nwod.NwodParser;
import info.reflectionsofmind.dicerobot.method.impl.nwod.NwodRequest;
import info.reflectionsofmind.dicerobot.method.impl.nwod.NwodResult;
import info.reflectionsofmind.dicerobot.method.impl.nwod.NwodRoller;
import info.reflectionsofmind.dicerobot.method.impl.nwod.NwodWriter;

import java.util.Arrays;

import org.junit.Test;

public class NewWorldOfDarknessTest
{
	@Test
	public void shouldParseRequests() throws Exception
	{
		final NwodParser parser = new NwodParser();
		final NwodRequest request = parser.parse("5");
		assertEquals(5, request.getDicePool());
	}
	
	@Test
	public void shouldRollNormalRequests() throws Exception
	{
		final IRollRoller<NwodRequest, NwodResult> roller = new NwodRoller(mockRolls(2, 6, 4, 9));
		final NwodResult result = roller.makeRoll(new NwodRequest(4));
		assertEquals(Arrays.asList(2, 6, 4, 9), result.getRolls());
	}
	
	@Test
	public void shouldRollChanceRequests() throws Exception
	{
		final IRollRoller<NwodRequest, NwodResult> roller = new NwodRoller(mockRolls(10, 4));
		final NwodResult result = roller.makeRoll(new NwodRequest(0));
		assertEquals(Arrays.asList(10, 4), result.getRolls());
	}
	
	@Test
	public void shouldWriteSuccess() throws Exception
	{
		final NwodResult result = new NwodResult(new NwodRequest(4)).add(2, 10, 8, 4, 7);
		assertWrite(new NwodWriter(), result, "<s>2</s> <b>10</b> 8 <s>4</s> <s>7</s> = <xb>2</xb> successes");
	}
	
	@Test
	public void shouldWriteFailure() throws Exception
	{
		final NwodWriter writer = new NwodWriter();
		assertWrite(writer, new NwodResult(new NwodRequest(4)).add(2, 5, 1, 4, 7),
				"<s>2</s> <s>5</s> <s>1</s> <s>4</s> <s>7</s> = <xb>failure</xb>");
	}
	
	@Test
	public void shouldWriteDramaticFailureOnBotchedChanceRolls() throws Exception
	{
		final NwodWriter writer = new NwodWriter();
		assertWrite(writer, new NwodResult(new NwodRequest(0)).add(1), "<s>1</s> = <xb>dramatic failure</xb>");
	}
	
	@Test
	public void shouldNotWriteDramaticFailureOnBotchedNormalRolls() throws Exception
	{
		final NwodWriter writer = new NwodWriter();
		assertWrite(writer, new NwodResult(new NwodRequest(3)).add(1, 2, 4), "<s>1</s> <s>2</s> <s>4</s> = <xb>failure</xb>");
	}
}
