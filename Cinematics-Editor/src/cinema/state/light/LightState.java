/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.state.light;

import cinema.state.CinemaState;
import com.jme3.app.SimpleApplication;

/**
 *
 * @author bob
 */
public class LightState extends CinemaState {
    
    public LightState(SimpleApplication app) {
        gui = new LightGui(app);
    }
    
}
