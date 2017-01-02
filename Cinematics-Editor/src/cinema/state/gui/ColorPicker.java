/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.state.gui;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import java.util.ArrayList;
import org.lwjgl.opengl.Display;

/**
 *
 * @author bob
 */
public class ColorPicker extends Node {
    
    private final SimpleApplication app;
    private final ArrayList<Button> buttonList;
    
    public ColorPicker(SimpleApplication app) {
    
        this.app   = app;
        buttonList = new ArrayList();
        initPallette();
        
    }
    
    private void initPallette() {
    
        for (int i = 0; i < 9; i++) {
        
            int width  = Display.getWidth()/50;
            int height = Display.getWidth()/50;
            int butNum = i+1;
            int xSpot;
            int ySpot;
            
            Button currentButton = new Button(app, width, height);
            currentButton.setUserData("Number", i);
            ((Geometry)currentButton.getChild("Box")).getMaterial().setColor("Color", getColor(i));
            attachChild(currentButton);
            
            switch (butNum) {
                case 1:
                case 4:
                case 7:
                    xSpot = 0;
                    break;
                case 2:
                case 5:
                case 8:
                    xSpot = width*2;
                    break;
                default:
                    xSpot = width*4;
                    break;
            }
            
            switch (butNum) {
                case 1:
                case 2:
                case 3:
                    ySpot = 0;
                    break;
                case 4:
                case 5:
                case 6:
                    ySpot = height*2;
                    break;
                default:
                    ySpot = height*4;
                    break;
            }

            buttonList.add(currentButton);
            currentButton.setLocalTranslation(xSpot, ySpot, 0);
        
        }
        
    }
    
    private ColorRGBA getColor(int num) {
    
        ColorRGBA color = new ColorRGBA();
        color.set(ColorRGBA.White);
        
        switch(num) {
    
            case 1:
                color.set(ColorRGBA.Blue);
                break;
            case 2:
                color.set(ColorRGBA.Magenta);
                break;
            case 3:
                color.set(ColorRGBA.Yellow);
                break;
            case 4:
                color.set(ColorRGBA.Red);
                break;
            case 5:
                color.set(ColorRGBA.Green);
                break;
            case 6:
                color.set(ColorRGBA.Pink);
                break;
            case 7:
                color.set(ColorRGBA.Orange);
                break;
            case 8:
                color.set(ColorRGBA.Cyan);
                break;
            case 9:
                color.set(ColorRGBA.Black);
                break;
        
        }
    
        return color;
        
    }
    
    public ColorRGBA clickCheck() {
    
        for (int i = 0; i <  buttonList.size(); i++) {
        
            Button currentButton = buttonList.get(i);
            
            if (currentButton.buttonCheck()) {
            
                return getColor((int) currentButton.getUserData("Number"));
                
            }
            
        }
        
        return null;
        
    }
    
}
