
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Ball implements KeyListener
{
	public int y;
	
	
	public void sety(int y)
	{
		this.y=y;
	}
	public int gety()
	{
		return y;
	}
	public void moveup()
	{
		y-=60;
	}
	public void fall()
	{
		y+=2;
	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		
		int code=e.getKeyCode();
		if(code== KeyEvent.VK_SPACE)
		{
			moveup();
		}
		
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
}
