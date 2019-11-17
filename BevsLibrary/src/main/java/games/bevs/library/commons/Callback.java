package games.bevs.library.commons;

@FunctionalInterface
public interface Callback<T>
{
	public abstract void done(T paramT);
}
