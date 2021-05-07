package ie.tudublin;

import processing.core.PImage;
import java.util.ArrayList;

public class Traffic extends Vehicle
{   
    public Traffic(PImage image, Rapid_Response game)
    {
        super(image, game);

    }   // end constructor

    public void render()
    {
        game.image(image, x, y, width, height);

    }   // end render
    
    public void move(ArrayList<PImage> textures, int low, int high)
    {
        if ((y + 0.5 * height) > game.height + height)
        {
            reset(textures, low, high);
        }
        else
        {
            y += speed;

        }   // end if

    }   // end move

    public void reset(ArrayList<PImage> textures, int low, int high)
    {
        int randomTexture = (int)game.random(0, textures.size());
        int randomSpeed = (int)game.random(low, high);
        int randomY = (int)game.random(1, 4);

        image = textures.get(randomTexture);
        speed = randomSpeed;
        y = -height * randomY;

    }   // end reset

}   // end Traffic
