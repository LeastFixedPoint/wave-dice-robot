package info.reflectionsofmind.dicerobot.method.impl;

import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;
import info.reflectionsofmind.dicerobot.method.IRollRequest;
import info.reflectionsofmind.dicerobot.method.IRollResult;
import info.reflectionsofmind.dicerobot.method.IRollRoller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class AbstractRollRoller2<TRollRequest extends IRollRequest, TRollOutput extends IRollResult<TRollRequest>> implements IRollRoller<TRollRequest, TRollOutput>
{
	private IDieRollerFactory factory;
	private final Class<TRollRequest> requestClass;
	private final Class<TRollOutput> resultClass;
	
	public AbstractRollRoller2(final Class<TRollRequest> requestClass, final Class<TRollOutput> resultClass)
	{
		this.requestClass = requestClass;
		this.resultClass = resultClass;
	}
	
	public AbstractRollRoller2<TRollRequest, TRollOutput> setFactory(final IDieRollerFactory factory)
	{
		this.factory = factory;
		return this;
	}
	
	public final TRollOutput makeRoll(final TRollRequest request) throws CannotMakeRollException
	{
		try
		{
			final Constructor<TRollOutput> constructor = this.resultClass.getConstructor(this.requestClass);
			final TRollOutput result = constructor.newInstance(request);
			doMakeRoll(request, result, createDieRoller());
			return result;
		}
		catch (final SecurityException e)
		{
			throw new CannotMakeRollException("internal failure");
		}
		catch (final NoSuchMethodException e)
		{
			throw new CannotMakeRollException("internal failure");
		}
		catch (final IllegalArgumentException e)
		{
			throw new CannotMakeRollException("internal failure");
		}
		catch (final InstantiationException e)
		{
			throw new CannotMakeRollException("internal failure");
		}
		catch (final IllegalAccessException e)
		{
			throw new CannotMakeRollException("internal failure");
		}
		catch (final InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	protected abstract void doMakeRoll(TRollRequest request, TRollOutput result, IDieRoller roller) throws CannotMakeRollException;
	
	public IDieRoller createDieRoller()
	{
		return this.factory.createDieRoller();
	}
	
	public IDieRollerFactory getFactory()
	{
		return this.factory;
	}
}
