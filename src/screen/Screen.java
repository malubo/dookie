package screen;

public interface Screen {
	public void init();

	public void enter();

	public void update(int delta);

	public void render();

	public void exit();

	public void destroy();
	
	public int getID();
	
}
