package unsw.venues;
/**
 * Saving request size detail
 * @author z5158229
 *
 */
public class Size {
	
	private int  large;
	private int medium;
	private int small;
	
	public Size(int small, int medium, int large) {
		this.small = small;
		this.medium = medium;
		this.large = large;
	}
	
	public int getLarge() {
		return large;
	}
	
	public int getMedium() {
		return medium;
	}

	public int getSmall() {
		return small;
	}
	
	public void setLarge(int large) {
		this.large = large;
	}

	public void setMedium(int medium) {
		this.medium = medium;
	}

	public void setSmall(int small) {
		this.small = small;
	}

	public void removeLarge() {
		if(this.large==0) return;
		this.large--;
	}
	
	public void removeMedium() {
		if(this.medium==0) return;
		this.medium--;
	}
	
	public void removeSmall() {
		if(this.small==0) return;
		this.small--;
	}
	
}
