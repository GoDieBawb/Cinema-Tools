/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.state.sky;

import cinema.AppManager;
import cinema.state.gui.Button;
import cinema.state.gui.Gui;
import cinema.state.select.SelectState;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import java.util.ArrayList;
import org.lwjgl.opengl.Display;

/**
 *
 * @author bob
 */
public class SkyGui extends Gui {
    
    private final ArrayList<Button> buttonList;
    private       Node              skyPallette;
    private       Spatial           currentSky;
    private       boolean           isVisible;
    
    public SkyGui(SimpleApplication app) {
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
        createDeleteButton();
        createSkyPallette();
    }
    
    private void createBackButton() {
        
        float width  = Display.getWidth()/10;
        float height = Display.getHeight()/25;
        
        Button backButton = new Button(app, width, height) {
        
            @Override
            public void act() {
                remove();
                app.getStateManager().getState(AppManager.class).setCurrentState(new SelectState(app));
            }
            
        };
        
        app.getGuiNode().attachChild(backButton);
        backButton.setLocalTranslation(Display.getWidth()/12 + Display.getWidth()/24, (Display.getHeight()/20)*2, 0);
        backButton.setText("Back");
        buttonList.add(backButton);
        
    }    
    
    private void createDeleteButton() {
        
        float width  = Display.getWidth()/10;
        float height = Display.getHeight()/25;
        
        Button deleteButton = new Button(app, width, height) {
        
            @Override
            public void act() {
                
                if (currentSky != null ) {
                    app.getRootNode().detachChild(currentSky);
                    currentSky = null;
                }
            }
            
        };
        
        app.getGuiNode().attachChild(deleteButton);
        deleteButton.setLocalTranslation(Display.getWidth()/12 + Display.getWidth()/24, (Display.getHeight()/20)*4, 0);
        deleteButton.setText("Delete");
        buttonList.add(deleteButton);
        
    }    
    
    private void createSkyPallette() {
        skyPallette    = new Node();
        float  width   = Display.getWidth()/20;
        float  height  = Display.getHeight()/20;
        String mapPath = "cinema/state/sky/maps/";
        String[] maps  = {"ashstorm","blight", "clear", "cloudy", "foggy", "overcast", "rainy", "starry", "stormy", "thunder"};
        
        for (int i = 0; i < 10; i++) {
            
            final String path = mapPath + maps[i] + ".png";
            
            Material mat    = new Material(app.getAssetManager(),"Common/MatDefs/Misc/Unshaded.j3md");
            Texture  tex    = app.getAssetManager().loadTexture(path);
            int      butNum = i+1;
            float    xSpot;
            float    ySpot;
            
            Button   button = new Button(app, width, height) {
            
                @Override
                public void act() {
                    
                    if (currentSky != null) {
                        app.getRootNode().detachChild(currentSky);
                        currentSky = null;
                    }
                    
                    currentSky = SkyFactory.createSky(app.getAssetManager(), path, SkyFactory.EnvMapType.SphereMap);
                    currentSky.rotate(-89,0,0);
                    app.getRootNode().attachChild(currentSky);
                }
                
            };
            
            mat.setTexture("ColorMap", tex);
            ((Geometry)button.getChild("Box")).setMaterial(mat);
            
            switch (butNum) {
                case 1:
                case 6:
                    xSpot = 0;
                    break;
                case 2:
                case 7:
                    xSpot = width*2;
                    break;
                case 3:
                case 8:
                    xSpot = width*4;
                    break;
                case 4:
                case 9:
                    xSpot = width*6;
                    break;
                case 5:
                case 10:
                    xSpot = width*8;
                    break;    
                    
                default:
                    xSpot = 0;
                    break;
                    
            }
            
            switch (butNum) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    ySpot = 0;
                    break;
                default:
                    ySpot = height*2;
                    break;
            }
            
            skyPallette.attachChild(button);
            buttonList.add(button);
            button.setLocalTranslation(xSpot, ySpot, 0);
            
        }
        
        app.getGuiNode().attachChild(skyPallette);
        skyPallette.setLocalTranslation(Display.getWidth()/2 - (Display.getWidth()/20)*5/2, (Display.getHeight()/20)*2, 0);
        
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
