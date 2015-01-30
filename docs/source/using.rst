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

===========  =======================================================================  =================  ====================
Command      Required                                                                 Parameter          Default Parameter
===========  =======================================================================  =================  ====================
-c           Yes                                                                      None               No Default Parameter
-i           Yes                                                                      "path/to/file/"    No Default Parameter
-g           Yes                                                                      "path/to/folder/"  No Default Parameter
-f1          Yes (one of -f1, -f0, -fbed, -fgff, -fdbSNPID)                           None               No Default Parameter
-f0          Yes (one of -f1, -f0, -fbed, -fgff, -fdbSNPID)                           None               No Default Parameter
-fbed        Yes (one of -f1, -f0, -fbed, -fgff, -fdbSNPID)                           None               No Default Parameter
-fgff        Yes (one of -f1, -f0, -fbed, -fgff, -fdbSNPID)                           None               No Default Parameter
-fdbSNPID    Yes (one of -f1, -f0, -fbed, -fgff, -fdbSNPID)                           None               No Default Parameter
-b           No                                                                       An integer value   1
-dnase       No                                                                       None               No Default Parameter
-histone     No                                                                       None               No Default Parameter
-tf          No                                                                       None               No Default Parameter
-kegg        No                                                                       None               No Default Parameter
-tfkegg      No                                                                       None               No Default Parameter
-celltfkegg  No                                                                       None               No Default Parameter
-udg         No                                                                       None               No Default Parameter
-udginput    Yes, if -udg is set                                                      "path/to/file/"    No Default Parameter
-udginfoid   Yes, if -udg is set (one of -udginfoid, -udginfosym, -udginforna)        None               -udginfoid
-udginfosym  Yes, if -udg is set (one of -udginfoid, -udginfosym, -udginforna)        None               -udginfoid
-udginforna  Yes, if -udg is set (one of -udginfoid, -udginfosym, -udginforna)        None               -udginfoid
-udgname     No                                                                       A string           "NoName"
-udgfile     No                                                                       "path/to/file/"    No Default Parameter
-udl         No                                                                       None               No Default Parameter
-udlinput    Yes, if -udl is set                                                      "path/to/file/"    No Default Parameter
-udlf0exc    Yes, if -udl is set (one of -udlf0exc, -udlf0inc, -udlf1exc, -udlf0inc)  None               -udlf0exc
===========  =======================================================================  =================  ====================
