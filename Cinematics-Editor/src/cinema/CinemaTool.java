package cinema;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class CinemaTool extends SimpleApplication {
    
    protected AppManager appManager;
    private   String     scenePath;
    
    public static void main(String[] args) {
        CinemaTool app = new CinemaTool();
        app.start();
    }

    public CinemaTool() {
    
    }    
    
    public CinemaTool(String scenePath) {
        this.scenePath = scenePath;
    }
    
    @Override
    public void simpleInitApp() { 
        appManager = new AppManager();
        stateManager.attach(appManager);
        appManager.setScenePath(scenePath);
        getFlyByCamera().setMoveSpeed(5);
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
}
