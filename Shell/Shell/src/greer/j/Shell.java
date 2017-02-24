package greer.j;

import java.io.File;
import java.io.IOException;
import java.lang.IllegalStateException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Shell
{	
    public static Path projectDir = Paths.get(".").toAbsolutePath();
    public static Path cdPlaceHolder = Paths.get(".").toAbsolutePath();
	public static String userInput;
	
	public static void main(String[] args)
	{
		System.out.println("Welcome to Shell!");
		System.out.println("");
		prompt();
	}
	
	public static void prompt()
	{
		System.out.printf("prompt> ");
		Scanner input = new Scanner(System.in);
		userInput = input.nextLine();
        
        if (userInput.contains("help"))
        {
        	help();
        }
        else if (userInput.contains("dir"))
        {
        	dir();
        }
        else if (userInput.contains("cd"))
        {
        	cd();
        }
        else if (userInput.contains("show"))
        {
        	show();
        }
        else if (userInput.contains("exit"))
        {
        	exit();
        }
        else
        {
        	System.out.println("Unknown command " + "\"" + userInput + "\"");
        	prompt();
        }
        
	}

	public static void help()
	{
		System.out.println("  help           Show list of commands");
		System.out.println("  dir            List contents of current directory");
		System.out.println("  cd [dir]       Change to directory");
		System.out.println("  show [file]    Show contents of file");
		System.out.println("  exit           Exit the shell");
		System.out.println("");
		prompt();
		
	}
	
	public static void dir()
	{
	    System.out.printf("Directory of %s%n", projectDir);  
	 
	    System.out.printf("%n");
	    try
	    {	   
	    	DirectoryStream<Path> children = Files.newDirectoryStream(projectDir);

	        for (Path p : children)
	        {
	        	if (Files.isDirectory(p))
	        	{
	        		System.out.printf("d        %s%n", p.getFileName());
	        	}
	        	else
	        	{
	        		long size = Files.size(p);
	        		System.out.printf("     " + size + " %s%n", p.getFileName());
	        	}
	        }
	    }
	    catch (Exception e)
	    {
	    }
	    
	    prompt();
	    
	}
	
	public static void cd()
	{
		try
		{
			DirectoryStream<Path> children = Files.newDirectoryStream(projectDir);
			userInput = userInput.replaceAll("cd" + "\\s*+", "");
			String red = userInput;
			
			if (userInput.equals(""))
			{
				System.out.println("Path not given.");
			}
			
			else 
			{					
				for (Path p : children)
				{
					if (userInput.equals("\\" + p.getFileName().toString()))
					{
						projectDir = Paths.get(projectDir + userInput);														
						red = projectDir.toString();
						break;							
					}						
					else
					{						
						red = Paths.get(userInput).toString();	
					}
				}
				
				cdPlaceHolder = Paths.get(red).toAbsolutePath();
				File blue = new File(red);
				if (userInput.equals(cdPlaceHolder.getFileName().toString()) && blue.exists() && !Files.isDirectory(cdPlaceHolder))
				{
					System.out.println("Path is not a directory: \"" + cdPlaceHolder.toAbsolutePath() + "\"");
				}
					
				else if(Files.isDirectory(cdPlaceHolder))
				{											
					projectDir = Paths.get(red).toAbsolutePath();
					System.out.println("SUCCESS: \"" + projectDir + "\"");
				}
					
				else
				{
					System.out.println("Path does not exist: \"" + red + "\"");
				}
					
			}
		}
		catch (Exception e)		
		{			
		}	    
	    prompt();
	}
	
	public static void show()
	{
		try
		{
			
			userInput = userInput.replaceAll("show" + "\\s*+", "");
			Path yellow = Paths.get(userInput).toAbsolutePath();
			String red = yellow.toString();
			File blue = new File(red);
			if (userInput.equals(""))
			{
				System.out.println("File not given.");
			}
			
			else if (blue.exists() && !Files.isDirectory(yellow))
			{				
				Path file = Paths.get(projectDir.toString() + "/" + userInput);   
				Scanner scanner  = new Scanner(file);
				while (scanner.hasNext()) 
				{
					System.out.printf("\"%s\"%n", scanner.nextLine());
				}
				scanner.close();              
        
				System.out.printf("%n");
				Scanner scanner1 = new Scanner(file);
          
				while (scanner.hasNext()) 
				{
					String line = scanner1.nextLine();
					String [] tokens = line.split("\t");
					int index = Integer.parseInt(tokens[0]);
					String lines = tokens[1];
					System.out.printf("Index: %d, Color: \"%s\"%n", index, lines);
				}
				scanner.close();
			}
			
			else if (Files.isDirectory(yellow))
			{
				System.out.println("Path is not a file: \"" + yellow.toAbsolutePath() + "\"");
			}
			
			else
			{
				System.out.println("Path does not exist: \"" + red + "\"");
			}
		}
		catch (Exception e)
		{
		}
		
		prompt();
	}
	
	public static void exit()
	{
		System.out.println("");
		System.out.println("Good bye!");
		System.exit(1);
	}
}