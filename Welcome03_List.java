import core.data.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Welcome03_List {
   public static void main(String[] args) {
      DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/index.xml").load();
      ArrayList<WeatherStation> allstns = ds.fetchList("WeatherStation", "station/station_name", 
             "station/station_id", "station/state",
             "station/latitude", "station/longitude");
      System.out.println("Total stations: " + allstns.size());

      Scanner sc = new Scanner(System.in);
      System.out.println("Enter a state abbreviation: ");
      String state = sc.next();

      // sort by latitude
      for (int i = 0; i < allstns.size() - 1; i++) {
         for (int j = i + 1; j < allstns.size(); j++) {
            if (allstns.get(i).getLatitude() > allstns.get(j).getLatitude()) {
               WeatherStation temp = allstns.get(i);
               allstns.set(i, allstns.get(j));
               allstns.set(j, temp);
            }
         }
      }

      System.out.println("Stations in " + state + ", sorted by latitude:");
      for (WeatherStation ws : allstns) {
         if (ws.isLocatedInState(state)) {
            System.out.println("  " + ws.getId() + ": " + ws.getName() + ", Latitude: " + ws.getLatitude());
         }
      }
   }
}
