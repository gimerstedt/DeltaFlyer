package gg.asdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class DeltaFlyerTest {
	DeltaFlyer<TestFlyer> tested;
	List<TestFlyer> fliers;

	@Before
	public void setUp() {
		tested = new DeltaFlyer<TestFlyer>();
		fliers = getSomeFliers();
	}

	@Test
	public void emptyDeltaFlyerHasNoUpdatedElements() {
		assertTrue(tested.getUpdated().isEmpty());
	}

	@Test
	public void updatedDeltaFlyerHasSameSizeAsUpdate() {
		tested.update(fliers);
		assertEquals(fliers.size(), tested.getUpdated().size());
	}

	@Test
	public void clearEmptiesDeltaFlyer() {
		tested.update(fliers);
		assertEquals(fliers.size(), tested.getUpdated().size());
		tested.clear();
		assertEquals(0, tested.getUpdated().size());
	}

	@Test
	public void notUpdatedFliersAreNotReturned() {
		tested.update(fliers);
		assertEquals(fliers.size(), tested.getUpdated().size());
		tested.getUpdated();
		assertEquals(0, tested.getUpdated().size());
	}

	@Test
	public void onlyUpdatedFliersAreReturned() {
		tested.update(fliers);
		tested.getUpdated();
		fliers = getSomeFliers(fliers.size() / 2);
		tested.update(fliers);

		Collection<TestFlyer> updated = tested.getUpdated();

		assertEquals(fliers.size(), updated.size());
		fliers.forEach(flyer -> assertTrue(updated.contains(flyer)));
	}

	@Test
	public void noOldFliersLeftInDeltaFlyer() {
		List<TestFlyer> oldFliers = fliers;
		tested.update(oldFliers);
		tested.getUpdated();
		List<TestFlyer> newFliers = getSomeFliers();
		tested.update(newFliers);

		Collection<TestFlyer> updated = tested.getUpdated();

		assertEquals(oldFliers.size(), updated.size());
		oldFliers.forEach(old -> assertFalse(updated.contains(old)));
	}

	@Test
	public void oldFliersStillInDeltaFlyer() {
		List<TestFlyer> oldFliers = fliers;
		tested.update(oldFliers);
		tested.getUpdated();
		int amountOfNewFliers = 2000;
		List<TestFlyer> newFliers = getSomeFliers(amountOfNewFliers);
		tested.update(newFliers);

		List<TestFlyer> hopefullyOldFliers = tested.getAll().stream().filter(item -> !newFliers.contains(item)).collect(Collectors.toList());

		assertEquals(oldFliers.size() - amountOfNewFliers, hopefullyOldFliers.size());
		hopefullyOldFliers.forEach(hopeful -> assertTrue(oldFliers.contains(hopeful)));
	}

	private List<TestFlyer> getSomeFliers() {
		return getSomeFliers(30000);
	}

	private List<TestFlyer> getSomeFliers(int size) {
		List<TestFlyer> ret = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			ret.add(new TestFlyer(new Long(i)));
		}
		return ret;
	}
}
