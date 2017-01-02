/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema;

import cinema.scene.SceneManager;
import cinema.state.CinemaState;
import cinema.state.select.SelectState;
import cinema.util.InteractionManager;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Spatial;

/**
 *
 * @author bob
 */
public class AppManager extends AbstractAppState {
    
    private CinemaState        currentState;
    private InteractionManager im;
    private SceneManager       sceneManager;
    private String             scenePath;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        sceneManager = new SceneManager(((SimpleApplication)app).getRootNode());
        currentState = new SelectState((SimpleApplication) app);
        im           = new InteractionManager((SimpleApplication) app);
        initScene(app.getAssetManager().loadModel(scenePath));
    }
 
    public void setScenePath(String path) {
        scenePath = path;
    }
    
    private void initScene(Spatial scene) {
        sceneManager.setScene(scene);
    }
    
    public void setCurrentState(CinemaState newState) {
        currentState = newState;
    }
    
    public CinemaState getCurrentState() {
        return currentState;
    }
    
}
