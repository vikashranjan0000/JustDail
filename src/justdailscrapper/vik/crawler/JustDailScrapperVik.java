package justdailscrapper.vik.crawler;

import java.net.URI;
import justdailscrapper.vik.GUI.JustDailGUI;
import static justdailscrapper.vik.GUI.JustDailGUI.detailList;
import static justdailscrapper.vik.GUI.JustDailGUI.loggerTextArea;
import static justdailscrapper.vik.GUI.JustDailGUI.proxyList;
import static justdailscrapper.vik.crawler.LoadCity.cityList;
import justdailscrapper.vik.proxy.ProxyImport;
import justdailscrapper.vik.utility.FetchPageDetail;
import justdailscrapper.vik.utility.FetchPageWithProxy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JustDailScrapperVik extends Thread {

    String location, businessName;

    @Override
    public void run() {
        if (cityList.size() == 0) {
            DetailCrawler();
        } else {
            for (String location : cityList) {
                this.location = location;
                DetailCrawler();
            }
        }
    }

    public JustDailScrapperVik(String name) {
        this.businessName = name;
    }

    public JustDailScrapperVik(String location, String businessName) {
        this.location = location;
        this.businessName = businessName;
    }

    public void DetailCrawler() {

//    public static void main(String[] args) {
        String url = "https://www.justdial.com/" + location + "/" + businessName;
        proxyList.add(new ProxyImport("201.16.141.71:8080"));
        proxyList.add(new ProxyImport("45.55.209.249:3128"));

        String responseDetails, responseDetail, responseDynamic;
        try {

            responseDetail = new FetchPageWithProxy().fetchPageSourcefromClientGoogle(new URI(url), proxyList);
            try {

                Document doc = Jsoup.parse(responseDetail);

                try {
                    Elements pr = doc.select("li[class=cntanr] section[class=jcar] span[class=jcn] a");
                    for (Element pras : pr) {
                        DetailInfo detailInfoObj = new DetailInfo();
                        while (JustDailGUI.stopValue == true) {
                            sleep(5000);
                        }
                        System.out.println("=============\n" + pras.attr("href"));
                        String urlobj = pras.attr("href");
                        responseDetails = new FetchPageDetail().fetchPageSourcefromClientGoogle(new URI(urlobj), proxyList);
                        loggerTextArea.append(urlobj + "\n");
                        detailInfoObj.setSourceLink(urlobj);
                        Document docs = Jsoup.parse(responseDetails);

                        try {
                            Element businessName = docs.select("div[class=company-details] h1[class=rstotle] span[class=fn]").first();
                            System.out.println("Business Name :" + businessName.text());
                            loggerTextArea.append("Business Name           :" + businessName.text() + "\n");
                            detailInfoObj.setBusinessName(businessName.text().replace(",", ";"));
                        } catch (Exception eee) {
                            detailInfoObj.setBusinessName("N/A");
                            loggerTextArea.append("Error in fetching Data \n");

                        }

                        try {
                            Element contactNumber = docs.select("ul[class=comp-contact] li").first();
                            StringBuffer teleString = new StringBuffer();
                            try {

                                Elements reviewCount = contactNumber.select(" div[class=telCntct cmawht] a[class=tel]");
                                loggerTextArea.append("Telephone Number     :");
                                for (Element E : reviewCount) {
                                    System.out.println("Telephone :" + E.text());
                                    teleString.append(E.text() + " | ");
                                    loggerTextArea.append(E.text() + " | ");
                                }
                                loggerTextArea.append("\n");
                                detailInfoObj.setTelephoneNumber(teleString.toString().replace(",", ";"));
                            } catch (Exception E) {
                                System.out.println("Error in Telephone contact \n");
                                if (teleString.length() < 4) {
                                    detailInfoObj.setTelephoneNumber("N/A");
                                }
                            }
                        } catch (Exception eee) {
                            detailInfoObj.setTelephoneNumber("N/A");
                            loggerTextArea.append("Error in fetching Data \n");
                        }
                        try {
                            Elements telephone = docs.select("ul[class=comp-contact] li span[id=mob_set] div[class=telCntct cmawht] a");
                            StringBuilder mobile = new StringBuilder();
                            System.out.println("Mobile Number :");
                            loggerTextArea.append("Mobile Number          :");
                            for (Element E : telephone) {
                                System.out.println("" + E.text());
                                loggerTextArea.append("" + E.text() + " | ");
                                mobile.append(E.text() + " | ");
                            }
                            loggerTextArea.append("\n");
                            detailInfoObj.setMobileNumber(mobile.toString().replace(",", ";"));
                        } catch (Exception eee) {
                            System.out.println("Error in fetching Mobile Number");
                            detailInfoObj.setMobileNumber("N/A");
                        }
                        try {
                            Element address = docs.select("span[id=fulladdress]").first();
                            System.out.println("Address :" + address.text());
                            loggerTextArea.append("Address         \t         :" + address.text() + "\n");
                            detailInfoObj.setAddress(address.text().replace(",", ";"));
                        } catch (Exception eee) {
                            System.out.println("Error in fetching address");
                            detailInfoObj.setAddress("N/A");

                        }
                        try {
                            Elements tagsul = docs.select("div[class=mreinfwpr] ul[class=alstdul]").eq(1);

                            StringBuilder tag = new StringBuilder();
                            Element tags = tagsul.select("li").first();
                            Element ta = tagsul.select("li").eq(1).first();

                            tag.append(tags.text() + " ; ");
                            tag.append(ta.text() + " ; ");

                            loggerTextArea.append("Tag     \t          :" + tag.toString() + "\n");
                        } catch (Exception eee) {

                        }
                        try {
                            Element website = docs.select("ul[class=comp-contact] span[class=mreinfp comp-text] a").last();
                            System.out.println("Website            :" + website.attr("href"));
                            loggerTextArea.append("Website          \t      :" + website.attr("href") + "\n");
                            detailInfoObj.setWebsite(website.attr("href").replace(",", ";"));

                        } catch (Exception eee) {
                            System.out.println("Error in fetching website");
                            detailInfoObj.setAddress("N/A");
                        }
                        try {
                            Element rating = docs.select("span[class=rating] span[class=total-rate] span[class=value-titles]").first();
                            System.out.println("Rating :" + rating.text());
                            loggerTextArea.append("Rating     \t       :" + rating.text() + "\n");
                            detailInfoObj.setRating(rating.text().replace(",", ";"));

                        } catch (Exception eee) {
                            System.out.println("Error in Rating");
                            detailInfoObj.setRating("N/A");
                        }
                        try {
                            Element reviewCount = docs.select("span[class=rtngsval] span[class=votes]").first();
                            System.out.println("review :" + reviewCount.text());
                            loggerTextArea.append("Review                   :" + reviewCount.text() + "\n");
                            detailInfoObj.setReviewCount(reviewCount.text().replace(",", ";"));
                        } catch (Exception eee) {
                            System.out.println("Error in review Count");
                            detailInfoObj.setReviewCount("N/A");
                        }

                        try {
                            Elements reviewCount = docs.select("ul[class=alstdul dn] li");
                            StringBuilder HrOfOperation = new StringBuilder();
                            System.out.println("Hour of Operation :");
                            loggerTextArea.append("Hour of Operation : ");
                            for (Element E : reviewCount) {
                                Element day = E.select("span[class=mreinflispn1]").first();
                                System.out.print(day.text() + " : ");
                                HrOfOperation.append(day.text() + " : ");
                                loggerTextArea.append(day.text() + " : ");
                                Element Hour = E.select("span[class=mreinflispn2]").first();
                                System.out.println(Hour.text() + " | ");
                                loggerTextArea.append(Hour.text() + " | ");
                                HrOfOperation.append(Hour.text() + " | ");
//                            System.out.println("operation HR :" + E);
                            }
                            loggerTextArea.append("\n");
                            detailInfoObj.setHoursOfOperation(HrOfOperation.toString().replace(",", ";"));
                        } catch (Exception eee) {
                            System.out.println("Error in Hour of Operation Detail");
                            detailInfoObj.setHoursOfOperation("N/A");

                        }
                        detailInfoObj.setTag("N/A");
                        try {
//                        System.out.println("" + docs);
                            Elements e = docs.select("div[class=mndtlwpr] div[class=mreinfwpr]");
                            for (Element ed : e) {
                                String para = ed.select("p").text();

                                if (para.equalsIgnoreCase("Modes of Payment")) {
                                    System.out.print(para);
                                    loggerTextArea.append(para + " : ");
                                    StringBuilder modeOfPayment = new StringBuilder();
                                    try {
                                        Elements ef = ed.select("ul[class=alstdul] li");
                                        for (Element es : ef) {
                                            System.out.print(es.text() + " | ");
                                            loggerTextArea.append(es.text() + " | ");
                                            modeOfPayment.append(es.text() + " | ");
                                            System.out.println("");
                                        }
                                        loggerTextArea.append("\n");

                                        detailInfoObj.setModeOfPayments(modeOfPayment.toString().replace(",", ";"));
                                    } catch (Exception re) {
                                        System.out.println("Error in Mode of Payment");
                                        detailInfoObj.setModeOfPayments("N/A");
                                    }
                                } else if (para.equalsIgnoreCase("Year Established")) {
                                    try {
                                        String yearOfEstablishment = ed.select("ul[class=alstdul] li").first().text();
                                        System.out.println("Establishment :" + yearOfEstablishment);
                                        loggerTextArea.append("Establishment        : " + yearOfEstablishment + "\n");
                                        detailInfoObj.setEstablished(yearOfEstablishment.replace(",", ";"));

                                    } catch (Exception er) {
                                        System.out.println("Error in year of Establishment");
                                        detailInfoObj.setEstablished("N/A");
                                    }
                                }
                            }
                        } catch (Exception detailerror) {

                        }
                        loggerTextArea.append("-------------------------------------------------------------\n");
                        detailList.add(detailInfoObj);
                    }

                } catch (Exception parsee) {
                    System.out.println("Error in fetching response ");

                }
            } catch (Exception e) {
                System.out.println("Error in Detail Parsing" + e);
            }
            System.out.println("hello");
        } catch (Exception ex) {
            System.out.println("Error in crawling");
        }
    }

}
