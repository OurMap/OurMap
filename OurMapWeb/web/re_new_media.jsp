<%-- 
    Document   : re_new_media
    Created on : 06-ene-2010, 21:59:52
    Author     : Camilo
--%>

<%@ page  pageEncoding="UTF-8" %>
<!DOCTYPE html >
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>


<logic:present name="mediaObject" scope="request" >
<bean:define id="mo" name="mediaObject"  scope="request" toScope="page" type="EasyObject" />
<script language="javascript" >
    parent.onObjectUploaded( );
</script>
</logic:present>
<logic:notPresent name="mediaObject" scope="request" >
</logic:notPresent>
