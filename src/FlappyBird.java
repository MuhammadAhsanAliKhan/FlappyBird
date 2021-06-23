package flappyBird;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class FlappyBird implements ActionListener, KeyListener
{
	public final static int WIDTH=400, HEIGHT=500;
	public static FlappyBird flappyBird;
	public Renderer renderer;
	public Timer timer;
	public JFrame jframe;
	public Ball ball;
	public Pipes pipe;
	public Pipes pipe2;
	public int dx;
	public int dy;
	public boolean stop;
	public int count;
	public int i;
	public int score;
	
	public FlappyBird() throws Exception
	{
		jframe= new JFrame();
		timer= new Timer(10, this);
		
		renderer= new Renderer();
		pipe=new Pipes();
		pipe2=new Pipes();
		ball=new Ball();
		dx=0;
		dy=0;
		count=0;
		i=1;
		score=0;
		
		jframe.add(renderer);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(WIDTH, HEIGHT);
		jframe.setResizable(false);
		jframe.setVisible(true);
		jframe.addKeyListener(ball);
		jframe.addKeyListener(this);
		
		
		defaultposition();
		timer.start();
	}
	public void keyPressed(KeyEvent e) {
		//restart game
		if(stop==true)
		{
			int code=e.getKeyCode();
			if(code== KeyEvent.VK_SPACE)
			{
				timer= new Timer(10, this);
				
				defaultposition();
				score=0;
				timer.start();
				stop=false;
			}
		}
	}
	public void defaultposition()
	{
		dx=0;
		dy=0;
		i=1;
		ball.sety(HEIGHT/2);
		pipe.setH(HEIGHT/4+200);
		pipe.setW(WIDTH/4);
		pipe.setX(WIDTH);
		pipe.setY(3*HEIGHT/4-100);
		pipe2.setH(HEIGHT/4+50);
		pipe2.setW(WIDTH/4);
		pipe2.setX(WIDTH);
		pipe2.setY(0);
	}
	public void repaint(Graphics g) 
	{
		//paint background
		g.setColor(Color.yellow);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		//paint ball
		g.setColor(Color.red);
		g.fillOval(WIDTH/4, ball.gety(), 20, 20);
		//paint pipe 1 lower
		g.setColor(Color.green);
		g.fillRect(pipe.getX(), pipe.getY(), pipe.getW(), pipe.getH());
		g.setColor(Color.green);
		//paint pipe 2 upper
		g.fillRect(pipe2.getX(), pipe2.getY(), pipe2.getW(), pipe2.getH());
		//print game over
		if(stop==true)
		{
			g.setColor(Color.red);
			g.setFont(new java.awt.Font("Arial", 50, 50));
			g.drawString("Gameover", WIDTH/4, HEIGHT/2);
			g.setFont(new java.awt.Font("Arial", 10, 10));
			g.drawString("Final score:  "+ score +"", WIDTH/4, HEIGHT/2+40);
			g.drawString("Press space to start again", WIDTH/4, HEIGHT/2+60);
		}
		//print score
		g.setColor(Color.red);
		g.setFont(new java.awt.Font("Arial", 10, 10));
		g.drawString("score:  " + score +"", 3*WIDTH/4, 15);
	}
	public void actionPerformed(ActionEvent e)
	{
		renderer.repaint();
		//move pipes up or down depending randomly
		Random r= new Random();
		int x=r.nextInt(2);
		switch(x) {
		case(0):
			if(pipe.getX()==WIDTH && dy!=100)
			{
			dy=100 ;
			pipe.setY(pipe.getY()+dy);
			pipe2.setH(pipe2.getH()+dy);
			}
		break;
		case(1):
			if(pipe.getX()==WIDTH && dy!=-100)
			{
			dy=-100 ;
			pipe.setY(pipe.getY()+dy);
			pipe2.setH(pipe2.getH()+dy);
			}
		break;
		}
		//ball gravity
		ball.fall();
		//move pipes across screen
		pipe.setX(WIDTH-dx);
		pipe2.setX(WIDTH-dx);
		
		if(count<1000)
		{
			dx+=i;
		}
		else
		{
			i++;
			dx+=i;
			count=0;
		}
		
		
		//reset pipe location after it leaves screen
		if(dx>500)
		{
			dx=0;
			score+=10;
		}
		//check collision between ball and pipe
		checkCollision();
		
		count++;
		
	}
	public void checkCollision()
	{
		Rectangle b = new Rectangle(WIDTH/4,ball.gety(), 20, 20);
	    Rectangle p = new Rectangle(pipe.getX(),pipe.getY(),pipe.getW(),pipe.getH());
	    Rectangle p2 = new Rectangle(pipe2.getX(),pipe2.getY(),pipe2.getW(),pipe2.getH());
	    if (b.intersects(p)||b.intersects(p2))
	    {
	    	timer.stop(); 
	    	stop=true;
	    }
	}
	
	public static void main(String[] args) throws Exception
	{
		flappyBird =new FlappyBird();
	}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	
	
}
