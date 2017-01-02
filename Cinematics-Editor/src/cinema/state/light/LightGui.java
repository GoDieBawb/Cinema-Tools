/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.state.light;

import cinema.AppManager;
import cinema.state.gui.Button;
import cinema.state.gui.ColorPicker;
import cinema.state.gui.Gui;
import cinema.state.gui.Slider;
import cinema.state.select.SelectState;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.shadow.AbstractShadowRenderer;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.shadow.PointLightShadowRenderer;
import com.jme3.texture.Texture;
import java.util.ArrayList;
import org.lwjgl.opengl.Display;

/**
 *
 * @author bob
 */
public class LightGui extends Gui {
    
    public  static final ArrayList<Light>                  LIGHT_LIST  = new ArrayList();
    private static final ArrayList<AbstractShadowRenderer> SHADOW_LIST = new ArrayList();    
    
    private       Light             selectedLight;
    private       Node              hbar, lbar, rbar, textArea;
    private       ColorPicker       cp;
    private       Slider            slider;
    private final ArrayList<Button> buttonList;
    
    public LightGui(SimpleApplication app) {
        super(app);
        buttonList = new ArrayList();
        createFrame();
        createButtons();
        
        if (!LIGHT_LIST.isEmpty()) {
            selectedLight = LIGHT_LIST.get(0);
        }
        
        updateOptions();
        
    }
    
    private void createFrame() {
        createHorizontalBar();
        createLeftBar();
        createRightBar();
        createLeftBarTitle();
        createLeftTextArea();
        createRightBarTitle();        
    }
    
    
    private void createButtons() {
        createMakeAmbientButton();
        createMakePointButton();
        createMakeDirectButton();
        createBackButton();
        createDeleteButton();
        createColorPicker();
        createMakeFireButton();
        createSlider();
    }
        
    private void createHorizontalBar() {
                   hbar  = new Node();
        Box        b     = new Box(Display.getWidth(), Display.getHeight()/3, 1);
        Geometry   g     = new Geometry("Box", b);
        Material   mat   = new Material(app.getAssetManager(),"Common/MatDefs/Misc/Unshaded.j3md");
        Texture    tex   = app.getAssetManager().loadTexture("cinema/state/gui/style/Metal.png");
        mat.setColor("Color", ColorRGBA.Red);
        mat.setTexture("ColorMap",tex);
        g.setMaterial(mat);
        hbar.attachChild(g);
        app.getGuiNode().attachChild(hbar);
    }
    
    private void createLeftBar() {
                   lbar  = new Node();
        Box        b     = new Box(Display.getWidth()/6, Display.getHeight(), 1);
        Geometry   g     = new Geometry("Box", b);
        Material   mat   = new Material(app.getAssetManager(),"Common/MatDefs/Misc/Unshaded.j3md");
        Texture    tex   = app.getAssetManager().loadTexture("cinema/state/gui/style/Metal.png");
        mat.setColor("Color", ColorRGBA.Red);
        mat.setTexture("ColorMap",tex);       
        mat.setColor("Color", ColorRGBA.Red);
        g.setMaterial(mat);
        lbar.attachChild(g);
        lbar.setLocalTranslation(Display.getWidth()/12, 0, 0);
        app.getGuiNode().attachChild(lbar);
    }    
    
    private void createLeftBarTitle() {
        BitmapFont font = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        BitmapText text = new BitmapText(font);
        text.setText("Light List");
        lbar.attachChild(text);
        text.setLocalTranslation(0, Display.getHeight()-text.getLineHeight(), 0);
    }
    
