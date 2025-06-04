package pack;

import pack.CRS.GUI;

//import java.util.Scanner;

//import org.junit.runner.JUnitCore;
//import org.junit.runner.Result;
//import org.junit.runner.notification.Failure;

public class Main {

//	@SuppressWarnings("restriction")
	public static void main(String[] args) 
	{
//		System.out.println("Run as a GUI, type \"GUI\" or run as a Console Test Informations, type \"CONSOLE\"");
//		
//		Scanner scan = new Scanner(System.in);
//		
//		String argument = scan.nextLine();
//		
//		if(argument.equalsIgnoreCase("GUI"))
//		{
		@SuppressWarnings("unused")
		CRS system = new CRS();
		GUI.setUp();
//		}
//		//Console Test Informations mode
//		else if(argument.equalsIgnoreCase("CONSOLE"))
//		{
//			CRS system = new CRS();
//			CRS.GUI.load(true);
//			System.out.println("System Informations: ");
//			System.out.println(system);
//			System.out.println("Tests Results: ");
//			
//			Result result = JUnitCore.runClasses(UnitTests.class);
//
//			// Print results for failed tests
//	        if (!result.getFailures().isEmpty()) {
//	            System.out.println("\tFailed Tests:");
//	            for (Failure failure : result.getFailures()) {
//	                System.out.println("\t\t"+failure.toString());
//	            }
//	        }
//
//	        // Print results for successful tests
//	        System.out.println("\tSuccessful Tests:");
//	        int successfulTests = result.getRunCount() - result.getFailureCount();
//	        System.out.println("\tTotal Successful Tests: " + successfulTests);
//
//	        // Overall test results
//	        System.out.println("\n\tOverall Result:");
//	        if (result.wasSuccessful()) {
//	            System.out.println("\tAll tests passed!");
//	        } else {
//	            System.out.println("\tSome tests failed. Total failed: " + result.getFailureCount());
//	        }
//		}
	}

}
