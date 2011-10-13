package mypackage;


/*	If your BB simulator does not seem to support BT use the following dummy BTServer

	public class BTServer {
	public BTServer(DataContext dc) {
	}
	public String getLog() {return "";}
	public void sendData(String s){};
}

*/

import java.io.IOException;
import net.rim.device.api.bluetooth.BluetoothSerialPort;
import net.rim.device.api.bluetooth.BluetoothSerialPortInfo;
import net.rim.device.api.bluetooth.BluetoothSerialPortListener;

public class BTServer {
	private BluetoothSerialPort bsp;
	private boolean devConnected = false;
	private String log;
	private long sentBytes;
	private DataContext dc;
	
	private int i;
	public BTServer(DataContext dc) {
	try{
		this.dc = dc;
		log = "";
		if (this.dc.debug)  {
			BluetoothSerialPortInfo [] bspi = BluetoothSerialPort.getSerialPortInfo();
			log += " " + bspi.length; //1
			for (i=0;i<bspi.length;i++) {
				log += "\r\n\r\n" + bspi[i].getDeviceName(); //MyMac 
				log += "\r\n" + bspi[i].getDeviceAddress(); // [
				log += "\r\n" + bspi[i].getServerID(); //3
				log += "\r\n" + bspi[i].getServiceName();  //Bluetooth-PDA..
				log += "\r\n" + bspi[i].toString(); //btspa:// ...:3
			}
			bsp = new BluetoothSerialPort("GPS",BluetoothSerialPort.BAUD_4800,BluetoothSerialPort.DATA_FORMAT_PARITY_NONE ^ BluetoothSerialPort.DATA_FORMAT_DATA_BITS_8 ^ BluetoothSerialPort.DATA_FORMAT_STOP_BITS_1,BluetoothSerialPort.FLOW_CONTROL_NONE,2048,2048,new MyBTListener());			
		} else {
			bsp = new BluetoothSerialPort("GPS",BluetoothSerialPort.BAUD_4800,BluetoothSerialPort.DATA_FORMAT_PARITY_NONE ^ BluetoothSerialPort.DATA_FORMAT_DATA_BITS_8 ^ BluetoothSerialPort.DATA_FORMAT_STOP_BITS_1,BluetoothSerialPort.FLOW_CONTROL_NONE,2048,2048,new MyBTListener());
		}
		log += "BT Port initialized";		
	} catch (IOException e) {
		log = "ERROR! "+e.toString();
		// TODO Auto-generated catch block
		//e.printStackTrace();
	}		

	}
		
	public String getLog() {return this.log;};
	
	public void sendData(String s) {
		log="";
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
