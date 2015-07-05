/**
 * 
 */
package rsat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Bur�ak Otlu
 * @date Nov 12, 2014
 * @project Glanet
 *
 */
public class TestPerl {

	public static void main( String[] args) throws IOException {

		// here we write the args. Think about command prompt.
		// we would write
		// "Perl /Users/CanFirtina/Desktop/hello.pl ..... (rest of the args here)"
		// if we wanted to run hello.pl program in command prompt. We split
		// those args by the space
		// character in the real command above. Then, put each command with the
		// corresponding order
		// in an array. So for example we put args[0] = "Perl" args[1] =
		// "//Users//CanFirtina//Desktop//hello.pl"
		// and so on... This is how java's runtime takes arguments to execute a
		// program.
		// String command =
		// "Perl C:\\Users\\Bur�ak\\Developer\\Java\\GLANET\\Glanet\\src\\rsat\\hello.pl skljd alksjd kajlsd aklsd alksjd";
		// String command =
		// "Perl C:\\Users\\Bur�ak\\Developer\\Java\\GLANET\\Glanet\\src\\rsat\\remap_api.pl batches";
		String command = "Perl C:\\Users\\Bur�ak\\Developer\\Java\\GLANET\\Glanet\\src\\rsat\\remap_api.pl --mode asm-asm --from GCF_000001405.13 --dest GCF_000001405.12 --annotation C:\\Users\\Bur�ak\\Developer\\Java\\GLANET\\Glanet\\src\\rsat\\REMAP_inputFile_LineByLine_AugmentedExonBasedKEGGPathwayResults_chrNumber_0BasedStart_1BasedEnd_GRCh37_coordinates.bed --annot_out C:\\Users\\Bur�ak\\Developer\\Java\\GLANET\\Glanet\\src\\rsat\\test_out.bed";
		String[] argsForPerlProgram = command.split( " ");
		Runtime runtime = Runtime.getRuntime();
		Process process = null;

		try{

			process = runtime.exec( argsForPerlProgram);
			process.waitFor();
		}catch( Exception e){

			System.out.println( "Error while executin: " + argsForPerlProgram[0]);
		}

		// output of the perl execution is here
		BufferedReader is = new BufferedReader( new InputStreamReader( process.getInputStream()));
		String line;
		while( ( line = is.readLine()) != null)
			System.out.println( line);

		System.out.flush();

		System.err.println( System.getProperty( "line.separator") + "Exit status = " + process.exitValue());
		return;
	}
}
