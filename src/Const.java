public class Const{
    // For Develope
    static boolean DEBUG = false;

    //Image Parameter
    static short IMAGE_HEIGHT = 37;
    static short IMAGE_WIDTH = 38;

    // Chess Color
    static short NO_CHESS = -1;
    static short BLACK_CHESS = 0;
    static short WHITE_CHESS = 1;

    // Rule Mode
    static short GO_CHESS = 0;
    static short GOMOKU_CHESS = 1;

    // Win Chess
    static short TIE = -1;
    static short NO_WIN = 0;
    static short BLACK_WIN = 1;
    static short WHITE_WIN = 2;

    //Way
    static short UP = 0;
    static short RIGHT = 1;
    static short DOWN = 2;
    static short LEFT = 3;

    //Check
    static short NOTHING = 0;
    static short SAME_COLOR = 1;
    static short DIFF_COLOR = 2;
    static short INIT = 3;

    //Stack
    static short NOITEM = -1;
    static short STARTPOS = -2;
    static short LOCX = 0;
    static short LOCY = 1;

    //Mapping
    static short CANGO = 1;
    static short CANNOTGO = 0;
    static short HASGONE = 1;
    static short HAVENGONE = 0;
    static short CANBEEAT = 1;
    static short CANNOTEAT = 0;
}