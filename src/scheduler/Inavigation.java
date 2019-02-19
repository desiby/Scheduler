/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.io.IOException;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author desib
 */
public interface Inavigation {
    
    /**
     *
     * @param anchorPane
     * @param path
     * @throws IOException
     */
    void navigateTo(AnchorPane anchorPane, String path) throws IOException;
}
