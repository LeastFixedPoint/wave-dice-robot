package info.reflectionsofmind.dicerobot.wrapper;

import info.reflectionsofmind.dicerobot.method.DocumentWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.wave.api.Blip;
import com.google.wave.api.Gadget;
import com.google.wave.api.TextView;

public class DiceRobotBlip
{
	private static final Pattern DIE_ROLL_PATTERN = Pattern.compile("\\[(?:(\\w+)\\:)?([^\\]=]+)\\]");
	
	private final Blip blip;
	
	public DiceRobotBlip(final Blip blip)
	{
		this.blip = blip;
	}
	
	protected Blip getBlip()
	{
		return this.blip;
	}
	
	/** Finds all unprocessed roll requests inside the blip. */
	public List<RollRequest> getRollRequests()
	{
		final TextView document = getBlip().getDocument();
		final Matcher matcher = DIE_ROLL_PATTERN.matcher(document.getText());
		final List<RollRequest> requests = new ArrayList<RollRequest>();
		
		while (matcher.find())
		{
			final String code = matcher.group(1);
			final String expression = matcher.group(2);
			requests.add(new RollRequest(new DocumentWriter(document, matcher.end(2)), code, expression));
		}
		
		return requests;
	}
	
	public static DiceRobotBlip create(final Blip blip)
	{
		final Gadget gadget = blip.getDocument().getGadgetView().getGadget(DiceRobotGadget.URL);
		
		if (gadget != null)
		{
			return new DiceRobotHomeBlip(blip);
		}
		else
		{
			return new DiceRobotBlip(blip);
		}
	}
}
