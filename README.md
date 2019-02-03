# Midnight 3D Engine #
A tool for creating 3D simulations with rigid body dynamics using the built in physics, or as a lightweight game creation tool. <br />Compatibile with Linux, Windows, and macOS
![alt text](https://github.com/jimdox/Midnight/blob/master/engine/res/launcher-prev2.png)


<br />
<br />
<br />
<br />

# Getting Started:
To get the engine up and running on your machine, make sure you have Java 8 or later installed to ensure the best compatibility on your system. 
In order to edit the script files which are loaded into the engine at runtime, navigate to /res/script, where the engine will read in any file with the extension '.mdnt', which is used to generate the world. Examples are provided on how to use this scripting system, but the engine will only look for files that are inside of this folder, so when creating new scripts, make sure to include them in this folder. 

The structure of the engine: 







<br />
# Troubleshooting:

If you encounter an error when attempting to run, saying that 'error: GLSL 4.0 is not supported.' then the first thing you should do is check to make sure you have the correct drivers installed. This is a more common issue on Linux based systems, so check if your graphics card manufacturer has a download available for your specific Linux distro.

Link to drivers for Linux systems:
* https://www.nvidia.com/Download/index.aspx?lang=en-us
* https://www.amd.com/en/support/kb/release-notes/rn-prorad-lin-amdgpupro
