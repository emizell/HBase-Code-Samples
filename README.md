# HBase-Code-Samples
A series of demos using HBase and Phoenix on HBase

## Using HBase-Code-Samples
There are four packages supporting the samples
I tend to run the code from my IDE, but you can JAR these up or call from other tools

## HBase Package
HBaseEngine calls the multi-threaded loader. Set the parameters for the number of rows and number of threads and execute. This was built to load 
millions of rows quickly into HBase. It will generate random data for you.
HBaseSingleThreadedLoader calls the HBaseLoadTable, HBaseSearchTable, and then the HBaseDeleteTable. This is designed to test several of the API's
in HBase

## Image Package
HBaseImageEngine takes in a folder with images in it. It will load all the images into HBase and alert if the image matches an existing image with a
different name. If the image name is the same, it will not store it twice.
HBaseReadImage will scan for a specific image you request and convert it from a byte array back to an image again. A quick test to make sure the 
byte conversion works both ways

## Phoenix Package
PhoenixHBaseLoader takes in several parameters like number of rows and zookeeper location. It will randomly generate data and load in the number
of rows you specific into Phoenix on HBase (You must have Phoenix enabled in your cluster first - see Apache Phoenix install guide)
PhoenixHBaseTest is a quick test program to test connection and table creation and load

## Util Package
Several utilities
