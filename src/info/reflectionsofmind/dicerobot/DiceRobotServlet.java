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
import com.google.wave.api.Event;
import com.google.wave.api.EventType;
import com.google.wave.api.RobotMessageBundle;
import com.google.wave.api.TextView;
import com.google.wave.api.Wavelet;

public class DiceRobotServlet extends AbstractRobotServlet
{
	private static final Pattern DIE_ROLL_PATTERN = Pattern.compile("\\[(?:(\\w+)\\:)?([^\\]=]+)\\]");
	private static final Map<String, IRollingMethod> ROLLING_METHODS = new HashMap<String, IRollingMethod>();
	
	static
	{
		final RandomBasedDieRollerFactory factory = new RandomBasedDieRollerFactory();
		registerRollingMethod("sum", new AdditiveRoll(factory));
		registerRollingMethod("ditv", new DogsInTheVineyard(factory));
	}
	
	private final String defaultMethod = "sum";
	
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
		document.append("Dice Robot online.");
		
		// Commented until a way to pre-select option is available
		//		
		// document.append("\nSelect default rolling method:");
		//		
		// document.getFormView().append(new
		// FormElement(ElementType.RADIO_BUTTON_GROUP, "default"));
		//		
		// for (final String code : ROLLING_METHODS.keySet())
		// {
		// document.append("\n");
		//			
		// final FormElement radio = new FormElement(ElementType.RADIO_BUTTON, "", "default", "", code);
		// if (code.equals(this.defaultMethod)) radio.setProperty("checked", "checked");
		//			
		// document.getFormView().append(radio);
		// document.getFormView().append(new FormElement(ElementType.LABEL, code, "Option [" + code + "]"));
		// }
	}
	
	private void onDocumentChanged(final Event event)
	{
		final TextView document = event.getBlip().getDocument();
		final String text = document.getText();
		
		final Matcher matcher = DIE_ROLL_PATTERN.matcher(text);
		
		while (matcher.find())
		{
			final String code = matcher.group(1);
			final String roll = matcher.group(2);
			
			final IRollingMethod method = ROLLING_METHODS.get(code == null ? this.defaultMethod : code);
			final IFormattedBufferedOutput output = new DocumentWriter(document, matcher.end(2)).append(" = ");
			
			if (method != null)
			{
				method.writeResult(roll, output);
			}
			else
			{
				output.append("unknown method \"" + code + "\"", "style/color", "red");
			}
			
			output.flush();
		}
	}
}
