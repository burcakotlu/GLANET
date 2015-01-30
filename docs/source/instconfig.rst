====================================
Installation and System Requirements
====================================

------------
Installation
------------

• Java

Install latest Java SE from `here <http://www.oracle.com/technetwork/articles/javase/index-jsp-138363.html>`_

• Perl

For **Windows** users, Strawberry Perl can be downloaded from `here <http://www.strawberryperl.com>`_

For **Mac OS X** users, the operating system comes with the installed Perl. If you want to update or install Perl, open Terminal.app and write the command below::

	$ sudo curl -L http://xrl.us/installperlosx | bash

For **Linux** users, Perl is probably installed in your operating system. If you want to update or install Perl, open a Terminal and write the command below::

	$ sudo curl -L http://xrl.us/installperlnix | bash

After you have installed Perl, you need to install the required modules.

First install cpanminus, which will allow other modules to be installed easily. Open Terminal (or Command Prompt in Windows) and write the command below::

	$ cpan App:cpanminus

Now, install JSON. Note that if JSON is already installed in your computer, the command below will notify you::

	$ cpanm JSON

Now, install XML/XPath.pm::

	$ cpanm XML::XPath

Once you have followed all these steps, you should be ready to run GLANET properly.

------------
Requirements
------------

You are free to use GLANET in any operating system Java supports (Windows, Mac OS X, Linux)

Your computer should have at least 8 GB memory. You may not be able to use all the functionalities of GLANET, if you have a memory less than 8GB.

Java SE 8 (or higher) should be installed in your computer in order to execute the program. We suggest you to use the latest Java SE update.

Perl should be installed in your computer.