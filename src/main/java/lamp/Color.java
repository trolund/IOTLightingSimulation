package lamp;

public class Color {

    private int R;
    private int G;
    private int B;

    public Color() {
        R = 0;
        G = 0;
        B = 0;
    }

    public Color(int r, int g, int b) {
        R = r;
        G = g;
        B = b;
    }

    private void setColor(int r, int g, int b){
        R = r;
        G = g;
        B = b;
    }

}
