package games.bevs.library.commons.utils;

@FunctionalInterface
public interface Callback<T>
{
	public abstract void done(T paramT);
}
