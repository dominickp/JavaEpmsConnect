public class Main {

    public static void main(String[] args) {

        // Declare some variables
        String epmsUrl = "http://epmsconnect.shawmutprinting.com/EnterpriseWebService";
        String username = "epmsconnect";
        String password = "automation1";

        // GetJobList parameters
        String JobType = "Order";
        String FilterType = "Customer";
        String FilterCriteria = "SHAWMUT";
        Boolean blnPriceOnLineReadyOnly = false;
        int lngNumberOfRecords = 10;

        // Make new EpmsConnect instance
        EpmsConnect epmsConnect = new EpmsConnect();
        // Set the URL
        epmsConnect.setUrl(epmsUrl);
        // Set credentials
        epmsConnect.setCredentials(username, password);
        // Get GetJobList response
        epmsConnect.getJobList(JobType, FilterType, FilterCriteria, blnPriceOnLineReadyOnly, lngNumberOfRecords);

        // Get the request message - for debugging
        epmsConnect.printSOAPRequest();

        epmsConnect.executeSoapRequest();

    }

}
