package info.reflectionsofmind.dicerobot.wrapper;

import com.google.wave.api.Gadget;

public class DiceRobotGadget
{
	public static final String URL = "http://dice-y.appspot.com/gadget.jsp";
	public static final String DEFAULT_ROLLING_METHOD_FIELD = "defaultRollingMethod";
	
	private final Gadget gadget;
	
	public DiceRobotGadget(final Gadget gadget)
	{
		this.gadget = gadget;
	}
	
	public DiceRobotGadget()
	{
		this(new Gadget(URL));
	}
	
	public String getDefaultRollingMethod()
	{
		return this.gadget.getField(DEFAULT_ROLLING_METHOD_FIELD);
	}
	
	public Gadget getGadget()
	{
		return this.gadget;
	}
}
