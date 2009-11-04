package info.reflectionsofmind.dicerobot;

import info.reflectionsofmind.dicerobot.diceroller.RandomBasedDieRollerFactory;
import info.reflectionsofmind.dicerobot.event.RollEvent;
import info.reflectionsofmind.dicerobot.event.SelfAddedEvent;
import info.reflectionsofmind.dicerobot.event.SetDefaultEvent;
import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;
import info.reflectionsofmind.dicerobot.exception.InvalidRollFormatException;
import info.reflectionsofmind.dicerobot.exception.RollTooLargeException;
import info.reflectionsofmind.dicerobot.method.IRollingMethod;
import info.reflectionsofmind.dicerobot.method.impl.ditv.DogsInTheVineyard;
import info.reflectionsofmind.dicerobot.method.impl.nwod.NewWorldOfDarkness;
import info.reflectionsofmind.dicerobot.method.impl.sum.AdditiveRoll;
import info.reflectionsofmind.dicerobot.wrapper.RollRequest;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@link DiceRobot} class is abstracted from Google's implementations of Wavelet, Blip and Gadget.
 */
public class DiceRobot
{
	public static final Map<String, IRollingMethod> ROLLING_METHODS = new LinkedHashMap<String, IRollingMethod>();
	
	static
	{
		final RandomBasedDieRollerFactory factory = new RandomBasedDieRollerFactory();
		ROLLING_METHODS.put("sum", new AdditiveRoll(factory));
		ROLLING_METHODS.put("ditv", new DogsInTheVineyard(factory));
		ROLLING_METHODS.put("nwod", new NewWorldOfDarkness(factory));
	}
	
	private final String defaultRollingMethod;
	
	public DiceRobot(final String defaultRollingMethod)
	{
		if (defaultRollingMethod == null)
		{
			this.defaultRollingMethod = ROLLING_METHODS.keySet().iterator().next();
		}
		else
		{
			this.defaultRollingMethod = defaultRollingMethod;
		}
	}
	
	public void onEvent(final SelfAddedEvent event)
	{
		event.getWavelet().setDefaultRollingMethod(this.defaultRollingMethod);
		event.getWavelet().createHomeBlip();
	}
	
	public void onEvent(final SetDefaultEvent event)
	{
		event.getWavelet().setDefaultRollingMethod(event.getNewDefaultMethod());
	}
	
	public void onEvent(final RollEvent event)
	{
		final RollRequest request = event.getRequest();
		request.getOutput().append(" = ");
		
		final String requestedCode = request.getMethod();
		final String defaultCode = event.getWavelet().getDefaultRollingMethod();
		final String resolvedCode = (requestedCode == null) ? defaultCode : requestedCode;
		
		if (ROLLING_METHODS.containsKey(resolvedCode))
		{
			final IRollingMethod method = ROLLING_METHODS.get(resolvedCode);
			
			try
			{
				method.writeResult(request.getRoll(), request.getOutput());
			}
			catch (final InvalidRollFormatException e)
			{
				request.getOutput().append("invalid roll format").with("style/color", "red");
			}
			catch (final RollTooLargeException e)
			{
				request.getOutput().append("you ask for too much").with("style/color", "red");
			}
			catch (final CannotMakeRollException e)
			{
				request.getOutput().append("unknown error").with("style/color", "red");
			}
		}
		else
		{
			request.getOutput().append("unknown method \"" + resolvedCode + "\"").with("style/color", "red");
		}
		
		request.getOutput().flush();
	}
}
