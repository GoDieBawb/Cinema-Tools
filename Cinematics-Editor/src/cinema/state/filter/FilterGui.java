/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.state.filter;

import cinema.AppManager;
import cinema.state.gui.Button;
import cinema.state.gui.ColorPicker;
import cinema.state.gui.Gui;
import cinema.state.gui.Slider;
import cinema.state.select.SelectState;
import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.Filter;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.CartoonEdgeFilter;
import com.jme3.post.filters.CrossHatchFilter;
import com.jme3.post.filters.DepthOfFieldFilter;
import com.jme3.post.filters.FogFilter;
import com.jme3.post.filters.LightScatteringFilter;
import com.jme3.post.filters.RadialBlurFilter;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.water.WaterFilter;
import java.util.ArrayList;
import org.lwjgl.opengl.Display;

/**
 *
 * @author bob
 */
public class FilterGui extends Gui {
    
    private static final ArrayList<Filter>   FILTER_LIST = new ArrayList();    
    private static final FilterPostProcessor fpp         = new FilterPostProcessor();
    private final ArrayList<Button>   buttonList;
    private       Filter              selectedFilter;
    private       Node                hbar, lbar, rbar, textArea;
    private       ColorPicker         cp;
    private       Slider              slider;
    
    public FilterGui(SimpleApplication app) {
        
        super(app);
        fpp.setAssetManager(app.getAssetManager());
        buttonList = new ArrayList();
        app.getViewPort().addProcessor(fpp);        
        createFrame();
        createButtons();
        
        if (!FILTER_LIST.isEmpty()) {
            selectedFilter = FILTER_LIST.get(0);
        }
        
        updateOptions();
        
    }
    
    private void createButtons() {
        createBackButton();
        createMakeFogButton();
        createMakeWaterButton();
        createMakeBloomButton();
        createMakeCrossHatchButton();
        createMakeCartoonEdgeButton();
        createMakeRadialBlurButton();
        createMakeLightScatteringButton();
        createMakeDepthOfFieldButton();
        createDeleteButton();
        createColorPicker();
        createSlider();
    }
    
    private void createFrame() {
        createHorizontalBar();
        createLeftBar();
        createRightBar();
        createLeftBarTitle();
        createLeftTextArea();
        createRightBarTitle();
    }
    
