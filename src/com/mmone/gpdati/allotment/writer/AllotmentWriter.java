/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmone.gpdati.allotment.writer;

import com.mmone.gpdati.allotment.record.AllotmentRecord;
import java.util.List;

/**
 *
 * @author mauro.larese
 */
public interface AllotmentWriter {
    void writeAllotment(List<AllotmentRecord>allotments) throws ErrorWritingAllotmentException;
}
