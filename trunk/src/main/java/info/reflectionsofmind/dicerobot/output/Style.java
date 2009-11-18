package info.reflectionsofmind.dicerobot.output;

public enum Style implements IStyle
{
	EXTRA_BOLD("xb", "style/fontFamily", "arial black, sans serif"),
	BOLD("b", "style/fontWeight", "bold"),
	RED("red", "style/color", "red"),
	GREEN("green", "style/color", "green"),
	SMALL("s", "style/fontSize", "0.8em"),
	EXTRA_SMALL("xs", "style/fontSize", "0.66em"),
	SUPER_SMALL("xxs", "style/fontSize", "0.50em"),
	STRIKEOUT("x", "style/textDecoration", "line-through"), ;
	
	private final String code;
	private final String name;
	private final String value;
	
	private Style(final String code, final String name, final String value)
	{
		this.code = code;
		this.name = name;
		this.value = value;
	}
	
	@Override
	public String getCode()
	{
		return this.code;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getValue()
	{
		return this.value;
	}
	
	public static IStyle getByCode(final String code)
	{
		for (final IStyle style : values())
			if (style.getCode().equals(code))
				return style;
		
		return null;
	}
}
