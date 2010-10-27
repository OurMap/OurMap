/**

com.cuesta.web.tags.ComboTag.java
Version: 1.0

********************************************************************************
Author:
Manuel Cuesta, programmer <camilocuesta@hotmail.com>

**************************************************

CuestaLib is Copyright (c) 2010, Manuel Cuesta  <camilocuesta@hotmail.com >
All rights reserved.

Published under the terms of the new BSD license.
See: [http://github.com/m-cuesta/tiers] for the full license and other info.

LICENSE:

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice,
this list of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer in the
documentation and/or other materials provided with the distribution.

Neither the name of The Banff Centre nor the names of its contributors may be
used to endorse or promote products derived from this software without specific
prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.


**************************************************
Revision History / Change Log:

**************************************************
Notes:

*******************************************************************************/


package com.cuesta.web.tags;

import com.cuesta.util.Helper;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * ComboTag.java
 *
 * Created on 2 de marzo de 2007, 23:56
 * @author Manuel Camilo Cuesta
 */
public class ComboTag extends TagSupport {
    
    private String name;
    private String listName;
    private String selected;
    private String value;
    private String label;
    private String addNull;
    private String nullValue;

    private String elementId;
    private String style ;
    
    
    public int doStartTag() throws JspException {
        
        JspWriter out = pageContext.getOut();
        HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
        
        establecerLista();

        if ( selected != null && request.getAttribute(selected) != null )
            selected = ( request.getAttribute(selected) ).toString();
        else if ( selected == null )
            selected = "";
        
        
        try
        {
            out.print( this.getHtml() );
        }
        catch (Exception ex) 
        {
            
            ex.printStackTrace();
        }
        
        /*
        this.itemList.clear();
        this.itemList = null;
        */
        
        return SKIP_BODY;
    }


    /**
     * Getter for property value.
     * @return Value of property value.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Setter for property value.
     * @param value New value of property value.
     */
    public void setValue(String value) {
        this.value = value;
    }


    /**
     * Getter for property label.
     * @return Value of property label.
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Setter for property label.
     * @param label New value of property label.
     */
    public void setLabel(String label) {
        this.label = label;
    }


    /**
     * Getter for property selected.
     * @return Value of property selected.
     */
    public String getSelected() {
        return this.selected;
    }

    /**
     * Setter for property selected.
     * @param selected New value of property selected.
     */
    public void setSelected(String selected) {
        this.selected = selected;
    }


    /**
     * Getter for property list.
     * @return Value of property list.
     */
    public String getList() {
        return this.listName;
    }

    /**
     * Setter for property list.
     * @param list New value of property list.
     */
    public void setList(String list) {
        this.listName = list;
    }
    


    /**
     * Getter for property name.
     * @return Value of property name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for property name.
     * @param name New value of property name.
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Getter for property addNull.
     * @return Value of property addNull.
     */
    public String isAddNull() {
        return this.addNull;
    }

    /**
     * Setter for property addNull.
     * @param addNull New value of property addNull.
     */
    public void setAddNull(String addNull) {
        this.addNull = addNull;
    }

    /**
     * Holds value of property onchange.
     */
    private String onchange;

    /**
     * Getter for property onchange.
     * @return Value of property onchange.
     */
    public String getOnchange() {
        return this.onchange;
    }

