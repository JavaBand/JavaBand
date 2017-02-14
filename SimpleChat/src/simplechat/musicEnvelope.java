/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplechat;

import java.io.Serializable;

/**
 *
 * @author fairbrother8338
 */
public final class musicEnvelope implements Serializable {
    
    private String instrument;
    private String number;
    private String note;

    public musicEnvelope(String instrument, String number) {
        this.instrument = instrument;
        this.number = number;
        setNote();
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNote() {
        return note;
    }

    public void setNote() {
        note = instrument;
        note += number;
    }
   

}
