package org.ligi.android.dubwise.con;

import org.ligi.ufo.MKCommunicator;



public final class MKProvider  {

	static MKCommunicator mk=null;
	
	public static MKCommunicator getMK() {
		if (mk==null)
			mk=new MKCommunicator();
		
		return mk;
	}
	public static void disposeMK() {
     mk=null;
    }
}