/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.scene;

import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author bob
 */
public class SceneManager {
    
    private       Spatial scene;
    private final Node    rootNode;
    
    
    public SceneManager(Node rootNode) {
        this.rootNode = rootNode;
        rootNode.setShadowMode(RenderQueue.ShadowMode.Off);
    }
    
    public void setScene(Spatial scene) {
        this.scene = scene;
        rootNode.attachChild(scene);
        scene.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
    }
    
    public Spatial getScene() {
        return scene;
    }
    
    public void removeScene() {
        scene.removeFromParent();
        scene = null;
    }
    
}
