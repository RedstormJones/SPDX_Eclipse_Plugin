#!/bin/bash
#Copyright 2015 University of Nebraska at Omaha (UNO)

#Clone Repo
git clone https://github.com/TCF-30/SPDX_Eclipse_Plugin

#Reconfiguring Eclipse Plugins Folder
echo "Reconfiguring Eclipse Plugins Folder"
cd SPDX_Eclipse_Plugin/Plugin\ Versions/Version\ 1.0/

echo "Please enter the directory to your Eclipse plugins folder: (be sure to include forward slashes and after your directory)"

read input_directory

#Copying Jar Files
echo "Copying .jar Files"
sudo cp org.spdx.spdxeclipse_1.0.0.0.jar $input_directory

echo "Install Complete"



