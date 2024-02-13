import core.data.*;

public class Welcome02_Object {
   public static void main(String[] args) {
      Observation ob1 = getObservation("KATL");
      System.out.println("KATL: " + ob1);
      
      Observation ob2 = getObservation("KSAV");
      System.out.println("KSAV: " + ob2);
      
      Observation ob3 = getObservation("KLVK");
      System.out.println("KLVK: " + ob3);
      
      Observation coldest = ob1;
      String coldestId = "KATL";

      if (ob2.colderThan(coldest)) {
         coldest = ob2;
         coldestId = "KSAV";
      }
      if (ob3.colderThan(coldest)) {
         coldest = ob3;
         coldestId = "KLVK";
      }

      System.out.println("Colder at " + coldestId + ": " + coldest);
   }

   private static Observation getObservation(String id) {
      DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/" + id + ".xml"); 
      ds.setCacheTimeout(15 * 60);  
      ds.load();
      return ds.fetch("Observation", "weather", "temp_f", "wind_degrees");
   }
}

class Observation {
   float temp;    // in fahrenheit
   int windDir;   // in degrees
   String description;
   
   Observation(String description, float temp, int windDir) {
      this.description = description;
      this.temp = temp;
      this.windDir = windDir;
   }

   /* determine if the temperature of this observation is colder than 'that's */
   public boolean colderThan(Observation that) {
      return this.temp < that.temp;
   }
   
   /* produce a string describing this observation */
   public String toString() {
      return (temp + " degrees; " + description + " (wind: " + windDir + " degrees)");
   }
}