    /**
     * Setter for property onchange.
     * @param onchange New value of property onchange.
     */
    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }

    /**
     * Holds value of property size.
     */
    private String size;

    /**
     * Getter for property size.
     * @return Value of property size.
     */
    public String getSize() {
        return this.size;
    }

    /**
     * Setter for property size.
     * @param size New value of property size.
     */
    public void setSize(String size) {
        this.size = size;
    }


    /**
     * Getter for property html.
     * @return Value of property html.
     */
    public String getHtml() {
        
        StringBuilder html = new StringBuilder();
        
        try
        {
            
            if ( onchange == null )
                onchange = "";
            if ( size == null )
                size = "";

            String idStr = "";
            if ( elementId != null )
                idStr = " id='" + elementId + "' " ;

            String styleStr = "";
            if ( style != null )
                styleStr = " style='" + style + "' " ;
            
            html.append("<select " + styleStr + idStr + "name='" + name + "' onchange='" + onchange + "' size='" + size + "' >\n");
            
            if ( nullValue == null )
                nullValue = "null";
                
            
            if ( addNull == null )
                html.append("<option value='" + nullValue + "' ></option>\n");
            else
            {
                if ( addNull.equalsIgnoreCase("true") )
                    html.append("<option value='" + nullValue + "' ></option>\n");
            }
            
                

            Iterator ite = itemList.iterator();
            String selectedStr = "";
            while ( ite.hasNext() )
            {
                Object o = ite.next();
                Object valueObject = Helper.getBeanValue(o,value);
                Object labelObject = Helper.getBeanValue(o,label);
                
                
                if ( selected != null && selected.equalsIgnoreCase( valueObject.toString() ) )
                    selectedStr = "selected";
                else 
                    selectedStr = "";
                
                html.append("<option value='" + valueObject + "' " + selectedStr + " >" + labelObject + "</option>\n" );

            }
            
            html.append("</select>\n");
            
            return html.toString();
            
            
        }
        catch (Exception ex) 
        {
            ex.printStackTrace();
            return null;
        }
        
        
    }

    /**
     * Holds value of property itemList.
     */
    private Collection itemList;

    /**
     * Getter for property itemList.
     * @return Value of property itemList.
     */
    public Collection getItemList() {
        return this.itemList;
    }

    /**
     * Setter for property itemList.
     * @param itemList New value of property itemList.
     */
    public void setItemList(Collection itemList) {
        this.itemList = itemList;
    }
    
    public void establecerLista() {
        
        if ( collection != null )
        {
            itemList = collection;
            collection = null;
            return;
        }
        
        HttpSession session = pageContext.getSession();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        ServletContext context = pageContext.getServletContext();
        

        if ( scope == null )
        {
            if ( request.getAttribute(listName) != null )
                itemList = (Collection) request.getAttribute(listName);
            else if ( pageContext.getAttribute(listName) != null )
                itemList = (Collection) pageContext.getAttribute(listName);
            else if ( session.getAttribute(listName) != null )
                itemList = (Collection) session.getAttribute(listName);
            else if ( context.getAttribute(listName) != null )
                itemList = (Collection) context.getAttribute(listName);
            else 
                itemList = null;
        }
        else if ( scope.equals("request") )
            itemList = (Collection) request.getAttribute(listName);
        else if ( scope.equals("page") )
            itemList = (Collection) pageContext.getAttribute(listName);
        else if ( scope.equals("session") )
            itemList = (Collection) session.getAttribute(listName);
        else if ( scope.equals("context") )
            itemList = (Collection) context.getAttribute(listName);
        else 
            itemList = null;
    }

    /**
     * Holds value of property scope.
     */
    private String scope;

    /**
     * Getter for property scope.
     * @return Value of property scope.
     */
    public String getScope() {
        return this.scope;
    }

    /**
     * Setter for property scope.
     * @param scope New value of property scope.
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * Holds value of property collection.
     */
    private Collection collection;

    /**
     * Getter for property collection.
     * @return Value of property collection.
     */
    public Collection getCollection() {
        return this.collection;
    }

    /**
     * Setter for property collection.
     * @param collection New value of property collection.
     */
    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public String getNullValue() {
        return nullValue;
    }

    public void setNullValue(String nullValue) {
        this.nullValue = nullValue;
    }

    /**
     * @return the elementId
     */
    public String getElementId() {
        return elementId;
    }

    /**
     * @param elementId the elementId to set
     */
    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    /**
     * @return the style
     */
    public String getStyle() {
        return style;
    }

    /**
     * @param style the style to set
     */
    public void setStyle(String style) {
        this.style = style;
    }


}
