/**
 * 
 */
package info.reflectionsofmind.dicerobot.method.impl.fudge.scale;

import static info.reflectionsofmind.dicerobot.util.StringUtil.MINUS;

public class TerribleScale implements IScale
{
	private final int rate;

	public TerribleScale(final int rate)
	{
		this.rate = rate;
	}

	@Override
	public IScale adjust(final int adjustment)
	{
		return Scale.TERRIBLE.adjust(adjustment - this.rate);
	}

	@Override
	public String getAdjective()
	{
		return Scale.TERRIBLE.getAdjective() + " " + MINUS + this.rate;
	}

	@Override
	public boolean equals(final Object obj)
	{
		return obj instanceof TerribleScale && ((TerribleScale) obj).rate == this.rate;
	}
}