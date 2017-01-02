/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.state.gui;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

/**
 *
 * @author bob
 */
public class Button extends Node {
    
    private BitmapText text;
    private float height, width;
    private final SimpleApplication app;
    
    public Button(SimpleApplication app, float width, float height) {
        this.app    = app;
        this.width  = width;
        this.height = height;
        initGeom();
    }
    
    public void setDimensions(float x, float y) {
        width  = x;
        height = y;
    }
    
    public void setHeight(float y) {
        height = y;
    }
    
    public void setWidth(float x) {
        width = x;
    }
    
    public float getHeight() {
        return height;
    }
    
    public float getWidth() {
        return width;
    }
    
    public void setText(String newText) {
        BitmapFont font = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        text            = new BitmapText(font);
        text.setText(newText);
        attachChild(text);
        text.setLocalTranslation(0-text.getLineWidth()/2, height/2, 0);
    }
    
    private void initGeom() {
        Box      b   = new Box(width, height, 1);
        Geometry g   = new Geometry("Box", b);
        Material mat = new Material(app.getAssetManager(),"Common/MatDefs/Misc/Unshaded.j3md");
        Texture  tex = app.getAssetManager().loadTexture("cinema/state/gui/style/Metal.png");    
        mat.setTexture("ColorMap", tex);        
        mat.setColor("Color", ColorRGBA.Blue);
        g.setMaterial(mat);
        attachChild(g);
    }
    
    public boolean buttonCheck() {
        
        float x    = app.getInputManager().getCursorPosition().x;
        float y    = app.getInputManager().getCursorPosition().y;
        
        float yMax = getWorldTranslation().y + height;
        float yMin = getWorldTranslation().y - height;
        
        float xMax = getWorldTranslation().x + width;
        float xMin = getWorldTranslation().x - width;
        
        if (x >= xMin && x <= xMax && y >= yMin && y <= yMax) {
            return true;
        }
        
        return false;
        
    }
    
    public void act() {
    }
    
}
