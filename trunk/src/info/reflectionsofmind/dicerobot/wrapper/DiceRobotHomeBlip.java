package info.reflectionsofmind.dicerobot.wrapper;

import com.google.wave.api.Blip;

public class DiceRobotHomeBlip extends DiceRobotBlip
{
	private final DiceRobotGadget gadget;
	
	public DiceRobotHomeBlip(final Blip blip)
	{
		super(blip);
		this.gadget = new DiceRobotGadget(getBlip().getDocument().getGadgetView().getGadget(DiceRobotGadget.URL));
	}
	
	public DiceRobotGadget getGadget()
	{
		return this.gadget;
	}
}
