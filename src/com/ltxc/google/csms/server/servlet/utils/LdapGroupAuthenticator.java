package com.ltxc.google.csms.server.servlet.utils;
import java.text.MessageFormat;
import java.util.*;    
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.*;    
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import com.ltxc.google.csms.server.domain.User;
import com.ltxc.google.csms.shared.SharedConstants;


public class LdapGroupAuthenticator {
	
	private static Logger logger = Logger
			.getLogger(LdapGroupAuthenticator.class.getName());
	
    public static final String DISTINGUISHED_NAME = "distinguishedName";
    public static final String CN = "cn";
    public static final String MEMBER = "member";
    public static final String MEMBER_OF = "memberOf";
    public static final String DISPLAY_NAME = "displayName";
    public static final String SEARCH_BY_SAM_ACCOUNT_NAME = "(SAMAccountName={0})";
    //public static final String SEARCH_BY_SAM_ACCOUNT_NAME = "(&(objectClass=person)(SAMAccountName={0})(!(objectClass=computer)))";
    public static final String SEARCH_GROUP_BY_GROUP_CN = "(&(objectCategory=group)(cn={0}))";
    
    /*
     * Prepares and returns CN that can be used for AD query
     * e.g. Converts "CN=**Dev - Test Group" to "**Dev - Test Group"
     * Converts CN=**Dev - Test Group,OU=Distribution Lists,DC=DOMAIN,DC=com to "**Dev - Test Group"
     */
    public static String getCN(String cnName) {
        if (cnName != null && cnName.toUpperCase().startsWith("CN=")) {
            cnName = cnName.substring(3);
        }
        int position = cnName.indexOf(',');
        if (position == -1) {
            return cnName;
        } else {
            return cnName.substring(0, position);
        }
    }
    public static boolean isSame(String target, String candidate) {
        if (target != null && target.equalsIgnoreCase(candidate)) {
            return true;
        }
        return false;
    }

