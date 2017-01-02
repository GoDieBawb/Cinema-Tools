/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.state.sky;

import cinema.state.CinemaState;
import com.jme3.app.SimpleApplication;

/**
 *
 * @author bob
 */
public class SkyState extends CinemaState {
    
    public SkyState(SimpleApplication app) {
        gui = new SkyGui(app);
    }
    
}
