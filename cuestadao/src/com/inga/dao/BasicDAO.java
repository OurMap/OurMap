/**

com.inga.dao.BasicDAO.java
Version: 1.0

********************************************************************************
Author:
Manuel Cuesta, programmer <camilocuesta@hotmail.com>

**************************************************

cuestadao is Copyright (c) 2010, Manuel Cuesta  <camilocuesta@hotmail.com >
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

Neither the name of Manuel Cuesta nor the names of its contributors may be
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



package com.inga.dao;

import com.inga.exception.BDException;
import com.inga.exception.NoConnectionException;
import com.inga.security.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * BasicDAO.java
 *
 * Created on Jan 30th 2007, 10:32
 *
 * Esta clase ofrece funciones básicas para los dao. Para aprender mas del patrón
 *  dao, siga este vínculo: <a href="http://java.sun.com/blueprints/corej2eepatterns/Patterns/DataAccessObject.html" >Dao con la estrategia Factory Method</a>, 
 *  <br/>
 *  <br/>
 *  Dado que todos los dao necesitan de funciones básicas de manejo de detalles de
 *  ejecución de sentencias sql, y manejo de transacciones, estas funciones se condensan
 *  en un solo lugar: esta clase. 
 *  En situaciones especiales donde se requiera mas flexibilidad, se puede usar la clase com.inga.dao.Sentencia directamente
 *  para operaciones en la base de datos. 
 *  <br/><br/>
 *  Los que hereden de esta clase, y que deseen ofrecer funciones de búsqueda o lectura de registros,
 *  deben preocuparse solo por la extracción de registros
 *  desde el ResultSet hasta el DTO correspondiente. Esto consiste en sobreescribir el método extract(ResultSet), 
 *  que es el único método que no es final, y además es abstract, esto significa que 
 *  se puede, y se debe sobreescribir.
 *  <br/><br/>
 *  Esta clase ha sido diseñada únicamente para ser heredada
 *  por los daos, para que puedan beneficiarse de sus métodos los 
 *  cuales contienen la funcionalidad básica para un dao. 
 *  <br/><br/>
 *  La funcionalidad básica ofrecida por esta clase es la siguiente:
 * <br/><br/>
 *  <list>
 *  <li>Se encarga de manejar los detalles de ejecución de comandos sql. Por 
 *  medio de los métodos executeQuery y executeUpdate, los que heredan esta clase 
 *  solo tienen que armar la sentencia sql, sin preocuparse de los detalles de
 *  ejecución como manejo de excepciones o limpieza de recursos. 
 * <li>Ofrece un nivel de programación por encima de clases como java.sql.Connection, 
 *  java.sql.ResultSet, y com.inga.dao.Sentencia.
 * <li>Implementa la interfaz ConnectionUser, que facilita la elaboración de 
 *  funciones de no repudiación, es decir el historial de las acciones del usuario. 
 *  </list>
 *  
 *   
 *
 * @author Camilo Cuesta
 */
public abstract class BasicDAO implements ConnectionUser {
    
    protected java.sql.Connection conn;
    protected User user;
    
