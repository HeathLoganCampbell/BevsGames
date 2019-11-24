package games.bevs.library.commons.manager;

import java.util.HashMap;

public class Manager<K, V>
{
	private HashMap<K, V> entities;
	
	public Manager()
	{
		this.entities = new HashMap<>();
	}
	
	public void register(K k, V v)
	{
		this.entities.put(k, v);
	}
	
	protected V get(K k)
	{
		return this.entities.get(k);
	}
	
	public void unregister(K k)
	{
		this.entities.remove(k);
	}
}