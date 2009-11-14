package info.reflectionsofmind.dicerobot.method.impl.d6;

import static java.util.Arrays.asList;
import info.reflectionsofmind.dicerobot.method.impl.AbstractRollResult;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6Request.Die;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6Request.Token;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6Request.Wild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class D6Result extends AbstractRollResult<D6Request>
{
	private final Map<Token, List<Integer>> rolls = new HashMap<Token, List<Integer>>();
	
	public D6Result(final D6Request request)
	{
		super(request);
		
		for (final Token token : request.getTokens())
			if (token instanceof Die || token instanceof Wild)
				this.rolls.put(token, new ArrayList<Integer>());
	}
	
	public D6Result add(final Die die, final Integer... results)
	{
		this.rolls.get(die).addAll(asList(results));
		return this;
	}
	
	public D6Result add(final Wild wild, final Integer... results)
	{
		this.rolls.get(wild).addAll(asList(results));
		return this;
	}
	
	public List<Integer> getRolls(final Token token)
	{
		return this.rolls.get(token);
	}
}
