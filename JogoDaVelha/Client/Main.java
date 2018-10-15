import java.util.Timer;
import java.util.TimerTask;
import java.io.*;
public class Main {

    private String str = "";

    TimerTask task = new TimerTask()
    {
        public void run()
        {
            if( str.equals("") )
            {
                clearConsole();
                System.out.println( "you input nothing. input:" );
            }
        }
    };

    private static void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (final IOException | InterruptedException e){
            // pass
            System.out.println();
            System.out.println();
        }
    }

    void getInput() throws Exception {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 2*1000, 2000);
        System.out.println( "Input a string within 10 seconds: " );
        BufferedReader in = new BufferedReader(
                new InputStreamReader( System.in ) );
        str = in.readLine();

        timer.cancel();
        System.out.println( "you have entered: "+ str );
    }

    public static void main( String[] args )
    {
        try
        {
            (new Main()).getInput();
        }
        catch( Exception e )
        {
            System.out.println( e );
        }
        System.out.println( "main exit..." );
    }

}
