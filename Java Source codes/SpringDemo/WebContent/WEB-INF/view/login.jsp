<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
  <h3>用户登录</h3>
  ${fail_key }<br/>
    <form action="<%=request.getContextPath() %>/saveLogin.htm" method="post">
      username:<input type="text" name="userName" ><br/>
      password:<input type="password" name="pwd"/><br/>
      <input type="submit"/>
    </form>
    <br/>
   <a href="javascript:void(0);" 
   onclick="javascript:self.location='<%=request.getContextPath() %>/usermgr/signup.htm'">注册</a>
  </body>
</html>
