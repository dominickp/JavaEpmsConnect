public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        System.out.print(Main.testFunction());

        // Declare some variables
        String epmsUrl = "http://epmsconnect.shawmutprinting.com/EnterpriseWebService";
        // Make new EpmsConnect instance
        EpmsConnect epmsConnect = new EpmsConnect();
        // Set the URL
        epmsConnect.setUrl(epmsUrl);
        // Set credentials
        epmsConnect.setCredentials("epmsconnect", "automation1");

        epmsConnect.getJobList();

        epmsConnect.main(args);


    }

    public static int testFunction()
    {
        return 5;
    }

}
