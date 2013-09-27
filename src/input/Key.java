package input;

import java.util.ArrayList;

public class Key {

	private boolean isDown = false;
	private boolean wasDown = false;

	private ArrayList<Integer> keyCodes;

	public Key(int... keyCodes) {
		this.keyCodes = new ArrayList<Integer>(keyCodes.length);
		for (int keyCode : keyCodes) {
			this.keyCodes.add(new Integer(keyCode));
		}
	}

	public void press() {
		isDown = true;
	}

	public ArrayList<Integer> getKeyCodes() {
		return keyCodes;
	}

	public boolean isDown() {
		return isDown;
	}

	public boolean wasDown() {
		boolean result = wasDown;
		wasDown = false;
		return result;
	}

	public void release() {
		isDown = false;
		wasDown = true;
	}

	public void reset() {
		isDown = false;
		wasDown = false;
	}

}
