package ui;

import org.newdawn.slick.Image;

import util.Resource;

public class Button {

	private float x;
	private float y;

	private float width;
	private float height;

	public String text;

	private Image background;
	private Image backgroundSelected;

	public boolean isSelected = false;

	public Button(float x, float y, float width, float height, String text) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.background = Resource.getImage("res/image/button.png").getScaledCopy(
				(int) width, (int) height);
		this.backgroundSelected = Resource.getImage("res/image/button_selected.png")
				.getScaledCopy((int) width, (int) height);
	}

	public void render() {
		if (isSelected) {
			backgroundSelected.draw(x, y);
		} else {
			background.draw(x, y);
		}
		ui.Font.renderText(text, x + (width - ui.Font.getMessageWidth(text))
				/ 2, y + (height - 8) / 2);
	}
}
