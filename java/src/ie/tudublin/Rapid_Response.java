package ie.tudublin;

import processing.core.PApplet;
import processing.core.PImage;
import ddf.minim.AudioPlayer;
import java.util.ArrayList;
import ddf.minim.Minim;

public class Rapid_Response extends PApplet
{
    Minim minim;
    AudioPlayer music;
    AudioPlayer crash;
    AudioPlayer radio;

    ArrayList<Traffic> vehicles = new ArrayList<Traffic>();
    ArrayList<PImage> textures = new ArrayList<PImage>();

    boolean[] keys = new boolean[1024];

    static short MARGIN = 320;
    static short NUM_CARS = 5;
    static short NUM_LANES = 8;
    static short LANE_WIDE = 8;
    static short LANE_SPEED = 8;
    static float LANE_SCALE = 0.4f;
    
    int laneGap;
    int laneHigh;
    int halfWidth;
    int halfHeight;

    String status;

    int score;
    int speedometer;
    short lowSpeed, topSpeed;
    
    PImage badge;
    Player p;

    public void settings()
    {
        fullScreen(P2D);

    }   // end settings
    
    public void setup() 
    {
        surface.setResizable(true);
        noCursor();

        minim = new Minim(this);
        music = minim.loadFile("song.mp3");
        crash = minim.loadFile("crash.mp3");
        radio = minim.loadFile("radio.mp3");

        imageMode(CENTER);
        rectMode(CENTER);
        textSize(14);
        
        laneGap = height / NUM_LANES;
        laneHigh = round(laneGap * LANE_SCALE);
        
        halfWidth = width / 2;
        halfHeight = height / 2;

        badge = loadImage("badge.png");

        textures.add(loadImage("bmw.png"));
        textures.add(loadImage("volvo.png"));
        textures.add(loadImage("acura.png"));
        textures.add(loadImage("bugatti.png"));
        textures.add(loadImage("volkswagen.png"));

        for (int i = 0; i < NUM_CARS; i++)
        {
            vehicles.add(new Traffic(textures.get(0), this));

        }   // end for

        p = new Player(loadImage("cop.png"), this);

        // set cars and numbers

        reset();

    }   // end setup

    public void reset()
    {  
        music.setGain(0);
        music.rewind();

        // set default score, speed and status

        score = 0;
        speedometer = 160;
        status = "Responding";

        // set default traffic speed

        lowSpeed = 4;
        topSpeed = 8;

        // set default cop location

        p.reset();

        // set default traffic locations

        for (int i = 0; i < NUM_CARS; i++)
        {
            float gap = 0.75f * vehicles.get(0).width;
            float rightBorder = width - (MARGIN + gap);
            float leftBorder = MARGIN + gap;

            float x = map(i, 0, NUM_CARS - 1, leftBorder, rightBorder);
            vehicles.get(i).setX(x);

            vehicles.get(i).reset(textures, lowSpeed, topSpeed);

        }   // end for
    }
    
    public void draw()
    {
        music.play();

        drawMap();
        drawHUD();

        player();
        traffic();

        if(p.collision(vehicles))
        {
            music.pause();

            crash.play();
            crash.rewind();

            status = "Crashed";
            drawStatus();

            reset();
            noLoop();

        }   // end if

        if (score == 10000)
        {
            status = "Merit Awarded";
            drawStatus();

            reset();
            noLoop();

        }   // end if

        if (score == 1000 || score == 2500 || score == 5000)
        {
            lowSpeed++;
            topSpeed++;

            speedometer += 20;

            radio.play();
            radio.rewind();

        }   // end if
        
        score++;
    }

    public void player()
    {
        p.move();
        p.render();

    }   // end player

    public void traffic()
    {
        // render and move traffic vehicles

        for (int i = 0; i < NUM_CARS; i++)
        {
            if (i == 2) // middle disabled
            {
                continue;
            }
            else
            {
                vehicles.get(i).render();
                vehicles.get(i).move(textures, lowSpeed, topSpeed);

            }   // end if

        }   // end for
    }

    public void drawMap()
    {
        final int scroll = frameCount % laneGap * LANE_SPEED;

        // desert sand

        background(220, 185, 155);

        // road

        fill(105, 105, 105);
        rect(halfWidth, halfHeight, width - MARGIN * 2, height);

        // borders

        stroke(0, 0, 0);
        strokeWeight(5);
        line(MARGIN, 0, MARGIN, height);
        line(width - MARGIN, 0, width - MARGIN, height);

        // lanes
        
        strokeWeight(LANE_WIDE);
        stroke(255, 255, 64);

        for (int i = -LANE_SPEED; i != NUM_LANES; ++i) 
        {
            final int high = laneGap*i + scroll;
            line(halfWidth, high, halfWidth, high + laneHigh);

        }   // end for
        
        noStroke();

    }   // end drawMap

    public void drawHUD()
    {
        image(badge, 0.89f * width, 0.16f * height, 0.10f * width, 0.2f * height);

        fill(0, 102, 153);

        String task = "Officers require assistance on Cactus Road. It is 10 KM from your current location.";
        String distance = "Distance: " + (10000 - score) + " Metres";
        String speed = "Speed: " + speedometer + " KM/H";

        text(task, 0.1f * width, 0.16f * height, 0.16f * width, 0.2f * height);
        text(distance, 0.1f * MARGIN, 0.3f * height);
        text(speed, 0.1f * MARGIN, 0.33f * height);

    }   // end drawHUD

    public void drawStatus()
    {
        textAlign(CENTER);
        text("Status: " + status, width - 0.5f * MARGIN, 0.3f * height);
        text("Press Space to Restart", width - 0.5f * MARGIN, 0.33f * height);
        textAlign(LEFT);

    }   // end drawStatus

    boolean checkKey(int k) 
    {
        if (keys.length >= k) 
        {
            return keys[k] || keys[Character.toUpperCase(k)];

        }   // end if

        return false;

    }   // end checkKey

    public void keyPressed() 
    {
        keys[keyCode] = true;

        final int k = keyCode;
      
        if (k == ' ')
        {
            if (looping)  
            {
                music.pause();

                textAlign(CENTER);
                text("Status: " + status, width - 0.5f * MARGIN, 0.3f * height);
                text("Press Space to Continue", width - 0.5f * MARGIN, 0.33f * height);
                textAlign(LEFT);

                noLoop();
            }
            else
            {
                music.play();
                loop();

            }   // end inner if

        }   // end outer if

    }   // end keyPressed

    public void keyReleased() 
    {
        keys[keyCode] = false;

    }   // end keyReleased

}   // end My_Game