# Music Visualiser Project

Name: Igor Alekhnovych

Student Number: D18130122

# Description of the assignment

I have always been a big fan of Need for Speed games. My first video game was Need for Speed 3: Hot Pursuit that came out in 1998 and it is still my favourite racing game even today. In this game you can be either a cop or a racer, but I always wanted to be a cop. 

Apart from stunning graphics for that time, the game had an amazing soundtrack by Rom Di Prisco, Saki Kaskas and Matt Ragan. Every track had its own unique soundtrack. The one I remember the most is a track located called Redrock Ridge, which is somewhere in the USA. That's how I got an inspiration for my assignment: I decided to make a racing game.

![A different image](https://gamefabrique.com/screenshots2/ps/need-for-speed-3-24.big.jpg)

My video game is a classic top-down 2D racing game, such games have been popular since the very first video game consoles, but some people still enjoy them even today in their smartphones while commuting or just to spend some time. The idea here is rather simple: you are a police officer and have received a request for backup from a group of other cops 10 kilometres from your current location. You have to get there as soon as possible without damaging any traffic cars. If you crash into something - game over.


# Instructions

- Open Repo in VS Code
- Press F5 (Or Function + F5)
- Game Starts
- Arrow Keys to Steer
- Space to Pause or Restart

# How it works

There are 4 classes: Rapid_Response, Vehicle, Traffic and Player.

First of all, I decided to implement vehicles. Since there are 2 different types of cars: police car that belongs to the player and other traffic cars, I had to make an abstract class that has all the necessary fields, such as coordinates, dimensions, speed, and image.

```Java
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
    	}
	
	...
}
```

Two other classes (Player and Traffic) inherit from Vehicle and implement an abstract method:

```Java
    public void render()
    {
        game.image(image, x, y, width, height);

    } 
```

Player is controlled via the arrow keys and can go wherever, but within the screen. It is spawned at a fixed position at the right bottom of the screen. These methods are very simple.

Player has a method which is used to check for the collisions. I had to draw quite a few rectangles before that, but managed to come up with a rather simple solution. It is not perfect, because I am using transparent images and the car is a bit smaller than its hitbox.

```Java
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
```

On the other hand, traffic vehicles are fixed in their rows and after they spawn somewhere above the screen they go towards the bottom of the screen. Once they reach end of the screen, they spawn above the screen and so on. I am using random to choose from a multiple car images, also random is responsible for the car's speed and the car's initial position above the screen when it is reset.

```Java
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
```

# What I am most proud of in the assignment

I cannot say that there is any thing in particular that I am most proud of. I am proud of my whole project. It was a great experience to develop a video game, even such a simple one. I faced multiple issues and had to think of workarounds for them. Some of the ideas did remain on paper, because they were rather difficult to implement at this point: I had to scrap an idea of a mode which represents a self-driving car capable of avoiding obstacles and traffic. But I am proud of what I did so far, after thorough testing the game does not have any major bugs and I already have some plans for the new features. For example, better collision detection and better traffic AI.

# Video

[![YouTube](http://img.youtube.com/vi/1vQvvt9M_u4/0.jpg)](https://youtu.be/1vQvvt9M_u4)


