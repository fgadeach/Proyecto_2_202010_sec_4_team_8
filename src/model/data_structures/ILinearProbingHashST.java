package model.data_structures;

public interface ILinearProbingHashST <K extends Comparable<K>, V>
{
	public void put(K key, V value);
	public V get(K key);
	public void delete(K key) throws Exception;
	public int size();
	public boolean isEmpty();
}