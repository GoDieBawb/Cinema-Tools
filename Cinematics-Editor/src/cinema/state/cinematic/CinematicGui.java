/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.state.cinematic;

import cinema.AppManager;
import cinema.state.gui.Button;
import cinema.state.gui.Gui;
import cinema.state.select.SelectState;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import java.util.ArrayList;
import org.lwjgl.opengl.Display;

/**
 *
 * @author bob
 */
public class CinematicGui extends Gui {
    
    private final ArrayList<Button> buttonList;
    private       boolean           isVisible;
    
    public CinematicGui(SimpleApplication app) {
        super(app);
        buttonList = new ArrayList();
        isVisible = true;
        createFrame();
        createButtons();
    }
    
    private void createFrame() {
        createHorizontalBar();
    }
    
    private void createHorizontalBar() {
        Node     hbar = new Node();
        Box      b    = new Box(Display.getWidth(), Display.getHeight()/3, 1);
        Geometry g    = new Geometry("Box", b);
        Material mat  = new Material(app.getAssetManager(),"Common/MatDefs/Misc/Unshaded.j3md");
        Texture  tex  = app.getAssetManager().loadTexture("cinema/state/gui/style/Metal.png");    
        mat.setTexture("ColorMap", tex);
        mat.setColor("Color", ColorRGBA.Red);
        g.setMaterial(mat);
        hbar.attachChild(g);
        app.getGuiNode().attachChild(hbar);
    }
    
    private void createButtons() {
        createBackButton();
    }
    
    private void createBackButton() {
        
        float width  = Display.getWidth()/10;
        float height = Display.getHeight()/20;
        
        Button backButton = new Button(app, width, height) {
        
            @Override
            public void act() {
                remove();
                app.getStateManager().getState(AppManager.class).setCurrentState(new SelectState(app));
            }
            
        };
        
        app.getGuiNode().attachChild(backButton);
        backButton.setLocalTranslation(Display.getWidth()/12 + Display.getWidth()/24, Display.getHeight()/5f - Display.getHeight()/15f, 0);
        backButton.setText("Back");
        buttonList.add(backButton);
        
    }    
    
    private void remove() {
        app.getGuiNode().detachAllChildren();
        buttonList.clear();
    }
    
    @Override
    public void onClickRelease() {
    
        for (int i = 0; i < buttonList.size(); i++) {
        
            Button currentButton = buttonList.get(i);
            
            if (currentButton.buttonCheck()) {
                currentButton.act();
            }
            
        }
        
    }

    @Override 
    public void onSpaceRelease() {
    
        if (isVisible) {
            isVisible = false;
            remove();
        }
        
        else {
            isVisible = true;
            createFrame();
            createButtons();
        }
        
    }
    
}
