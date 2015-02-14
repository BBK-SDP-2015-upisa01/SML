package sml;
import java.util.ArrayList;
import lombok.Data;
/*
 * The machine language interpreter
 * F:\00_MSc\03_Soft-Des-Prog\courseWork\workshop\SML6\SML
 * I:\git\RepSml\SML
 */
@Data
public class Machine {
	// The labels in the SML program, in the order in which
	// they appear (are defined) in the program

	private Labels labels;

	// The SML program, consisting of prog.size() instructions, each
	// of class Instruction (or one of its subclasses)
	private ArrayList<Instruction> prog;

	// The registers of the SML machine
	private Registers registers;

	// The program counter; it contains the index (in prog) of
	// the next instruction to be executed.
	private int pc;
	{
		labels = new Labels();
		prog = new ArrayList<>();
		pc = 0;
	}
	public static void main(String[] args) {
		// print String arguments passed to the main
        //System.out.println("String arguments passed while running this Java Program : ");
        //for(String argument : args){ System.out.print(argument+ " ");}
        System.out.println("INSTRUCTION:\n 1| Lombook must be install if using eclipse \n 2| In this programm the user need to pass <programmeInst.txt> as programme argument \n"
        		+"In Eclipse Right click on Machine.java > run as > Run configuration > argument tab > in programme enviroment you must type: programmeInst.txt \n\n");
        
		Machine m = new Machine();
		Translator t;
		try {
			t = new Translator(args[0]);
			t.readAndTranslate(m.getLabels(), m.getProg());	
		} catch (Exception ex) {
			System.out.println("*** User need to pass programmeInst.txt as programme argument \n*** Else 0 istruction will be passed to the programme *** ");
			return;
		}
		//Translator t = new Translator(args[0]);//____________________________
		//Translator t = new Translator("programmeInst.txt");

		if(m.getProg().size()==0){System.out.println("\nSorry The program has " + m.getProg().size() + " instructions.");return;}
		System.out.println("\nHere is the program; it has " + m.getProg().size() + " instructions.");
		System.out.println(m);

		System.out.println("Beginning program execution.");
		m.execute();
		System.out.println("Ending program execution.");

		System.out.println("Values of registers at program termination:");
		System.out.println(m.getRegisters() + ".");
	}
	// Print the program
	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i != getProg().size(); i++)
			s.append(getProg().get(i) + "\n");
		return s.toString();
	}
	// Execute the program in prog, beginning at instruction 0.
	// Precondition: the program and its labels have been store properly.
	public void execute() {
		setPc(0);
		setRegisters(new Registers());
		while (getPc() < getProg().size()) {
			Instruction ins = getProg().get(getPc());
			setPc(getPc() + 1);
			ins.execute(this);
		}
	}
	
}
