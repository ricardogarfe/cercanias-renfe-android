/**
 * 
 */
package com.ricardogarfe.renfe.model;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author ricardo
 * 
 */
public class ValidezType {

    private XMLGregorianCalendar desde;
    private XMLGregorianCalendar hasta;

    public XMLGregorianCalendar getDesde() {
        return desde;
    }

    public void setDesde(XMLGregorianCalendar desde) {
        this.desde = desde;
    }

    public XMLGregorianCalendar getHasta() {
        return hasta;
    }

    public void setHasta(XMLGregorianCalendar hasta) {
        this.hasta = hasta;
    }

}
