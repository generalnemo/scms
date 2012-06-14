package org.scms.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public abstract class DefaultPhaseListener implements PhaseListener {

	private static final long serialVersionUID = -1745560559137360376L;

	private PhaseId phaseId;

	public DefaultPhaseListener(PhaseId phaseId) {
		this.phaseId = phaseId;
	}

	@Override
	public PhaseId getPhaseId() {
		return phaseId;
	}

	@Override
	public void afterPhase(PhaseEvent event) {
		// NOOP.
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		// NOOP.
	}

}
