package com.github.kreatures.extraction;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;

/**
 * 
 * @author Manuel Barbi
 *
 */
public class Premise extends TreeMap<String, Object> implements Comparable<Premise> {

	private static final long serialVersionUID = 4995974767713423184L;

	public Premise() {};

	public Premise(String key, Object value) {
		this.put(Objects.requireNonNull(key, "key must not be null"), Objects.requireNonNull(value, "value must not be null"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int compareTo(Premise other) {
		Iterator<Entry<String, Object>> it1 = this.entrySet().iterator();
		Iterator<Entry<String, Object>> it2 = other.entrySet().iterator();

		Entry<String, Object> e1, e2;
		int result;

		while (it1.hasNext() && it2.hasNext()) {
			e1 = it1.next();
			e2 = it2.next();

			if ((result = e1.getKey().compareTo(e2.getKey())) != 0)
				return result;

			if (e1.getValue() instanceof Comparable) {
				if ((result = ((Comparable) e1.getValue()).compareTo(e2.getValue())) != 0)
					return result;
			} else {
				if ((result = e1.getValue().toString().compareTo(e2.getValue().toString())) != 0)
					return result;
			}
		}

		return Integer.compare(this.size(), other.size());

	}

	public boolean contains(Premise other) {
		if (other.size() > this.size())
			return false;

		for (Entry<String, Object> e : other.entrySet()) {
			if (!e.getValue().equals(this.get(e.getKey())))
				return false;
		}

		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (!this.isEmpty()) {
			int i = 0;
			for (Entry<String, Object> s_i : this.entrySet()) {
				if (i > 0)
					sb.append(' ').append('^').append(' ');

				sb.append(s_i.getKey());
				sb.append('_');
				sb.append(s_i.getValue());
				i++;
			}
		} else {
			sb.append('T');
		}

		return sb.toString();
	}

}