    /** Use este método para extraer los registros en el ResultSet, y convertirlos
     *  en objetos DTOs. 
     *  <br/><br/>
     *  La implementación de este método abstracto consiste en leer cada registro 
     *  del ResultSet, extraer las columnas necesarias, y crear un objeto DTO asignándole
     *  las características del registro de tal forma que el DTO modele el registro. 
     *  <br/><br/>
     *  Ejemplo: 
     *  <br/><br/>
     *  La tabla de la base de datos "items" tiene las siguientes columnas: <br/>
     *  number  codigo<br/>
     *  varchar item<br/>
     *  date    fecha<br/>
     *  varchar descripcion<br/>
     *  <br/>
     *  El código del DTO es el siguiente:<br/>
     *  <br/>
     *  class Item() {<br/>
     *  <br/>
     *  private Integer codigo;<br/>
     *  private String item; <br/>
     *  private java.util.Date fecha<br/>
     *  <br/>
     *  public Item() {<br/>
     *  codigo = null;<br/>
     *  item = null; <br/>
     *  fecha = new java.util.Date();<br/>
     *  }<br/> 
     * 
     *  public void setCodigo(Integer pCodigo) {<br/>
     *    codigo = pCodigo;<br/>
     *  }<br/>
     * <br/>
     *  public Integer getCodigo() {<br/>
     *    return codigo;<br/>
     *  }<br/>
     * <br/>
     *  public void setItem(String pItem) {<br/>
     *    item = pItem;<br/>
     *  }<br/>
     *  <br/>
     *  public String getItem() {<br/>
     *     return item;<br/>
     *  }<br/>
     *  <br/>
     *  public void setFecha( java.util.Date pFecha) {<br/>
     *    fecha = pFecha;<br/>
     *  }<br/>
     *  <br/>
     *  public java.util.Date getFecha() {<br/>
     *    return fecha;<br/>
     *  }<br/>
     *  }<br/>
     * <br/>
     *  <b>Por lo tanto, una implementación adecuada  de esta método sería:</b><br/>
     *  <br/>

    protected Vector extract(ResultSet rs ) throws BDException {<br/>
        Vector<Item> results = new Vector<Item>();<br/>
        try<br/>
        {<br/>
            while ( rs.next() )<br/>
            {<br/>
                try<br/>
                {<br/>
                    Item o = new Item();<br/>
                    <br/>
                    o.setCodigo( new Integer(rs.getInt("CODIGO")) );<br/>
                    o.setItem( rs.getString("ITEM") );<br/>
                    o.setFecha( new Integer(rs.getTimestamp("FECHA")) );<br/>
                    <br/>
                    results.add( o );<br/>
                }<br/>
                catch (Exception e)<br/>
                {<br/>
                    e.printStackTrace();<br/>
                }<br/>
            }<br/>
            rs.close();<br/>
        }<br/>
        catch ( SQLException e )<br/>
        {<br/>
            throw new BDException(e);<br(>
        }<br/>
        <br/>
        return results;<br/>
    }<br/>
     */
    protected abstract Vector extract(ResultSet rs) throws BDException ;
    
    /** Ejecuta una sentencia sql, que retorne un listado de registros
     * 
     * @param sql Sentencia sql 
     * @return Vector de objetos que modelan cada registro. Normalmente 
     *         este no es vector de objetos java.lang.Object, sino de objetos del tipo del dto. 
     * @throws com.inga.exception.BDException
     * @throws com.inga.exception.NoConnectionException
     */
    protected final Vector executeQuery(String sql) throws BDException, NoConnectionException {
        Sentencia s = new Sentencia(user,conn);
        ResultSet rs = s.executeQuery(sql);
        Vector results = extract( rs );
        s.close();
        return results;
    }
    
    /** Ejecuta una sentencia sql que no retorne listados, y también
     *  sentencias DDL. 
     * 
     *  Ejecuta una sentencia sql que no retorne listados, , como INSERT
     *  UPDATE, DELETE, sentencias que no retornen nada como sentencias DDL.
     * 
     * @param sql
     * @return
     * @throws com.inga.exception.NoConnectionException
     * @throws com.inga.exception.BDException
     */
    protected final int executeUpdate(String sql) throws NoConnectionException, BDException {
        Sentencia s = new Sentencia(user,conn);
        int rows =  s.executeUpdate(sql);
        s.close();
        return rows;
    }
    
    
    @Override
    public final void setConnection(Connection pConn ) {
        conn = pConn;
    }
    
    @Override
    public final java.sql.Connection getConnection() {
        return conn;
    }
    
    @Override
    public final void setUser(User pUser) {
        user = pUser;
    }
    
    @Override
    public final User getUser() {
        return user;
    }
    
}


