WebGPIO2
========

Raspberry Pi web app to turn on and off GPIO pins from a browser.

Thanks to Jeronen Kransen for his Framboos Java wrapper for the Wiring Pi library.  You cn see his GitHub here:
https://github.com/jkransen/framboos

His blog on the Pi is here: http://jkransen.wordpress.com/2012/07/16/raspberry-pi/#comment-17

Dependencies:

You must load WiringPi on your Raspberry Pi for this to work.  Thanks to Gordon for his work: 
https://projects.drogon.net/raspberry-pi/wiringpi/

Thanks to Gordon!

Tomcat6 is needed as the Servlet Container to run this web app.  If you need a smaller Servlet Container to save memory,
Winstone or Jetty will probably work too.

If you don't have Java installed on your Pi yet you must install it before installing Tomcat.

Use the command: sudo apt-get install openjdk-6-jdk

To see if Java is installed, run the command: java -version

You sould get a message like: 
java version "1.6.0_24"
OpenJDK Runtime Environment (IcedTea6 1.11.3) (6b24-1.11.3-2+rpi1)
OpenJDK Zero VM (build 20.0-b12, mixed mode)

Install Tomcat 6 on the Pi with: sudo apt-get install tomcat6

For some reason I had to set the user of Tomcat6 to root, even though Wiring Pi will run from the command line as 
non-root.  This is not straightforward because Raspbian divides Tomcat up among several directories.  
There is also a bug in the setup that requres you to change the ownership of Tomcat in two places.

The directories and/or files involved are /usr/share/tomcat6, /etc/tomcat6, /var/lib/tomcat6, /etc/init.d/tomcat6, 
/etc/default/tomcat6, /var/lib/tomcat6/logs, and most importantly /var/lib/tomcat6/webapps.

In /etc/default/tomcat6 set TOMCAT6_USER=root and TOMCAT6_GROUP=root
Do the same in /etc/init.d/tomcat6.

When you clean and build the web app an updated war file gets created in the /dist directory.

Copy file WebGPIO2.war from your development machine to your Pi and put it into the directory /var/lib/tomcat6/webapps.

When Tomcat runs it will unpack the war file into a regular directory and load the web app.

The output from logs will go into /var/lib/tomcat6/logs/catalina.out.
System.out.println output will go there too.

Tomcat gets installed as a service that starts at boot time.  You can manually control the service by using the 
commands stop, start, restart, and status as in:
sudo service tomcat6 start

If you load a new web app into a running Tomcat, it will try to launch it.  Sometimes this doesn't work.  If it 
doesn't use the command:

sudo service tomcat6 restart

The default port is set to 8080 so to run the web app put the following in your browser's URL line:

http://192.168.1.5:8080/WebGPIO2

Use your ip address instead of 192.168.1.5.  If you don't know it type this at the command line:

ip addr

The "inet" lines will list your IP number.

If you have your Pi set up on a wireless router you can run the web app on a smart phone with a browser, or any 
computer on your network with a browser.

Most newer smart phones will work.  Tablets like the iPad work too.

If you want to access your Pi from the outside world set your router up to forward the port 8080 to your Pi's IP number.

To set up the router so the IP number stays the same and you don't have to reconfigure your router all the time, you can 
either set up your router to always use the same IP number with your Pi's Mac Address, or set up the Pi for a static IP number.

The Mac Address is visible when you use "ip addr" in the link/ether line.

To set up a static IP number put lines like the following in your /etc/network/interfaces file.

 #iface eth0 inet dhcp

iface eth0 inet static

address 192.168.1.222

netmask 255.255.255.0

gateway 192.168.1.1


The line "#iface eth0 inet dhcp" is the default that sets up dhcp.  I commented it out with the "#" so I can easily 
reconfigure if I need to.  Use your IP number and gateway address.


This code is formatted as a NetBeans project.  You can get NetBeans from NetBeans.org for free.

Select download and choose the "All" option.  When you install click the custom button and select Tomcat 
for installation.

You can then load this project into NetBeans, run it and NetBeans will launch Tomcat and load the web app into it automatically.

You need to have the Java 6 JDK installed on your development machine and select it for the project.  Java 7 comes with 
Netbeans but it isn't available for the Pi yet.

The Web App is a Java Server Faces (JSF) Facelet, which means that the web page has a Class called a backing bean supporting it.
When you click a button on the web page a method n the Class /src/conf/java/beans/MainSessionBean gets called.

MainSessionBean is session scoped, so it stays alive the entire time the user is logged into the web app.

There are two static booleans in src/java/beans/MainSessionBean.java: DEBUGGING and PRINTTRACE.

To run the web app on your Windows box or Mac in Tomcat under NetBeans, set DEBUGGING to true.  This prevents the 
Framboos code from calling the WiringPi library, which doesn't exist on your Mac or PC.

If PRINTTRACE is set to true, text lines are sent to System.out.println that describe which pin is being set and how.  

This helps you debug the code on your development machine before you deploy it to the Pi.

Set DEBUGGING to false and clean and build before deploying the web app to the Pi.  That lets the GPIO commands execute.

The web app is very simple.  It has a button that turns on and off GPIO pin 7 (see the WiringPi pin diagram) three times 
while increasing the time between turn ons.

Another button turns pin 2, 3, and 4 on and off in sequence for 5 seconds, then turns them all on for 1/4 second (250 mS) and 
off for 1/4 second for 120 iterations (60 seconds).

The last two buttons turn pin 0 on and off.

The methods called by the buttons on the index.xhtml page are in the Class /src/conf/java/beans/MainSessionBean.

JSF uses an expression language in index.xhtml that lets the web page have access to Java methods and variables.

When you see action="#{mainSessionBean.blink7}" in the HTML for a button, it means to call the method "blink7" in the 
Class MainSessionBean.

Tomcat will instantiate MainSessionBean for you, that's what the annotation "@ManagedBean" means at the beginning of 
the class file.

If you want to change the layout or appearance of the buttons in index.xhtml you can change the cascading style sheet 
web/resources/stylesheets/main.css

