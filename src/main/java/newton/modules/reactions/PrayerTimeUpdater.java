package newton.modules.reactions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Pair;
import newton.interfaces.IReaction;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class PrayerTimeUpdater implements IReaction {

    @Override
    public void react() {
        String formattedDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        // Prepare API URL
        Pair<Double, Double> p = getLocation();
        double latitude = p.getKey();
        double longitude = p.getValue();
        String apiUrl = String.format(
                "http://api.aladhan.com/v1/timings/%s?latitude=%f&longitude=%f&method=%d",
                formattedDate, latitude, longitude, "Egyptian General Authority of Survey"
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).
                thenApply(HttpResponse::body).thenApply(this::parsePrayerTimesJson);
    }

    private PrayerTimesData parsePrayerTimesJson(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode data = mapper.readTree(jsonString).get("data");
            JsonNode timings = data.get("timings");


            PrayerTimesData prayerTimes = new PrayerTimesData();

            // Extract prayer times

            prayerTimes.imsak = timings.get("imsak").asText();
            prayerTimes.fajr =  timings.get("Fajr").asText();
            prayerTimes.sunrise = timings.get("Sunrise").asText();
            prayerTimes.dhuhr =  timings.get("Dhuhr").asText();
            prayerTimes.asr =  timings.get("Asr").asText();
            prayerTimes.maghrib = timings.get("Maghrib").asText();
            prayerTimes.isha = timings.get("Isha").asText();


            return prayerTimes;
        } catch (Exception e) {
            System.err.println("Error parsing prayer times JSON: " + e.getMessage());
            return null;
        }
    }

    public Pair<Double, Double> getLocation(){
        try {
            URL url = new URL("http://ip-api.com/json/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.toString());
            double lat = rootNode.get("lat").asDouble();
            double lon = rootNode.get("lon").asDouble();
            return new Pair<>(lat, lon);
        } catch (Exception e) {
            e.printStackTrace();
            return new Pair<>(0.0, 0.0);
        }
    }



    private static class PrayerTimesData {
        String imsak;
        String fajr;
        String sunrise;
        String dhuhr;
        String asr;
        String maghrib;
        String isha;

        public String getImsak() {
            return imsak;
        }

        public void setImsak(String imsak) {
            this.imsak = imsak;
        }

        public String getFajr() {
            return fajr;
        }

        public void setFajr(String fajr) {
            this.fajr = fajr;
        }

        public String getSunrise() {
            return sunrise;
        }

        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
        }

        public String getDhuhr() {
            return dhuhr;
        }

        public void setDhuhr(String dhuhr) {
            this.dhuhr = dhuhr;
        }

        public String getAsr() {
            return asr;
        }

        public void setAsr(String asr) {
            this.asr = asr;
        }

        public String getMaghrib() {
            return maghrib;
        }

        public void setMaghrib(String maghrib) {
            this.maghrib = maghrib;
        }

        public String getIsha() {
            return isha;
        }

        public void setIsha(String isha) {
            this.isha = isha;
        }
    }
}
