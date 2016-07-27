/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.otasoapui;

/**
 *
 * @author mauro.larese
 */
public interface AllotmentUpdatePropertiesCollector { 
    String getDbPath();
    String getRpcUser();
    String getRpcPwd();
    String getRpcUrl();
    String getAvailFile();
    String getErrorMessage() ;
    boolean hasError() ;
}
