package gg.asdf;

import java.util.UUID;

public class TestFlyer implements DeltaFlyable {
	private Long id;
	private String rand;

	public TestFlyer(Long id) {
		this.id = id;
		rand = UUID.randomUUID().toString();
	}

	@Override
	public Long getId() {
		return id;
	}

	public String getRand() {
		return rand;
	}
}
