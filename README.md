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
Fields:
    - id: A unique identifier for the sensor
    - name: A name for the sensor
    - device: Currently two types are supported:
        - dummy: A simple "device" for testing, it automatically generates random data
        - file: Reads sensor data from a file.
            - path: The absolute path to the file
            - adapter: Currently only the "temperature" adapter is available. It expects a string in the format "Temperature: 20.1" in the file. The temperature "20.1" is then extracted and saved.
    - captureInterval: (Optional) Describes the interval in milliseconds for capturing/reading the sensor data. Default is 3 seconds.
    - dataType: (Optional) Describes the type of the captured data, so that it can be transformed back by the systems consuming the data from a sensor node. As a convention it expects a MIME-Type because it is widely known format for describing a content's type. Default is "text/plain".

    Examples:
    
            <sensors>
                
                // creates a dummy sensor for testing
                <basic>
                    <id>101</id>
                    <name>DummySensor1</name>
                    <device type="dummy"/>
                    <captureInterval>20000</captureInterval>
                    <dataType>text/plain</dataType>
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
                    <dataType>text/plain</dataType>
                </basic>
            
            </sensors>
		
Run application
---------------
Either use the gradle run task:

    ./gradlew run
    
Or build a fat jar and run it with the java command:

    ./gradlew fatJar
    java -jar build/libs/SensorNode-all-1.0.jar