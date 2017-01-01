package game;

import engine.essential.*;
import engine.render.*;

/**
 * Created by Szymon Piechaczek on 18.12.2016.
 */
public class Main {

    public static void main(String[] args){
        System.out.println("Joł!");
        try {
            GameLogic logic = new GameLogic();
            EngineThread engine = new EngineThread("Flying Hamsters", 1366, 768, true, logic);
            engine.start();
        }
        catch (Exception e){
            System.out.println("A critical error has occured during engine execution. More info in stacktrace.");
            e.printStackTrace();
        }
    }
}
