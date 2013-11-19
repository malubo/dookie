package ui;

import core.Main;

import java.util.ArrayList;

public class ButtonMenu {

	private float x;
	private float y;

	private float width;
	private float height;

	public long lastMenuMove;
	public long delay = 220; // delay in ms between menu selection

	private int selectedMenuItemIndex;
	
	private String[] menuItems;

	private ArrayList<Button> menuButtons = new ArrayList<Button>();

	public ButtonMenu(String... menuItems) {
		this.menuItems = menuItems;
		lastMenuMove = Main.getTime();
		selectedMenuItemIndex = 0;

		width = 65;
		height = 17;
		
		x = (Main.WIDTH - width) / 2;
		y = (Main.HEIGHT - height * menuItems.length) / 2;

		for (String menuItem : menuItems) {
			menuButtons.add(new Button(x, y, width, height, menuItem));
			y += height;
		}
	}

	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}

	public void next() {
		long time = Main.getTime();
		if (time - lastMenuMove > delay) {
			if (selectedMenuItemIndex < menuItems.length - 1) {
				selectedMenuItemIndex++;
				lastMenuMove = time;
				//Resources.shot_shotgun.playAsSoundEffect(1f, 1f, false);
			}
			lastMenuMove = Main.getTime();
		}
	}

	public void previous() {
		long time = Main.getTime();
		if (time - lastMenuMove > delay) {
			if (selectedMenuItemIndex > 0) {
				selectedMenuItemIndex--;
				lastMenuMove = time;
			//	Resources.reload.playAsSoundEffect(1f, 1f, false);
			}
			lastMenuMove = Main.getTime();
		}
	}

	public void reset() {
		selectedMenuItemIndex = 0;
	}

	public String getSelectedItem() {
		return menuItems[selectedMenuItemIndex];
	}

	public void render() {
		for (Button bt : menuButtons) {
			bt.isSelected = false;
	
			if (bt.text.equals(menuItems[selectedMenuItemIndex])) {
				bt.isSelected = true;
			}
			
			bt.render();
		}
	}
}
