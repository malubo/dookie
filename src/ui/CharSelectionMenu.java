package ui;

import core.Main;
import entity.Entity;

public class CharSelectionMenu extends Entity {

	private int selectedIndex = 0;
	
	private long lastMove;

	private static final int MAX_INDEX = 7;
	
	private static final int COOLDOWN = 155;

	/**
	 * List of possible selections.
	 */
	private CharSelectionMenuItem[] charSelMenuItems;

	public CharSelectionMenu() {

		lastMove = Main.getTime();
		
		charSelMenuItems = new CharSelectionMenuItem[MAX_INDEX + 1];

		// set menu width
		setWidth(charSelMenuItems.length * CharSelectionMenuItem.WIDTH);

		// set menu height
		setHeight(CharSelectionMenuItem.HEIGHT);

		// center the menu
		moveTo((Main.WIDTH - getWidth()) / 2, (Main.HEIGHT - getHeight()) / 2);

		for (int i = 0; i < MAX_INDEX + 1; i++) {
			charSelMenuItems[i] = new CharSelectionMenuItem(getX()
					+ CharSelectionMenuItem.WIDTH * i, getY(), i);
		}

		charSelMenuItems[0].select();
	}

	public void next() {

		if(lastMove + COOLDOWN > Main.getTime()){
			return;
		}
		
		charSelMenuItems[selectedIndex].deSelect();

		selectedIndex++;
	
		if (selectedIndex > MAX_INDEX) {
			selectedIndex = MAX_INDEX;
		}

		charSelMenuItems[selectedIndex].select();
		
		lastMove = Main.getTime();
	}

	public void previous() {
		
		if(lastMove + COOLDOWN > Main.getTime()){
			return;
		}
		
		charSelMenuItems[selectedIndex].deSelect();
		
		selectedIndex--;
		
		if (selectedIndex < 0) {
			selectedIndex = 0;
		}

		charSelMenuItems[selectedIndex].select();
		
		lastMove = Main.getTime();
	}
	
	public int getIndex() {
		return selectedIndex;
	}
	

	@Override
	public void update(int delta) {
		for (CharSelectionMenuItem item : charSelMenuItems) {
			item.update(delta);
		}
	}

	@Override
	public void render() {
		for (CharSelectionMenuItem item : charSelMenuItems) {
			item.render();
		}
	}

}