    public static User authenticate(String domain, String username, String password) throws NamingException {
        Hashtable<String, String> env = new Hashtable<String,String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL,SharedConstants.ActiveDirectory_LADPURL);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, SharedConstants.ActiveDirectory_DOMAIN + "\\" + username);
        env.put(Context.SECURITY_CREDENTIALS, password);
        DirContext ctx = null;
        User user = new User(username);
        try {
            ctx = new InitialDirContext(env);
            user.setAuthenticated(true);
            // userName is SAMAccountName
            SearchResult sr = executeSearchSingleResult(ctx, SearchControls.SUBTREE_SCOPE, SharedConstants.ActiveDirectory_DefaultSearchBase,
                    MessageFormat.format( SEARCH_BY_SAM_ACCOUNT_NAME, new Object[] {username}),
                    new String[] {DISTINGUISHED_NAME, CN, MEMBER_OF, SharedConstants.ActiveDirectory_Attribute_AAAcount, DISPLAY_NAME}
                    );

            String groupCN = getCN(SharedConstants.ActiveDirectory_GroupDistinguishName);
            HashMap<String,String> processedUserGroups = new HashMap<String,String>();
            HashMap<String,String> unProcessedUserGroups = new HashMap<String,String>();
            //set attribute
            String aaacount = getAttributeStringValue(sr.getAttributes().get(SharedConstants.ActiveDirectory_Attribute_AAAcount));
            if(aaacount!=null)
            	user.setAaacount(aaacount);
            else
            	user.setAaacount(user.getUserid());
            user.setDisplayname(getAttributeStringValue(sr.getAttributes().get(DISPLAY_NAME)));

            // Look for and process memberOf
            Attribute memberOf = sr.getAttributes().get(MEMBER_OF);
            if (memberOf != null) {
                for ( Enumeration e1 = memberOf.getAll() ; e1.hasMoreElements() ; ) {
                    String unprocessedGroupDN = e1.nextElement().toString();
                    String unprocessedGroupCN = getCN(unprocessedGroupDN);
                    // Quick check for direct membership
                    if (isSame (groupCN, unprocessedGroupCN) && isSame (SharedConstants.ActiveDirectory_GroupDistinguishName, unprocessedGroupDN)) {
                        logger.log(Level.INFO, username + " is authorized.");
                        user.setAuthorized(true);
                        return user;
                    } else {
                        unProcessedUserGroups.put(unprocessedGroupDN, unprocessedGroupCN);
                    }
                }
                if (userMemberOf(ctx, SharedConstants.ActiveDirectory_DefaultSearchBase, processedUserGroups, unProcessedUserGroups, groupCN, SharedConstants.ActiveDirectory_GroupDistinguishName)) {
                	logger.log(Level.INFO, username + " is authorized.");
                    user.setAuthorized(true);
                    return user;
                }
            }

            logger.log(Level.INFO, username + " is NOT authorized.");
            return user;
        } catch (AuthenticationException e) {
        	logger.log(Level.WARNING, username + " is NOT authenticated");
            return user;
        } catch (NamingException e) {
            throw e;
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                    throw e;
                }
            }
        }
    }
    
    public static String getAttributeStringValue(Attribute attribute)
    {
    	String value = null;
    	if(attribute!=null)
    	{
    		try{
    		Object v = attribute.get();
    		if(v!=null)
    			value = v.toString();
    		}catch(Exception xe)
    		{
    			xe.printStackTrace();
    		}
    	}
    	
    	return value;
    }

    public static boolean userMemberOf(DirContext ctx, String searchBase, HashMap processedUserGroups, HashMap unProcessedUserGroups, String groupCN, String groupDistinguishedName) throws NamingException {
        HashMap newUnProcessedGroups = new HashMap();
        for (Iterator entry = unProcessedUserGroups.keySet().iterator(); entry.hasNext();) {
            String  unprocessedGroupDistinguishedName = (String) entry.next();
            String unprocessedGroupCN = (String)unProcessedUserGroups.get(unprocessedGroupDistinguishedName);
            if ( processedUserGroups.get(unprocessedGroupDistinguishedName) != null) {
            	logger.log(Level.INFO, "Found  : " + unprocessedGroupDistinguishedName +" in processedGroups. skipping further processing of it..." );
                // We already traversed this.
                continue;
            }
            if (isSame (groupCN, unprocessedGroupCN) && isSame (groupDistinguishedName, unprocessedGroupDistinguishedName)) {
            	logger.log(Level.INFO, "Found Match DistinguishedName : " + unprocessedGroupDistinguishedName +", CN : " + unprocessedGroupCN );
                return true;
            }
        }

        for (Iterator entry = unProcessedUserGroups.keySet().iterator(); entry.hasNext();) {
            String  unprocessedGroupDistinguishedName = (String) entry.next();
            String unprocessedGroupCN = (String)unProcessedUserGroups.get(unprocessedGroupDistinguishedName);

            processedUserGroups.put(unprocessedGroupDistinguishedName, unprocessedGroupCN);

            // Fetch Groups in unprocessedGroupCN and put them in newUnProcessedGroups
            NamingEnumeration ns = executeSearch(ctx, SearchControls.SUBTREE_SCOPE, searchBase,
                    MessageFormat.format( SEARCH_GROUP_BY_GROUP_CN, new Object[] {unprocessedGroupCN}),
                    new String[] {CN, DISTINGUISHED_NAME, MEMBER_OF, SharedConstants.ActiveDirectory_Attribute_AAAcount});

            // Loop through the search results
            while (ns.hasMoreElements()) {
                SearchResult sr = (SearchResult) ns.next();

                // Make sure we're looking at correct distinguishedName, because we're querying by CN
                String userDistinguishedName = sr.getAttributes().get(DISTINGUISHED_NAME).get().toString();
                if (!isSame(unprocessedGroupDistinguishedName, userDistinguishedName)) {
                	logger.log(Level.INFO, "Processing CN : " + unprocessedGroupCN + ", DN : " + unprocessedGroupDistinguishedName +", Got DN : " + userDistinguishedName +", Ignoring...");
                    continue;
                }

                logger.log(Level.INFO, "Processing for memberOf CN : " + unprocessedGroupCN + ", DN : " + unprocessedGroupDistinguishedName);
                // Look for and process memberOf
                Attribute memberOf = sr.getAttributes().get(MEMBER_OF);
                if (memberOf != null) {
                    for ( Enumeration e1 = memberOf.getAll() ; e1.hasMoreElements() ; ) {
                        String unprocessedChildGroupDN = e1.nextElement().toString();
                        String unprocessedChildGroupCN = getCN(unprocessedChildGroupDN);
                        logger.log(Level.INFO, "Adding to List of un-processed groups : " + unprocessedChildGroupDN +", CN : " + unprocessedChildGroupCN);
                        newUnProcessedGroups.put(unprocessedChildGroupDN, unprocessedChildGroupCN);
                    }
                }
            }
        }
        if (newUnProcessedGroups.size() == 0) {
        	logger.log(Level.INFO, "newUnProcessedGroups.size() is 0. returning false...");
            return false;
        }

        //  process unProcessedUserGroups
        return userMemberOf(ctx, searchBase, processedUserGroups, newUnProcessedGroups, groupCN, groupDistinguishedName);
    }

    private static NamingEnumeration executeSearch(DirContext ctx, int searchScope,  String searchBase, String searchFilter, String[] attributes) throws NamingException {
        // Create the search controls
        SearchControls searchCtls = new SearchControls();

        // Specify the attributes to return
        if (attributes != null) {
            searchCtls.setReturningAttributes(attributes);
        }

        // Specify the search scope
        searchCtls.setSearchScope(searchScope);

        // Search for objects using the filter
        NamingEnumeration result = ctx.search(searchBase, searchFilter,searchCtls);
        return result;
    }

    private static SearchResult executeSearchSingleResult(DirContext ctx, int searchScope,  String searchBase, String searchFilter, String[] attributes) throws NamingException {
        NamingEnumeration result = executeSearch(ctx, searchScope,  searchBase, searchFilter, attributes);

        SearchResult sr = null;
        // Loop through the search results
        while (result.hasMoreElements()) {
            sr = (SearchResult) result.next();
            break;
        }
        return sr;
    }
}