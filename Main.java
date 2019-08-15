import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Main {
    static public void main(String argv[]) {
        ArrayList<String> ls1 = new ArrayList<>();
        ArrayList<String> ls2 = new ArrayList<>();
        ArrayList<String> df1 = new ArrayList<>();
        ArrayList<String> df2 = new ArrayList<>();
        ls1.add("l");
        ls1.add("o");
        ls1.add("p");
        ls1.add("k");
        ls1.add("ñ");
        ls1.add(",");
        ls1.add(".");

        ls2.add("s");
        ls2.add("w");
        ls2.add("e");
        ls2.add("a");
        ls2.add("d");
        ls2.add("z");
        ls2.add("x");

        df1.add("d");
        df1.add("e");
        df1.add("r");
        df1.add("s");
        df1.add("f");
        df1.add("x");
        df1.add("c");

        df2.add("f");
        df2.add("r");
        df2.add("t");
        df2.add("d");
        df2.add("g");
        df2.add("c");
        df2.add("v");

        System.out.println("Bienvenido a la consola \"inteligente\" ");
        System.out.println("Deseas iniciar? (Y/N)");
        String input = System.console().readLine();
        input = input.toLowerCase();
        while (input.equals("y")) {
            System.out.print(":");
            String command = System.console().readLine();
            
            String fileName = "ls_alternative.txt";

            String line = null;
            ArrayList<String> readListLS = new ArrayList<>();
            try {
                FileReader fileReader = new FileReader(fileName);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while ((line = bufferedReader.readLine()) != null) {
                    readListLS.add(line);
                }
                bufferedReader.close();
            } catch (FileNotFoundException ex) {
            } catch (IOException ex) {
            }

            String fileName2 = "df_alternative.txt";

            String line2 = null;
            ArrayList<String> readListDF = new ArrayList<>();
            try {
                FileReader fileReader = new FileReader(fileName2);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while ((line2 = bufferedReader.readLine()) != null) {
                    readListDF.add(line2);
                }
                bufferedReader.close();
            } catch (FileNotFoundException ex) {
            } catch (IOException ex) {
            }

            if (command.equals("ls") || command.equals("df")) {
                runShell(command);
            }else if (readListLS.contains(command)){
                runShell("ls");
            }else if (readListDF.contains(command)){
                runShell("df");
            }else if (ls1.contains(String.valueOf(command.charAt(0)))
                    || ls2.contains(String.valueOf(command.charAt(1)))) {
                System.out.println("Did you mean? \"ls\" Y/N ");
                String resp = System.console().readLine();
                resp = resp.toLowerCase();
                if (resp.equals("y")) {
                    runShell("ls");
                    write(command);
                } else {
                    System.out.println("OK");
                }
            } else if (df1.contains(String.valueOf(command.charAt(0)))
                    || df2.contains(String.valueOf(command.charAt(1)))) {
                System.out.println("Did you mean? \"df\" Y/N ");
                String resp = System.console().readLine();
                resp = resp.toLowerCase();
                if (resp.equals("y")) {
                    runShell("df");
                    write2(command);
                } else {
                    System.out.println("OK");
                }
            } else {
                runShell(command);
                System.out.println("Commando ejecutado pero no aprendemos de estos!!");
            }

            System.out.println("Deseas continuar? (Y/N)");
            input = System.console().readLine();
            input = input.toLowerCase();
        }

    }

    static void runShell(String comm) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", comm);

        try {

            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println(output);
            } else {
                System.out.println("Error!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void write(String input) {

        String path = "ls_alternative.txt";
        input = input+"\n";
        try {
            Files.write(Paths.get(path), input.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
        }
    }

    static void write2(String input) {
       
        String path = "df_alternative.txt";
        input = input+"\n";
        try {
            Files.write(Paths.get(path), input.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
        }
    }
}
