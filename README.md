Juno 3D  is an Engine designed to provide a cross-platform, simulation tool for a variety of physics environments and properties.
___________________________________________________________________________

  
Getting started using the Juno 3D Engine:
    
    To get started using the Juno 3D engine, make sure to inspect the test.juno file in /res/script/ for a demo on how to use the juno scripting system.

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
    
