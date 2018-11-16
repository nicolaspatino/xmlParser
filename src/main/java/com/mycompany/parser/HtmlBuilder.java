/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.parser;

/**
 *
 * @author 2112712
 */
public class HtmlBuilder {
    public static void main(String[] args) throws Exception {
        String file="pensiones.bpmn";
        xmlParser doc = new xmlParser();
        doc.parse(file);
    }
     
}