    private void createLeftTextArea() {
        textArea      = new Node("Text Area");
        Node textNode = new Node("Text Node");
        float width   = Display.getWidth()/6 - Display.getWidth()/15;
        float height  = Display.getHeight()/3 - Display.getHeight()/10;
        Box      b    = new Box(width, height, 1);
        Geometry g    = new Geometry("Box", b);
        Material mat  = new Material(app.getAssetManager(),"Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Black);
        g.setMaterial(mat);
        textArea.attachChild(g);
        lbar.attachChild(textArea);
        textArea.attachChild(textNode);
        textArea.setLocalTranslation(Display.getWidth()/12 - Display.getWidth()/24, Display.getHeight() - Display.getHeight()/3f, 0);
    }

    private void createRightBar() {
                   rbar = new Node();
        Box        b    = new Box(Display.getWidth()/6, Display.getHeight(), 1);
        Geometry   g    = new Geometry("Box", b);
        Material   mat  = new Material(app.getAssetManager(),"Common/MatDefs/Misc/Unshaded.j3md");
        Texture    tex  = app.getAssetManager().loadTexture("cinema/state/gui/style/Metal.png");
        mat.setTexture("ColorMap",tex);
        mat.setColor("Color", ColorRGBA.Red);
        mat.setColor("Color", ColorRGBA.Red);
        mat.setTexture("ColorMap",tex);
        mat.setColor("Color", ColorRGBA.Red);
        g.setMaterial(mat);
        rbar.attachChild(g);
        rbar.setLocalTranslation(Display.getWidth()-Display.getWidth()/12, 0, 0);
        app.getGuiNode().attachChild(rbar);
    }    
    
    private void createRightBarTitle() {
        BitmapFont font = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        BitmapText text = new BitmapText(font);
        text.setText("Light Options");
        rbar.attachChild(text);
        text.setLocalTranslation(-text.getLineWidth(), Display.getHeight()-text.getLineHeight(), 0);
    }     
    
    private void createMakeAmbientButton() {
        
        Button ambientButton = new Button(app, Display.getWidth()/10, Display.getHeight()/20) {
        
            @Override
            public void act() {
                createNewAmbientLight();
            }
            
        };
        
        app.getGuiNode().attachChild(ambientButton);
        ambientButton.setLocalTranslation(Display.getWidth()/3, Display.getHeight()/5 + Display.getHeight()/15f, 0);
        ambientButton.setText("Ambient");
        buttonList.add(ambientButton);
        
    }
    
    private void createMakePointButton() {
        
        Button pointButton = new Button(app, Display.getWidth()/10, Display.getHeight()/20) {
        
            @Override
            public void act() {
                createNewPointLight();
            }
            
        };
        
        app.getGuiNode().attachChild(pointButton);
        pointButton.setLocalTranslation(Display.getWidth()/3, Display.getHeight()/5f - Display.getHeight()/15f, 0);
        pointButton.setText("Point");
        buttonList.add(pointButton);
        
    }    
    
    private void createMakeFireButton() {
        
        Button fireButton = new Button(app, Display.getWidth()/10, Display.getHeight()/20) {
        
            @Override
            public void act() {
                createNewFireLight();
            }
            
        };
        
        app.getGuiNode().attachChild(fireButton);
        fireButton.setLocalTranslation(Display.getWidth()/3 + (Display.getWidth()/10)*2.5f, Display.getHeight()/5f + Display.getHeight()/15f, 0);
        fireButton.setText("Fire");
        buttonList.add(fireButton);
        
    }    
    
    private void createMakeDirectButton() {
        
        Button directButton = new Button(app, Display.getWidth()/10, Display.getHeight()/20) {
        
            @Override
            public void act() {
                createNewDirectLight();
            }
            
        };
        
        app.getGuiNode().attachChild(directButton);
        directButton.setLocalTranslation(Display.getWidth()/3 + (Display.getWidth()/10)*2.5f, Display.getHeight()/5f - Display.getHeight()/15f, 0);
        directButton.setText("Directional");
        buttonList.add(directButton);
        
    }     
    
    private void createDeleteButton() {
        
        Button deleteButton = new Button(app, Display.getWidth()/10, Display.getHeight()/15) {
        
            @Override
            public void act() {
                
                if (LIGHT_LIST.isEmpty())
                    return;
                
                int index = LIGHT_LIST.indexOf(selectedLight);
                app.getViewPort().removeProcessor(SHADOW_LIST.get(index));
                SHADOW_LIST.remove(SHADOW_LIST.get(index));
                
                app.getRootNode().removeLight(selectedLight);
                LIGHT_LIST.remove(selectedLight);
                
                if (!LIGHT_LIST.isEmpty()) {
                    selectedLight = LIGHT_LIST.get(0);
                }
                
                else {
                    selectedLight = null;
                }
                
                System.out.println("Shadows List: " + SHADOW_LIST);
                updateOptions();
                
            }
            
        };
        
        app.getGuiNode().attachChild(deleteButton);
        deleteButton.setLocalTranslation(Display.getWidth()/12 + Display.getWidth()/24, Display.getHeight()/3f + Display.getHeight()/12f, 0);
        deleteButton.setText("Delete");
        buttonList.add(deleteButton);
        
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
    
    private void createColorPicker() {
        cp = new ColorPicker(app);
        app.getGuiNode().attachChild(cp);
        cp.setLocalTranslation(Display.getWidth() - Display.getWidth()/6f, Display.getHeight()/3f + Display.getHeight()/12f, 0);
    }
    
    private void createSlider() {
        slider = new Slider(app);
        app.getGuiNode().attachChild(slider);
        slider.setLocalTranslation(Display.getWidth() - Display.getWidth()/8f, Display.getHeight() - slider.getHeight()*4, .5f);
    }
    
    private void createNewAmbientLight() {
        AmbientLight al = new AmbientLight();
        selectedLight   = al;
        app.getRootNode().addLight(al);
        LIGHT_LIST.add(al);
        //Add pointless PLSR to keep lists equal size
        SHADOW_LIST.add(new PointLightShadowRenderer(app.getAssetManager(), 3));
        updateOptions();
    }
    
    private void createNewPointLight() {
        PointLight pl   = new PointLight();
        selectedLight   = pl;
        app.getRootNode().addLight(pl);
        pl.setPosition(app.getCamera().getLocation());
        LIGHT_LIST.add(pl);
        createPointShadowFilter(pl);
        updateOptions();
    }

    private void createNewFireLight() {
        FireLight fl   = new FireLight(app.getStateManager());
        selectedLight   = fl;
        app.getRootNode().addLight(fl);
        fl.setPosition(app.getCamera().getLocation());
        LIGHT_LIST.add(fl);
        createPointShadowFilter(fl);
        updateOptions();
    }    
    
    private void createPointShadowFilter(PointLight pl) {
        
        final int SHADOWMAP_SIZE=1024;
        
        AssetManager assetManager = app.getAssetManager();
        ViewPort viewPort         = app.getViewPort();
        
        PointLightShadowRenderer dlsr = new PointLightShadowRenderer(assetManager, SHADOWMAP_SIZE);
        dlsr.setLight(pl);
        viewPort.addProcessor(dlsr);
        SHADOW_LIST.add(dlsr);
    
    }
    
    private void createNewDirectLight() {
        DirectionalLight dl  = new DirectionalLight();
        selectedLight        = dl;
        app.getRootNode().addLight(dl);
        dl.setDirection(app.getCamera().getDirection());
        LIGHT_LIST.add(dl);
        createDirectShadowFilter(dl);
        updateOptions();
    }        
    
    private void createDirectShadowFilter(DirectionalLight dl) {
        
        final int SHADOWMAP_SIZE=1024;
        AssetManager assetManager = app.getAssetManager();
        ViewPort viewPort         = app.getViewPort();

        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(assetManager, SHADOWMAP_SIZE, 3);
        dlsr.setLight(dl);
        viewPort.addProcessor(dlsr);
        SHADOW_LIST.add(dlsr);
        
    }
    
    private void updateListText() {
    
        Node textNode = (Node) textArea.getChild("Text Node");
        textNode.detachAllChildren();
        
        float height = Display.getHeight()/3 - Display.getHeight()/10;
        
        for (int i = 0; i < LIGHT_LIST.size(); i++) {
            
            Light currentLight = LIGHT_LIST.get(i);
            
            BitmapFont font = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
            BitmapText text = new BitmapText(font);
            String prefix;
            
            if (currentLight instanceof PointLight)
                prefix = "Point";
            else if (currentLight instanceof DirectionalLight)
                prefix = "Directional";
            else if (currentLight instanceof AmbientLight)
                prefix = "Ambient";
            else
                prefix = "";
            
            text.setText(i+1 + ") " + prefix + "Light");
            
            textNode.attachChild(text);
            text.setLocalTranslation(0-text.getLineWidth()/2, height-text.getLineHeight()*i, 0);
            
            if (currentLight == selectedLight) {
                text.setColor(ColorRGBA.Red);
            }
             
        }
        
    }
    
    private void updateOptions() {
    
        cp.removeFromParent();
        slider.removeFromParent();
        
        if (selectedLight instanceof AmbientLight) {
            app.getGuiNode().attachChild(slider);
            app.getGuiNode().attachChild(cp);
        }
  
        else if (selectedLight instanceof PointLight) {
            app.getGuiNode().attachChild(cp);
            app.getGuiNode().attachChild(slider);
        }        
        
        else if (selectedLight instanceof DirectionalLight) {
            app.getGuiNode().attachChild(slider);
            app.getGuiNode().attachChild(cp);
        }
        
        updateListText();
        
    }
    
    private void sliderAct() {
        
        if (selectedLight instanceof AmbientLight) {
            slider.setMin(0);
            slider.setMax(2);
            selectedLight.setColor(selectedLight.getColor().mult(slider.getCurrentValue()));
        }
  
        if (selectedLight instanceof PointLight) {
            slider.setMin(.01f);
            slider.setMax(150);
            ((PointLight) selectedLight).setRadius(slider.getCurrentValue());
        }        
        
        if (selectedLight instanceof DirectionalLight) {
            slider.setMin(0);
            slider.setMax(2);
            selectedLight.setColor(selectedLight.getColor().mult(slider.getCurrentValue()));
        }
        
    }
    
    public void remove() {
        buttonList.clear();
        app.getGuiNode().detachAllChildren();
    }
    
    @Override
    public void onClickPress() {
        slider.clickCheck();
    }
    
    @Override
    public void onClickRelease() {
    
        for (int i = 0; i < buttonList.size(); i++) {
        
            Button currentButton = buttonList.get(i);
            
            if (currentButton.buttonCheck()) {
                currentButton.act();
                return;
            }
            
        }
        
        if (slider.isClicked()) {
            slider.release();
            sliderAct();
        }
        
        ColorRGBA color = cp.clickCheck();
        
        if (color != null && selectedLight != null) {
            selectedLight.setColor(color);
        }
        
    }

    @Override
    public void onUpRelease() {
        
        int newIndex  = LIGHT_LIST.indexOf(selectedLight)+1;
        
        if (newIndex > LIGHT_LIST.size()-1)
            newIndex = 0;
        
        selectedLight = LIGHT_LIST.get(newIndex);
        updateOptions();
        
    }
    
    @Override
    public void onDownRelease() {
    
        int newIndex  = LIGHT_LIST.indexOf(selectedLight)-1;
        
        if (newIndex < 0)
            newIndex = LIGHT_LIST.size()-1;
        
        selectedLight = LIGHT_LIST.get(newIndex);
        updateOptions();        
        
    }
    
}
