
Midnight 3D Engine is designed to provide a portable, cross-platform (Linux, Windows, and macOS) simulation tool for rigid body collisions and dynamics, or as a lightweight game creation tool.
___________________________________________________________________________

![alt text]()
![alt text]()


  # Running A Simulation #
  
 
    
    Note: When using linux, it is necessary to use terminal to navigate to the directory of the repo, then type: 'java -jar  Midnight.jar'
  
  
  
  
  
  




    
Getting started creating your own environment using the Midnight 3D Engine scripting tool [Simulation] :


    Make sure to inspect the test.mdnt file in /res/script/ for an example of how to use the Midnight basic scripting system.

- ".Midnight" files are just text files containing the script which is fed into the engine at runtime, which then tells Midnight how to construct your simulation.
  
  In res/script/test.Midnight you will find: 
  
  Lines marked with a '#', these act as comments in the scripting system, so if a line starts with the '#' char, then the interpreter will skip that line.
  

  
  The current list of supported types in the interpreter are as follows: 

    
    - Object: The general purpose object used when conducting a sim. Given a (non-animated) .obj 3D model file and texture files to represent its position, initial rotation, and scale.

    - Planet: Generally intened to be used for planets, or other large, massive objects, such as moons; this object will have an assigned gravitational influence determined by the gravity physics subsystem.

    - Phy: Short for Physics scripting object, this assigns physical properties to objects in the world, however, the interpreter is strict, and the number of Phy types and Object MUST MATCH the number of objects or the engine will not have been given sufficient information to begin a simulation.
    ( who wants an asteroid with no mass, or linear/angular velocity?)
    
    - Light: This represents a light in the 3D world space. Currently, it only supports a limited number of light sources, as each light increases the rendering load per frame.
    
    - Skybox: This is the backdrop of the simulation, use a .png filepath here to change the background in the simulation.
    
    - SkySphere: [experimental] will create a background in the shape of a sphere, useful for instances in space.







Getting started with linking the engine with an external program:



    To use Midnight 3D in your own project in Eclipse, clone the repository, then copy file Midnight3D.jar from include/ and the contents of /lib into your project, 
    go into Eclipse, right click on your project, go to Build Path > Configure Build Path
    > Click on the libraries tab, then "Add External JARs" and find Midnight3D.jar in your project directory.
    
    
 # Using Midnight for creating games #
Instead of using the provided source and dependencies in the Midnight3D-master folder, goto the readme in "GameEngine" for further information on setting up a new project.









# How the system pipeline works #







    
