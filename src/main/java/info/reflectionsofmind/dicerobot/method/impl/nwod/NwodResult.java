package info.reflectionsofmind.dicerobot.method.impl.nwod;

import static java.util.Arrays.asList;
import info.reflectionsofmind.dicerobot.method.impl.AbstractRollResult;

import java.util.ArrayList;
import java.util.List;

public final class NwodResult extends AbstractRollResult<NwodRequest>
{
	private final List<Integer> results;
	
	public NwodResult(final NwodRequest request)
	{
		this(request, new ArrayList<Integer>());
	}
	
	public NwodResult(final NwodRequest request, final List<Integer> results)
	{
		super(request);
		this.results = results;
	}
	
	public NwodResult add(final Integer... results)
	{
		this.results.addAll(asList(results));
		return this;
	}
	
	public List<Integer> getResults()
	{
		return this.results;
	}
}
