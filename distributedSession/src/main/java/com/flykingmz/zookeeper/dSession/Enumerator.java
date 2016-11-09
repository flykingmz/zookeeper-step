package com.flykingmz.zookeeper.dSession;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author flyking
 */
public class Enumerator implements Enumeration {

	// ----------------------------------------------------------- Constructors

	/**
	 * Return an Enumeration over the values of the specified Collection.
	 *
	 * @param collection
	 *            Collection whose values should be enumerated
	 */
	public Enumerator(Collection collection) {

		this(collection.iterator());

	}

	/**
	 * Return an Enumeration over the values of the specified Collection.
	 *
	 * @param collection
	 *            Collection whose values should be enumerated
	 * @param clone
	 *            true to clone iterator
	 */
	public Enumerator(Collection collection, boolean clone) {

		this(collection.iterator(), clone);

	}

	/**
	 * Return an Enumeration over the values returned by the specified Iterator.
	 *
	 * @param iterator
	 *            Iterator to be wrapped
	 */
	public Enumerator(Iterator iterator) {

		super();
		this.iterator = iterator;

	}

	/**
	 * Return an Enumeration over the values returned by the specified Iterator.
	 *
	 * @param iterator
	 *            Iterator to be wrapped
	 * @param clone
	 *            true to clone iterator
	 */
	@SuppressWarnings("unchecked")
	public Enumerator(Iterator iterator, boolean clone) {

		super();
		if (!clone) {
			this.iterator = iterator;
		} else {
			List list = new ArrayList();
			while (iterator.hasNext()) {
				list.add(iterator.next());
			}
			this.iterator = list.iterator();
		}

	}

	/**
	 * Return an Enumeration over the values of the specified Map.
	 *
	 * @param map
	 *            Map whose values should be enumerated
	 */
	public Enumerator(Map map) {

		this(map.values().iterator());

	}

	/**
	 * Return an Enumeration over the values of the specified Map.
	 *
	 * @param map
	 *            Map whose values should be enumerated
	 * @param clone
	 *            true to clone iterator
	 */
	public Enumerator(Map map, boolean clone) {

		this(map.values().iterator(), clone);

	}

	// ----------------------------------------------------- Instance Variables

	/**
	 * The <code>Iterator</code> over which the <code>Enumeration</code>
	 * represented by this class actually operates.
	 */
	private Iterator iterator = null;

	// --------------------------------------------------------- Public Methods

	/**
	 * Tests if this enumeration contains more elements.
	 *
	 * @return <code>true</code> if and only if this enumeration object
	 *         contains at least one more element to provide, <code>false</code>
	 *         otherwise
	 */
	public boolean hasMoreElements() {

		return (iterator.hasNext());

	}

	/**
	 * Returns the next element of this enumeration if this enumeration has at
	 * least one more element to provide.
	 *
	 * @return the next element of this enumeration
	 *
	 * @exception NoSuchElementException
	 *                if no more elements exist
	 */
	public Object nextElement() throws NoSuchElementException {

		return (iterator.next());

	}

}
