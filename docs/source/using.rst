=================
GLANET User Guide
=================

GLANET includes both graphical user interface (GUI) and command-line interface. In either case, to run GLANET, one should write the following basic command on Terminal (Linux or Mac OS X) or on Command Prompt (Windows)\*::

	$ java −jar ~path/to/GLANET.jar -Xms8G -Xmx8G

Note that we ask you to allow GLANET to allocate 8GB of memory, if necessary. Therefore, even if you want to use the GUI, it is crucial to run GLANET.jar with this way.

\* Throughout the guide, we will use ~path/to/GLANET.jar to indicate your absolute path to GLANET.jar

--------------------
Command Line Options
--------------------

===========  =======================================================================  =================  =================
Command      Required                                                                 Parameter          Default Parameter
===========  =======================================================================  =================  =================
-c           Yes                                                                      None               None
-i           Yes                                                                      "path/to/file/"    None
-g           Yes                                                                      "path/to/folder/"  None
-f1          Yes (one of -f1, -f0, -fbed, -fgff, -fdbSNPID)                           None               None
-f0          Yes (one of -f1, -f0, -fbed, -fgff, -fdbSNPID)                           None               None
-fbed        Yes (one of -f1, -f0, -fbed, -fgff, -fdbSNPID)                           None               None
-fgff        Yes (one of -f1, -f0, -fbed, -fgff, -fdbSNPID)                           None               None
-fdbSNPID    Yes (one of -f1, -f0, -fbed, -fgff, -fdbSNPID)                           None               None
-b           No                                                                       An integer value   1
-dnase       No                                                                       None               None
-histone     No                                                                       None               None
-tf          No                                                                       None               None
-kegg        No                                                                       None               None
-tfkegg      No                                                                       None               None
-celltfkegg  No                                                                       None               None
-udg         No                                                                       None               None
-udginput    Yes, if -udg is set                                                      "path/to/file/"    None
-udginfoid   Yes, if -udg is set (one of -udginfoid, -udginfosym, -udginforna)        None               -udginfoid
-udginfosym  Yes, if -udg is set (one of -udginfoid, -udginfosym, -udginforna)        None               -udginfoid
-udginforna  Yes, if -udg is set (one of -udginfoid, -udginfosym, -udginforna)        None               -udginfoid
-udgname     No                                                                       A string           "NoName"
-udgfile     No                                                                       "path/to/file/"    None
-udl         No                                                                       None               None
-udlinput    Yes, if -udl is set                                                      "path/to/file/"    None
-udlf0exc    Yes, if -udl is set (one of -udlf0exc, -udlf0inc, -udlf1exc, -udlf0inc)  None               -udlf0exc
===========  =======================================================================  =================  =================
