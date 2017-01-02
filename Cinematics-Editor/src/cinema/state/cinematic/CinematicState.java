package cinema.state.cinematic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cinema.state.CinemaState;
import com.jme3.app.SimpleApplication;

/**
 *
 * @author bob
 */
public class CinematicState extends CinemaState {
    
    public CinematicState(SimpleApplication app) {
        gui = new CinematicGui(app);
    }
    
}
