<%-- 
    Document   : newjsp
    Created on : 16-feb-2010, 7:09:55
    Author     : Camilo
--%>

<%@ page  pageEncoding="UTF-8" %>
<%@ page import="com.bnmi.ourmap.model.*" %>
<%@ page import="com.bnmi.ourmap.web.Constantes" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/ourmaptags.tld" prefix="easy" %>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insert template="layout.jsp">
  <tiles:put name="header" value="header.jsp" type="page" />
  <tiles:put name="leftMenu" value="leftMenu.jsp" type="page" />
  <tiles:put name="content" type="string"  >
<!-- CONTENT HERE _________________________________________________________________ -->
THIS PAGE WORKS
<!-- CONTENT ENDS HERE ____________________________________________________________ -->
  </tiles:put>
</tiles:insert>

