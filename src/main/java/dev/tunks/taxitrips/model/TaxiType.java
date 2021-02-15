package dev.tunks.taxitrips.model;


public enum TaxiType {
	 YELLOW,
	 GREEN,
	 RHV;

	 public static TaxiType getValue(String value) {
		 if(value == null || value.isEmpty()) 
		 {
			 return null;
		 }
		 switch(value.toLowerCase()) {
		 case "yellow":
			  return TaxiType.YELLOW;
		 case "green":
			 return TaxiType.GREEN;
		 case "rhv":
			 return TaxiType.RHV;
		 default:
			 return null;
		 }
	 }
}
