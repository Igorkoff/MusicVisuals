package ie.tudublin;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

public class Player extends Vehicle 
{
    public Player(PImage img, Rapid_Response game)
    {
        super(img, game);
    }

    public void render()
    {
        game.image(image, x, y, width, height);

    }   // end render

    public void move()
    {
        if (game.checkKey(PApplet.UP))
        {
            if (y > 0.2 * game.height)
            {
                y -= speed;
            }
        }
        if (game.checkKey(PApplet.DOWN))
        {
            if (y < 0.8 * game.height)
            {
                y += speed + 3;
            }
        }
        if (game.checkKey(PApplet.LEFT))
        {
            if (x > game.MARGIN + 0.75 * width)
            {
               x -= speed; 
            }
        }
        if (game.checkKey(PApplet.RIGHT))
        {
            if (x < game.width - game.MARGIN - 0.75 * width)
            {
                x += speed;
            }
        }

    }   // end move

    public void reset()
    {
        x = game.halfWidth + width * 1.5f;
        y = game.halfHeight + height;

    }   // end reset

    public boolean collision(ArrayList<Traffic> vehicles)
    {
        float left = x - 0.5f * width;
        float right = x + 0.5f * width;

        float top = y - 0.5f * height;
        float bottom = y + 0.5f * height;

        for (int i = 0; i < game.NUM_CARS; i++)
        {
            float otherLeft = vehicles.get(i).x - 0.5f * vehicles.get(i).width;
            float otherRight = vehicles.get(i).x + 0.5f * vehicles.get(i).width;
    
            float otherTop = vehicles.get(i).y - 0.5f * vehicles.get(i).height;
            float otherBottom = vehicles.get(i).y + 0.5f * vehicles.get(i).height;

            if (right > otherLeft && left < otherRight && bottom > otherTop && top < otherBottom)
            {
                return true;

            }   // end if

        }   // end for

        return false;

    }   // end collision
    
}   // end Player
