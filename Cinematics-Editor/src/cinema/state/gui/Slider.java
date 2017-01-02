/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.state.gui;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import org.lwjgl.opengl.Display;

/**
 *
 * @author bob
 */
public class Slider extends Node {
    
    private       Button  slider;
    private       Node    background;
    private final float   width, height;
    private       float   min, max;
    private       boolean isClicked;
    private final SimpleApplication app;
    
    public Slider(SimpleApplication app) {
        width    = Display.getWidth()/10;
        height   = Display.getHeight()/30;
        min      = 0;
        max      = 30;
        this.app = app;
        initSlider();
        initBackground();
    }
    
    private void initSlider() {
        
        slider = new Button(app, width/15, height) {
            @Override
            public void act() {
                System.out.println("Black");
            }
        };
        
        slider.addControl(new SliderControl(app, this));
        attachChild(slider);
        
    }
    
    private void initBackground() {
        background   = new Node();
        Box      b   = new Box(width, height, 0);
        Geometry g   = new Geometry("Box", b);
        Material mat = new Material(app.getAssetManager(),"Common/MatDefs/Misc/Unshaded.j3md");
        Texture  tex  = app.getAssetManager().loadTexture("cinema/state/gui/style/Metal.png");    
        mat.setTexture("ColorMap", tex);        
        mat.setColor("Color", ColorRGBA.Green);
        g.setMaterial(mat);
        background.attachChild(g);
        background.setLocalTranslation(0,0,-.5f);
        attachChild(background);
    }
    
    public void setMin(float newVal) {
        min=newVal;
    }
    
    public void setMax(float newVal) {
        max = newVal;
    }
    
    public float getWidth() {
        return width;
    }
    
    public float getHeight() {
        return height;
    }
    
    public boolean isClicked() {
        return isClicked;
    }
    
    public void clickCheck() {
        isClicked = slider.buttonCheck();
        slider.getControl(SliderControl.class).setEnabled(isClicked);
        app.getFlyByCamera().setEnabled(!isClicked);
    }
    
    public void release() {
        isClicked = false;
        slider.getControl(SliderControl.class).setEnabled(isClicked);
        app.getFlyByCamera().setEnabled(!isClicked);
    }
    
    public float getCurrentValue() {
        float slideMax   = slider.getControl(SliderControl.class).getMaxVal();
        float slideCur   = slider.getControl(SliderControl.class).getCurrentVal();
        float slideRatio = slideCur/slideMax;
        float currentVal = slideRatio*max;
        
        if (currentVal == 0) {
            currentVal += min;
        }
        
        return currentVal;
        
    }
    
}