    private void createHorizontalBar() {
                 hbar = new Node();
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
    
    private void createLeftBar() {
                 lbar = new Node();
        Box      b    = new Box(Display.getWidth()/6, Display.getHeight(), 1);
        Geometry g    = new Geometry("Box", b);
        Material mat  = new Material(app.getAssetManager(),"Common/MatDefs/Misc/Unshaded.j3md");
        Texture  tex  = app.getAssetManager().loadTexture("cinema/state/gui/style/Metal.png");    
        mat.setTexture("ColorMap", tex);        
        mat.setColor("Color", ColorRGBA.Red);
        g.setMaterial(mat);
        lbar.attachChild(g);
        lbar.setLocalTranslation(Display.getWidth()/12, 0, 0);
        app.getGuiNode().attachChild(lbar);
    }

    private void createLeftBarTitle() {
        BitmapFont font = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        BitmapText text = new BitmapText(font);
        text.setText("Filter List");
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
        Box      b    = new Box(Display.getWidth()/6, Display.getHeight(), 1);
        Geometry g    = new Geometry("Box", b);
        Material mat  = new Material(app.getAssetManager(),"Common/MatDefs/Misc/Unshaded.j3md");
        Texture  tex  = app.getAssetManager().loadTexture("cinema/state/gui/style/Metal.png");    
        mat.setTexture("ColorMap", tex);        
        mat.setColor("Color", ColorRGBA.Red);
        g.setMaterial(mat);
        rbar.attachChild(g);
        rbar.setLocalTranslation(Display.getWidth()-Display.getWidth()/12, 0, 0);
        app.getGuiNode().attachChild(rbar);
    }      
    
    private void createRightBarTitle() {
        BitmapFont font = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        BitmapText text = new BitmapText(font);
        text.setText("Filter Options");
        rbar.attachChild(text);
        text.setLocalTranslation(-text.getLineWidth(), Display.getHeight()-text.getLineHeight(), 0);
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
    
    private void createMakeFogButton() {
        
        Button fogButton = new Button(app, Display.getWidth()/15, Display.getHeight()/20) {
        
            @Override
            public void act() {
                createNewFogFilter();
            }
            
        };
        
        app.getGuiNode().attachChild(fogButton);
        fogButton.setLocalTranslation(Display.getWidth()/3, Display.getHeight()/5 + Display.getHeight()/15f, 0);
        fogButton.setText("Fog");
        buttonList.add(fogButton);
        
    }    
    
    private void createNewFogFilter() {
        FogFilter fog = new FogFilter();
        fog.setFogDistance(5);
        fpp.addFilter(fog);
        selectedFilter = fog;
        FILTER_LIST.add(fog);
        updateOptions();
    }
    
    private void createMakeWaterButton() {
        
        Button waterButton = new Button(app, Display.getWidth()/15, Display.getHeight()/20) {
        
            @Override
            public void act() {
                createNewWaterFilter();
            }
            
        };
        
        app.getGuiNode().attachChild(waterButton);
        waterButton.setLocalTranslation(Display.getWidth()/3, Display.getHeight()/5 - Display.getHeight()/15f, 0);
        waterButton.setText("Water");
        buttonList.add(waterButton);
        
    }       
    
    private void createNewWaterFilter() {
        WaterFilter water = new WaterFilter();
        fpp.addFilter(water);
        selectedFilter = water;
        FILTER_LIST.add(water);
        updateOptions();
    }
    
    private void createMakeBloomButton() {
        
        Button bloomButton = new Button(app, Display.getWidth()/15, Display.getHeight()/20) {
        
            @Override
            public void act() {
                createNewBloomFilter();
            }
            
        };
        
        app.getGuiNode().attachChild(bloomButton);
        bloomButton.setLocalTranslation(Display.getWidth()/3 + (Display.getWidth()/15)*2.5f, Display.getHeight()/5 - Display.getHeight()/15f, 0);
        bloomButton.setText("Bloom");
        buttonList.add(bloomButton);
        
    }       
    
    private void createNewBloomFilter() {
        BloomFilter bloom = new BloomFilter();
        fpp.addFilter(bloom);
        selectedFilter = bloom;
        FILTER_LIST.add(bloom);
        updateOptions();
    }    
    
    private void createMakeCrossHatchButton() {
        
        Button crossHatchButton = new Button(app, Display.getWidth()/15, Display.getHeight()/20) {
        
            @Override
            public void act() {
                createNewCrossHatchFilter();
            }
            
        };
        
        app.getGuiNode().attachChild(crossHatchButton);
        crossHatchButton.setLocalTranslation(Display.getWidth()/3 + (Display.getWidth()/15)*2.5f, Display.getHeight()/5 + Display.getHeight()/15f, 0);
        crossHatchButton.setText("CrossHatch");
        buttonList.add(crossHatchButton);
        
    }       
    
    private void createNewCrossHatchFilter() {
        CrossHatchFilter crossHatch = new CrossHatchFilter();
        fpp.addFilter(crossHatch);
        selectedFilter = crossHatch;
        FILTER_LIST.add(crossHatch);
        updateOptions();
    }      
    
    private void createMakeCartoonEdgeButton() {
        
        Button cartoonEdgeButton = new Button(app, Display.getWidth()/15, Display.getHeight()/20) {
        
            @Override
            public void act() {
                createNewCartoonEdgeFilter();
            }
            
        };
        
        app.getGuiNode().attachChild(cartoonEdgeButton);
        cartoonEdgeButton.setLocalTranslation(Display.getWidth()/3 + (Display.getWidth()/15)*5f, Display.getHeight()/5 + Display.getHeight()/15f, 0);
        cartoonEdgeButton.setText("CartoonEdge");
        buttonList.add(cartoonEdgeButton);
        
    }       
    
    private void createNewCartoonEdgeFilter() {
        CartoonEdgeFilter cartoonEdge = new CartoonEdgeFilter();
        fpp.addFilter(cartoonEdge);
        selectedFilter = cartoonEdge;
        FILTER_LIST.add(cartoonEdge);
        updateOptions();
    }    
    
    private void createMakeRadialBlurButton() {
        
        Button radialBlurButton = new Button(app, Display.getWidth()/15, Display.getHeight()/20) {
        
            @Override
            public void act() {
                createNewRadialBlurFilter();
            }
            
        };
        
        app.getGuiNode().attachChild(radialBlurButton);
        radialBlurButton.setLocalTranslation(Display.getWidth()/3 + (Display.getWidth()/15)*5f, Display.getHeight()/5 - Display.getHeight()/15f, 0);
        radialBlurButton.setText("RadialBlur");
        buttonList.add(radialBlurButton);
        
    }       
    
    private void createNewRadialBlurFilter() {
        RadialBlurFilter radialBlur = new RadialBlurFilter();
        fpp.addFilter(radialBlur);
        selectedFilter = radialBlur;
        FILTER_LIST.add(radialBlur);
        updateOptions();
    }      
    
    private void createMakeLightScatteringButton() {
        
        Button lightScatteringButton = new Button(app, Display.getWidth()/15, Display.getHeight()/20) {
        
            @Override
            public void act() {
                createNewLightScatteringFilter();
            }
            
        };
        
        app.getGuiNode().attachChild(lightScatteringButton);
        lightScatteringButton.setLocalTranslation(Display.getWidth()/3 + (Display.getWidth()/15)*7.5f, Display.getHeight()/5 - Display.getHeight()/15f, 0);
        lightScatteringButton.setText("Light Scatter");
        buttonList.add(lightScatteringButton);
        
    }       
    
    private void createNewLightScatteringFilter() {
        LightScatteringFilter lightScattering = new LightScatteringFilter();
        lightScattering.setLightPosition(new Vector3f(0,10,0));
        fpp.addFilter(lightScattering);
        selectedFilter = lightScattering;
        FILTER_LIST.add(lightScattering);
        updateOptions();
    }     
    
    private void createMakeDepthOfFieldButton() {
        
        Button depthOfFieldButton = new Button(app, Display.getWidth()/15, Display.getHeight()/20) {
        
            @Override
            public void act() {
                createNewDepthOfFieldFilter();
            }
            
        };
        
        app.getGuiNode().attachChild(depthOfFieldButton);
        depthOfFieldButton.setLocalTranslation(Display.getWidth()/3 + (Display.getWidth()/15)*7.5f, Display.getHeight()/5 + Display.getHeight()/15f, 0);
        depthOfFieldButton.setText("DepthOfField");
        buttonList.add(depthOfFieldButton);
        
    }       
    
    private void createNewDepthOfFieldFilter() {
        DepthOfFieldFilter depthOfField = new DepthOfFieldFilter();
        depthOfField.setBlurScale(2);
        fpp.addFilter(depthOfField);
        selectedFilter = depthOfField;
        FILTER_LIST.add(depthOfField);
        updateOptions();
    }        
    
    private void createDeleteButton() {
        
        Button deleteButton = new Button(app, Display.getWidth()/10, Display.getHeight()/20) {
        
            @Override
            public void act() {
                
                if (!FILTER_LIST.isEmpty()) {
                    selectedFilter.setEnabled(false);
                    fpp.removeFilter(selectedFilter);
                    FILTER_LIST.remove(selectedFilter);                
                }
                
                if (!FILTER_LIST.isEmpty()) {
                    selectedFilter = FILTER_LIST.get(0);
                }
                
                else {
                    selectedFilter = null;
                }
                
                updateOptions();
                
            }
            
        };
        
        app.getGuiNode().attachChild(deleteButton);
        deleteButton.setLocalTranslation(Display.getWidth()/12 + Display.getWidth()/24, Display.getHeight()/3f + Display.getHeight()/12f, 0);
        deleteButton.setText("Delete");
        buttonList.add(deleteButton);
        
    }       
    
    private void remove() {
        app.getGuiNode().detachAllChildren();
        buttonList.clear();
    }
    
    private void updateListText() {
    
        Node textNode = (Node) textArea.getChild("Text Node");
        textNode.detachAllChildren();
        
        float height = Display.getHeight()/3 - Display.getHeight()/10;
        
        for (int i = 0; i < FILTER_LIST.size(); i++) {
            
            Filter     currentFilter = FILTER_LIST.get(i);
            BitmapFont font          = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
            BitmapText text          = new BitmapText(font);
            String     filterName    = "";

            if (currentFilter instanceof FogFilter) {
                filterName = "Fog";
            }

            else if (currentFilter instanceof WaterFilter) {
                filterName = "Water";
            }        

            else if (currentFilter instanceof CartoonEdgeFilter) {
                filterName = "CartoonEdge";
            }

            else if (currentFilter instanceof CrossHatchFilter) {
                filterName = "CrossHatch";
            }

            else if (currentFilter instanceof RadialBlurFilter) {
                filterName = "RadialBlur";
            }

            else if (currentFilter instanceof BloomFilter) {
                filterName = "Bloom";
            }    

            else if (currentFilter instanceof LightScatteringFilter) {
                filterName = "Light Scatter";
            }  

            else if (currentFilter instanceof DepthOfFieldFilter) {
                filterName = "DepthOfField";
            }               
            
            text.setText(i+1 + ") " + filterName + "Filter");
            
            textNode.attachChild(text);
            text.setLocalTranslation(0-text.getLineWidth()/2, height-text.getLineHeight()*i, 0);
            
            if (currentFilter == selectedFilter) {
                text.setColor(ColorRGBA.Red);
            }
            
            
        }
        
    }    
    
    private void updateOptions() {
    
        cp.removeFromParent();
        slider.removeFromParent();
        
        if (selectedFilter instanceof FogFilter) {
            app.getGuiNode().attachChild(cp);
            app.getGuiNode().attachChild(slider);
        }
  
        else if (selectedFilter instanceof WaterFilter) {
            app.getGuiNode().attachChild(cp);
            app.getGuiNode().attachChild(slider);
        }        
        
        else if (selectedFilter instanceof CartoonEdgeFilter) {
            app.getGuiNode().attachChild(cp);
            app.getGuiNode().attachChild(slider);
        }
        
        else if (selectedFilter instanceof CrossHatchFilter) {
            app.getGuiNode().attachChild(cp);
            app.getGuiNode().attachChild(slider);
        }
        
        else if (selectedFilter instanceof RadialBlurFilter) {
            app.getGuiNode().attachChild(slider);
        }
        
        else if (selectedFilter instanceof BloomFilter) {
            app.getGuiNode().attachChild(slider);
        }    
        
        else if (selectedFilter instanceof LightScatteringFilter) {
            app.getGuiNode().attachChild(slider);
        }  

        else if (selectedFilter instanceof DepthOfFieldFilter) {
            app.getGuiNode().attachChild(slider);
        }          
        
        updateListText();
        
    }    
    
    private void actOnColor() {
        
        ColorRGBA color = cp.clickCheck();
        
        if (color != null && selectedFilter != null) {
                
            if (selectedFilter instanceof FogFilter) {
                ((FogFilter) selectedFilter).setFogColor(color);
            }
            
            else if (selectedFilter instanceof WaterFilter) {
                ((WaterFilter) selectedFilter).setWaterColor(color);
            }
            
            else if (selectedFilter instanceof CartoonEdgeFilter) {
                ((CartoonEdgeFilter) selectedFilter).setEdgeColor(color);
            }

            else if (selectedFilter instanceof CrossHatchFilter) {
                ((CrossHatchFilter) selectedFilter).setPaperColor(color);
            }                
        
        }
        
    }
    
    private void sliderAct() {
        
        if (selectedFilter instanceof FogFilter) {
            slider.setMin(0);
            slider.setMax(2.5f);
            ((FogFilter) selectedFilter).setFogDensity(slider.getCurrentValue());
        }

        else if (selectedFilter instanceof WaterFilter) {
            slider.setMin(-10);
            slider.setMax(10);
            ((WaterFilter) selectedFilter).setWaterHeight(slider.getCurrentValue());
        }

        else if (selectedFilter instanceof CartoonEdgeFilter) {
            slider.setMin(0);
            slider.setMax(100);
            ((CartoonEdgeFilter) selectedFilter).setEdgeIntensity(slider.getCurrentValue());
        }

        else if (selectedFilter instanceof CrossHatchFilter) {
            slider.setMin(0);
            slider.setMax(20);
            ((CrossHatchFilter) selectedFilter).setLineThickness(slider.getCurrentValue());
        }
        
        else if (selectedFilter instanceof RadialBlurFilter) {
            slider.setMin(0);
            slider.setMax(10);
            ((RadialBlurFilter) selectedFilter).setSampleStrength(slider.getCurrentValue());
        }        
        
        else if (selectedFilter instanceof BloomFilter) {
            slider.setMin(0);
            slider.setMax(10);
            ((BloomFilter) selectedFilter).setBloomIntensity(slider.getCurrentValue());
        }

        else if (selectedFilter instanceof LightScatteringFilter) {
            slider.setMin(0);
            slider.setMax(30);
            ((LightScatteringFilter) selectedFilter).setBlurWidth(slider.getCurrentValue());
        }        
        
        else if (selectedFilter instanceof DepthOfFieldFilter) {
            slider.setMin(0);
            slider.setMax(30);
            ((DepthOfFieldFilter) selectedFilter).setFocusDistance(slider.getCurrentValue());
        }        
        
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
            }
            
        }
        
        if (slider.isClicked()) {
            slider.release();
            sliderAct();
        }
        
        actOnColor();
        
    }

    @Override
    public void onUpRelease() {
        
        int newIndex  = FILTER_LIST.indexOf(selectedFilter)+1;
        
        if (newIndex > FILTER_LIST.size()-1)
            newIndex = 0;
        
        selectedFilter = FILTER_LIST.get(newIndex);
        updateOptions();
        
    }
    
    @Override
    public void onDownRelease() {
    
        int newIndex  = FILTER_LIST.indexOf(selectedFilter)-1;
        
        if (newIndex < 0)
            newIndex = FILTER_LIST.size()-1;
        
        selectedFilter = FILTER_LIST.get(newIndex);
        updateOptions();        
        
    }    
    
}
