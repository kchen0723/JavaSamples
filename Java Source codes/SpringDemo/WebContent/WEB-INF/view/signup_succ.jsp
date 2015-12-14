<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
   注册成功欢迎：${userName}
   <br/>
   点 <a href="javascript:void(0);"  onclick="javascript:self.location='<%=request.getContextPath() %>/login.htm'">这里</a>登录
  </body>
</html>
