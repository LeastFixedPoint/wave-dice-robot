package info.reflectionsofmind.dicerobot.method;

import static info.reflectionsofmind.dicerobot.TestingUtil.assertWrite;
import static info.reflectionsofmind.dicerobot.TestingUtil.mockRolls;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import info.reflectionsofmind.dicerobot.method.impl.exa.ExaParser;
import info.reflectionsofmind.dicerobot.method.impl.exa.ExaRequest;
import info.reflectionsofmind.dicerobot.method.impl.exa.ExaResult;
import info.reflectionsofmind.dicerobot.method.impl.exa.ExaRoller;
import info.reflectionsofmind.dicerobot.method.impl.exa.ExaWriter;

import org.junit.Test;

public class ExaltedTest
{
	@Test
	public void shouldParseSimplePools() throws Exception
	{
		final ExaRequest request = new ExaParser().parse("5");
		assertEquals(asList(new ExaRequest.Group(7, 5)), request.getGroups());
	}
	
	@Test
	public void shouldParsePoolsWithDifferentTargetNumbers() throws Exception
	{
		final ExaRequest request = new ExaParser().parse("5 tn 6");
		assertEquals(asList(new ExaRequest.Group(6, 5)), request.getGroups());
	}
	
	@Test
	public void shouldParseCombinedPools() throws Exception
	{
		final ExaRequest request = new ExaParser().parse("5 + 2 tn 5 + 3 + 1 tn 5");
		assertEquals(asList(new ExaRequest.Group(7, 5), new ExaRequest.Group(5, 2),
				new ExaRequest.Group(7, 3), new ExaRequest.Group(5, 1)), request.getGroups());
	}
	
	@Test
	public void shouldParseMortalRows() throws Exception
	{
		final ExaRequest request = new ExaParser().parse("m5");
		assertFalse(request.isTensExplosive());
	}
	
	@Test
	public void shouldParseExaltedRows() throws Exception
	{
		final ExaRequest request = new ExaParser().parse("5");
		assertTrue(request.isTensExplosive());
	}
	
	@Test
	public void shouldSetDifficultyOneByDefault() throws Exception
	{
		final ExaRequest request = new ExaParser().parse("5");
		assertEquals(1, request.getDifficulty());
	}
	
	@Test
	public void shouldParseDifficulty() throws Exception
	{
		final ExaRequest request = new ExaParser().parse("5 vs 3");
		assertEquals(3, request.getDifficulty());
	}
	
	@Test
	public void shouldAllowSlashesForTargetNumbers() throws Exception
	{
		final ExaRequest request = new ExaParser().parse("3/5");
		assertEquals(asList(new ExaRequest.Group(5, 3)), request.getGroups());
	}
	
	@Test
	public void shouldAllowDifficulty() throws Exception
	{
		final ExaRequest request = new ExaParser().parse("10 vs 2");
		assertEquals(new ExaRequest().add(10).difficulty(2), request);
	}
	
	@Test
	public void shouldAllowMortalRolls() throws Exception
	{
		final ExaRequest request = new ExaParser().parse("m 6");
		assertEquals(new ExaRequest().add(6).explodeTens(false), request);
	}
	
	@Test
	public void shouldRollPools() throws Exception
	{
		final ExaRequest request = new ExaRequest().add(3, 6).add(2);
		final ExaResult result = new ExaRoller(mockRolls(4, 6, 2, 8, 3)).makeRoll(request);
		assertEquals(new ExaResult(request).add(6, 4, 6, 2).add(7, 8, 3), result);
	}
	
	@Test
	public void shouldWriteSimpleRolls() throws Exception
	{
		final ExaRequest request = new ExaRequest().add(3);
		final ExaResult result = new ExaResult(request).add(7, 2, 7, 9);
		assertWrite(new ExaWriter(), result, //
				"<red>2</red> <green>7</green> <green>9</green> = <green><xb>success +1</xb></green>");
	}
	
