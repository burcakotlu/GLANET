===============
GLANET Tutorial
===============

GLANET includes both graphical user interface (GUI) and command-line interface. 
In either case, to run GLANET, one should write the following basic command on Terminal (Linux or Mac OS X) or on Command Prompt (Windows)\*::

	$ java −jar ~path/to/GLANET.jar -Xms8G -Xmx8G

Note that we ask you to allow GLANET to allocate 8GB of memory in order to make use of all GLANET facilities.

\* Throughout the guide, we will use ~path/to/GLANET.jar to indicate your absolute path to GLANET.jar

----------
GLANET GUI
----------

.. image:: GLANET_GUI_UpperPart.jpg
.. image:: GLANET_GUI_LowerPart.jpg

GLANET GUI enables user to annotate given genomic intervals w.r.t. ENCODE regulatory elements, predefined gene sets such as KEGG pathways, user defined gene sets and user defined library.

Other facilities of GLANET GUI includes enrichment analysis and regulatory sequence analysis.

1)	Input File Name: You have to provide input file which contains user given genomic intervals.
	Assume that Data.zip is extracted under a directory called GLANET. 
	Then sample input data can be reached from ~path/to/GLANET/Data/demo_input_data/.
	
2)	Input Format: For the user given input file, Input Format has to be selected accordingly.
	GLANET supports input formats such as dbSNP IDs, BED, GFF3, 1-based coordinates (End Inclusive) and 0-based coordinates (End Inclusive).
	
	* dbSNP IDs
		-  Sample input data for dbSNP IDs can be reached at ~path/to/GLANET/Data/demo_input_data/CVD_rsIDs_Mediation.txt, Input Format must be selected as dbSNP IDs.
	
	* BED
		-  Sample input data for BED can be reached at ~path/to/GLANET/Data/demo_input_data/CVD_Mediation_0BasedStart_EndExclusive_GRCh37_p13_coordinates.bed, Input Format must be selected as BED.

	* GFF3
		-  Sample input data  can be reached at ~path/to/GLANET/Data/demo_input_data/CVD_Mediation_0Based_Start_End_GRCh37_p13_coordinates.gff3, Input Format must be selected as GFF3.	

	* 1-based coordinates (End Inclusive)
	* 0-based coordinates (End Inclusive)

 	Other sample input data can be reached in ~path/to/GLANET/Data/demo_input_data/.
	

3)	Assembly: GLANET supports two assemblies.

	* GRCh38
	* GRCH37.p13


	In case of BED, GFF3, 1-based coordinates (End Inclusive) or 0-based coordinates (End Inclusive) is chosen as Input Format, then Assembly has to be set as either GRCh38 or GRCH37.p13.
	In cased of dbSNP IDs, there is no need for Assembly selection.

4)	Glanet Folder: Set the GLANET folder (e.g.:  ~path/to/GLANET) which is the parent of Data directory.
	Please note that Glanet folder can be any valid directory name (e.g.:  ~path/to/tool), the important point is that Glanet folder has to be the parent of Data folder (e.g.:  ~path/to/tool/Data).

5)	Annotation, Overlap Definition, Number of Bases: For Annotation Part, set the number of bases for overlap definition. 
	e.g: Setting number of bases as 3 means that two intervals are accepted as overlapped if and only if at least 3 bases of these intervals overlap.
	Default is 1 in order to handle the case where the snps are given as input data.
	
6) 	Annotation, Annotation Options:

	* DNase Annotation(CellLine Based)
		-  Check this check box, if you want to annotate given intervals w.r.t. ENCODE provided DNase hypersensitive sites.

	* Histone Annotation(CellLine Based)
		-  Check this check box, if you want to annotate given intervals w.r.t. ENCODE provided Histone Modifications sites.
	
	* Transcription Factor (TF) Annotation(CellLine Based)
		-  Check this check box, if you want to annotate given intervals w.r.t. ENCODE provided Transcription Factors binding sites.
	
	* KEGG Pathway Annotation
		-  Check this check box, if you want to annotate given intervals w.r.t. KEGG pathways in exon-based, regulation-based and all-based manner.
	
	* TF and KEGG Pathway Annotation
		-  Check this check box, if you want to annotate given intervals w.r.t. joint Transcription Factors binding sites and KEGG pathways in exon-based, regulation-based and all-based manner.  
		Joint annotation means that if given interval has overlapped with TF  and KEGG Pathway, then if TF and KEGG Pathway also overlaps with each other, then this TF and KEGG pathway is output. 
		Here TFs are cell line pooled.

	* TF and KEGG Pathway Annotation (CellLine Based)
		-  Check this check box, if you want to annotate given intervals w.r.t. joint Transcription Factors binding sites (CellLine Based) and KEGG pathways in exon-based, regulation-based and all-based manner.  
		Joint annotation means that if given interval has overlapped with TF  and KEGG Pathway, then if TF and KEGG Pathway also overlaps with each other, then this TF and KEGG pathway is output. 

	* User Defined Gene Set Annotation
	* User Defined Library Annotation

7)	For Enrichment Part, check Perform Enrichment box if you want Enrichment Analysis.
	GLANET will accomplish enrichment for the selected annotation options.
	
8)	Choose the Generate Random Data Mode which can be either With GC and Mapability or Without GC and Mapability.
	Default is With GC and Mapability.
	
