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













Getting started with using the engine utils for an external program:

    To use Juno 3D in your own project in Eclipse, clone the repository, then copy file Juno3D.jar from include/ and the contents of /lib into your project, 
    go into Eclipse, right click on your project, go to Build Path > Configure Build Path
    > Click on the libraries tab, then "Add External JARs" and find Juno3D.jar in your project directory.
    
