package info.reflectionsofmind.dicerobot.method.impl.fudge;

public class Fudge extends AbstractStagedMethod<FudgeConfig>
{
	@Override
	public FudgeRoll createRoll()
	{
		return new FudgeRoll();
	}

	@Override
	public String getName()
	{
		return "Fudge";
	}
}
