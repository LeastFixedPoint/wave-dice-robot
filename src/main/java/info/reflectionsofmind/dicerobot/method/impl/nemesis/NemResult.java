package info.reflectionsofmind.dicerobot.method.impl.nemesis;

import static java.util.Arrays.asList;
import info.reflectionsofmind.dicerobot.method.impl.AbstractRollResult;

import java.util.ArrayList;
import java.util.List;

public class NemResult extends AbstractRollResult<NemRequest>
{
	private final List<Integer> rolls = new ArrayList<Integer>();
	
	public NemResult(final NemRequest request)
	{
		super(request);
	}
	
	public NemResult add(final Integer... values)
	{
		this.rolls.addAll(asList(values));
		return this;
	}
	
	public List<Integer> getRolls()
	{
		return this.rolls;
	}
	
	@Override
	public int hashCode()
	{
		return this.rolls.hashCode();
	}
	
	@Override
	public boolean equals(final Object obj)
	{
		if (obj instanceof NemResult)
			return this.rolls.equals(((NemResult) obj).rolls);
		return false;
	}
}
