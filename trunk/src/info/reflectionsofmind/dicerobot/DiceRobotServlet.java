package info.reflectionsofmind.dicerobot;

import info.reflectionsofmind.dicerobot.diceroller.RandomBasedDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.DocumentWriter;
import info.reflectionsofmind.dicerobot.method.IFormattedBufferedOutput;
import info.reflectionsofmind.dicerobot.method.IRollingMethod;
import info.reflectionsofmind.dicerobot.method.impl.ditv.DogsInTheVineyard;
import info.reflectionsofmind.dicerobot.method.impl.sum.AdditiveRoll;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.wave.api.AbstractRobotServlet;
import com.google.wave.api.ElementType;
import com.google.wave.api.Event;
import com.google.wave.api.EventType;
import com.google.wave.api.FormElement;
import com.google.wave.api.RobotMessageBundle;
import com.google.wave.api.TextView;
import com.google.wave.api.Wavelet;

public class DiceRobotServlet extends AbstractRobotServlet
{
	private static final String DEFAULT_ROLLING_METHOD_DATA_KEY = "default-rolling-method";
	private static final Pattern DIE_ROLL_PATTERN = Pattern.compile("\\[(?:(\\w+)\\:)?([^\\]=]+)\\]");
	private static final Map<String, IRollingMethod> ROLLING_METHODS = new HashMap<String, IRollingMethod>();
	
	static
	{
		final RandomBasedDieRollerFactory factory = new RandomBasedDieRollerFactory();
		registerRollingMethod("sum", new AdditiveRoll(factory));
		registerRollingMethod("ditv", new DogsInTheVineyard(factory));
	}
	
	private static void registerRollingMethod(final String id, final IRollingMethod method)
	{
		ROLLING_METHODS.put(id, method);
	}
	
	@Override
	public void processEvents(final RobotMessageBundle bundle)
	{
		final Wavelet wavelet = bundle.getWavelet();
		
		if (bundle.wasSelfAdded())
			onSelfAdded(wavelet);
		
		for (final Event event : bundle.getEvents())
			if (event.getType() == EventType.DOCUMENT_CHANGED)
				onDocumentChanged(event);
	}
	
	private void onSelfAdded(final Wavelet wavelet)
	{
		final TextView document = wavelet.appendBlip().getDocument();
		document.append("Dice Robot online.\n");
		document.append("[default:sum]");
		
		// final FormElement group = new FormElement(ElementType.RADIO_BUTTON_GROUP, "dm");
		// document.getFormView().append(group);
		//		
		// appendOption(document, "dm", "sum", "Simple roll");
		// appendOption(document, "dm", "ditv", "Dogs in the Vineyard");
		// appendOption(document, "dm", "ore", "One Roll Engine");
		// appendOption(document, "dm", "nwod", "New World of Darkness");
		// appendOption(document, "dm", "exl", "Exalted");
		//		
		// group.setDefaultValue("sum");
	}
	
	public static void appendOption(final TextView view, final String group, final String id, final String label)
	{
		view.append("\n");
		view.getFormView().append(new FormElement(ElementType.RADIO_BUTTON, "", group, "", id));
		view.getFormView().append(new FormElement(ElementType.LABEL, "", id, "", label));
	}
	
	private void onDocumentChanged(final Event event)
	{
		final TextView document = event.getBlip().getDocument();
		final Matcher matcher = DIE_ROLL_PATTERN.matcher(document.getText());
		
		while (matcher.find())
		{
			final String code = matcher.group(1);
			final String expression = matcher.group(2);
			
			final IFormattedBufferedOutput output = new DocumentWriter(document, matcher.end(2)).append(" = ");
			
			if ("default".equals(code))
			{
				setDefaultMethod(event, expression, output);
			}
			else
			{
				rollDice(event, code, expression, output);
			}
			
			output.flush();
		}
	}
	
	private void setDefaultMethod(final Event event, final String expression, final IFormattedBufferedOutput output)
	{
		if (ROLLING_METHODS.keySet().contains(expression))
		{
			event.getWavelet().setDataDocument(DEFAULT_ROLLING_METHOD_DATA_KEY, expression);
			output.append("default method set");
		}
		else
		{
			output.append("unknown method \"" + expression + "\"", "style/color", "red");
		}
	}
	
	private void rollDice(final Event event, final String code, final String roll, final IFormattedBufferedOutput output)
	{
		final String resolvedCode = (code == null) ? event.getWavelet().getDataDocument(DEFAULT_ROLLING_METHOD_DATA_KEY) : code;
		
		if (ROLLING_METHODS.containsKey(resolvedCode))
		{
			ROLLING_METHODS.get(resolvedCode).writeResult(roll, output);
		}
		else
		{
			output.append("unknown method \"" + code + "\"", "style/color", "red");
		}
	}
}
