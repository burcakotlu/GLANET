===============
GLANET Overview
===============

------
GLANET
------

GLANET is Genomic Loci AssociatioN and Enrichment Tool.

GLANET achieves annotation, enrichmeny analysis and regulatory sequence analsyis 
between given two sets of genomic intervals.                                                                                                             
The first set of intervals are the user provided genomic intervals.
They may come from GWAS yielded genomic variants (SNPs,CNVs) or from a NGS experiment.  
The important point is that given genomic intervals must be related with each other.

The second set consists of identified/annotated genomic                                                                                 
intervals such as DNaseI Hypersensitive sites (DHs), Transcription Factor                                                                              
binding sites (TFs), Histone Modification sites (HMs), exons, introns, proximal and distal of                                                          
genes and so on.  User can provide the second set using GLANET's user defined gene set
or user defined library features.
                                                                                                                                   
In order to gain biological insight, intersecting these two sets, annotation of given genomic intervals 
with identified/annotated genomic intervals and finding the genomic intervals overlapping significantly are vital 
for a comprehensive understanding and interpretation of the GWAS disease associated variants.

GLANET aims to find the intersections between these two sets which is called Annotation and to find out the 
significant intersections by creating a sampling based null distribution which is called Enrichment. 

Annotation which is finding overlapping intervals is easily achieved by interval tree search where the second set is 
represented as an interval tree. 
However, Enrichment which is finding out the genomic intervals which overlap significantly is not trivial 
since genome is not homogeneous, gene density differs and genome organization is complex. 
For Enrichment Analysis, GLANET utilizes sampling based statistical test which takes 
such as GC content and Mapability, genomic biases into account, during random interval generation. 

And in Regulatory Sequence Analysis, transcription factor annotation results are further analysed whether any SNP (given in the first set) 
increases or decreases the binding affinity of the annotated transcription factor by scanning the reference sequence, 
all possible altered sequences and transcription factor sequence with the position frequency matrices 
of the annotated transcription factor. 
During regulatory sequence analysis `RSAT <http://www.rsat.eu/>`_'s matrix scan web service is used.

GLANET Pipeline

![Pipeline](/docs/images/GLANET_pipeline.jpg).

GLANET enables user to load its user defined library and/or user defined gene sets in order to use them as Annotation Library. 
Namely, genomic intervals obtained from user defined library and/or user defined gene sets will be populated in the Annotation Library
and Annotation will be achieved w.r.t. these genomic intervals.

---------------
GLANET Features
---------------

* GLANET accepts given genomic intervals of any length.
* GLANET has developed sampling-based enrichment analysis which compensate for the bias introduced by given genomic intervals of varying length.
* GLANET's sampling-based enrichment analysis accounts for genomic biases in addition to the given interval's length and the chromosome it is located on.
* GLANET considers genomic biases such as GC content and mapability of given genomic intervals.
* GLANET interprets gene sets in three different ways which are exon-based, regulation-based and all-based manner.
  Exon-based gene set takes exons of genes, regulation-based takes introns, upstream and downstream proximal regions of genes 
  and lastly all-based takes all the defined regions in exon-based and regulation-based, plus upstream and downstream distal regions of genes of each gene set into account.
* GLANET enables user to load user defined gene sets and/or user defined library and to achieve Annotation w.r.t the genomic intervals obtained from them.
* GLANET provides Regulatory Sequence Analysis for all of the annotated TFs using `RSAT <http://www.rsat.eu/>`_'s matrix scan web service.
* GLANET has assessed its type I error and power by designing data-driven computational experiments on two cell lines, GM12878 and K562.


-------------
GLANET Output
-------------

After a successful GLANET execution 

* Annotation results can be found under ~path/to/tool/Output/givenJobName/Annotation/.
* Enrichment results can be found under ~path/to/tool/Output/givenJobName/Enrichment/.
* Regulatory Sequence Analysis results can be found under ~path/to/tool/Output/givenJobName/RegulatorySequenceAnalysis/.
* GLANET log file (GLANET.log) can be found under the same directory where GLANET.jar is located. If you are running GLANET from the source code, GLANET log file will be created under Glanet project directory.

===========================================
GLANET Installation and System Requirements
===========================================

-------------------
GLANET Installation
-------------------

1. Java

Install latest Java SE from [here](http://www.oracle.com/technetwork/articles/javase/index-jsp-138363.html).

2. Perl

For **Windows** users, Strawberry Perl can be downloaded from [here](http://www.strawberryperl.com>).

For **Mac OS X** users, the operating system comes with the installed Perl. If you want to update or install Perl, open Terminal.app and write the command below::

	$ sudo curl -L http://xrl.us/installperlosx | bash

For **Linux** users, Perl is probably installed in your operating system. If you want to update or install Perl, open a Terminal and write the command below. After installing perl, you also may need to install parser library for Perl. You may run the commands below seperately::

	$ sudo curl -L http://xrl.us/installperlnix | bash
	$ sudo apt-get build-dep libxml-parser-perl

After you have installed Perl, you need to install the required modules.

First install cpanminus, which will allow other modules to be installed easily. Open Terminal (or Command Prompt in Windows) and write the command below::

	$ cpan App:cpanminus

Now, install Getopt/Long.pm module. Note that if any of the modules below is installed in your computer, you will be notified::

	$ cpanm Getopt::Long

**Important**: If one of your modules is not installed successfully, then you may run the command with sudo, if you have Linux/Mac OS X operating system. For Windows, you may want to run command prompt as administrator instead of running the command with sudo. For Linux and Mac OS X operating systems, you may install a module with sudo as following::

	$ sudo cpanm Getopt::Long

**Important**: If you are still having issues for installing a module, try to add them -f option, which will take longer but it will try to force the module to be installed.  Note that if this step still does not work, we suggest you to try installing the modules another time. There might be some problems with the server currently. For example, if Getopt/Long.pm is still not installed, you may want to write::

	$ cpan -f Getopt::Long

Install LWP/UserAgent.pm module::

	$ cpanm LWP::UserAgent

Install HTTP/Request/Common.pm module::

	$ cpanm HTTP::Request::Common

Install HTTP/Headers.pm module::

	$ cpanm HTTP::Headers

Install XML/XPath.pm module::

	$ cpanm XML::XPath

Install XML/XPath/XMLParser.pm module::

	$ cpanm XML::XPath::XMLParser
	
Install JSON module::

	$ cpanm JSON
	
3. Download executable GLANET.jar from [here](https://drive.google.com/open?id=0BwmVAJuppNSMOTJJSXZMZDRzSzg&usp=sharing).
	
4. Download Data.zip from [here](https://drive.google.com/open?id=0BwmVAJuppNSMTnlVLWpqdGNzOFk&usp=sharing) and extract it under a directory you name it, for example GLANET (e.g.: ~path/to/GLANET/). 
   Data.zip contains the necessary data for Annotation.	
   The important point is that this directory must be the parent directory of extracted Data directory (e.g.: ~path/to/GLANET/Data).
   

Once you have followed all these steps, you should be ready to run GLANET properly.

--------------------------
GLANET System Requirements
--------------------------

1. You can download and run GLANET in any operating system (Windows, Mac OS X, Linux).

2. Your computer should have at least 8 GB memory. Otherwise, you may not be able to use all the functionalities of GLANET.

3. Java SE 8 (or higher) should be installed in your computer in order to execute GLANET. We suggest you to use the latest Java SE update.

4. Perl should be installed in your computer.

5. During execution of GLANET calls NCBI E-utilities and RSAT web service, therefore GLANET must be run an a computer with an internet connection.
