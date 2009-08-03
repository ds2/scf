/**
 * 
 */
package ds2.enterspace.rules.impl;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author kaeto23
 * 
 */
@WebService(name = "SoCoFo", serviceName = "SoCoFo", targetNamespace = "http://www.ds2/ns/socofo")
public class WsDummy {

	/**
	 * 
	 */
	public WsDummy() {
		// TODO Auto-generated constructor stub
	}

	@WebMethod
	public XmlFormatRulesXml dummyMethod() {
		return null;
	}

}
