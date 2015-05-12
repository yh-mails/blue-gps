The project aim is to develop an Blackberry App that will allow iPhone/iPod/iPad and other devices to source GPS information from Blackberry as it was a regular Bluetooth GPS Receiver.

&lt;BR/&gt;


This is a JAVA project based on Blackberry JAVA API (OS5).
## If you are satisfied and you appreciate my efforts please consider donating ##
[![](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=TECVJ5ST7828G&item_name=Donation%20from%20Blue-GPS.%20Thank%20you.) 

&lt;BR/&gt;




&lt;BR/&gt;

[![](http://blue-gps.googlecode.com/svn/survey.png)](http://freeonlinesurveys.com/app/showpoll.asp?sid=yjig6t7wena6cvt106591&qid=106591&new=true) 

&lt;BR/&gt;



<b>What works:</b><br />
<ul>
<li>Bluetooth connection</li>
<li>NMEA generation based on location information</li>
<li>Tested and works with iOS AirBlue GPS</li>
<li>Tested and works with Nokia s60 phone</li>
<li> Runs on BB OS 5, BB OS 6, BB OS 7</li>
</ul><br />
<b>Design highlights:</b><br />
<ul>
<li>Blackberry CLDC application (OS 5) </li>
<li>Service + Client (UI) architecture</li>
</ul>
<img src='http://blue-gps.googlecode.com/svn/trunk/docs/screenshot1.png' />



&lt;BR/&gt;


Currently, the device is capable to emulate the following NMAE sentences based on its location information:
<ul>
<li>GPGGA</li>
<li>GPGLL</li>
<li>GPRMC</li>
<li>GPGSA</li>
</ul>
<br />
<b>Troubleshooting:</b><br />
<ul>
<li>If something does not work please ensure that all the options are checked and set to default!</li>
<li>Remove all Bluetooth paired devices from your BB</li>
<li>Disable all Bluetooth Profiles on you BB</li>
<li>Reboot your BB (preferably by removing you battery)</li>
<li>Start Blue GPS</li>
<li>Try connecting to you BB via bluetooth to get GPS data</li>
<li>Your BB will display some debug info and after a few minutes GPS data should be sent to your device</li>
</ul>


&lt;Br/&gt;


<b>How to disable Bluetooth Profiles on you BB</b>
<ul>
<li>When connecting to Blue GPS it might happen that your device (i.e. iPod) selects and tries to connect to the first found bluetooth service on you Blackberry</li>
<li>The first found bluetooth service on you Blackberry is not 'blue gps' (in majority of cases) but something<br>
called 'desktop connectivity'</li>
<li>That is why it is advisable to disable all Bluetooth Profiles on you Blackberry; steps (BB OS6):</li>
<li>Go to your Blackberry Options->Networks and Connections->Bluetooth Connections->Options (from context menu)</li>
<li>Disable all services, especially making sure all 'Serial Port Profiles' are unchecked and hard reboot your BB (remove its battery)</li>
<li><b>Alternatively</b> (if using iDevice i.e. iPod), try using AirBlue GPS application to connect to your Blackberry</li>
</ul>
<br />
<b>Todo:</b><br />
<ul>
<li>Add satellite information</li>
<li>Improve communication between the service and UI</li>
<li>Redesign the UI</li>
</ul>