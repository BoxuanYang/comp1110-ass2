package comp1110.ass2;
import static comp1110.ass2.State.*;

public enum Shape {

    a ('a'), b ('b'), c ('c'), d ('d'), e ('e'), f ('f'), g ('g'), h ('h'), i ('h'), j ('j');

    private final char shape;

    Shape(char shape) {
        this.shape = shape;
    }


    public static State[][] getShape(char shape) {

        State[][] a = {{GREEN, WHITE, RED}, {null, RED, null}};
        State[][] b = {{null, BLUE, GREEN, GREEN}, {WHITE, WHITE, null, null}};
        State[][] c = {{null, null, GREEN, null}, {RED, RED, WHITE, BLUE}};
        State[][] d = {{RED, RED, RED}, {null, null, BLUE}};
        State[][] e = {{BLUE, BLUE, BLUE}, {RED, RED, null}};
        State[][] f = {{WHITE, WHITE, WHITE}};
        State[][] g = {{WHITE, BLUE, null}, {null, BLUE, WHITE}};
        State[][] h = {{RED, GREEN, GREEN}, {WHITE, null, null}, {WHITE, null, null}};
        State[][] i = {{BLUE, BLUE,}, {null, WHITE}};
        State[][] j = {{GREEN, GREEN, WHITE, RED}, {GREEN, null, null, null}};

        switch (shape) {
            case 'a':
                return a;
            case 'b':
                return b;
            case 'c':
                return c;
            case 'd':
                return d;
            case 'e':
                return e;
            case 'f':
                return f;
            case 'g':
                return g;
            case 'h':
                return h;
            case 'i':
                return i;
            case 'j':
                return j;
            default:
                return null;


        }

    }




}


