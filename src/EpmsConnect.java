/*

    Copyright (C) 2014  Dominick Peluso

    Adapted from SOAP SAAJ code provided by acdcjunior here:
    http://stackoverflow.com/questions/15940234/how-to-do-a-soap-web-service-call-from-java-class

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>

 */

import javax.xml.soap.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

public class EpmsConnect {

    // EPMS URL, expects something like 'http://192.168.x.x/EnterpriseWebService'
    private String epmsUrl;

    // The built request
    private SOAPMessage soapRequest;

    // Credentials
    private String username;
    private String password;

    // URL setter
    public void setUrl(String url){

        this.epmsUrl = url;

    }

    // Credentials setter
    public void setCredentials(String username, String password){

        this.username = username;
        this.password = password;

    }

    public void main(String args[]) {

    }

    public void executeSoapRequest(){
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            String url = this.epmsUrl+"/Service.asmx";
            SOAPMessage soapResponse = soapConnection.call(this.soapRequest, url);

            // Process the SOAP Response
            printSOAPResponse(soapResponse);

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("Error occurred while sending SOAP Request to Server");
            e.printStackTrace();
        }
    }

    public void getJobList(String uJobType, String uFilterType, String uFilterCriteria, Boolean ublnPriceOnLineReadyOnly, int ulngNumberOfRecords ){
        try {
            // Start SOAP message
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();

            SOAPPart soapPart = soapMessage.getSOAPPart();

            // EPMS Connect requires the namespace set as this
            String serverURI = "http://localhost/EnterpriseWebService/Enterprise Connect";
            String actionURI = serverURI+"/GetJobList";

            // SOAP Envelope
            SOAPEnvelope envelope = soapPart.getEnvelope();
            envelope.setPrefix("soap");
            envelope.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
            envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");

            // SOAP Body
            SOAPBody soapBody = envelope.getBody();
            soapBody.setPrefix("soap");

            // GetJobList node added with xmlns namespace set to null with a value of the serverURI
            SOAPElement GetJobList = soapBody.addChildElement("GetJobList", "", serverURI);

            // Add credentials
            SOAPElement Credentials = GetJobList.addChildElement("Credentials");
            SOAPElement Username = Credentials.addChildElement("Username");
            SOAPElement Password = Credentials.addChildElement("Password");
            Username.addTextNode(this.username);
            Password.addTextNode(this.password);

            // GetJobList parameters
            SOAPElement JobType = GetJobList.addChildElement("JobType");
            SOAPElement FilterType = GetJobList.addChildElement("FilterType");
            SOAPElement FilterCriteria = GetJobList.addChildElement("FilterCriteria");
            SOAPElement blnPriceOnLineReadyOnly = GetJobList.addChildElement("blnPriceOnLineReadyOnly");
            SOAPElement lngNumberOfRecords = GetJobList.addChildElement("lngNumberOfRecords");

            JobType.addTextNode(uJobType);
            FilterType.addTextNode(uFilterType);
            FilterCriteria.addTextNode(uFilterCriteria);
            blnPriceOnLineReadyOnly.addTextNode(String.valueOf(ublnPriceOnLineReadyOnly));
            lngNumberOfRecords.addTextNode(String.valueOf(ulngNumberOfRecords));

            // Set some headers
            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", actionURI );

            soapMessage.saveChanges();

            // Set soapMessage
            this.soapRequest = soapMessage;


        } catch (Exception e) {
            System.err.println("Error occurred while sending SOAP Request to Server");
            e.printStackTrace();
        }

    }

    public void printSOAPRequest(){
        try {
            System.out.print("Request SOAP Message = ");
            SOAPMessage soapRequest = this.soapRequest;
            soapRequest.writeTo(System.out);
            System.out.println();
        } catch (Exception e) {
            System.err.println("Error occurred while sending SOAP Request to Server");
            e.printStackTrace();
        }
    }

    private static void printSOAPResponse(SOAPMessage soapResponse) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        System.out.print("\nResponse SOAP Message = ");
        StreamResult result = new StreamResult(System.out);
        transformer.transform(sourceContent, result);
    }

}