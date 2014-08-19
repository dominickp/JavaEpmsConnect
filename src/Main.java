public class Main {

    public static void main(String[] args) {

        // Declare some variables
        String epmsUrl = "http://192.168.x.x/EnterpriseWebService";
        String username = "your_username";
        String password = "your_password";

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
