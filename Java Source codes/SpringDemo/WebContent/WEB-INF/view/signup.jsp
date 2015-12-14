<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
  <h3>用户注册</h3>
    <form action="<%=request.getContextPath() %>/usermgr/saveSignup.htm" method="post">
      <input type="text" name="userName" >
      <input type="password" name="pwd"/>
      <input type="submit"/>
    </form>
  </body>
</html>
