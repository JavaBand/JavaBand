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
public class Envelope implements Serializable {
    
    private String recipient;
    private Object contents;
    
    public Envelope(String recipient, Object contents)
    {
        setRecipient(recipient);
        setContents(contents);
    }

    /**
     * @return the recipient
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * @param recipient the recipient to set
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * @return the contents
     */
    public Object getContents() {
        return contents;
    }

    /**
     * @param contents the contents to set
     */
    public void setContents(Object contents) {
        this.contents = contents;
    }
}
