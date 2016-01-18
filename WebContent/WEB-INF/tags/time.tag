<%@tag import="java.util.Date"%>
<%@tag import="com.example.utils.DateUtil"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="time" type="java.lang.Long" required="true"%>
<%=(time==null)?0:DateUtil.getDateTimeStr(new Date(time))%>