	@Test
	public void shouldWriteAltTargetNumberRolls() throws Exception
	{
		final ExaRequest request = new ExaRequest().add(3, 6);
		final ExaResult result = new ExaResult(request).add(6, 5, 6, 7);
		assertWrite(new ExaWriter(), result,
				"<red>5</red> <green>6</green> <green>7</green> = <green><xb>success +1</xb></green>");
	}
	
	@Test
	public void shouldWriteAltDifficultyRolls() throws Exception
	{
		final ExaRequest request = new ExaRequest().add(3).difficulty(2);
		final ExaResult result = new ExaResult(request).add(7, 7, 7, 9);
		assertWrite(new ExaWriter(), result,
				"<green>7</green> <green>7</green> <green>9</green> = <green><xb>success +1</xb></green>");
	}
	
	@Test
	public void shouldWriteJustSuccesesWithoutQualificator() throws Exception
	{
		final ExaRequest request = new ExaRequest().add(3);
		final ExaResult result = new ExaResult(request).add(7, 2, 4, 9);
		assertWrite(new ExaWriter(), result,
				"<red>2</red> <red>4</red> <green>9</green> = <green><xb>success</xb></green>");
	}
	
	@Test
	public void shouldWriteTensExploded() throws Exception
	{
		final ExaRequest request = new ExaRequest().add(1);
		final ExaResult result = new ExaResult(request).add(7, 10);
		assertWrite(new ExaWriter(), result,
				"<green>10</green> = <green><xb>success +1</xb></green>");
	}
	
	@Test
	public void shouldWriteFailure() throws Exception
	{
		final ExaRequest request = new ExaRequest().add(1);
		final ExaResult result = new ExaResult(request).add(7, 6);
		assertWrite(new ExaWriter(), result, "<red>6</red> = <xb>failure -1</xb>");
	}
	
	@Test
	public void shouldWriteFailureForHigherDifficulty() throws Exception
	{
		final ExaRequest request = new ExaRequest().add(3).difficulty(2);
		final ExaResult result = new ExaResult(request).add(7, 5, 6, 7);
		assertWrite(new ExaWriter(), result,
				"<red>5</red> <red>6</red> <green>7</green> = <xb>failure -1</xb>");
	}
	
	@Test
	public void shouldWriteFailureForHigherDifficultyEvenWithTens() throws Exception
	{
		final ExaRequest request = new ExaRequest().add(3).difficulty(3);
		final ExaResult result = new ExaResult(request).add(7, 5, 6, 10);
		assertWrite(new ExaWriter(), result,
				"<red>5</red> <red>6</red> <green>10</green> = <xb>failure -1</xb>");
	}
	
	@Test
	public void shouldWriteBotch() throws Exception
	{
		final ExaRequest request = new ExaRequest().add(2);
		final ExaResult result = new ExaResult(request).add(7, 1, 3);
		assertWrite(new ExaWriter(), result,
				"<red>1</red> <red>3</red> = <red><xb>BOTCH</xb></red>");
	}
	
	@Test
	public void shouldNotWriteBotchIfThereAreSuccesses() throws Exception
	{
		final ExaRequest request = new ExaRequest().add(2);
		final ExaResult result = new ExaResult(request).add(7, 1, 7);
		assertWrite(new ExaWriter(), result,
				"<red>1</red> <green>7</green> = <green><xb>success</xb></green>");
	}
	
	@Test
	public void shouldNotWriteBotchIfThereAreNoOnes() throws Exception
	{
		final ExaRequest request = new ExaRequest().add(2);
		final ExaResult result = new ExaResult(request).add(7, 2, 3);
		assertWrite(new ExaWriter(), result,
				"<red>2</red> <red>3</red> = <xb>failure -1</xb>");
	}
	
	@Test
	public void shouldNotWriteExplodedTensForMortalRolls() throws Exception
	{
		final ExaRequest request = new ExaRequest().add(1).explodeTens(false);
		final ExaResult result = new ExaResult(request).add(7, 10);
		assertWrite(new ExaWriter(), result,
				"<green>10</green> = <green><xb>success</xb></green>");
	}
}
