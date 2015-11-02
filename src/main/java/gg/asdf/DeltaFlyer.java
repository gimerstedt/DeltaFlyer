package gg.asdf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeltaFlyer<T extends DeltaFlyable> {
	private HashMap<Long, T> items;
	private Set<Long> updated;

	public DeltaFlyer() {
		items = new HashMap<>();
		updated = new HashSet<>();
	}

	public synchronized Collection<T> getAll() {
		return items.values();
	}

	public synchronized void update(Collection<T> items) {
		items.forEach(item -> updated.add(item.getId()));
		items.forEach(item -> this.items.put(item.getId(), item));
	}

	public synchronized Collection<T> getUpdated() {
		List<T> ret = new ArrayList<>();
		updated.forEach(id -> ret.add(items.get(id)));
		updated.clear();
		return ret;
	}

	public void clear() {
		items.clear();
		updated.clear();
	}
}
