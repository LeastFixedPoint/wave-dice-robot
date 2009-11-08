package info.reflectionsofmind.dicerobot.method.impl;

import info.reflectionsofmind.dicerobot.method.IRollRequest;

import java.util.ArrayList;
import java.util.List;

public class MultipleRollResult<TRollResult extends MultipleRollResult<TRollResult, TRollRequest>, TRollRequest extends IRollRequest> extends AbstractRollResult<TRollRequest>
{
	private final List<RollResult> rolls = new ArrayList<RollResult>();
	
	public MultipleRollResult(final TRollRequest request)
	{
		super(request);
	}
	
	@SuppressWarnings("unchecked")
	public TRollResult add(final int dieSize, final int... results)
	{
		for (final Integer result : results)
			this.rolls.add(new RollResult(result, dieSize));
		
		return (TRollResult) this;
	}
	
	public List<RollResult> getResults()
	{
		return this.rolls;
	}
}
