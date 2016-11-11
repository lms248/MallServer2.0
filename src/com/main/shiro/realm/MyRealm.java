package main.shiro.realm;

import java.util.List;

import javax.management.relation.Role;

import main.bean.admin.User;
import main.service.admin.AdminUserService;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class MyRealm extends AuthorizingRealm {

	@Autowired
    private AdminUserService adminUserService;

	/** 
     * 权限认证，获取登录用户的权限
     */  
    @Override  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {  
        String username = (String) principalCollection.fromRealm(getName()).iterator().next();  
        //此处连库匹配了登录用户的数据，具体怎么做，需要根据个人需求而定
        User user = adminUserService.userContentByName.get(username);
        List<Role> list = user.getRoleList();
        if(user!=null){  
            SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();  
            //获取用户的角色名称
            info.setRoles(user.getRolesName());  
            //获取用户的权限
            List<Role> roleList=user.getRoleList();  
            for (Role role : roleList) {  
                info.addStringPermissions(role.getPermissionsName());  
            }  
            return info;  
        }  
        return null;  
    }  
  
    /** 
     * 登录认证，创建用户的登录信息
     */  
    @Override  
    protected AuthenticationInfo doGetAuthenticationInfo(  
            AuthenticationToken authenticationToken) throws AuthenticationException {  
        UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;  
        //判断用户登录状态
        User user = adminUserService.userContentByName.get(token.getUsername());  
        if(user != null){  
            //保存用户登录信息到认证中
            return new SimpleAuthenticationInfo(user.getName(), user.getPassword(), getName());  
        }  
        return null;  
    }
}
