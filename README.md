Juno 3D  is an Engine designed to provide a cross-platform (Windows, Linux, and MacOS), simulation tool for a variety of physics environments and properties, or as a lightweight game creation tool.
___________________________________________________________________________

![alt text](https://github.com/Dox-Box/Juno3DEngine/blob/master/Juno3DEngine-master/res/juno-preview.png)
![alt text](https://github.com/Dox-Box/Juno3DEngine/blob/master/Juno3DEngine-master/res/opengl-java.png)

https://github.com/Dox-Box/Juno3DEngine/blob/master/Juno3DEngine-master/res/opengl-java.png

  # Running A Simulation #
  
    In order to run the default script, just clone Juno3DEngine-master, and run Juno3D.jar 
    
    Note: When using linux, it is necessary to use terminal to navigate to the directory of the repo, then type: 'java -jar  juno3D.jar'
  
  
  
  
  
  




    
Getting started creating your own environment using the Juno 3D Engine [Simulation] :


    Make sure to inspect the test.juno file in /res/script/ for an example of how to use the juno basic scripting system.

- ".juno" files are just text files containing the script which is fed into the engine at runtime, which then tells Juno3D how to construct your simulation.

- Firstly, its important to know the general layout of the scripting system in Juno:
  
  In /script/test.juno you will find: 
  
  Lines marked with a '#', these act as comments in the scripting system, so if a line has a '#', everything after that is skipped by the interpreter system.
  
  Each of these lines below the comments begin with a type which specifies what kind of environmental object, or data type it might be.
  
  The current list of supported types in the interpreter are as follows: 

    
    - Object: The general purpose object used when conducting a sim. Given the .obj 3D model file and texture files to represent its    scale, initial rotation, and position.

    - Planet: Generally intened to be used for planets, or other large, massive objects, such as moons or the like.

    - Phy: Short for Physics script, this assigns physical properties to objects in the world, however, the interpreter is strict, and the number of Phy types and Object MUST MATCH or the engine will not have been given sufficient information to begin a simulation.
    ( who wants an asteroid with no mass?)
    
    - Light: This represents a light in the 3D world space. Currently, Juno 3D supports a single light source, but including more Lights will not affect the rendering.
    
    - Skybox: This is the backdrop of the simulation, use a .png filepath here to change the background in the simulation.








Getting started with using the engine utils for an external program:



    To use Juno 3D in your own project in Eclipse, clone the repository, then copy file Juno3D.jar from include/ and the contents of /lib into your project, 
    go into Eclipse, right click on your project, go to Build Path > Configure Build Path
    > Click on the libraries tab, then "Add External JARs" and find Juno3D.jar in your project directory.
    
    
 # Using Juno for creating games #
Instead of using the provided source and dependencies in the Juno3DEngine-master folder, goto the readme in "GameEngine" for further information on setting up a new project.









# How it all works #







    
