/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.state.filter;

import cinema.state.CinemaState;
import com.jme3.app.SimpleApplication;

/**
 *
 * @author bob
 */
public class FilterState extends CinemaState {
    
    private final SimpleApplication app;
    
    public FilterState(SimpleApplication app) {
        this.app = app;
        gui      = new FilterGui(app);
    }
    
    
}
