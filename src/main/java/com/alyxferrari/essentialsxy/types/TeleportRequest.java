package com.alyxferrari.essentialsxy.types;
public class TeleportRequest {
	private final int requesterEntityID;
	private final TeleportRequestType type;
	private final long millis;
	public TeleportRequest(int requesterEntityID, TeleportRequestType type) {
		this.requesterEntityID = requesterEntityID;
		this.type = type;
		millis = System.currentTimeMillis();
	}
	public int getRequesterEntityID() {
		return requesterEntityID;
	}
	public TeleportRequestType getTeleportRequestType() {
		return type;
	}
	public boolean isOvertime() {
		if (System.currentTimeMillis()-millis > 120000) {
			return true;
		}
		return false;
	}
}