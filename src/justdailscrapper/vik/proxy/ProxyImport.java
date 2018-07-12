
package justdailscrapper.vik.proxy;

public class ProxyImport {

    public String proxyIP;
    public String proxyPort;
    public String proxyUserName;
    public String proxyPassword;
    public int proxyLen;

    public ProxyImport(String line) {
        String[] divideEachProperty = line.split(":");
        proxyIP = divideEachProperty[0];
        proxyPort = divideEachProperty[1];
        proxyLen=divideEachProperty.length;

        if (proxyLen == 4) {
            proxyUserName = divideEachProperty[2];
            proxyPassword = divideEachProperty[3];
        }

    }

}
