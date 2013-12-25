package com.hasherr.dinopizzaattack.core;

import com.hasherr.dinopizzaattack.screens.ScreenManager;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Evan
 * Date: 10/31/13
 */
public class Game
{
    ScreenManager screenManager;
    InputManager inputManager;

    private static double time, lastTime, frames, lastDelta, delta;
    public static final int WIDTH = 1000, HEIGHT = 700;

    public static void main(String[] args) // Main method for running.
    {
        new Game().initGL(WIDTH, HEIGHT); // Start the game.
    }

    private void initGL(int width, int height) // Displaying screen using OpenGL.
    {
        try
        {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
        } catch (LWJGLException e)
        {
            e.printStackTrace();
            System.exit(0);
        }
        lastDelta = getTime();

        // Initial GL elements.
        glMatrixMode(GL_PROJECTION);                        // *
        glLoadIdentity();                                   // *
        glOrtho(0, width, 0, height, 1, -1);                // *
        glMatrixMode(GL_MODELVIEW);                         // *
        glEnable(GL_TEXTURE_2D);                            // *
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);  // *
        glEnable(GL_BLEND);                                 // *

        screenManager = new ScreenManager();
        inputManager = new InputManager();


        // Infinite game logic loop.
        while (!Display.isCloseRequested())
        {
            // Operations to do with timing.
            displayFps();

            time = getTime();
            delta = time - lastDelta;
            lastDelta = time;

            Display.update(); // Update the screen at a constant rate.
            glClear(GL_COLOR_BUFFER_BIT); // Clear the screen for efficient rendering of sprites and textures.

            Display.sync(60); // Cap the FPS to 60 frames.

            inputManager.pollInput(screenManager.getPlayer()); // Handle all player input for the game.

            // Render and update the game.
            screenManager.update();
            screenManager.render();
        }

        Display.destroy(); // If the user closes the game, dissolve all attributes.
    }

    /* Timer methods. */

    // Return the system time in seconds.
    public static double getTime()
    {
        return (double) Sys.getTime() / Sys.getTimerResolution();
    }


    public static double getDeltaTime()
    {
        return delta;
    }

    private void displayFps()
    {
        if (getTime() - lastTime >= 1)
        {
            Display.setTitle("Dino Pizza Attack! FPS: " + (int) frames);
            lastTime = getTime();
            frames = 0;
        }
        frames++;
    }

}

