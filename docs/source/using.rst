=================
GLANET User Guide
=================

GLANET includes both graphical user interface (GUI) and command-line interface. In either case, to run GLANET, one should write the following basic command on Terminal (Linux or Mac OS X) or on Command Prompt (Windows)\*::

	$ java −jar ~path/to/GLANET.jar -Xms8G -Xmx8G

Note that we ask you to allow GLANET to allocate 8GB of memory, if necessary. Therefore, even if you want to use the GUI, it is crucial to run GLANET.jar with this way.

\* Throughout the guide, we will use ~path/to/GLANET.jar to indicate your absolute path to GLANET.jar

--------------------
Command-Line Options
--------------------

In the following table, commands and their prerequisite commands, if any, are specified. A command is required if and only if its precondition command(s) is specified. Command IDs distinguish options between each other. You must at most set one option per ID. For example, if you set both -f0 and -fbed, the program will terminate by giving an error message. Details of the commands with examples are specified below. Note that command "-c" (1) indicates that GLANET will run in command-line, not with GUI.

==  ==============  ========  ===========================  =================  =================
ID  Command         Required  Precondition                 Parameter          Default Parameter
==  ==============  ========  ===========================  =================  =================
1   `-c`_           No        None                         None               None
2   `-i`_           Yes       1                            "path/to/file"     None
3   `-grch37`_      Yes       1                            None               `-grch37`_
3   `-grch38`_      Yes       1                            None               `-grch37`_
4   `-g`_           Yes       1                            "path/to/folder/"  None
5   `-f1`_          Yes       1                            None               None
5   `-f0`_          Yes       1                            None               None
5   `-fbed`_        Yes       1                            None               None
5   `-fgff`_        Yes       1                            None               None
5   `-fdbsnp`_      Yes       1                            None               None
6   `-b`_           No        1                            An integer value   1
7   `-dnase`_       No        1                            None               None
8   `-histone`_     No        1                            None               None
9   `-tf`_          No        1                            None               None
10  `-kegg`_        No        1                            None               None
11  `-tfkegg`_      No        1                            None               None
12  `-celltfkegg`_  No        1                            None               None
13  `-udg`_         No        1                            None               None
14  `-udginput`_    Yes       13                           "path/to/file"     None
15  `-udginfoid`_   Yes       13                           None               `-udginfoid`_
15  `-udginfosym`_  Yes       13                           None               `-udginfoid`_
15  `-udginforna`_  Yes       13                           None               `-udginfoid`_
16  `-udgname`_     No        13                           A string           "NoName"
17  `-udgdfile`_    No        13                           "path/to/file"     None
18  `-udl`_         No        1                            None               None
19  `-udlinput`_    Yes       18                           "path/to/file"     None
20  `-udldf0exc`_   Yes       18                           None               `-udlf0exc`_
20  `-udldf0inc`_   Yes       18                           None               `-udlf0exc`_
20  `-udldf1exc`_   Yes       18                           None               `-udlf0exc`_
20  `-udldf1inc`_   Yes       18                           None               `-udlf0exc`_
21  `-e`_           No        7, 8, 9, 19, 11, 12, 13, 18  None               None
22  `-rd`_          Yes       21                           None               `-rd`_
22  `-rdgcm`_       Yes       21                           None               `-rd`_
23  `-mtbhfdr`_     Yes       21                           None               `-mtbhfdr`_
23  `-mtbc`_        Yes       21                           None               `-mtbhfdr`_
24  `-fdr`_         Yes       21                           A float value      0.05
25  `-sc`_          Yes       21                           A float value      0.05
26  `-p`_           Yes       21                           An integer value   5000
27  `-pe`_          Yes       21                           An integer value   1000
28  `-rsat`_        No        9, 11, 12, 21                None               None
29  `-j`_           Yes       1                            A string           "NoName"
==  ==============  ========  ===========================  =================  =================

:option:`dest_dir`

--------------------------------
Command-Line Option Descriptions
--------------------------------

There are several parameters that are either required or optional to make GLANET run in Terminal or in Command Prompt. Whether a parameter is required or not will be specified as we describe it. The order of parameters is not fixed. One may set the parameters in any order. Some parameters may require some other parameters to be set as preconditions and postconditions, which will also be indicated. You can see the preconditions and postconditions of a command as shown in `Command-Line Options`_

-c
^^

To enable GLANET to run in Terminal or Command Prompt, it must be indicated with :option:`-c` option. If there is no such option specified, program will run with its graphical user interface. Example run is as following::

	$ java −jar ~path/to/GLANET.jar -Xms8G -Xmx8G -c

-i
^^

**Required** if :option:`-c` is set. Input file location must be specified just after :option:`-i` option as parameter. Example run::

	$ java −jar ~path/to/GLANET.jar -Xms8G -Xmx8G -c -i "/Users/User/InputFile.txt"

-grch37
^^^^^^^

**Required** if :option:`-c` is set. This option specifies assembly format as GRCh37.p13. If you do not set anything, :option:`-grch37` is set as default. Example run::

	$ java −jar ~path/to/GLANET.jar -Xms8G -Xmx8G -c -i "/Users/User/InputFile.txt" -grch38

-grch38
^^^^^^^

**Required** if :option:`-c` is set. This option specifies assembly format as GRCh38. If you do not set anything, :option:`-grch37` is set as default. Example run::

	$ java −jar ~path/to/GLANET.jar -Xms8G -Xmx8G -c -i "/Users/User/InputFile.txt" -grch38

