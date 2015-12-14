<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
   注册成功欢迎：${userName}
   
   你可以：
   </br>查看用户列表:
   <a href="javascript:void(0)" 
   onclick="avascript:self.location='<%=request.getContextPath() %>/usermgr/list.htm'">查看</a>
   <br>
   <a href="javascript:void(0)" 
   onclick="avascript:self.location='<%=request.getContextPath() %>/logout.htm'">退出</a>
  </body>
</html>
