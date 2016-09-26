SensorNode
==========

Description
-----------
SensorNode is one component of a project to build a sensor network. It aims at connecting multiple sensors to a sensor node, and multiple sensor nodes to a server from where the data can be accessed via a web application and/or a mobile application. In this case, sensor can be seen as a broad term. It can be any device that delivers data which can be stored.

The application has two main components: the sensor management and the sensor data management. 
The sensor management initiates and controls the connected sensors. The sensor itself reads data from the hardware device regularly in a specified capture interval and puts it into a data queue.
The sensor data management is responsible for storing data to and retrieving it from a database. The data is taken from the data queue to be stored in the database.


Dependencies
------------
Gradle
- http://www.gradle.org/
- Gradle is used for managing the dependencies and as build tool

Set up
------
1. System config
Config file: src/main/resources/config.xml
Copy the config.example.xml file and rename it to config.xml.  
Set the path and filename for the database file:

        <config>
            <database>
                <file>data/.sensorNodeDb</file>
            </database>
        </config>
  
2. Sensor config
Config file: src/main/resources/sensors.xml
Copy the sensors.example.xml file and rename it to sensors.xml.  
Every connected sensor needs to be configured in the sensors.xml file.
At the moment a basic sensor with two device types are implemented:

        <sensors>
            
            // creates a dummy sensor for testing
            <basic>
                <id>101</id>
                <name>DummySensor1</name>
                <device type="dummy"/>
            </basic>
        
            // creates a sensor where the information is stored in a text file
            <basic>
                <id>103</id>
                <name>Remote temperature sensor</name>
                <device type="file">
                    <path>/path/to/temperature.txt</path>
                    <adapter type="temperature"/>
                </device>
                <captureInterval>30000</captureInterval>
            </basic>
        
        </sensors>
		
3. Run application
Use "gradlew run" to build and run the application.
