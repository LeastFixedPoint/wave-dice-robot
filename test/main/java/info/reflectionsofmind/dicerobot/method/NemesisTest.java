package info.reflectionsofmind.dicerobot.method;

import static info.reflectionsofmind.dicerobot.TestingUtil.assertWrite;
import static info.reflectionsofmind.dicerobot.TestingUtil.mockRolls;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import info.reflectionsofmind.dicerobot.MockOutput;
import info.reflectionsofmind.dicerobot.method.impl.nemesis.NemParser;
import info.reflectionsofmind.dicerobot.method.impl.nemesis.NemRequest;
import info.reflectionsofmind.dicerobot.method.impl.nemesis.NemResult;
import info.reflectionsofmind.dicerobot.method.impl.nemesis.NemRoller;
import info.reflectionsofmind.dicerobot.method.impl.nemesis.NemWriter;
import info.reflectionsofmind.dicerobot.method.impl.nemesis.Nemesis;

import org.junit.Test;

public class NemesisTest
{
	@Test
	public void shouldExecutePipeline() throws Exception
	{
		final MockOutput output = new MockOutput();
		new Nemesis().writeResult(mockRolls(2, 4, 5, 2, 4, 4), null, "1+4d+3+2d+4+3td+2", output);
		assertEquals("<xb>4x4</xb> + <xb>3x2</xb> + <xb>3td</xb> + 5 + 3 + 1", output.getFormatted());
	}
	
	@Test
	public void shouldParseSimpleRolls() throws Exception
	{
		final NemRequest request = new NemParser().parse("6d");
		assertEquals(new NemRequest().addStandard(6), request);
	}
	
	@Test
	public void shouldParseMultipleRolls() throws Exception
	{
		final NemRequest request = new NemParser().parse("3 + 3d + 5 + 2td + 2d + 4");
		
		assertEquals(new NemRequest().addStandard(5).addExpert(3, 4, 5).addTrump(2), request);
	}
	
	@Test
	public void shouldRollStandardDice() throws Exception
	{
		final NemRequest request = new NemRequest().addStandard(6);
		final NemResult result = new NemRoller(mockRolls(2, 4, 5, 2, 4, 4)).makeRoll(request);
		
		assertEquals(asList(2, 4, 5, 2, 4, 4), result.getRolls());
	}
	
	@Test
	public void shouldWriteStandardSets() throws Exception
	{
		final NemRequest request = new NemRequest();
		final NemResult result = new NemResult(request).add(2, 4, 5, 2, 4, 4);
		assertWrite(new NemWriter(), result, "<xb>3x4</xb> + <xb>2x2</xb> + 5");
	}
	
	@Test
	public void shouldWriteOtherDiceTypes() throws Exception
	{
		final NemRequest request = new NemRequest().addStandard(7).addExpert(2, 6).addTrump(3);
		final NemResult result = new NemResult(request).add(2, 4, 5, 2, 4, 4, 1);
		assertWrite(new NemWriter(), result, "<xb>3x4</xb> + <xb>3x2</xb> + <xb>3td</xb> + 6 + 5 + 1");
	}
	
	@Test
	public void shouldWriteNothing() throws Exception
	{
		final NemRequest request = new NemRequest();
		final NemResult result = new NemResult(request);
		assertWrite(new NemWriter(), result, "nothing");
	}
}
