<%-- 
    Document   : editdisplaykeywords
    Created on : 25-abr-2010, 21:44:05
    Author     : Manuel Camilo Cuesta
--%>

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insert template="layout_form1.jsp">
  <tiles:put name="header" value="map_header.jsp" type="page" />
  <tiles:put name="leftMenu" value="mapconfig_menu2.jsp" type="page" />
  <tiles:put name="content" type="string"  >
   <script type="text/javascript" src="ajax.js"></script>
   <script type="text/javascript" >
   </script>



<html:form action="editdisplaykeywords" method="post" >
    
        <input type="hidden" name="formSubmited" value="true" />
        <input type="hidden" name="mapid" value="<bean:write name="map" property="mapid" />" />
        
<span class="Estilo6" >EDIT THE DISPLAY SETTING FOR KEYWORDS AND CATEGORIES</span>
<br/>
<br/>
<p class="Estilo1">You have the option to keep your Keywords/Categories hidden or see them displayed in the Layers menu on the left side of your map. If you choose to display them, then any map Layer containing at least one hotspot with a given Keyword will show that Keyword as a clickable &ldquo;sub-layer&rdquo;.</p>
<p class="Estilo1">&nbsp;</p>


<p class="Estilo8">Choose Your Display Option:</p>
<p class="Estilo1"><br>
</p>
<p class="Estilo1">

Do you want your Keywords and, if applicable, Categories to be displayed in the Layers menu on the left side of your map?

    <br>
</p>

<logic:iterate id="choice" name="choices" >
  <html:radio property="dsKs" idName="choice" value="value" />
  <bean:write name="choice" property="label"/>
  <br>
</logic:iterate>


        <br/>
        <br/>
        <button type="button" onclick="document.location='mapconfiguration.do?mapid=<bean:write name="map" property="mapid" />'" >Cancel</button>
        <button type="submit"  >Save</button>
    </html:form>




  </tiles:put>

        <tiles:put name="sidearea" type="string" >
            
        </tiles:put>

</tiles:insert>


