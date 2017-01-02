/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.state.select;

import cinema.AppManager;
import cinema.state.cinematic.CinematicState;
import cinema.state.filter.FilterState;
import cinema.state.gui.Button;
import cinema.state.gui.Gui;
import cinema.state.light.LightState;
import cinema.state.sky.SkyState;
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
public class SelectGui extends Gui {
    
    private final ArrayList<Button> buttonList;
    private final float             buttonHeight = Display.getHeight()/12;
    private final float             buttonWidth  = Display.getWidth()/12;
    private final float             space        = Display.getWidth()/6.5f;  
    
    public SelectGui(SimpleApplication app) {
        super(app);
        buttonList = new ArrayList();
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
        createLightButton();
        createCinemaButton();
        createFilterButton();
        createSkyButton();
    }
    
    private void createLightButton() {
    
        float  count       = buttonList.size();
        float  hpos        = (space + buttonWidth)*count + space;        
        Button lightButton = new Button(app, buttonWidth, buttonHeight) {
        
            @Override
            public void act() {
                remove();
                app.getStateManager().getState(AppManager.class).setCurrentState(new LightState(app));
            }
        
        };
        
        app.getGuiNode().attachChild(lightButton);
        lightButton.setLocalTranslation(hpos, Display.getHeight()/5, 0);
        lightButton.setText("Light");
        buttonList.add(lightButton);
        
    }    
    
    private void createCinemaButton() {
    
        float  count        = buttonList.size();
        float  hpos         = (space + buttonWidth)*count + space;       
        Button cinemaButton = new Button(app, buttonWidth, buttonHeight) {
        
            @Override
            public void act() {
                remove();
                app.getStateManager().getState(AppManager.class).setCurrentState(new CinematicState(app));
            }
        
        };
        
        app.getGuiNode().attachChild(cinemaButton);
        cinemaButton.setLocalTranslation(hpos, Display.getHeight()/5, 0);
        cinemaButton.setText("Cinema");
        buttonList.add(cinemaButton);
        
    }    
    
    private void createFilterButton() {
    
        float  count        = buttonList.size();
        float  hpos         = (space + buttonWidth)*count + space;       
        
        Button filterButton = new Button(app, buttonWidth, buttonHeight) {
        
            @Override
            public void act() {
                remove();
                app.getStateManager().getState(AppManager.class).setCurrentState(new FilterState(app));
            }
        
        };
        
        app.getGuiNode().attachChild(filterButton);
        filterButton.setLocalTranslation(hpos, Display.getHeight()/5, 0);
        filterButton.setText("Filter");
        buttonList.add(filterButton);
        
    }        
    
    private void createSkyButton() {
    
        float  count     = buttonList.size();
        float  hpos      = (space + buttonWidth)*count + space;       
        Button skyButton = new Button(app, buttonWidth, buttonHeight) {
        
            @Override
            public void act() {
                remove();
                app.getStateManager().getState(AppManager.class).setCurrentState(new SkyState(app));
            }
        
        };
        
        app.getGuiNode().attachChild(skyButton);
        skyButton.setLocalTranslation(hpos, Display.getHeight()/5, 0);
        skyButton.setText("Sky");
        buttonList.add(skyButton);
        
    }        
    
    public void remove() {
        buttonList.clear();
        app.getGuiNode().detachAllChildren();
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
    
}
