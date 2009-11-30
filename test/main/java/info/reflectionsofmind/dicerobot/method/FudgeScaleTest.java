package info.reflectionsofmind.dicerobot.method;

import static org.junit.Assert.assertEquals;
import info.reflectionsofmind.dicerobot.method.impl.fudge.scale.Scale;
import info.reflectionsofmind.dicerobot.method.impl.fudge.scale.SuperbScale;
import info.reflectionsofmind.dicerobot.method.impl.fudge.scale.TerribleScale;

import org.junit.Test;

public class FudgeScaleTest
{

	@Test
	public void shouldCorrectlyUpscaleInsideRande() throws Exception
	{
		assertEquals(Scale.SUPERB, Scale.TERRIBLE.adjust(+6));
	}

	@Test
	public void shouldCorrectlyDownscaleInsideRande() throws Exception
	{
		assertEquals(Scale.TERRIBLE, Scale.SUPERB.adjust(-6));
	}

	@Test
	public void shouldCorrectlyUpscaleOutsideRande() throws Exception
	{
		assertEquals("superb +2", Scale.GREAT.adjust(+3).getAdjective());
	}

	@Test
	public void shouldCorrectlyDownscaleOutsideRande() throws Exception
	{
		assertEquals("terrible −2", Scale.POOR.adjust(-3).getAdjective());
	}

	@Test
	public void shouldCorrectlyUpscaleFromOutsideRande() throws Exception
	{
		assertEquals(Scale.POOR, new TerribleScale(2).adjust(+3));
	}

	@Test
	public void shouldCorrectlyDownscaleFromOutsideRande() throws Exception
	{
		assertEquals(Scale.GREAT, new SuperbScale(2).adjust(-3));
	}

	@Test
	public void shouldRecognizeAdjectivesInRange() throws Exception
	{
		assertEquals(Scale.GREAT, Scale.findByAdjective(" gReAt "));
	}

	@Test
	public void shouldRecognizeAdjectivesOverRange() throws Exception
	{
		assertEquals(new SuperbScale(2), Scale.findByAdjective(" sUPerB  +2 "));
	}

	@Test
	public void shouldRecognizeAdjectivesUnderRange() throws Exception
	{
		assertEquals(new TerribleScale(2), Scale.findByAdjective(" teRRIBle  -2 "));
	}

}
