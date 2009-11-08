package info.reflectionsofmind.dicerobot.method.impl.nwod;

import static java.util.Arrays.asList;
import info.reflectionsofmind.dicerobot.method.impl.AbstractRollResult;

import java.util.ArrayList;
import java.util.List;

public final class Result extends AbstractRollResult<Request>
{
	private final List<Integer> results;
	
	public Result(final Request request)
	{
		this(request, new ArrayList<Integer>());
	}
	
	public Result(final Request request, final List<Integer> results)
	{
		super(request);
		this.results = results;
	}
	
	public Result add(final Integer... results)
	{
		this.results.addAll(asList(results));
		return this;
	}
	
	public List<Integer> getResults()
	{
		return this.results;
	}
}
