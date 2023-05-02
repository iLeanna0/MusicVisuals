package D22127059;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PShape;


public class Circle extends PApplet
{
    Minim minim;
    AudioPlayer ap;
    AudioInput ai;
    AudioBuffer ab;

    int mode = 0;

    float y = 0;
    float smoothedY = 0;
    float smoothedAmplitude = 0;
    PShape snare;

    public void settings()
    {
        size(1024, 1000, P3D);
    }

    public void setup()
    {
        minim = new Minim(this);
        ap = minim.loadFile("chucky.mp3", 1024);
        ap.play();
        ab = ap.mix;
        y = height / 2;
        smoothedY = y; 
        snare = loadShape("snare.obj");
        snare.scale(4);
        
    }

    float off = 0;

    float lerpedBuffer[] = new float[1024];

    public void draw()
{
    float halfH = height / 2;
    float average = 0;
    float sum = 0;
    off += 1;

    for(int i = 0 ; i < ab.size() ; i ++)
    {
        sum += abs(ab.get(i));
        lerpedBuffer[i] = lerp(lerpedBuffer[i], ab.get(i), 0.1f);
    }
    average = sum / (float) ab.size();

    smoothedAmplitude = lerp(smoothedAmplitude, average, 0.1f);

    float cx = width / 2;
    float cy = height / 2;

    background(0);

    stroke(255, 255);
    strokeWeight(10);
    float r = halfH * 0.5f;
    float t = TWO_PI / ab.size();

    beginShape();
    for(int i = 0 ; i < ab.size() ; i ++)
    {
        float amplitude = lerpedBuffer[i] * halfH * 1.0f;
        float x = cx + cos(t * i) * (r - amplitude); 
        float y = cy + sin(t * i) * (r - amplitude); 
        vertex(x, y);
    }

    endShape(CLOSE); 

    stroke(74); //outer grey ring
    strokeWeight(15);
    float r2 = halfH * 0.5f;

    
    beginShape();
    for(int i = 0 ; i < ab.size() ; i ++)
    {
        float amplitude = lerpedBuffer[i] * halfH * .0f;
        float x = cx + cos(t * i) * (r2 - amplitude);
        float y = cy + sin(t * i) * (r2 - amplitude);
        vertex(x, y);
    }
    endShape(CLOSE);  

        lights();
        noStroke();
        
        pushMatrix();
        translate(100, 100);
        rotateX(smoothedAmplitude * 10);
        rotateZ(smoothedAmplitude * 15);
       
        shape(snare);
        popMatrix();

        pushMatrix();
        translate(width - 100, 100);
        rotateX(smoothedAmplitude * 15);
        rotateZ(smoothedAmplitude * 5);
       
        shape(snare);
        popMatrix();

        pushMatrix();
        translate(100, height - 100);
        rotateX(smoothedAmplitude * 5);
        rotateZ(smoothedAmplitude * 20);
       
        shape(snare);
        popMatrix();

        pushMatrix();
        translate(width - 100, height - 100);
        rotateX(smoothedAmplitude * 20);
        rotateZ(smoothedAmplitude * 10);
        
        shape(snare);
        popMatrix();
    }
}

