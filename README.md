SensorNode
==========

Description
-----------
SensorNode is one component of a project to build a sensor network. It aims at connecting multiple sensors to a sensor
node, and multiple sensor nodes to a server from where the data can be accessed via a web application and/or a mobile
application. In this case, sensor can be seen as a broad term. It can be any device that delivers data which can be
stored.

The application has two main components: the sensor management and the sensor data management. 
The sensor management initiates and controls the connected sensors. The sensor itself reads data from the hardware
device regularly in a specified capture interval and puts it into a data queue.
The sensor data management is responsible for storing data to and retrieving it from a database, in this case a MongoDB.
The data is taken from the data queue to be stored in the database.


Dependencies
------------
Gradle  
- http://www.gradle.org/  
- Gradle is used for managing the dependencies and as build tool

MongoDB  
- http://www.mongodb.org/  
- Needed to store the sensor data. It can be replaced by any other DBMS by implementing the SensorDataRepository and adjusting the SensorDataRepositoryFactory.

OrientDB
- http://orientdb.com/orientdb/

Couchbase lite
- http://www.couchbase.com/nosql-databases/couchbase-mobile

v4l4j (Video for Linux for Java)  
- https://code.google.com/p/v4l4j/  
- Needed to access a web cam.


Set up
------
1. Add v4lvj library  
Add the v4l4j.jar to the lib folder

2. Set up database
2.1 Set up MongoDB  
Install MongoDB and create a database and collection with following values:  
database => "sensorNode"  
collection => "sensorData"

2.2. Set up OrientDB
To use the console use "/path-to-orientdb/bin/console.sh"
- create user for database
- create DB sensor_node ("create database plocal:../databases/sensor_node admin admin plocal DOCUMENT")
- create class ("CREATE CLASS SensorData")
- create properties
    CREATE PROPERTY SensorData.id string
    CREATE PROPERTY SensorData.sensorId integer
    CREATE PROPERTY SensorData.timestamp long
    CREATE PROPERTY SensorData.data binary

2.3 Set up Couchbase lite
The couchbase lite database needs a natively compiled library:
- git clone https://github.com/couchbase/couchbase-lite-java.git
- git submodule update --init --recursive
- gradlew -Pspec=java clean
- gradlew -Pspec=java build

3. Configuration
3.1 System config
Config file: src/main/resources/config.xml
Copy the config.example.xml file and rename it to config.xml.  
Configure which database to use and add the settings for the database.
MongoDB: <use>mongoDb</use>
OrientDB: <use>orientDb</use>
Couchbase lite: <use>couchbase</use>
  
3.2 Sensor config
Config file: src/main/resources/sensors.xml
Copy the sensors.example.xml file and rename it to sensors.xml.  
Every connected sensor needs to be configured in the sensors.xml file (src/main/resources/sensors.xml).  
Copy/rename src/main/resources/sensors.example.xml to src/main/resources/sensors.xml and edit the configuration.
At the moment two types of sensors are implemented:

	Dummy sensor: a sensor for testing purposes
		
			<dummy>
				<id>101</id>
				<name>DummySensor1</name>
			</dummy>
	
	Web Cam: a sensor for taking pictures. (As the application uses v4l4j, web cams are only supported on Unix systems at the moment)
	
			<webCam>
				<id>102</id>
				<name>webCam1</name>
				<devicePath>/dev/video0</devicePath>
				<width>640</width>
				<height>480</height>
				<channel>0</channel>
			</webCam>
		
4. Run application  
Use "gradlew run" to build and run the application.
