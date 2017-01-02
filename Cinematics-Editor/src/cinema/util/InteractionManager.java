/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.util;

import cinema.AppManager;
import cinema.state.CinemaState;
import com.jme3.app.SimpleApplication;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;

/**
 *
 * @author bob
 */
public class InteractionManager implements ActionListener {

    private final InputManager      inputManager;
    private final AppManager        appManager;
    private final SimpleApplication app;
    
    public InteractionManager(SimpleApplication app) {
        inputManager = app.getInputManager();
        appManager   = app.getStateManager().getState(AppManager.class);
        this.app     = app;
        setUpKeys();
    }
    
    private void setUpKeys(){
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("Click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("Cursor", new KeyTrigger(KeyInput.KEY_E));
        inputManager.addMapping("Space", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
        inputManager.addListener(this, "Click");
        inputManager.addListener(this, "Cursor");
        inputManager.addListener(this, "Space");
    }    
    
    @Override
    public void onAction(String binding, boolean isPressed, float tpf) {
        
        CinemaState cs = appManager.getCurrentState();
        
        switch (binding) {
        
            case "Up":
                
                app.getFlyByCamera().setEnabled(!isPressed);
                
                if (isPressed)
                    cs.getGui().onUpPress();
                
                else
                    cs.getGui().onUpRelease();
                
                break;
            
            case "Down":
                
                app.getFlyByCamera().setEnabled(!isPressed);
                
                if (isPressed)
                    cs.getGui().onDownPress();
                
                else
                    cs.getGui().onDownRelease();                
                
                break;

            case "Click":
                
                if (isPressed)
                    cs.getGui().onClickPress();
                
                else
                    cs.getGui().onClickRelease();
                
                break;

            case "Space":
                
                if (isPressed) 
                    cs.getGui().onSpacePress();
                
                else
                    cs.getGui().onSpaceRelease();
                
                break;
                
            case "Cursor":
                
                if (isPressed) {
                    app.getFlyByCamera().setEnabled(false);
                    app.getInputManager().setCursorVisible(true);
                }
                
                else {
                    app.getFlyByCamera().setEnabled(true);
                    app.getInputManager().setCursorVisible(false);
                }
                
                break;                
                
        }
        
    }
    
}
