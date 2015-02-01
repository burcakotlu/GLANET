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

=========================  ===========  ========  ============  =================  =================
Description                Command      Required  Prerequisite  Parameter          Default Parameter
=========================  ===========  ========  ============  =================  =================
Command Line               -c           Yes                     None               None
Input file                 -i           Yes                     "path/to/file"     None
Assembly                   -hg19        No                      None               -hg19
Assembly                   -hg38        No                      None               -hg19
GLANET folder              -g           Yes                     "path/to/folder/"  None
Input format               -f1          Yes                     None               None
Input format               -f0          Yes                     None               None
Input format               -fbed        Yes                     None               None
Input format               -fgff        Yes                     None               None
Input format               -fdbSNPID    Yes                     None               None
Num. of bases              -b           No                      An integer value   1
DNase                      -dnase       No                      None               None
Histone                    -histone     No                      None               None
Transcription Factor       -tf          No                      None               None
KEGG Pathway               -kegg        No                      None               None
TF And KEGG                -tfkegg      No                      None               None
TF (Cell Line) And KEGG    -celltfkegg  No                      None               None
User-defined gene set      -udg         No                      None               None
UDG Input File             -udginput    Yes                     "path/to/file"     None
UDG Gene Information Type  -udginfoid   Yes                     None               -udginfoid
UDG Gene Information Type  -udginfosym  Yes                     None               -udginfoid
UDG Gene Information Type  -udginforna  Yes                     None               -udginfoid
UDG Name                   -udgname     No                      A string           "NoName"
UDG Description File       -udgdfile     No                     "path/to/file"     None
User-defined library       -udl         No                      None               None
UDL Input File             -udlinput    Yes                     "path/to/file"     None
UDL Data Format            -udlf0exc    Yes                     None               -udlf0exc
=========================  ===========  ========  ============  =================  =================
