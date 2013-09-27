package input;

import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.input.Keyboard;

public class Keys {

	public ArrayList<Key> keyList = new ArrayList<Key>();
	public HashMap<Integer, Key> keyMap = new HashMap<Integer, Key>();

	public Key up = new Key(Keyboard.KEY_UP, Keyboard.KEY_W);
	public Key down = new Key(Keyboard.KEY_DOWN, Keyboard.KEY_S);
	public Key right = new Key(Keyboard.KEY_RIGHT, Keyboard.KEY_D);
	public Key left = new Key(Keyboard.KEY_LEFT, Keyboard.KEY_A);

	public Key exit = new Key(Keyboard.KEY_ESCAPE);
	public Key confirm = new Key(28);
	public Key shoot = new Key(Keyboard.KEY_SPACE);

	public Keys() {
		keyList.add(up);
		keyList.add(down);
		keyList.add(right);
		keyList.add(left);

		keyList.add(exit);
		keyList.add(confirm);
		keyList.add(shoot);

		for (Key key : keyList) {
			for (Integer keyCode : key.getKeyCodes()) {
				keyMap.put(keyCode, key);
			}
		}
	}

	/**
	 * Sets the key as pressed.
	 * 
	 * @param keyCode KeyCode of the key.
	 */
	public void pressKey(int keyCode) {
		Key key = keyMap.get(keyCode);
		if (key != null) {
			key.press();
		}
	}

	public void releaseKey(int keyCode) {
		Key key = keyMap.get(keyCode);
		if (key != null) {
			key.release();
		}
	}

	public void updateKeys() {

		while (Keyboard.next()) {
			int eventKey = Keyboard.getEventKey();
			if (Keyboard.getEventKeyState()) {
				pressKey(eventKey);
			} else {
				releaseKey(eventKey);
			}
		}
	}
	
	
	public void resetKeys() {
		for(Key key : keyList) {
			key.reset();
		}
	}

}