9)	Select the Multiple Testing procedure which can be either Bonferroni Correction or Benjamini Hochberg FDR.
	In fact, GLANET performs both of the Multiple Testing procedures but results are further analysed depending on the selected Multiple Testing procedure.
	Default is Benjamini Hochberg FDR.
	
10)	Default False Discovery Rate (FDR) is 0.05.

11)	Default Bonferroni Correction Significance Criteria is 0.05.

12)	Choose the number of permutations among 5000, 10000, 50000 and 100000 choices.
	Start with smaller number of permutations, and increase number of permutations depending on your computer's performance.
	
13)	Please note that Regulatory Sequence Analysis is enabled if you have checked at least one of the following annotation options such as "TF", "TF and KEGG Pathway" or 
	"CellLine based TF and KEGG Pathway" and you have checked Perform Enrichment check box..
	If you want to carry on Regulatory Sequence Analysis, you must check the RSAT box.
	Please notice that for a lot of number of transcription factors which found to be significant after enrichment analysis, 
	this Regulatory Sequence Analysis using RSAT takes quite long time. Regulatory Sequence Analysis makes use of web service calls of RSAT.
	
14)	Please give a job name which can be any valid string. Choose shorter job name so that folder names do not exceed the allowable length.

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
20  `-udldf0exc`_   Yes       18                           None               `-udldf0exc`_
20  `-udldf0inc`_   Yes       18                           None               `-udldf0exc`_
20  `-udldf1exc`_   Yes       18                           None               `-udldf0exc`_
20  `-udldf1inc`_   Yes       18                           None               `-udldf0exc`_
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

Note that exact path to the input file comes just after :option:`-i` option. Unless the correct path location is specified after :option:`-i`, the program may run unexpectedly. You are responsible to indicate the correct path to the input file.

-grch37
^^^^^^^

**Required** if :option:`-c` is set. This option specifies assembly format as GRCh37.p13. If you do not set anything, :option:`-grch37` is set as default. Example run::

	$ java −jar ~path/to/GLANET.jar -Xms8G -Xmx8G -c -i "/Users/User/InputFile.txt" -grch38

-grch38
^^^^^^^

**Required** if :option:`-c` is set. This option specifies assembly format as GRCh38. If you do not set anything, :option:`-grch37` is set as default. Example run::

	$ java −jar ~path/to/GLANET.jar -Xms8G -Xmx8G -c -i "/Users/User/InputFile.txt" -grch38

-g
^^

**Required** if :option:`-c` is set. Glanet folder location must be specified just after writing :option`-g`. Example run::

	$ java −jar ~path/to/GLANET.jar -Xms8G -Xmx8G -c -g "~/Users/User/GLANET/"

-f1
^^^

**Required** if :option:`-c` is set. One of the input format options ( :option:`-f1`, :option:`-f0`, :option:`-fbed`, :option:`-fgff`, :option:`-fdbsnp`) must be specified. This option specifies 1-based coordinates (End Inclusive) is used in the input file as input format. Example run::

	$ java −jar ~path/to/GLANET.jar -Xms8G -Xmx8G -c -i "/Users/User/InputFile.txt" -grch38 -f1

-f0
^^^

**Required** if :option:`-c` is set. This option specifies 0-based coordinates (End Inclusive) is used in the input file as input format. See also `-f1`_. Example run::

	$ java −jar ~path/to/GLANET.jar -Xms8G -Xmx8G -c -i "/Users/User/InputFile.txt" -grch38 -f0

-fbed
^^^^^

**Required** if :option:`-c` is set. This option specifies BED is used in the input file as input format. See also `-f1`_. Example run::

	$ java −jar ~path/to/GLANET.jar -Xms8G -Xmx8G -c -i "/Users/User/InputFile.txt" -grch38 -fbed

-fgff
^^^^^

**Required** if :option:`-c` is set. This option specifies GFF3 is used in the input file as input format. See also `-f1`_. Example run::

	$ java −jar ~path/to/GLANET.jar -Xms8G -Xmx8G -c -i "/Users/User/InputFile.txt" -grch38 -fgff

-fdbsnp
^^^^^^^

**Required** if :option:`-c` is set. This option specifies dbSNP IDs is used in the input file as input format. See also `-f1`_. Example run::

	$ java −jar ~path/to/GLANET.jar -Xms8G -Xmx8G -c -i "/Users/User/InputFile.txt" -grch38 -fdbsnp

-b
^^

-dnase
^^^^^^

-histone
^^^^^^^^

-tf
^^^

-kegg
^^^^^

-tfkegg
^^^^^^^

-celltfkegg
^^^^^^^^^^^

-udg
^^^^

-udginput
^^^^^^^^^

-udginfoid
^^^^^^^^^^

-udginfosym
^^^^^^^^^^^

-udginforna
^^^^^^^^^^^

-udgname
^^^^^^^^

-udgdfile
^^^^^^^^^

-udl
^^^^

-udlinput
^^^^^^^^^^

-udldf0exc
^^^^^^^^^^

-udldf0inc
^^^^^^^^^^

-udldf1exc
^^^^^^^^^^

-udldf1inc
^^^^^^^^^^

-e
^^

-rd
^^^

-rdgcm
^^^^^^

-mtbhfdr
^^^^^^^^

-mtbc
^^^^^

-fdr
^^^^

-sc
^^^

-p
^^

-pe
^^^

-rsat
^^^^^

-j
^^
