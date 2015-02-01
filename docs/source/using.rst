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

==  ===========  ========  ===========================  =================  =================
ID  Command      Required  Prerequisite                 Parameter          Default Parameter
==  ===========  ========  ===========================  =================  =================
1   -c           No        None                         None               None
2   -i           Yes       1                            "path/to/file"     None
3   -hg19        No        1                            None               -hg19
3   -hg38        No        1                            None               -hg19
4   -g           Yes       1                            "path/to/folder/"  None
5   -f1          Yes       1                            None               None
5   -f0          Yes       1                            None               None
5   -fbed        Yes       1                            None               None
5   -fgff        Yes       1                            None               None
5   -fdbSNPID    Yes       1                            None               None
6   -b           No        1                            An integer value   1
7   -dnase       No        1                            None               None
8   -histone     No        1                            None               None
9   -tf          No        1                            None               None
10  -kegg        No        1                            None               None
11  -tfkegg      No        1                            None               None
12  -celltfkegg  No        1                            None               None
13  -udg         No        1                            None               None
14  -udginput    Yes       13                           "path/to/file"     None
15  -udginfoid   Yes       13                           None               -udginfoid
15  -udginfosym  Yes       13                           None               -udginfoid
15  -udginforna  Yes       13                           None               -udginfoid
16  -udgname     No        13                           A string           "NoName"
17  -udgdfile    No        13                           "path/to/file"     None
18  -udl         No        1                            None               None
19  -udlinput    Yes       18                           "path/to/file"     None
20  -udldf0exc   No        18                           None               -udlf0exc
20  -udldf0inc   No        18                           None               -udlf0exc
20  -udldf1exc   No        18                           None               -udlf0exc
20  -udldf1inc   No        18                           None               -udlf0exc
21  -e           No        7, 8, 9, 19, 11, 12, 13, 18  None               None
==  ===========  ========  ===========================  =================  =================
