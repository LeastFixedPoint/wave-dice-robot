package info.reflectionsofmind.dicerobot.method.impl.d20;

import static java.util.Arrays.asList;
import info.reflectionsofmind.dicerobot.method.impl.AbstractRollResult;
import info.reflectionsofmind.dicerobot.method.impl.sum.SumResult;

import java.util.ArrayList;
import java.util.List;

public abstract class D20Result extends AbstractRollResult<D20Request>
{
	public D20Result(final D20Request request)
	{
		super(request);
	}
	
	public static final class CharGen extends D20Result
	{
		private final List<Integer> values = new ArrayList<Integer>();
		
		public CharGen(final D20Request.CharGen request)
		{
			super(request);
		}
		
		public CharGen addValues(final Integer... values)
		{
			this.values.addAll(asList(values));
			return this;
		}
		
		public List<Integer> getValues()
		{
			return this.values;
		}
	}
	
	public static final class SumAllRolls extends D20Result
	{
		private final SumResult result;
		
		public SumAllRolls(final D20Request.SumAllRolls request, final SumResult result)
		{
			super(request);
			this.result = result;
		}
		
		public SumResult getResult()
		{
			return this.result;
		}
	}
}
