/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.state.gui;

import com.jme3.app.SimpleApplication;
import com.jme3.input.InputManager;
import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author bob
 */
public class SliderControl extends AbstractControl {

    private final InputManager inputManager;
    private final Slider       slider;
    private final float        minVal, maxVal;
    private       float        currentVal;
    
    public SliderControl(SimpleApplication app, Slider slider) {
        this.slider  = slider;
        inputManager = app.getInputManager();
        enabled      = false;
        minVal       = 0;
        maxVal       = slider.getWidth()*2;
    }
    
    public float getMinVal() {
        return minVal;
    }
    
    public float getMaxVal() {
        return maxVal;
    }
    
    public float getCurrentVal() {
        return currentVal;
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        
        float cursorSpot = inputManager.getCursorPosition().x;
        
        float xSpot      = FastMath.abs(cursorSpot) - slider.getWorldTranslation().x;
        float ySpot      = spatial.getLocalTranslation().y;
        
        float minX       = slider.getLocalTranslation().x - slider.getWidth();
        float maxX       = slider.getLocalTranslation().x + slider.getWidth();

        currentVal       = spatial.getLocalTranslation().x + slider.getWidth();
        
        if (cursorSpot < maxX && cursorSpot > minX) {
            spatial.setLocalTranslation(xSpot, ySpot, 0);
        }
        
        else if (cursorSpot > maxX) {
            currentVal = maxVal;
        }
        
        else if (cursorSpot < minX) {
            currentVal = minVal;
        }
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
    
}
