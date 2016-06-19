===========================================
GLANET Installation and System Requirements
===========================================

-------------------
GLANET Installation
-------------------

1. Java

Install latest Java SE from `here <http://www.oracle.com/technetwork/articles/javase/index-jsp-138363.html>`_

2. Perl

**Important:** If you have installed Perl before but it is not a version of Strawberry Perl, we strongly suggest that you, first, uninstall the Perl you have and then follow the steps specified below. It is important that you do that because we assume the required modules will be downloaded from Strawberry Perl's database.

For **Windows** users, Strawberry Perl can be downloaded from `here <http://www.strawberryperl.com>`_

For **Mac OS X** users, the operating system comes with the installed Perl. If you want to update or install Perl, open Terminal.app and write the command below::

	$ sudo curl -L http://xrl.us/installperlosx | bash

For **Linux** users, Perl is probably installed in your operating system. If you want to update or install Perl, open a Terminal and write the command below. After installing perl, you may also need to install parser library for Perl. You may run the commands below seperately::

	$ sudo curl -L http://xrl.us/installperlnix | bash
	$ sudo apt-get build-dep libxml-parser-perl

After you have installed Perl, you need to install the required modules.

First install cpanminus, which will allow other modules to be installed easily. **Open Terminal** (**or Command Prompt in Windows**) and write the command below::

	$ cpan App:cpanminus

Now, install Getopt/Long.pm module. Note that if any of the modules below is already installed in your computer, you will be notified::

	$ cpanm Getopt::Long

**Important**: If one of your modules is not installed successfully, then you may run the command with sudo, if you have Linux/Mac OS X operating system. For Windows, you may want to run command prompt as administrator instead of running the command with sudo. For Linux and Mac OS X operating systems, you may install a module with sudo as following::

	$ sudo cpanm Getopt::Long

**Important**: If you are still having issues for installing a module, try to add them using -f option, which will take longer but it will try to force the module to be installed.  Note that if this step still does not work, we suggest you to try installing the modules another time. There might be some problems with the server at that time. For example, if Getopt/Long.pm is still not installed, you may want to write::

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
	
3. Download executable GLANET.jar from :ref:`executable-label`
	
4. Download Data.zip from :ref:`data-label` and extract it as **Data** under a directory you name it, which will become your *GLANET Folder*. 

			   | e.g.: ~path/to/GLANET Folder/
			   
   Data.zip contains the all the necessary data for Annotation.	
   The important point is that this *GLANET Folder* directory must be the parent directory of extracted **Data** directory.
   
			   | ~path/to/GLANET Folder/Data/
   

Once you have followed all these steps, you should be ready to run GLANET properly.

--------------------------
GLANET System Requirements
--------------------------

1. You can download and run GLANET in any operating system (Windows, Mac OS X, Linux).

2. Your computer should have at least 8 GB memory. Otherwise, you may not be able to use all the functionalities of GLANET.

3. Java SE 8 (or higher) should be installed in your computer in order to execute GLANET. We suggest you to use the latest Java SE update.

4. Perl should be installed in your computer.

5. During execution, GLANET makes calls to NCBI E-utilities and RSAT web service, therefore GLANET must be run an a computer with an internet connection.
