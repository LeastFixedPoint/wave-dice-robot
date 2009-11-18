package info.reflectionsofmind.dicerobot.method.impl.nemesis;

import static java.util.Collections.reverseOrder;
import static java.util.Collections.sort;
import info.reflectionsofmind.dicerobot.exception.CannotRenderRollException;
import info.reflectionsofmind.dicerobot.exception.output.OutputException;
import info.reflectionsofmind.dicerobot.method.IRollWriter;
import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;
import info.reflectionsofmind.dicerobot.output.JoiningWriter;
import info.reflectionsofmind.dicerobot.output.Style;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NemWriter implements IRollWriter<NemResult>
{
	public void render(final IFormattedBufferedOutput output, final NemResult result) throws CannotRenderRollException, OutputException
	{
		final JoiningWriter joiner = new JoiningWriter(output, " + ");
		
		for (final WxH set : getSets(result))
			joiner.append(set.getWidth() + "x" + set.getHeight()).with(Style.EXTRA_BOLD);
		
		if (result.getRequest().getTrumpDice() > 0)
			joiner.append(result.getRequest().getTrumpDice() + DiceType.TRUMP.getCode()).with(Style.EXTRA_BOLD);
		
		for (final Integer value : getLeftovers(result))
			joiner.append(value);
		
		if (joiner.isEmpty())
			joiner.append("nothing");
	}
	
	private List<Integer> getLeftovers(final NemResult result)
	{
		final int[] counts = getCounts(result);
		final List<Integer> leftovers = new ArrayList<Integer>();
		
		for (int height = 1; height <= 10; ++height)
		{
			final int width = counts[height - 1];
			if (width == 1) leftovers.add(height);
		}
		
		sort(leftovers, reverseOrder());
		
		return leftovers;
	}
	
	private List<WxH> getSets(final NemResult result)
	{
		final int[] counts = getCounts(result);
		final List<WxH> sets = new ArrayList<WxH>();
		
		for (int height = 1; height <= 10; ++height)
		{
			final int width = counts[height - 1];
			if (width > 1) sets.add(new WxH(width, height));
		}
		
		sort(sets, new WxHComparator());
		return sets;
	}
	
	private int[] getCounts(final NemResult result)
	{
		final int[] counts = new int[10];
		
		for (final int roll : result.getRolls())
			++counts[roll - 1];
		
		for (final int value : result.getRequest().getExpertDice())
			++counts[value - 1];
		
		return counts;
	}
	
	public static final class WxH
	{
		private final int width;
		private final int height;
		
		public WxH(final int width, final int height)
		{
			this.width = width;
			this.height = height;
		}
		
		public int getWidth()
		{
			return this.width;
		}
		
		public int getHeight()
		{
			return this.height;
		}
		
		@Override
		public int hashCode()
		{
			return this.width * 31 + this.height;
		}
		
		@Override
		public boolean equals(final Object obj)
		{
			if (obj instanceof WxH)
				return ((WxH) obj).width == this.width && ((WxH) obj).height == this.height;
			
			return false;
		}
	}
	
	public class WxHComparator implements Comparator<WxH>
	{
		public int compare(final int i1, final int i2)
		{
			return Integer.valueOf(i1).compareTo(i2);
		}
		
		public int compare(final WxH o1, final WxH o2)
		{
			return -Integer.valueOf(o1.height).compareTo(o2.height);
		}
	}
}
