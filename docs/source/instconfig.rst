===========================================
GLANET Installation and System Requirements
===========================================

-------------------
GLANET Installation
-------------------

1. Java

Install latest Java SE from `here <http://www.oracle.com/technetwork/articles/javase/index-jsp-138363.html>`_

2. Perl

For **Windows** users, Strawberry Perl can be downloaded from `here <http://www.strawberryperl.com>`_

For **Mac OS X** users, the operating system comes with the installed Perl. If you want to update or install Perl, open Terminal.app and write the command below::

	$ sudo curl -L http://xrl.us/installperlosx | bash

For **Linux** users, Perl is probably installed in your operating system. If you want to update or install Perl, open a Terminal and write the command below::

	$ sudo curl -L http://xrl.us/installperlnix | bash

After you have installed Perl, you need to install the required modules.

First install cpanminus, which will allow other modules to be installed easily. Open Terminal (or Command Prompt in Windows) and write the command below::

	$ cpan App:cpanminus

Now, install LWP/UserAgent.pm module. Note that if any of the modules below is installed in your computer, you will be notified::

	$ cpanm LWP::UserAgent

Install XML/XPath.pm module::

	$ cpanm XML::XPath

Install JSON module::

	$ cpanm JSON
	
3. Download executable GLANET.jar  from :ref:`glanet-jar-label`
	
4. Download Data.zip from :ref:`data-zip-label`  and extract it under a directory you name it, for example GLANET (e.g.: ~path/to/GLANET/). 
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