package mypackage;


/*	If your BB simulator does not seem to support BT use the following dummy BTServer
 
	public class BTServer {
	public BTServer() {
	}
	public String getLog() {return "";}
	public void sendData(String s){};
}



*/
import java.io.IOException;
import net.rim.device.api.bluetooth.BluetoothSerialPort;
import net.rim.device.api.bluetooth.BluetoothSerialPortListener;

public class BTServer {
	private BluetoothSerialPort bsp;
	private boolean devConnected = false;
	private String log;
	private long sentBytes;
	
	public BTServer() {
	try{
		bsp = new BluetoothSerialPort("GPS",BluetoothSerialPort.BAUD_9600,BluetoothSerialPort.DATA_FORMAT_PARITY_NONE ^ BluetoothSerialPort.DATA_FORMAT_DATA_BITS_8,BluetoothSerialPort.FLOW_CONTROL_NONE,2048,2048,new MyBTListener());
		log = "BT Port initialized";
	} catch (IOException e) {
		log = "ERROR! "+e.toString();
		// TODO Auto-generated catch block
		//e.printStackTrace();
	}		

	}
		
	public String getLog() {return this.log;};
	
	public void sendData(String s) {
		if (!devConnected) {
			log = "BT no device connected.";
			return;
		}
		try {
			//_dout.write(s.getBytes());
			//_dout.flush();
			byte [] b = s.getBytes("UTF8");
			bsp.write(b);
			sentBytes+=b.length;
			//OutputStream _dout = _bluetoothConnection.openOutputStream();
			//_dout.write(s.getBytes());
			//_dout.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log = "INIT ERROR! "+e.toString();
		}
		log = log + "\n\nSent bytes: "+sentBytes;
	}
	
	private class MyBTListener implements BluetoothSerialPortListener {

		public void dataReceived(int length) {
			byte [] buf = new byte[1024];
			String s;
			try {
				bsp.read(buf);
				s = new String(buf);
				log = "BT received data...\n"+s;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				log = "RCV ERROR! "+e.toString();
			}
			
		}

		public void dataSent() {
			log = "BT Sending data...";
			log = log + "\n\nSent bytes: "+sentBytes;
		}

		public void deviceConnected(boolean success) {
			devConnected = true;
			log = "BT Dev connected: "+success;
		}

		public void deviceDisconnected() {
			devConnected = false;
			log = "BT Dev disconnected";
		}

		public void dtrStateChange(boolean high) {
			// TODO Auto-generated method stub
			
		}
	}
}

