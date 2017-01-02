/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.state.gui;

import com.jme3.app.SimpleApplication;

/**
 *
 * @author bob
 */
public abstract class Gui {
    
    protected       boolean           isClick = false, isUp = false, isDown = false, isSpace = false;
    protected final SimpleApplication app;
    
    public Gui(SimpleApplication app) {
        this.app = app;
    }
    
    public void onClickPress() {
        isClick = true;
    }
    
    public void onClickRelease() {
        isClick = false;
    }
    
    public void onUpPress() {
        isUp = true;
    }
    
    public void onUpRelease() {
        isUp = false;
    }
    
    public void onDownPress() {
        isDown = true;
    }
    
    public void onDownRelease() {
        isDown = false;
    }
    
    public void onSpacePress() {
        isSpace = true;
    }
    
    public void onSpaceRelease() {
        isSpace = false;
    }
    
}
