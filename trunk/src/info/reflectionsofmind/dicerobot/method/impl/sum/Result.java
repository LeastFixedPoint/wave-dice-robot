package info.reflectionsofmind.dicerobot.method.impl.sum;

import static java.util.Arrays.asList;
import info.reflectionsofmind.dicerobot.method.impl.AbstractRollResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In this class roll results are split by roll group. E.g. if roll was 2d6+5+3d8,
 * results will be split like [2,5] and [8,3,4] - and results for "5" will not be
 * stored anywhere, because there is no roll. Get underlying request for those.
 */
public class Result extends AbstractRollResult<Request>
{
	private final Map<Request.Roll, List<Integer>> results = new HashMap<Request.Roll, List<Integer>>();
	
	public Result(final Request request)
	{
		super(request);
	}
	
	public Result add(final Request.Roll roll, final Integer... results)
	{
		this.results.put(roll, asList(results));
		return this;
	}
	
	/** Returns results from given roll. */
	public List<Integer> getResults(final Request.Roll roll)
	{
		return this.results.get(roll);
	}
	
	/** An utility method, mainly for testing. */
	public List<Integer> getResults()
	{
		final List<Integer> results = new ArrayList<Integer>();
		
		for (final Request.Token token : getRequest().getTokens())
		{
			if (token instanceof Request.Number)
			{
				results.add(((Request.Number) token).getNumber());
			}
			else
			{
				results.addAll(this.results.get(token));
			}
		}
		
		return results;
	}
}
