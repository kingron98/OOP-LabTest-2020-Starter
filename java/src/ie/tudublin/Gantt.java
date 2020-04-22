package ie.tudublin;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

public class Gantt extends PApplet
{	
	ArrayList<Task> tasks = new ArrayList<Task>();

	float border;
	float value;
	
	public void settings()
	{
		size(800, 600);
		
		border = height * 0.1f;
	}

	public void loadTasks()
	{
		Table t = loadTable("tasks.csv", "header");
		for(TableRow tr : t.rows())
		{
			Task ts = new Task(tr);
			tasks.add(ts);
		}
	}

	public void printTasks()
	{
		for(Task ts : tasks)
		{
			println(ts);
		}
	}
	
	public void displayTasks()
	{
		for(int i = 1; i <= 30; i++)
		{
			float x = map(i, 1, 30, 150, width - 50);
			int value = 100;
			if(i % 2 == 0)
			{
				value = 50;
			}
			stroke(value);
			strokeWeight(value / 50);
			fill(255);
			textAlign(PApplet.CENTER, PApplet.CENTER);
			textSize(15);
			text(i, x, border -20);
			line(x, border, x, height - 50);
		}

		for(int i = 0; i < tasks.size(); i++)
		{
			float y = map(i, 0, tasks.size(), border + 50, height - 100);

			float start = tasks.get(i).getStart();
			float end = tasks.get(i).getEnd();

			float s = map(start, 1, 30, 150, width - 50);
			float e = map(end, 1, 30, 150, width - 50);

			float c = 255 / tasks.size();

			fill(255);
			textAlign(PApplet.CENTER, PApplet.CENTER);
			textSize(15);
			text(tasks.get(i).getTask(), width * 0.1f, y);
			float test;
			noStroke();
			fill(i * c, 255, 255); 
			if(mousePressed && (mouseX >= (e + s) * 0.5f) && ((mouseX < e) || (mouseX < width -50))
				&& (mouseY > y-20) && (mouseY < y + 20))
			{

				rect(s, y - 20, value - 150, 40, 7);
			}
			else
			{
				rect(s, y - 20, (e - s), 40, 7);
			}

		}
	}

	public void mousePressed()
	{
		// println("Mouse pressed");
		value = mouseX;
	}

	public void mouseDragged()
	{
		// println("Mouse dragged");
		if(value < 750 && value > 150)
		{
			value = mouseX;
		}
	}
	
	public void setup() 
	{
		loadTasks();
		printTasks();
		colorMode(HSB);
	}
	
	public void draw()
	{			
		background(0);
		displayTasks();
	}
}
