package info.reflectionsofmind.dicerobot.method.impl.sum;

import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.IFormattedBufferedOutput;
import info.reflectionsofmind.dicerobot.method.IRollingMethod;
import info.reflectionsofmind.parser.Grammar;
import info.reflectionsofmind.parser.Matchers;
import info.reflectionsofmind.parser.ResultTree;
import info.reflectionsofmind.parser.exception.GrammarParsingException;
import info.reflectionsofmind.parser.matcher.Matcher;
import info.reflectionsofmind.parser.node.NamedNode;
import info.reflectionsofmind.util.Files;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

public class AdditiveRoll implements IRollingMethod
{
	private static Matcher GRAMMAR;
	private final IDieRollerFactory factory;
	
	public AdditiveRoll(final IDieRollerFactory factory)
	{
		this.factory = factory;
	}
	
	public void writeResult(final String input, final IFormattedBufferedOutput output)
	{
		final List<ResultTree> results = Matchers.fullMatch(GRAMMAR, input);
		
		if (results.isEmpty())
		{
			output.append("invalid roll", "style/color", "red");
			return;
		}
		
		final ResultTree result = results.get(0);
		final NamedNode expression = (NamedNode) result.root;
		
		final RollInProgress roll = new RollInProgress(output).appendExpression(expression, +1);
		
		if (roll.getCount() > 1)
		{
			output.append(" = ").append(roll.getTotal());
		}
	}
	
	private final class RollInProgress implements IFormattedBufferedOutput
	{
		private final IDieRoller roller = AdditiveRoll.this.factory.createDieRoller();
		private final IFormattedBufferedOutput output;
		private int total = 0;
		private int count = 0;
		
		public RollInProgress(final IFormattedBufferedOutput output)
		{
			this.output = output;
		}
		
		private RollInProgress appendExpression(final NamedNode expression, final int multiplier)
		{
			final NamedNode tokenPlusMinus = expression.get(0);
			
			if (tokenPlusMinus.is("token"))
			{
				appendToken(tokenPlusMinus, multiplier);
			}
			else if (tokenPlusMinus.is("plus"))
			{
				appendToken(tokenPlusMinus.get(0), multiplier).append(" + ").appendExpression(tokenPlusMinus.get(1), +1);
			}
			else if (tokenPlusMinus.is("minus"))
			{
				appendToken(tokenPlusMinus.get(0), multiplier).append(" - ").appendExpression(tokenPlusMinus.get(1), -1);
			}
			else
			{
				append("?");
			}
			
			return this;
		}
		
		private RollInProgress appendToken(final NamedNode token, final int multiplier)
		{
			final NamedNode numberRoll = token.get(0);
			
			if (numberRoll.is("number"))
			{
				final String text = numberRoll.getText();
				append(text);
				this.total += Integer.parseInt(text) * multiplier;
			}
			else if (numberRoll.is("roll"))
			{
				final int count = Integer.parseInt(numberRoll.get(0).getText());
				final int dieSize = Integer.parseInt(numberRoll.get(1).getText());
				
				int sum = 0;
				for (int i = 0; i < count; i++)
					sum += this.roller.roll(dieSize);
				
				append(sum);
				this.total += sum * multiplier;
			}
			else
			{
				append("?");
			}
			
			this.count++;
			
			return this;
		}
		
		public RollInProgress append(final Object object, final String annotation, final String value)
		{
			this.output.append(object, annotation, value);
			return this;
		}
		
		public RollInProgress append(final Object object)
		{
			this.output.append(object);
			return this;
		}
		
		public void flush()
		{
			this.output.flush();
		}
		
		public int getTotal()
		{
			return this.total;
		}
		
		public int getCount()
		{
			return this.count;
		}
	}
	
	static
	{
		try
		{
			final String grammarFilePath = "AdditiveRoll.grammar";
			final InputStream stream = AdditiveRoll.class.getResourceAsStream(grammarFilePath);
			final String string = Files.readAsString(stream);
			GRAMMAR = Grammar.generate(string, "expression");
		}
		catch (final IOException e)
		{
			Logger.getAnonymousLogger().severe("Cannot read arithmetic grammar");
			e.printStackTrace();
		}
		catch (final GrammarParsingException e)
		{
			Logger.getAnonymousLogger().severe("Cannot parse arithmetic grammar");
			e.printStackTrace();
		}
	}
}
