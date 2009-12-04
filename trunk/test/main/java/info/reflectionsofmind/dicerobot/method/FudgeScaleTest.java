package info.reflectionsofmind.dicerobot.method;

import static org.junit.Assert.assertEquals;
import info.reflectionsofmind.dicerobot.method.impl.fudge.scale.Scale;
import info.reflectionsofmind.dicerobot.method.impl.fudge.scale.LegendaryScale;
import info.reflectionsofmind.dicerobot.method.impl.fudge.scale.AbysmalScale;

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
		assertEquals("legendary +2", Scale.EPIC.adjust(+3).getAdjective());
	}

	@Test
	public void shouldCorrectlyDownscaleOutsideRande() throws Exception
	{
		assertEquals("abysmal −2", Scale.TERRIBLE.adjust(-3).getAdjective());
	}

	@Test
	public void shouldCorrectlyUpscaleFromOutsideRande() throws Exception
	{
		assertEquals(Scale.TERRIBLE, new AbysmalScale(2).adjust(+3));
	}

	@Test
	public void shouldCorrectlyDownscaleFromOutsideRande() throws Exception
	{
		assertEquals(Scale.EPIC, new LegendaryScale(2).adjust(-3));
	}

	@Test
	public void shouldRecognizeAdjectivesInRange() throws Exception
	{
		assertEquals(Scale.GREAT, Scale.findByAdjective(" gReAt "));
	}

	@Test
	public void shouldRecognizeAdjectivesOverRange() throws Exception
	{
		assertEquals(new LegendaryScale(2), Scale.findByAdjective(" lEgEndARY  +2 "));
	}

	@Test
	public void shouldRecognizeAdjectivesUnderRange() throws Exception
	{
		assertEquals(new AbysmalScale(2), Scale.findByAdjective(" abYsmAl  -2 "));
	}

}
