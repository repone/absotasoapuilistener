/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.allotment.reader;

import java.util.List;

/**
 *
 * @author mauro.larese
 */
public interface AllotmentLineProvvider {
    List<String> getLines() throws Exception;
}
