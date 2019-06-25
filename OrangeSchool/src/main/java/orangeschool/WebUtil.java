package orangeschool;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class WebUtil {
	
	public static String toString(Object _object)
	{
		
		return "";
	}
	public static String GetTime()
    {

    	LocalDate localDate = LocalDate.now(ZoneId.of("GMT+07:00"));
    	LocalTime time = LocalTime.now();
    	LocalDateTime ldt = LocalDateTime.now();
    	String dateandtime = ldt.toString();
    	
    	return dateandtime;
    }
	
	
	public static void WriteText2File(String _text, String _uri )
	{
		

		try {
			FileOutputStream fos = new FileOutputStream(_uri);
		    DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
		    outStream.writeUTF(_text);
		    outStream.close();
		}catch(Exception ex)
		{
			System.out.println("could not write the string to file");
		}
	}
	
	
	public static final String deleteSuccessfull ="Deleted successfully";
	public static final String addSuccessfull ="Added successfully";
	public static final String editSuccessfull ="Edited successfully";
	
	

}
