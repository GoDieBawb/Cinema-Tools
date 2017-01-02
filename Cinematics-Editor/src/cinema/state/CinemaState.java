/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.state;

import cinema.state.gui.Gui;

/**
 *
 * @author bob
 */
public abstract class CinemaState {
    
    protected Gui gui;
    
    public Gui getGui() {
        return gui;
    }
    
}
