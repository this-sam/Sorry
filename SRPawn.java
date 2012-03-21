public class SRPawn {

	public boolean onStart;
	public boolean onHome;
	public int trackIndex;
	public int safetyIndex;
	public int player;
	
	//chain the constructors
	public SRPawn(int player){
		this(true, false, player);
	}
	
	public SRPawn(boolean onStart, boolean onHome, int player) {
		this(onStart, onHome, player, -1, -1);
	}
	
	public SRPawn(boolean onStart, boolean onHome, int player, int trackIndex, int safetyIndex) {
		this.onStart = onStart;
		this.onHome = onHome;
		this.player = player;
		this.trackIndex = trackIndex;
		this.safetyIndex = safetyIndex;
		this.player = player;
	}
	
	
	public boolean isOnStart() {
		return onStart;
	}
	
	public boolean isOnHome() {
		return onHome;
	}
	
	public int getPosition() {				//This will need to be changed if trackIndex and safetyIndex share numbers (otherwise displaying something like '3' will be confusing)
		if (safetyIndex != -1) {
			return safetyIndex;
		}
		else {
			return trackIndex;
		}
	}
	
	public int getPlayer() {
		return player;
	}
	
	public boolean ownedBy(int secondPosition) {
		if (secondPosition == trackIndex) {
			return true;						
		}
		else if (secondPosition == safetyIndex) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setOnStart(boolean onStart) {
		this.onStart = onStart;
		this.onHome = false;
		this.trackIndex = -1;
	}
	
	public void setOnHome(boolean onHome) {
		this.onHome = onHome;
		this.onStart = false;
		this.trackIndex = -1;
	}

	public void setTrackIndex(int index) {
		this.trackIndex = index;
		this.onHome = false;
		this.onStart = false;
	}

	public int getTrackIndex() {
		return this.trackIndex;
	}

	public void bump() {
		this.setOnHome(true);
	}
}