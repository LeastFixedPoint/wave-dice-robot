package info.reflectionsofmind.dicerobot.method.impl.nemesis;

public enum DiceType
{
	STANDARD("d"), EXPERT("ed"), TRUMP("td");
	
	private final String code;
	
	private DiceType(final String code)
	{
		this.code = code;
	}
	
	public static DiceType findTypeByCode(final String code)
	{
		for (final DiceType type : values())
			if (type.code.equals(code))
				return type;
		
		return null;
	}
	
	public String getCode()
	{
		return this.code;
	}
}