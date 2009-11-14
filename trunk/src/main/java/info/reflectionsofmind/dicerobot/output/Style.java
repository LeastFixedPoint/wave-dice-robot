package info.reflectionsofmind.dicerobot.output;

public enum Style
{
	EXTRA_BOLD("style/fontFamily", "arial black, sans serif"),
	BOLD("style/fontWeight", "bold"),
	RED("style/color", "red"),
	GREEN("style/color", "green"), ;
	
	private final String name;
	private final String value;
	
	private Style(final String name, final String value)
	{
		this.name = name;
		this.value = value;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getValue()
	{
		return this.value;
	}
}
