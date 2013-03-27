package com.ltxc.google.csms.aa;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import com.ltxc.google.csms.server.domain.User;
import com.ltxc.google.csms.server.servlet.utils.LdapGroupAuthenticator;
import com.ltxc.google.csms.shared.SharedConstants;

public class TestAD {

	@Test
	public void test() {
		String username = "jlu";
		String password = "Firewood&10";
		try{
			User user = LdapGroupAuthenticator.authenticate(SharedConstants.ActiveDirectory_DOMAIN, username, password);
			Assert.assertTrue(user.isAuthorized());
		}catch(Exception xe)
		{
			xe.printStackTrace();
			Assert.assertFalse(xe.getMessage(), true);
			
		}
	}

}
