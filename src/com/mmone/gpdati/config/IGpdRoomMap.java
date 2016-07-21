/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.config;

import java.util.Hashtable;

/**
 *
 * @author mauro.larese
 */
public interface IGpdRoomMap {
    void put(String gpstructure,String gproom,GpdRoomRecord record);
    GpdRoomRecord get(String gpstructure,String gproom);
}
