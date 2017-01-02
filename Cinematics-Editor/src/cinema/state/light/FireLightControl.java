/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.state.light;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author bob
 */
public class FireLightControl extends AbstractControl {

    private final FireLight flame;
    
    public FireLightControl(FireLight flame) {
        this.flame = flame;
        enabled    = true;
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        flame.update(tpf);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
    
}
