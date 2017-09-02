package xyz.derkades.skywars;

public enum Mode {
	
	SOLO,
	TEAMS;
	
	@Override
	public String toString() {
		return this.name().substring(0, 1) + this.name().substring(1).toLowerCase();
	}

}
