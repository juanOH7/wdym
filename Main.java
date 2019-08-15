import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static public void main(String argv[]) {

        System.out.println("Bienvenido a la consola \"inteligente\" ");
        System.out.println("Deseas iniciar? (Y/N)");
        String input = System.console().readLine();
        input = input.toLowerCase();
        while (input.equals("y")) {
            System.out.print(":");
            String command = System.console().readLine();
            runShell(command);
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

}