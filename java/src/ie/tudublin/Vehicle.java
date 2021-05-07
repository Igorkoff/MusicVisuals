package ie.tudublin;

import processing.core.PImage;

public abstract class Vehicle 
{
    protected float x;
    protected float y;
    protected int speed;
    protected int width;
    protected int height;
    protected PImage image;
    protected Rapid_Response game;

    public Vehicle(PImage image, Rapid_Response game)
    {
        this.x = 0;
        this.y = 0;

        this.speed = 5;

        this.width = 120;
        this.height = 245;

        this.image = image;
        this.game = game;

    }   // end constructor

    public abstract void render();

    public void setX(float x)
    {
        this.x = x;
    }

    public float getX()
    {
        return x;
    }


    public void setY(float y)
    {
        this.y = y;
    }

    public float getY()
    {
        return y;
    }


    public void setSpeed(int speed) 
    {
        this.speed = speed;
    }

    public int getSpeed() 
    {
        return speed;
    }


    public void setImage(PImage img) 
    {
        this.image = img;
    }

    public PImage getImage() 
    {
        return image;
    }


    public int getWidth() 
    {
        return width;
    }

    public int getHeight() 
    {
        return height;
    }
    
}   // end Vehicle
