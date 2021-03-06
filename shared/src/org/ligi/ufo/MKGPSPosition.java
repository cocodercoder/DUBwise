/*********************************************
 *                                            
 * class representing the DebugData Structure 
 *                                            
 * Author:        Marcus -LiGi- Bueschleb     
 * 
 * see README for further Infos
 *
 * Some code taken from here:
 * http://www.koders.com/java/fidFC75A641A87B51BB757E9CD3136C7886C491487F.aspx
 * 
 * and
 * http://www.movable-type.co.uk/scripts/latlong.html
 *
 * thanx a lot for sharing!
 *
 ********************************************/

package org.ligi.ufo;


import java.lang.Math;
public class MKGPSPosition 
    implements DUBwiseDefinitions
{

    public byte act_gps_format=GPS_FORMAT_DECIMAL;
    public byte act_speed_format=SPEED_FORMAT_KMH;

    public final static int MAX_WAYPOINTS=100;

    public int[] LongWP;
    public int[] LatWP;
    public String[] NameWP;

    int UBatt;

    public int last_wp=0;

    public int Longitude;
    public int Latitude;
    public int Altitude;

    public int TargetLongitude;
    public int TargetLatitude;
    public int TargetAltitude;

    public int HomeLongitude;
    public int HomeLatitude;
    public int HomeAltitude;

    public int Distance2Target;
    public int Angle2Target;

    public int Distance2Home;
    public int Angle2Home;

    public byte SatsInUse=-1;
    public byte WayPointNumber=-1;
    public byte WayPointIndex=-1;
	
    public int AngleNick = -1;
    public int AngleRoll = -1;
    public int SenderOkay = -1;
    public int MKFlags= -1;
    public int NCFlags= -1;
    public int ErrorCode= 0;



    public int Altimeter=-1; // hight according to air pressure
    public int Variometer=-1; // climb(+) and sink(-) rate
    public int FlyingTime=-1;

    public int GroundSpeed=-1;
    public int Heading=-1;
    public int CompasHeading=-1;



//#if cldc11=="on"
    public static final double PI = Math.PI;
    public static final double PI_div2 = PI / 2.0;
    public static final double PI_div4 = PI / 4.0;
    public static final double RADIANS = PI / 180.0;
    public static final double DEGREES = 180.0 / PI;

    private static final double p4 = 0.161536412982230228262e2;
    private static final double p3 = 0.26842548195503973794141e3;
    private static final double p2 = 0.11530293515404850115428136e4;
    private static final double p1 = 0.178040631643319697105464587e4;
    private static final double p0 = 0.89678597403663861959987488e3;
    private static final double q4 = 0.5895697050844462222791e2;
    private static final double q3 = 0.536265374031215315104235e3;
    private static final double q2 = 0.16667838148816337184521798e4;
    private static final double q1 = 0.207933497444540981287275926e4;
    private static final double q0 = 0.89678597403663861962481162e3;



    private static double _ATAN(double X) 
    {
	if (X < 0.414213562373095048802)
	    return _ATANX(X);
        else if (X > 2.414213562373095048802)
	    return PI_div2 - _ATANX(1.0 / X);
        else 
	    return PI_div4 + _ATANX((X - 1.0) / (X + 1.0));
    }


    private static double _ATANX(double X) 
    {
	double XX = X * X;
	return X * ((((p4 * XX + p3) * XX + p2) * XX + p1) * XX + p0)/ (((((XX + q4) * XX + q3) * XX + q2) * XX + q1) * XX + q0);
    }



    public double aTan2(double Y, double X) 
    {

	if (X == 0.0) {
	    if (Y > 0.0) 
		return PI_div2;
      
	    else if (Y < 0.0) 
		return -PI_div2;
	    else 
		return 0.0;
	}

	// X<0
	else if (X < 0.0) {
	    if (Y >= 0.0) 
		return (PI - _ATAN(Y / -X)); // Y>=0,X<0 |Y/X|
	    else 
		return -(PI - _ATAN(Y / X)); // Y<0,X<0 |Y/X|
	
	}

	// X>0
	else if (X > 0.0) 
	    {
	    if (Y > 0.0) 
		return _ATAN(Y / X);
	    else 
		return -_ATAN(-Y / X);
	    
	    }

    return 0.0;

  }

    public int distance2wp(int id)
    {
	double lat1=(Latitude/10000000.0)*RADIANS;
	double long1=(Longitude/10000000.0)*RADIANS;

	double lat2=(LatWP[id]/10000000.0)*RADIANS;
	double long2=(LongWP[id]/10000000.0)*RADIANS;


	double dLat= (lat2-lat1);
	double dLon= (long2-long1);

	double a = Math.sin(dLat/2.0) * Math.sin(dLat/2.0) +
        Math.cos(lat1) * Math.cos(lat2) * 
        Math.sin(dLon/2.0) * Math.sin(dLon/2.0); 

	return (int)(( 2.0 * aTan2(Math.sqrt(a), Math.sqrt(1.0-a)) )*6371008.8);
    }




    public int angle2wp(int id)
    {
	// TODO reuse from distance
	double lat1=(Latitude/10000000.0)*RADIANS;
	double long1=(Longitude/10000000.0)*RADIANS;

	double lat2=(LatWP[id]/10000000.0)*RADIANS;
	double long2=(LongWP[id]/10000000.0)*RADIANS;


	double dLat= (lat2-lat1);
	double dLon= (long2-long1);

	

	double y = Math.sin(dLon) * Math.cos(lat2);
	double x = Math.cos(lat1)*Math.sin(lat2) -   Math.sin(lat1)*Math.cos(lat2)*Math.cos(dLon);
	return ((int)(aTan2(y, x)*DEGREES)+360)%360;

    }


//#endif

//#if cldc11!="on"
//#    public int distance2wp(int id)
//#    {
//#	return -1;
//#    }

//#    public int angle2wp(int id)
//#    {
//#	return -1;
//#    }
//#endif

    public void push_wp()
    {
	LongWP[last_wp]=Longitude;
	LatWP[last_wp]=Latitude;

	last_wp++;
    }

    /*    public void next_gps_format()
    {
	act_gps_format=(byte)((act_gps_format+1)%GPS_FORMAT_COUNT);
	}*/



    public String gps_format_str(int val,int format)
    {
	switch(format)
	    {
	    case GPS_FORMAT_DECIMAL:
		return "" + val/10000000 + "." +val%10000000  ;
	    case GPS_FORMAT_MINSEC:
		return "" +  val/10000000 + "^" +  ((val%10000000)*60)/10000000 + "'" + ((((val%10000000)*60)%10000000)*60)/10000000 +  "." + ((((val%10000000)*60)%10000000)*60)%10000000;
	    default: 
		return "invalid format" + act_gps_format;
	    }
    }
    public String act_gps_format_str(int val)
    {
	return gps_format_str(val,act_gps_format);

    }

    

    public String act_speed_format_str(int val)
    {
	switch(act_speed_format)
	    {
	    case SPEED_FORMAT_KMH:
		return "" +  ((((val*60)/100)*60)/1000) + " km/h";

	    case SPEED_FORMAT_MPH:
		return "" +  (((((val*60)/100)*60)/1000)*10)/16 + " m/h";

	    case SPEED_FORMAT_CMS:
		return "" + val+ " cm/s";
		
	    default: 
		return "invalid speed format";
	    }
    }

    public String GroundSpeed_str()
    {
	return act_speed_format_str(GroundSpeed);

    }

    public String WP_Latitude_str(int id)
    {
	
	return act_gps_format_str(LatWP[id]); //+ "''N"  ;
    }

    public String WP_Longitude_str(int id)
    {
	return act_gps_format_str(LongWP[id]); //+ "''E"  ;

    }

    public String Latitude_str()
    {
	return act_gps_format_str(Latitude) ;
    }

    public String Longitude_str()
    {
	return act_gps_format_str(Longitude)  ;
    }


    public String TargetLatitude_str()
    {
	return act_gps_format_str(TargetLatitude) ;
    }

    public String TargetLongitude_str()
    {
	return act_gps_format_str(TargetLongitude)  ;
    }

    public String HomeLatitude_str()
    {
	return act_gps_format_str(HomeLatitude) ;
    }

    public String HomeLongitude_str()
    {
	return act_gps_format_str(HomeLongitude)  ;
    }


    // Constructor
    public MKGPSPosition() 
    {

	LongWP=new int[MAX_WAYPOINTS];
	LatWP=new int[MAX_WAYPOINTS];
	

	NameWP=new String[MAX_WAYPOINTS];
	// predefined waypoints

	/*
	LongWP[0]=123230170;
	LatWP[0]= 513600170 ;
	NameWP[0]="Sicherer PC1";

	LongWP[1]=123269000;
	LatWP[1]= 513662670;
	NameWP[1]="Sicherer PC2";

	LongWP[2]=123475570;
	LatWP[2]= 513569750 ;
	NameWP[2]="Treffpunkt Seba";
	*/

	last_wp=0;
    }
    private int parse_arr_4(int offset,int[] in_arr)
    {
	return ((in_arr[offset+3]<<24) |
		(in_arr[offset+2]<<16) |
		(in_arr[offset+1]<<8)  |
		(in_arr[offset+0]));
    }

    private int parse_arr_2(int offset,int[] in_arr)
    {
	return (((in_arr[offset+1]&0xFF)<<8)  |
		(in_arr[offset+0]&0xFF ));
    }



    public void set_by_mk_data(int[] in_arr,MKVersion version)
    {
	int off=0;
	if (version.proto_minor>0) // fixme
	    off++;
	Longitude=parse_arr_4(off+0,in_arr);
	Latitude=parse_arr_4(off+4,in_arr);
	Altitude=parse_arr_4(off+8,in_arr);
	//status=in_arr[12];

	TargetLongitude=parse_arr_4(off+13,in_arr);
	TargetLatitude=parse_arr_4(off+17,in_arr);
	TargetAltitude=parse_arr_4(off+21,in_arr);
	//Targetstatus=in_arr[25];

	Distance2Target=parse_arr_2(off+26,in_arr);
	Angle2Target=parse_arr_2(off+28,in_arr);

	HomeLongitude=parse_arr_4(off+30,in_arr);
	HomeLatitude=parse_arr_4(off+34,in_arr);
	HomeAltitude=parse_arr_4(off+38,in_arr);
	//Targetstatus=in_arr[42];

	Distance2Home=parse_arr_2(off+43,in_arr);
	Angle2Home=parse_arr_2(off+45,in_arr);

	WayPointIndex=(byte)in_arr[off+47];
	WayPointNumber=(byte)in_arr[off+48];

	SatsInUse=(byte)in_arr[off+49];
	
	
	Altimeter=parse_arr_2(off+50,in_arr); // hight according to air pressure
	Variometer=parse_arr_2(off+52,in_arr);; // climb(+) and sink(-) rate
	FlyingTime=parse_arr_2(off+54,in_arr);;
	
	UBatt= in_arr[off+56];


	GroundSpeed= parse_arr_2(off+57,in_arr);
	Heading= parse_arr_2(off+59,in_arr);
	CompasHeading= parse_arr_2(off+61,in_arr); 
	
	AngleNick = in_arr[off+63];
	AngleRoll = in_arr[off+64];
	SenderOkay = in_arr[off+65];

	MKFlags=in_arr[off+66];
	NCFlags=in_arr[off+67];

	ErrorCode=in_arr[off+68];


    }



}
