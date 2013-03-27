package com.ltxc.google.csms.server.servlet.utils;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.ws.rs.WebApplicationException;

import com.ltxc.google.csms.server.domain.User;


public class ADClient {

	private LdapContext ldapContext;
	

	
	public ADClient()  {
		

	}
	


	private User findUser(String url, String username, String password) throws NamingException {
		LdapContext ctx = null;
		User user = new User(username);
		Hashtable<String,String> env = new Hashtable<String,String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION, "Simple");
		// it can be <domain\\userid> something that you use for windows
		// login
		// it can also be
		env.put(Context.SECURITY_PRINCIPAL, username);
		env.put(Context.SECURITY_CREDENTIALS, password);
		// in following property we specify ldap protocol and connection
		// url.
		// generally the port is 389
		env.put(Context.PROVIDER_URL, url);
		try {

			ctx = new InitialLdapContext(env, null);
			//authenticated
			user.setAuthenticated(true);			
			System.out.println("Connection Successful.");
			
			//authorization, search group the user belongs to
			SearchControls groupsSearchCtls = new SearchControls();
			groupsSearchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			
			
		} catch (NamingException nex) {
			System.out.println("LDAP Connection: FAILED");
			nex.printStackTrace();
			throw nex;
		}
		return user;
	}

}
