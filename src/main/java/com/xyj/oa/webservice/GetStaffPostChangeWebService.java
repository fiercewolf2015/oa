package com.xyj.oa.webservice;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;

import com.xyj.oa.util.LogUtil;
import com.xyj.oa.workflow.entity.WorkFlowInstance;
import com.xyj.oa.workflow.service.WorkFlowService;

@Component
public class GetStaffPostChangeWebService {

	private static Logger logger = LoggerFactory.getLogger(GetStaffPostChangeWebService.class);

	private final String wsdlUrl = "http://59.110.67.70:8080/ormrpc/services/WSFluctuationAuditFacade?wsdl";

	@Autowired
	private WorkFlowService workFlowService;

	public void doPost(WorkFlowInstance flowInstance) {
		Map<String, Object> endPostChange = workFlowService.endPostChange(flowInstance);
		try {
			SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
			SOAPConnection sc = scf.createConnection();
			URL urlEndpoint = new URL(wsdlUrl);
			MessageFactory messageFactory = MessageFactory.newInstance();
			SOAPMessage message = messageFactory.createMessage();
			SOAPPart soapPart = message.getSOAPPart();
			SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
			SOAPBody soapBody = soapEnvelope.getBody();
			QName qname = new QName("http://schemas.xmlsoap.org/soap/encoding/", "setAuditResult", "web");
			SOAPBodyElement addBodyElement = soapBody.addBodyElement(qname);
			addBodyElement.addChildElement("BillNumber").setValue(endPostChange.get("BillNo").toString());
			addBodyElement.addChildElement("AuditResult").setValue(endPostChange.get("ApprResult").toString());
			addBodyElement.addChildElement("AuditView").setValue(endPostChange.get("ApprView").toString());
			message.saveChanges();
			message.getMimeHeaders().addHeader("SOAPAction", "\"\"");
			SOAPMessage response = sc.call(message, urlEndpoint);
			if (response != null) {
				Node node = response.getSOAPPart().getElementsByTagName("setAuditResultReturn").item(0).getFirstChild();
				if (node == null)
					logger.error("No response received from SESSIONID!");
				else {
					String nodeValue = node.getNodeValue();
					String[] split = nodeValue.split(" ");
					if (split == null || split.length == 0) {
						System.err.println("失败");
					} else {
						if ("0".equals(split[0]))
							logger.info("成功");
						else
							logger.error(split[1]);
					}
				}
			} else {
				logger.info("No response received from SESSIONID!");
			}
			sc.close();
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
	}

	public boolean logout() throws SOAPException, MalformedURLException {
		SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
		SOAPConnection sc = scf.createConnection();
		URL urlEndpoint = new URL("http://59.110.67.70:8080/ormrpc/services/EASLogin?wsdl");
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage message = messageFactory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
		SOAPBody soapBody = soapEnvelope.getBody();
		QName qname = new QName("http://schemas.xmlsoap.org/soap/encoding/", "logout", "log");
		SOAPBodyElement addBodyElement = soapBody.addBodyElement(qname);
		addBodyElement.addChildElement("userName").setValue("test");
		addBodyElement.addChildElement("slnName").setValue("eas");
		addBodyElement.addChildElement("dcName").setValue("sHR002");
		addBodyElement.addChildElement("language").setValue("L2");
		message.saveChanges();
		message.getMimeHeaders().addHeader("SOAPAction", "\"\"");
		SOAPMessage response = sc.call(message, urlEndpoint);
		if (response != null) {
			Node node = response.getSOAPPart().getElementsByTagName("logoutReturn").item(0).getFirstChild();
			sc.close();
			if (node == null)
				return false;
			return true;
		}
		sc.close();
		return false;
	}

	public String login() throws SOAPException, MalformedURLException {
		SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
		SOAPConnection sc = scf.createConnection();
		URL urlEndpoint = new URL("http://59.110.67.70:8080/ormrpc/services/EASLogin?wsdl");
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage message = messageFactory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
		SOAPBody soapBody = soapEnvelope.getBody();
		QName qname = new QName("http://schemas.xmlsoap.org/soap/encoding/", "login", "log");
		SOAPBodyElement addBodyElement = soapBody.addBodyElement(qname);
		addBodyElement.addChildElement("userName").setValue("test");
		addBodyElement.addChildElement("password").setValue("test");
		addBodyElement.addChildElement("slnName").setValue("eas");
		addBodyElement.addChildElement("dcName").setValue("sHR002");
		addBodyElement.addChildElement("language").setValue("L2");
		addBodyElement.addChildElement("dbType").setValue("2");
		message.saveChanges();
		message.getMimeHeaders().addHeader("SOAPAction", "\"\"");
		SOAPMessage response = sc.call(message, urlEndpoint);
		if (response != null) {
			Node node = response.getSOAPPart().getElementsByTagName("sessionId").item(0).getFirstChild();
			sc.close();
			if (node == null)
				return null;
			return node.getNodeValue();
		}
		sc.close();
		return null;
	}

	public int transData() throws SOAPException, MalformedURLException {
		SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
		SOAPConnection sc = scf.createConnection();
		URL urlEndpoint = new URL("http://59.110.67.70:8080/ormrpc/services/WSFluctuationAuditFacade?wsdl");
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage message = messageFactory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
		SOAPBody soapBody = soapEnvelope.getBody();
		QName qname = new QName("http://schemas.xmlsoap.org/soap/encoding/", "setAuditResult", "web");
		SOAPBodyElement addBodyElement = soapBody.addBodyElement(qname);
		addBodyElement.addChildElement("BillNumber").setValue("20170828-00002");
		addBodyElement.addChildElement("AuditResult").setValue("2");
		addBodyElement.addChildElement("AuditView").setValue("123");
		message.saveChanges();
		message.getMimeHeaders().addHeader("SOAPAction", "\"\"");
		SOAPMessage response = sc.call(message, urlEndpoint);
		Node node = response.getSOAPPart().getElementsByTagName("setAuditResultReturn").item(0).getFirstChild();
		if (node == null)
			logger.error("No response received from SESSIONID!");
		else {
			String nodeValue = node.getNodeValue();
			String[] split = nodeValue.split(" ");
			if (split == null || split.length == 0) {
				System.err.println("失败");
			} else {
				if ("0".equals(split[0])) {
					logger.info("成功");
					return 1;
				} else {
					logger.error(split[1]);
					return 0;
				}
			}
		}
		sc.close();
		return 0;
	}

}
