import javax.xml.soap.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import java.util.logging.Filter;

public class EpmsConnect {

    /**
     * Starting point for the SAAJ - SOAP Client Testing
     */
    public static void main(String args[]) {
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            String url = "http://epmsconnect.shawmutprinting.com/EnterpriseWebService/Service.asmx";
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);

            // Process the SOAP Response
            printSOAPResponse(soapResponse);

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("Error occurred while sending SOAP Request to Server");
            e.printStackTrace();
        }
    }

    private static SOAPMessage createSOAPRequest() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String serverURI = "http://localhost/EnterpriseWebService/Enterprise Connect/GetJobList";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        //envelope.addNamespaceDeclaration("example", serverURI);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();

        SOAPElement soapBodyElem = soapBody.addChildElement("GetJobList");

        SOAPElement Credentials = soapBodyElem.addChildElement("Credentials");

        SOAPElement Username = Credentials.addChildElement("Username");
        SOAPElement Password = Credentials.addChildElement("Password");
        Username.addTextNode("");
        Password.addTextNode("");


        SOAPElement JobType = soapBodyElem.addChildElement("JobType");
        SOAPElement FilterType = soapBodyElem.addChildElement("FilterType");
        SOAPElement FilterCriteria = soapBodyElem.addChildElement("FilterCriteria");
        SOAPElement blnPriceOnLineReadyOnly = soapBodyElem.addChildElement("blnPriceOnLineReadyOnly");
        SOAPElement lngNumberOfRecords = soapBodyElem.addChildElement("lngNumberOfRecords");

        JobType.addTextNode("Order");
        FilterType.addTextNode("Customer");
        FilterCriteria.addTextNode("SHAWMUT");
        blnPriceOnLineReadyOnly.addTextNode("false");
        lngNumberOfRecords.addTextNode("50");

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI );

        soapMessage.saveChanges();

        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }

    /**
     * Method used to print the SOAP Response
     */
    private static void printSOAPResponse(SOAPMessage soapResponse) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        System.out.print("\nResponse SOAP Message = ");
        StreamResult result = new StreamResult(System.out);
        transformer.transform(sourceContent, result);
    }

}