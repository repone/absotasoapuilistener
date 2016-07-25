/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.otasoapui;

import com.mmone.gpdati.allotment.writer.*;

/**
 *
 * @author mauro.larese
 */
public class MissingParametersException extends Exception{

    public MissingParametersException(String message) {
        super(message);
    }
    
}
