package ie.tudublin;

public class Main
{
    public void game()
    {
        String[] a = {"MAIN"};
        processing.core.PApplet.runSketch(a, new Rapid_Response());
    }

    public static void main(String[] args)
    {
        Main main = new Main();
        main.game();
    }
}
