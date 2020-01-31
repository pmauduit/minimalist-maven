package fr.spironet;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

public class Ldap {

    public static void main(String[] args) {
     DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource("ldap://localhost:3389/dc=georchestra,dc=org");
     contextSource.setAnonymousReadOnly(true);
     contextSource.setCacheEnvironmentProperties(false);
     LdapTemplate ldapTemplate = new LdapTemplate(contextSource);
     
     List lst = ldapTemplate.search(
				"ou=users", "(uid=testadmin)",
				SearchControls.ONELEVEL_SCOPE, new String[] {"memberOf"},
				new AttributesMapper() {
					@Override
					public Object mapFromAttributes(Attributes attributes) throws NamingException {
						return Collections.list(attributes.get("memberOf").getAll())
								.stream().collect(Collectors.toList());
					}
				});
     System.out.println(lst.get(0));
     return;
    }


}
